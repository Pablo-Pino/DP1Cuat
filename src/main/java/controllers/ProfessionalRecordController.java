
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.CurriculumService;
import services.ProfessionalRecordService;
import domain.Curriculum;
import domain.HandyWorker;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/professionalRecord/handyWorker")
public class ProfessionalRecordController extends AbstractController {

	@Autowired
	ProfessionalRecordService	professionalRecordService;

	@Autowired
	CurriculumService			curriculumService;

	@Autowired
	ActorService				actorService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ProfessionalRecord professionalRecord;
		final HandyWorker handyWorker = (HandyWorker) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		final Curriculum curriculum = this.curriculumService.findByHandyWorker(handyWorker);

		professionalRecord = this.professionalRecordService.create(curriculum);
		result = this.createEditModelAndView(professionalRecord);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int professionalRecordId) {
		ModelAndView result;
		ProfessionalRecord pr;
		pr = this.professionalRecordService.findOne(professionalRecordId);
		result = new ModelAndView("professionalRecord/display");
		result.addObject("professionalRecord", pr);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(professionalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord, final String messageCode) {
		final ModelAndView result;
		Collection<Curriculum> curriculums;

		curriculums = this.curriculumService.findAll();

		result = new ModelAndView("professionalRecord/create");
		result.addObject("professionalRecord", professionalRecord);
		result.addObject("curriculums", curriculums);

		result.addObject("message", messageCode);

		return result;
	}
}
