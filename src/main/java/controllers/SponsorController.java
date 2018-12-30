
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
import domain.Sponsor;

@Controller
@RequestMapping("sponsor/sponsorship")
public class SponsorController extends AbstractController {

	int					tripId	= 0;
	//Services
	@Autowired
	SponsorService		sponsorService;

	@Autowired
	SponsorshipService	sponsorshipService;


	@Autowired
	//Constructors (Debugueo)
	public SponsorController() {
		super();
	}

	//Actions

	//create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView res;
		Sponsor sponsor;

		this.tripId = tripId;
		sponsor = this.sponsorService.create();

		res = this.createEditModelAndView(sponsor);

		return res;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorId) {
		ModelAndView res;
		Sponsor sponsor;

		sponsor = this.sponsorService.findOne(sponsorId);
		Assert.notNull(sponsor);
		res = this.createEditModelAndView(sponsor);

		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsor);
		else
			try {
				final Sponsor aud = this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:display.do?sponsorId=" + aud.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsor, "sponsor.commit.error");
			}

		return result;
	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(@Valid final Sponsor sponsor, final BindingResult binding) {
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
	public ModelAndView display(@RequestParam final int sponsorId) {
		final ModelAndView result;
		Sponsor sponsor;

		sponsor = this.sponsorService.findOne(sponsorId);
		result = new ModelAndView("sponsor/display");
		result.addObject("sponsor", sponsor);

		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Sponsor> sponsors = new ArrayList<>();

		res = new ModelAndView("sponsor/list");
		sponsors = this.sponsorService.findAll(); //TODO probar con el servicio que da directamente los sponsors del sponsorship

		res.addObject("sponsors", sponsors);
		res.addObject("requestURI", "sponsor/list.do");

		return res;
	}

	//Ancillary methods

	private ModelAndView createEditModelAndView(final Sponsor sponsor) {
		final ModelAndView res;

		res = this.createEditModelAndView(sponsor, null);

		return res;

	}

	protected ModelAndView createEditModelAndView(final Sponsor sponsor, final String message) {
		ModelAndView result;

		result = new ModelAndView("sponsor/edit");
		result.addObject("sponsor", sponsor);

		return result;
	}

	//	private ModelAndView createEditModelAndView(final Sponsor sponsor, final String string) {
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
