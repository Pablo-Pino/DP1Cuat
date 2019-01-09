
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PhaseService;
import services.WorkPlanService;
import domain.Phase;
import domain.WorkPlan;

@Controller
@RequestMapping("phase/handyworker")
public class PhaseController extends AbstractController {

	//----------------Services------------------------
	@Autowired
	private PhaseService	phaseService;
	@Autowired
	private WorkPlanService	workPlanService;


	//-----------------List----------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int workplanId) {
		ModelAndView result;
		Collection<Phase> phases;
		WorkPlan w;
		w = this.workPlanService.findOne(workplanId);
		phases = w.getPhases();

		result = new ModelAndView("phase/list");
		result.addObject("requestURI", "phase/handyworker/list.do");
		result.addObject("phases", phases);

		return result;
	}

}
