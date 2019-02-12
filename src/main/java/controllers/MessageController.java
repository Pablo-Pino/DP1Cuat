
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
import domain.Administrator;
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
		final Collection<Message> messages = this.messageService.listById(folderId);
		res.addObject("messages", messages);
		res.addObject("requestURI", "message/actor/list.do");
		return res;
	}

	// Create

	@SuppressWarnings("unused")
	@RequestMapping("create")
	private ModelAndView create(@RequestParam(required = false) final Boolean isBroadcast) {
		final Actor principal = this.actorService.findPrincipal();
		final Folder folder = this.folderService.findFolderByActorAndName(principal, "inBox");
		final Message message = this.messageService.create(folder);
		Boolean broadcast = false;
		if (isBroadcast != null)
			broadcast = isBroadcast;
		if (broadcast)
			Assert.isTrue(principal instanceof Administrator);
		final ModelAndView res = this.createEditModelAndView(message, broadcast);
		return res;
	}

	// Edit

	@SuppressWarnings("unused")
	@RequestMapping("edit")
	private ModelAndView edit(@RequestParam(required = true) final Integer messageId) {
		ModelAndView res = new ModelAndView("redirect:/folder/actor/list.do");
		try {
			final Message message = this.messageService.findForEdit(messageId);
			res = this.createEditModelAndView(message, false);

		} catch (final IllegalArgumentException e) {

		}
		return res;

	}
	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(@Valid final Message message, final BindingResult binding) {
		ModelAndView res = null;
		if (binding.hasErrors())
			res = this.createEditModelAndView(message, false);
		else
			//try {
			this.messageService.save(message);
		res = new ModelAndView("redirect:/folder/actor/list.do");
		//} catch (final Throwable ops) {
		res = this.createEditModelAndView(message, "cannot.commit.error", false);
		//}
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "broadcast")
	private ModelAndView broadcast(@Valid final Message message, final BindingResult binding) {
		ModelAndView res = null;
		if (binding.hasErrors())
			res = this.createEditModelAndView(message, true);
		else
			try {
				this.messageService.broadcast(message);
				res = new ModelAndView("redirect:/folder/actor/list.do");
			} catch (final Throwable t) {
				res = this.createEditModelAndView(message, "cannot.commit.error", true);
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
			res = this.createEditModelAndView(message, "cannot.commit.error", false);
		}
		return res;
	}

	// Ancillary methods

	private ModelAndView createEditModelAndView(final Message message, final Boolean isBroadcast) {
		return this.createEditModelAndView(message, null, isBroadcast);
	}

	private ModelAndView createEditModelAndView(final Message message, final String messageCode, Boolean isBroadcast) {
		final ModelAndView res = new ModelAndView("message/edit");
		final Actor principal = this.actorService.findPrincipal();
		res.addObject("message", message);
		res.addObject("messageCode", messageCode);
		res.addObject("actors", this.actorService.findAll());
		res.addObject("folders", this.folderService.findAllByActor(principal));
		if (!(this.actorService.findPrincipal() instanceof Administrator))
			isBroadcast = false;
		res.addObject("isBroadcast", isBroadcast);
		this.isPrincipalAuthorizedEdit(res, message.getFolder());
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
