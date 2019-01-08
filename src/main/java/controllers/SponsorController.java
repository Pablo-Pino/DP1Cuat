
package controllers;

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
import services.SponsorService;
import domain.Actor;
import domain.Administrator;
import domain.Sponsor;

@Controller
@RequestMapping("sponsor")
public class SponsorController extends AbstractController {

	// Services

	@Autowired
	private SponsorService	sponsorService;
	@Autowired
	private ActorService	actorService;


	// List

	@RequestMapping("/sponsor/profile")
	public ModelAndView profile(@RequestParam(required = true) final Integer sponsorId) {
		final ModelAndView res = new ModelAndView("sponsor/profile");
		final Sponsor sponsor = this.sponsorService.findOne(sponsorId);
		res.addObject("sponsor", sponsor);
		res.addObject("requestURI", "sponsor/profile.do");
		this.isPrincipalAuthorizedEdit(res, sponsor);
		return res;
	}

	// Create

	@SuppressWarnings("unused")
	@RequestMapping("administrator/create")
	private ModelAndView create() {
		final Sponsor sponsor = this.sponsorService.create();
		final ModelAndView res = this.createEditModelAndView(sponsor);
		return res;
	}

	// Edit

	@SuppressWarnings("unused")
	@RequestMapping("sponsor-administrator/edit")
	private ModelAndView edit(@RequestParam(required = true) final Integer sponsorId) {
		final Sponsor sponsor = this.sponsorService.findOne(sponsorId);
		Assert.notNull(sponsor);
		final ModelAndView res = this.createEditModelAndView(sponsor);
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "sponsor-administrator/edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(@Valid final Sponsor sponsor, final BindingResult binding) {
		ModelAndView res = null;
		if (binding.hasErrors())
			res = this.createEditModelAndView(sponsor);
		else
			//try {
			this.sponsorService.save(sponsor);
		res = new ModelAndView("redirect:/");
		//} catch (final Throwable t) {
		res = this.createEditModelAndView(sponsor, "cannot.commit.error");
		//}
		return res;
	}
	// Ancillary methods

	private ModelAndView createEditModelAndView(final Sponsor sponsor) {
		return this.createEditModelAndView(sponsor, null);
	}

	private ModelAndView createEditModelAndView(final Sponsor sponsor, final String message) {
		final ModelAndView res = new ModelAndView("sponsor/edit");
		res.addObject("sponsor", sponsor);
		res.addObject("message", message);
		this.isPrincipalAuthorizedEdit(res, sponsor);
		return res;
	}

	private void isPrincipalAuthorizedEdit(final ModelAndView modelAndView, final Sponsor sponsor) {
		Boolean res = false;
		final Actor principal = this.actorService.findPrincipal();
		if (sponsor.getId() == 0)
			if (principal instanceof Administrator)
				res = true;
			else if (principal.equals(sponsor))
				res = true;
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}

}
