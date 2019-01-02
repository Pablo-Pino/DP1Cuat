
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
import services.EducationRecordService;
import domain.Curriculum;
import domain.EducationRecord;
import domain.HandyWorker;

@Controller
@RequestMapping("/educationRecord/handyWorker")
public class EducationRecordController extends AbstractController {

	//-----------------Services-------------------------
	@Autowired
	EducationRecordService	educationRecordService;

	@Autowired
	CurriculumService		curriculumService;

	@Autowired
	ActorService			actorService;


	//-------------------------- List ----------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<EducationRecord> educationRecords;
		Curriculum curriculum;
		HandyWorker handyWorker;

		handyWorker = (HandyWorker) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		curriculum = this.curriculumService.findByHandyWorker(handyWorker);
		educationRecords = this.educationRecordService.findAll(curriculum);

		result = new ModelAndView("educationRecord/list");
		result.addObject("educationRecords", educationRecords);
		result.addObject("requestURI", "educationRecord/handyWorker/list.do");

		return result;
	}

	//-----------------Display-------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int educationRecordId) {
		ModelAndView result;
		EducationRecord educationRecord;
		educationRecord = this.educationRecordService.findOne(educationRecordId);
		result = new ModelAndView("educationRecord/display");
		result.addObject("educationRecord", educationRecord);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final String messageCode) {
		final ModelAndView result;

		Curriculum curriculum;
		HandyWorker handyWorker;

		handyWorker = (HandyWorker) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		curriculum = this.curriculumService.findByHandyWorker(handyWorker);

		result = new ModelAndView("educationRecord/edit");
		result.addObject("educationRecord", educationRecord);
		result.addObject("curriculum", curriculum);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(educationRecord, null);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		EducationRecord educationRecord;

		educationRecord = this.educationRecordService.create();
		result = this.createEditModelAndView(educationRecord);
		return result;
	}

}
