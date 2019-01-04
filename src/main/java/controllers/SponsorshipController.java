
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

import services.SponsorService;
import services.SponsorshipService;
import domain.Sponsorship;

@Controller
@RequestMapping("sponsorship")
public class SponsorshipController extends AbstractController {

	//Services
	@Autowired
	SponsorService		sponsorService;

	@Autowired
	SponsorshipService	sponsorshipService;


	//create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.create();

		res = this.createEditModelAndView(sponsorship);

		return res;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorId) {
		ModelAndView res;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(sponsorId);
		Assert.notNull(sponsorship);
		res = this.createEditModelAndView(sponsorship);

		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsorship);
		else
			try {
				final Sponsorship aud = this.sponsorshipService.save(sponsorship);
				result = new ModelAndView("redirect:display.do?sponsorId=" + aud.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsorship, "sponsor.commit.error");
			}

		return result;
	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(@Valid final Sponsorship sponsor, final BindingResult binding) {
	//		ModelAndView res;
	//
	//		if (binding.hasErrors())
	//			res = this.createEditModelAndView(sponsor);
	//		else
	//			try {
	//				this.sponsorService.save(sponsor);
	//				res = new ModelAndView("redirect:list.do");
	//			} catch (final Throwable oops) {
	//				res = this.createEditModelAndView(sponsor, "sponsor.commit.error");
	//
	//			}
	//		return res;
	//	}
	//display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int sponsorshipId) {
		final ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		result = new ModelAndView("sponsorship/display");
		result.addObject("sponsorship", sponsorship);

		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Sponsorship> sponsorships = new ArrayList<>();

		res = new ModelAndView("sponsorship/list");
		sponsorships = this.sponsorshipService.findAll(); //TODO probar con el servicio que da directamente los sponsors del sponsorship

		res.addObject("sponsorships", sponsorships);
		res.addObject("requestURI", "sponsor/sponsorship/list.do");

		return res;
	}

	//Ancillary methods

	private ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		final ModelAndView res;

		res = this.createEditModelAndView(sponsorship, null);

		return res;

	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String message) {
		ModelAndView result;

		result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);

		return result;
	}

	//	private ModelAndView createEditModelAndView(final Sponsorship sponsor, final String string) {
	//		final Date date = new SimpleDateFormat("MM/dd/yyyy").getCalendar().getTime();
	//		ModelAndView res;
	//		final Sponsorship sponsorship;
	//		final Trip trip;
	//		Date momentOfCreate = date;
	//		String title;
	//		String description;
	//		Collection<URL> attachments;
	//
	//		if (sponsor.getSponsorship() == null) {
	//
	//			momentOfCreate = null;
	//			title = null;
	//			description = null;
	//			attachments = null;
	//
	//			res = new ModelAndView("sponsor/edit");
	//
	//		} else {
	//			momentOfCreate = sponsor.getMomentOfCreate();
	//			title = sponsor.getTitle();
	//			description = sponsor.getDescription();
	//			attachments = sponsor.getAttachments();
	//
	//			res = new ModelAndView("sponsor/edit");
	//		}
	//		res.addObject("sponsor", sponsor);

	//		res.addObject("momentOfCreate", momentOfCreate);
	//		res.addObject("title", title);
	//		res.addObject("description", description);
	//		res.addObject("attachments", attachments);
	//
	//		res.addObject("message", string);

	//		return res;
	//	}

}
