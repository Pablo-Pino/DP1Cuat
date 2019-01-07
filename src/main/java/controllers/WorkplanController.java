
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
import services.WorkPlanService;
import domain.WorkPlan;

@Controller
@RequestMapping("workplan/handyWorker")
public class WorkplanController extends AbstractController {

	//Services
	@Autowired
	HandyWorkerService	handyWorkerService;

	@Autowired
	WorkPlanService		workplanService;


	//create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int handyWorkerId) {
		ModelAndView res;
		WorkPlan workplan;
		workplan = this.workplanService.create();

		res = this.createEditModelAndView(workplan);

		return res;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int workplanId) {
		ModelAndView res;
		WorkPlan workplan;

		workplan = this.workplanService.findOne(workplanId);
		Assert.notNull(workplan);
		res = this.createEditModelAndView(workplan);

		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final WorkPlan workplan, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(workplan);
		else
			try {
				final WorkPlan aud = this.workplanService.save(workplan);
				result = new ModelAndView("redirect:display.do?workplanId=" + aud.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(workplan, "workplan.commit.error");
			}

		return result;
	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(@Valid final WorkPlan workplan, final BindingResult binding) {
	//		ModelAndView res;
	//
	//		if (binding.hasErrors())
	//			res = this.createEditModelAndView(workplan);
	//		else
	//			try {
	//				this.handyWorkerService.save(workplan);
	//				res = new ModelAndView("redirect:list.do");
	//			} catch (final Throwable oops) {
	//				res = this.createEditModelAndView(workplan, "workplan.commit.error");
	//
	//			}
	//		return res;
	//	}
	//display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int workplanId) {
		final ModelAndView result;
		WorkPlan workplan;

		workplan = this.workplanService.findOne(workplanId);
		result = new ModelAndView("workplan/display");
		result.addObject("workplan", workplan);

		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<WorkPlan> workplans = new ArrayList<>();

		res = new ModelAndView("workplan/list");
		workplans = this.workplanService.findAll(); //TODO probar con el servicio que da directamente los workplans del workplan

		res.addObject("workplans", workplans);
		res.addObject("requestURI", "workplan/handyWorker/list.do");

		return res;
	}

	//Ancillary methods

	private ModelAndView createEditModelAndView(final WorkPlan workplan) {
		final ModelAndView res;

		res = this.createEditModelAndView(workplan, null);

		return res;

	}

	protected ModelAndView createEditModelAndView(final WorkPlan workplan, final String message) {
		ModelAndView result;

		result = new ModelAndView("workplan/edit");
		result.addObject("workplan", workplan);

		return result;
	}

	//	private ModelAndView createEditModelAndView(final WorkPlan workplan, final String string) {
	//		final Date date = new SimpleDateFormat("MM/dd/yyyy").getCalendar().getTime();
	//		ModelAndView res;
	//		final WorkPlan workplan;
	//		final Trip trip;
	//		Date momentOfCreate = date;
	//		String title;
	//		String description;
	//		Collection<URL> attachments;
	//
	//		if (workplan.getWorkPlan() == null) {
	//
	//			momentOfCreate = null;
	//			title = null;
	//			description = null;
	//			attachments = null;
	//
	//			res = new ModelAndView("workplan/edit");
	//
	//		} else {
	//			momentOfCreate = workplan.getMomentOfCreate();
	//			title = workplan.getTitle();
	//			description = workplan.getDescription();
	//			attachments = workplan.getAttachments();
	//
	//			res = new ModelAndView("workplan/edit");
	//		}
	//		res.addObject("workplan", workplan);

	//		res.addObject("momentOfCreate", momentOfCreate);
	//		res.addObject("title", title);
	//		res.addObject("description", description);
	//		res.addObject("attachments", attachments);
	//
	//		res.addObject("message", string);

	//		return res;
	//	}

}
