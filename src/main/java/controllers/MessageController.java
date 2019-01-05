
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

import services.ActorService;
import services.FolderService;
import services.MessageService;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Controller
@RequestMapping("message/actor")
public class MessageController extends AbstractController {

	// Services

	@Autowired
	private MessageService	messageService;
	@Autowired
	private FolderService	folderService;
	@Autowired
	private ActorService	actorService;


	// List

	@RequestMapping("list")
	public ModelAndView list(@RequestParam(required = true) final Integer folderId) {
		final ModelAndView res = new ModelAndView("message/list");
		final Folder folder = this.folderService.findOne(folderId);
		final Collection<Message> messages = this.messageService.findByFolder(folder);
		res.addObject("messages", messages);
		System.out.println(messages);
		res.addObject("requestURI", "message/actor/list.do");
		this.isPrincipalAuthorizedEdit(res, folder);
		return res;
	}

	// Create

	@SuppressWarnings("unused")
	@RequestMapping("create")
	private ModelAndView create() {
		final Actor principal = this.actorService.findPrincipal();
		final Folder folder = this.folderService.findFolderByActorAndName(principal, "inBox");
		final Message message = this.messageService.create(folder);
		final ModelAndView res = this.createEditModelAndView(message);
		return res;
	}

	// Edit

	@SuppressWarnings("unused")
	@RequestMapping("edit")
	private ModelAndView edit(@RequestParam(required = true) final Integer messageId) {
		final Message message = this.messageService.findOne(messageId);
		Assert.notNull(message);
		final ModelAndView res = this.createEditModelAndView(message);
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(@Valid final Message message, final BindingResult binding) {
		ModelAndView res = null;
		if (binding.hasErrors())
			res = this.createEditModelAndView(message);
		else
			try {
				this.messageService.save(message);
				res = new ModelAndView("redirect:list.do?folderId=" + String.valueOf(message.getFolder().getId()));
			} catch (final Throwable t) {
				res = this.createEditModelAndView(message, "cannot.commit.error");
			}
		return res;
	}

	// Delete

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	private ModelAndView delete(final Message message, final BindingResult binding) {
		ModelAndView res = null;
		try {
			this.messageService.delete(message);
			res = new ModelAndView("redirect:/folder/actor/list.do");
		} catch (final Throwable t) {
			res = this.createEditModelAndView(message, "cannot.commit.error");
		}
		return res;
	}

	// Ancillary methods

	private ModelAndView createEditModelAndView(final Message messageObject) {
		return this.createEditModelAndView(messageObject, null);
	}

	private ModelAndView createEditModelAndView(final Message messageObject, final String message) {
		final ModelAndView res = new ModelAndView("message/edit");
		final Actor principal = this.actorService.findPrincipal();
		res.addObject("messageObject", messageObject);
		res.addObject("message", message);
		res.addObject("actors", this.actorService.findAll());
		res.addObject("folders", this.folderService.findAllByActor(principal));
		this.isPrincipalAuthorizedEdit(res, messageObject.getFolder());
		return res;
	}
	private void isPrincipalAuthorizedEdit(final ModelAndView modelAndView, final Folder folder) {
		Boolean res = false;
		final Actor principal = this.actorService.findPrincipal();
		if (folder == null)
			res = true;
		else if (folder.getActor().equals(principal))
			res = true;
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}

}
