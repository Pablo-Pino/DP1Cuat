
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.EducationRecordService;
import domain.Curriculum;
import domain.EducationRecord;

@Controller
@RequestMapping("educationaRecord/handyWorker")
public class EducationRecordController extends AbstractController {

	@Autowired
	EducationRecordService	educationRecordService;

	@Autowired
	CurriculumService		curriculumService;


	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final String messageCode) {
		final ModelAndView result;
		Collection<Curriculum> curriculums;

		curriculums = this.curriculumService.findAll();

		result = new ModelAndView("educationRecord/create");
		result.addObject("educationRecord", educationRecord);
		result.addObject("curriculums", curriculums);

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
