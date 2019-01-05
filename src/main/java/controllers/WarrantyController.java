
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

import services.HandyWorkerService;
import services.WarrantyService;
import domain.HandyWorker;
import domain.Warranty;

@Controller
@RequestMapping("warranty")
public class WarrantyController extends AbstractController {

	//Services
	@Autowired
	HandyWorkerService	handyWorkerService;

	@Autowired
	WarrantyService		warrantyService;


	//create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int handyWorkerId) {
		ModelAndView res;
		Warranty profRec;
		profRec = this.warrantyService.create();

		res = this.createEditModelAndView(profRec);

		return res;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorId) {
		ModelAndView res;
		Warranty sponsor;

		sponsor = this.warrantyService.findOne(sponsorId);
		Assert.notNull(sponsor);
		res = this.createEditModelAndView(sponsor);

		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Warranty sponsor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsor);
		else
			try {
				final Warranty aud = this.warrantyService.save(sponsor);
				result = new ModelAndView("redirect:display.do?sponsorId=" + aud.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsor, "sponsor.commit.error");
			}

		return result;
	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(@Valid final Warranty sponsor, final BindingResult binding) {
	//		ModelAndView res;
	//
	//		if (binding.hasErrors())
	//			res = this.createEditModelAndView(sponsor);
	//		else
	//			try {
	//				this.handyWorkerService.save(sponsor);
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
		Warranty sponsor;

		sponsor = this.warrantyService.findOne(sponsorId);
		result = new ModelAndView("sponsor/display");
		result.addObject("sponsor", sponsor);

		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<HandyWorker> profRecs = new ArrayList<>();

		res = new ModelAndView("sponsor/list");
		profRecs = this.handyWorkerService.findAll(); //TODO probar con el servicio que da directamente los sponsors del warranty

		res.addObject("sponsors", profRecs);
		res.addObject("requestURI", "sponsor/warranty/list.do");

		return res;
	}

	//Ancillary methods

	private ModelAndView createEditModelAndView(final Warranty sponsor) {
		final ModelAndView res;

		res = this.createEditModelAndView(sponsor, null);

		return res;

	}

	protected ModelAndView createEditModelAndView(final Warranty sponsor, final String message) {
		ModelAndView result;

		result = new ModelAndView("sponsor/edit");
		result.addObject("sponsor", sponsor);

		return result;
	}

	//	private ModelAndView createEditModelAndView(final Warranty sponsor, final String string) {
	//		final Date date = new SimpleDateFormat("MM/dd/yyyy").getCalendar().getTime();
	//		ModelAndView res;
	//		final Warranty warranty;
	//		final Trip trip;
	//		Date momentOfCreate = date;
	//		String title;
	//		String description;
	//		Collection<URL> attachments;
	//
	//		if (sponsor.getWarranty() == null) {
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
