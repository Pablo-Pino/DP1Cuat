
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.PersonalRecordService;
import domain.Curriculum;
import domain.PersonalRecord;

@Controller
@RequestMapping("/personalRecord/handyWorker")
public class PersonalRecordController extends AbstractController {

	@Autowired
	PersonalRecordService	personalRecordService;

	@Autowired
	CurriculumService		curriculumService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		PersonalRecord personalRecord;

		personalRecord = this.personalRecordService.create();
		result = this.createEditModelAndView(personalRecord);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int personalRecordId) {
		ModelAndView result;
		PersonalRecord pr;
		pr = this.personalRecordService.findOne(personalRecordId);
		result = new ModelAndView("personalRecord/display");
		result.addObject("personalRecord", pr);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(personalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord, final String messageCode) {
		final ModelAndView result;
		Collection<Curriculum> curriculums;

		curriculums = this.curriculumService.findAll();

		result = new ModelAndView("personalRecord/create");
		result.addObject("personalRecord", personalRecord);
		result.addObject("curriculums", curriculums);

		result.addObject("message", messageCode);

		return result;
	}
}
