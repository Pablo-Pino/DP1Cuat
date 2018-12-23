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
import domain.SocialProfile;

import services.ActorService;
import services.SocialProfileService;

@Controller
@RequestMapping("socialprofile")
public class SocialProfileController extends AbstractController {

	// Services
	
	@Autowired
	private SocialProfileService socialProfileService;
	@Autowired
	private ActorService actorService;
	
	// List
	
	@RequestMapping("list")
	public ModelAndView list(@RequestParam(required=true) Integer actorId) {
		ModelAndView res = new ModelAndView("socialprofile/list");
		Actor actor;
		if(actorId == null) {
			actor = this.actorService.findPrincipal();
		} else {
			actor = this.actorService.findOne(actorId);
		}
		Collection<SocialProfile> socialProfiles = this.socialProfileService.findAllByActor(actor);
		res.addObject("socialProfiles", socialProfiles);
		res.addObject("requestURI", "socialProfile/list.do");
		return res;
	}
	
	// Create
	
	@SuppressWarnings("unused")
	@RequestMapping("create")
	private ModelAndView create() {
		Actor actor = this.actorService.findPrincipal();
		SocialProfile socialProfile = this.socialProfileService.create(actor);
		ModelAndView res = this.createEditModelAndView(socialProfile);
		return res;
	}
	
	// Edit
	
	@SuppressWarnings("unused")
	@RequestMapping("edit")
	private ModelAndView edit(@RequestParam(required = true) Integer socialProfileId) {
		SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);
		Assert.notNull(socialProfile);
		ModelAndView res = this.createEditModelAndView(socialProfile);
		return res;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(@Valid SocialProfile socialProfile, BindingResult binding) {
		ModelAndView res = null;
		if(binding.hasErrors()) {
			this.createEditModelAndView(socialProfile);
		} else {
			try {
				this.socialProfileService.save(socialProfile);
				res = new ModelAndView("redirect:list.do");
			} catch(Throwable t) {
				res = new ModelAndView("cannot.commit.error");
			}
		}
		return res;
	}
	
	// Delete
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	private ModelAndView delete(@Valid SocialProfile socialProfile, BindingResult binding) {
		ModelAndView res = null;
		if(binding.hasErrors()) {
			this.createEditModelAndView(socialProfile);
		} else {
			try {
				this.socialProfileService.delete(socialProfile);
				res = new ModelAndView("redirect:list.do");
			} catch(Throwable t) {
				res = new ModelAndView("cannot.commit.error");
			}
		}
		return res;
	}
	
	// Ancillary methods
	
	private ModelAndView createEditModelAndView(SocialProfile socialProfile) {
		return createEditModelAndView(socialProfile, null);
	}
	
	private ModelAndView createEditModelAndView(SocialProfile socialProfile, String message) {
		ModelAndView res = new ModelAndView("socialProfile/edit");
		res.addObject("socialProfile", socialProfile);
		res.addObject("message", message);
		return res;
	}
	
}
