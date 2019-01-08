
package controllers;

import java.util.ArrayList;
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

import security.Authority;
import security.LoginService;
import services.ActorService;
import services.CurriculumService;
import services.EndorsementService;
import domain.Endorsable;
import domain.Endorsement;

@Controller
@RequestMapping("/endorsement")
public class EndorsementController extends AbstractController {

	//-----------------Services-------------------------
	@Autowired
	EndorsementService	endorsementService;

	@Autowired
	CurriculumService	curriculumService;

	@Autowired
	ActorService		actorService;


	//-------------------------- List ----------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Endorsement> endorsements;

		endorsements = this.endorsementService.findAllByActor(this.actorService.findOneByUserAccount(LoginService.getPrincipal()));

		result = new ModelAndView("endorsement/list");
		result.addObject("endorsements", endorsements);
		result.addObject("requestURI", "endorsement/list.do");

		return result;
	}

	//-----------------Display-------------------------

	//	@RequestMapping(value = "/display", method = RequestMethod.GET)
	//	public ModelAndView display(@RequestParam final int endorsementId) {
	//		ModelAndView result;
	//		Endorsement endorsement;
	//		endorsement = this.endorsementService.findOne(endorsementId);
	//		result = new ModelAndView("endorsement/display");
	//		result.addObject("endorsement", endorsement);
	//
	//		return result;
	//	}

	//------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Endorsement er;

		er = this.endorsementService.create();

		result = this.createEditModelAndView(er);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorsementId) {
		ModelAndView result;
		Endorsement er;
		final Collection<Endorsable> recievers = new ArrayList<>();

		er = this.endorsementService.findOne(endorsementId);
		Assert.notNull(er);

		final Authority hw = new Authority();
		hw.setAuthority(Authority.HANDYWORKER);

		final Authority c = new Authority();
		c.setAuthority(Authority.CUSTOMER);

		if (er.getSender().getUserAccount().getAuthorities().contains(hw)) {

		}

		result = this.createEditModelAndView(er);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Endorsement endorsement, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorsement);
		else
			try {
				this.endorsementService.save(endorsement);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorsement, "endorsement.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Endorsement endorsement, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("endorsement/edit");
		result.addObject("endorsement", endorsement);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Endorsement endorsement) {
		ModelAndView result;

		result = this.createEditModelAndView(endorsement, null);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Endorsement endorsement, final BindingResult binding) {
		ModelAndView result;
		try {
			this.endorsementService.delete(endorsement);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorsement, "endorsement.commit.error");

		}
		return result;
	}

}
