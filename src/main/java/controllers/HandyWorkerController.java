
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

import services.ApplicationService;
import services.CurriculumService;
import services.FinderService;
import services.HandyWorkerService;
import services.TutorialService;
import services.WorkPlanService;
import domain.Application;
import domain.Curriculum;
import domain.Finder;
import domain.HandyWorker;
import domain.Tutorial;
import domain.WorkPlan;

@Controller
@RequestMapping("/handyworker")
public class HandyWorkerController extends AbstractController {

	@Autowired
	HandyWorkerService	handyWorkerService;

	@Autowired
	WorkPlanService		workPlanService;

	@Autowired
	ApplicationService	applicationService;

	@Autowired
	TutorialService		tutorialService;

	@Autowired
	FinderService		finderService;

	@Autowired
	CurriculumService	curriculumService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.create();
		result = this.createEditModelAndView(handyWorker);

		return result;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int handyWorkerId) {
		ModelAndView result;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findOne(handyWorkerId);
		Assert.notNull(handyWorker);
		result = this.createEditModelAndView(handyWorker);

		return result;
	}
	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final HandyWorker handyWorker, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(handyWorker);
		else
			try {
				this.handyWorkerService.save(handyWorker);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(handyWorker, "handyWorker.commit.error");
			}
		return result;
	}

	//delete

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final HandyWorker handyWorker, final BindingResult binding) {
		ModelAndView result;
		try {
			this.handyWorkerService.delete(handyWorker);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(handyWorker, "handyWorker.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final HandyWorker handyWorker) {
		ModelAndView result;

		result = this.createEditModelAndView(handyWorker, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final HandyWorker handyWorker, final String messageCode) {
		final ModelAndView result;
		Collection<Finder> finders;
		Collection<WorkPlan> workplans;
		final Collection<Curriculum> curriculums;
		Collection<Tutorial> tutorials;
		Collection<Application> applications;

		finders = this.finderService.findAll();
		curriculums = this.curriculumService.findAll();
		workplans = this.workPlanService.findAll();
		tutorials = this.tutorialService.findAll();
		applications = this.applicationService.findAll();

		result = new ModelAndView("handyWorker/edit");
		result.addObject("handyWorker", handyWorker);
		result.addObject("finders", finders);
		result.addObject("curriculums", curriculums);
		result.addObject("workplans", workplans);
		result.addObject("tutorials", tutorials);
		result.addObject("applications", applications);

		result.addObject("message", messageCode);

		return result;
	}
}
