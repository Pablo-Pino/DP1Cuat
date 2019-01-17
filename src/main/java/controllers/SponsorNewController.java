/*
 * SponsorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.ActorService;
import services.SocialProfileService;
import services.SponsorService;
import services.UserAccountService;
import domain.Sponsor;

@Controller
@RequestMapping("/none/sponsor")
public class SponsorNewController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public SponsorNewController() {
		super();
	}


	//-----------------Services-------------------------

	@Autowired
	SponsorService			sponsorService;

	@Autowired
	ActorService			actorService;

	@Autowired
	SocialProfileService	socialProfileService;

	@Autowired
	UserAccountService		userAccountService;


	//display creado para mostrar al sponsor logueado
	//	@RequestMapping(value = "/display", method = RequestMethod.GET)
	//	public ModelAndView display() {
	//		ModelAndView result;
	//		Sponsor sponsor;
	//
	//		sponsor = (Sponsor) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
	//		result = new ModelAndView("none/sponsor/display");
	//		result.addObject("sponsor", sponsor);
	//
	//		return result;
	//	}

	//------------------Edit---------------------------------------------

	//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//	public ModelAndView edit(@RequestParam final int sponsorId) {
	//		ModelAndView result;
	//		Sponsor sponsor;
	//
	//		sponsor = this.sponsorService.findOne(sponsorId);
	//		Assert.notNull(sponsor);
	//		result = this.createEditModelAndView(sponsor);
	//
	//		return result;
	//
	//	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(@Valid final Sponsor sponsor, final BindingResult binding) {
	//		ModelAndView result;
	//
	//		if (binding.hasErrors())
	//			result = this.createEditModelAndView(sponsor);
	//		else
	//
	//			try {
	//				this.sponsorService.save(sponsor);
	//				result = new ModelAndView("redirect:display.do");
	//			} catch (final Throwable oops) {
	//				result = this.createEditModelAndView(sponsor, "sponsor.commit.error");
	//			}
	//
	//		return result;
	//	}

	//---------------------------create------------------------------------------------------
	// Creation
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Sponsor sponsor;

		sponsor = this.sponsorService.create();
		result = new ModelAndView("none/sponsor/create");
		result.addObject("sponsor", sponsor);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		sponsor.getUserAccount().setPassword(encoder.encodePassword(sponsor.getUserAccount().getPassword(), null));
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(sponsor);
			result.addObject("sponsor", sponsor);
			result.addObject("message", "sponsor.commit.error");
		} else
			try {
				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:/sponsor/display.do");
			} catch (final Throwable ops) {

				result = new ModelAndView("none/sponsor/create");
				result.addObject("sponsor", sponsor);
				result.addObject("message", "sponsor.commit.error");
			}

		return result;

	}

	//---------------------------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Sponsor sponsor) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsor sponsor, final String messageCode) {
		final ModelAndView result;
		UserAccount userAccount = new UserAccount();
		userAccount = sponsor.getUserAccount();

		result = new ModelAndView("none/sponsor/create");
		result.addObject("sponsor", sponsor);
		result.addObject("userAccount", userAccount);
		result.addObject("message", messageCode);

		return result;
	}

}
