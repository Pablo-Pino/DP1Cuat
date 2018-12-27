package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Folder;
import domain.Message;

import services.ActorService;
import services.FolderService;
import services.MessageService;

@Controller
@RequestMapping("message/actor")
public class MessageController extends AbstractController {

	// Services
	
	@Autowired
	private MessageService messageService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private ActorService actorService;
		
	// List
	
	@RequestMapping("list")
	public ModelAndView list(@RequestParam(required = true) Integer folderId) {
		ModelAndView res = new ModelAndView("message/list");
		Folder folder = this.folderService.findOne(folderId);
		Collection<Message> messages = this.messageService.findByFolder(folder);
		res.addObject("messages", messages);
		res.addObject("requestURI", "message/actor/list.do");
		this.isPrincipalAuthorizedEdit(res, folder);
		return res;
	}
	
	// Create
	
	@SuppressWarnings("unused")
	@RequestMapping("create")
	private ModelAndView create() {
		Actor principal = this.actorService.findPrincipal();
		Folder folder = this.folderService.findFolderByActorAndName(principal, "inBox");
		Message message = this.messageService.create(folder);
		ModelAndView res = this.createEditModelAndView(message);
		return res;
	}
	
	// Edit
	
	@SuppressWarnings("unused")
	@RequestMapping("edit")
	private ModelAndView edit(@RequestParam(required = true) Integer messageId) {
		Message message = this.messageService.findOne(messageId);
		Assert.notNull(message);
		ModelAndView res = this.createEditModelAndView(message);
		return res;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(@Valid Message message, BindingResult binding) {
		ModelAndView res = null;
		if(binding.hasErrors()) {
			this.createEditModelAndView(message);
		} else {
			try {
				this.messageService.save(message);
				res = new ModelAndView("redirect:list.do?folderId=" +  String.valueOf(message.getId()));
			} catch(Throwable t) {
				res = this.createEditModelAndView(message, "cannot.commit.error");
			}
		}
		return res;
	}
	
	// Delete
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	private ModelAndView delete(@Valid Message message, BindingResult binding) {
		ModelAndView res = null;
		if(binding.hasErrors()) {
			this.createEditModelAndView(message);
		} else {
			try {
				this.messageService.delete(message);
				res = new ModelAndView("redirect:list.do");
			} catch(Throwable t) {
				res = this.createEditModelAndView(message, "cannot.commit.error");
			}
		}
		return res;
	}
	
	// Ancillary methods
	
	private ModelAndView createEditModelAndView(Message messageObject) {
		return createEditModelAndView(messageObject, null);
	}
	
	private ModelAndView createEditModelAndView(Message messageObject, String message) {
		ModelAndView res = new ModelAndView("message/edit");
		res.addObject("messageObject", messageObject);
		res.addObject("message", message);
		res.addObject("actors", this.actorService.findAll());
		this.isPrincipalAuthorizedEdit(res, messageObject.getFolder());
		return res;
	}
	
	private void isPrincipalAuthorizedEdit(ModelAndView modelAndView, Folder folder) {
		Boolean res = false;
		Actor principal = this.actorService.findPrincipal();
		if(folder == null) 
			res = true;
		else if(folder.getActor().equals(principal))
			res = true;
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}
	
}
