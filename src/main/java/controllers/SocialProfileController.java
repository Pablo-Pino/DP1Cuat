
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SocialProfileService;
import domain.SocialProfile;

@Controller
@RequestMapping("socialprofile/actor")
public class SocialProfileController extends AbstractController {

	// Services

	@Autowired
	private SocialProfileService	socialProfileService;


	// List

	@RequestMapping("list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("socialprofile/list");
		final Collection<SocialProfile> socialProfiles = this.socialProfileService.list();
		res.addObject("socialProfiles", socialProfiles);
		res.addObject("requestURI", "socialprofile/actor/list.do");
		return res;
	}

	// Create

	@SuppressWarnings("unused")
	@RequestMapping("create")
	private ModelAndView create() {
		final SocialProfile socialProfile = this.socialProfileService.create();
		final ModelAndView res = this.createEditModelAndView(socialProfile);
		return res;
	}

	// Edit

	@SuppressWarnings("unused")
	@RequestMapping("edit")
	private ModelAndView edit(@RequestParam(required = true) final Integer socialProfileId) {
		final SocialProfile socialProfile = this.socialProfileService.findForEdit(socialProfileId);
		final ModelAndView res = this.createEditModelAndView(socialProfile);
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(@Valid final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView res = null;
		if (binding.hasErrors())
			res = this.createEditModelAndView(socialProfile);
		else
			try {
				this.socialProfileService.save(socialProfile);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable t) {
				res = this.createEditModelAndView(socialProfile, "cannot.commit.error");
			}
		return res;
	}

	// Delete

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	private ModelAndView delete(final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView res = null;
		try {
			this.socialProfileService.delete(socialProfile);
			res = new ModelAndView("redirect:list.do");
		} catch (final Throwable t) {
			res = this.createEditModelAndView(socialProfile, "cannot.commit.error");
		}
		return res;
	}

	// Ancillary methods

	private ModelAndView createEditModelAndView(final SocialProfile socialProfile) {
		return this.createEditModelAndView(socialProfile, null);
	}

	private ModelAndView createEditModelAndView(final SocialProfile socialProfile, final String message) {
		final ModelAndView res = new ModelAndView("socialprofile/edit");
		res.addObject("socialProfile", socialProfile);
		res.addObject("message", message);
		return res;
	}

}
