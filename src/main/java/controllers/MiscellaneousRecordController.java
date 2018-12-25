
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.MiscellaneousRecordService;
import domain.Curriculum;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/miscellaneousRecord")
public class MiscellaneousRecordController extends AbstractController {

	@Autowired
	MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	CurriculumService			curriculumService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecordService.create();
		result = this.createEditModelAndView(miscellaneousRecord);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final String messageCode) {
		final ModelAndView result;
		Collection<Curriculum> curriculums;

		curriculums = this.curriculumService.findAll();

		result = new ModelAndView("miscellaneousRecord/create");
		result.addObject("miscellaneousRecord", miscellaneousRecord);
		result.addObject("curriculums", curriculums);

		result.addObject("message", messageCode);

		return result;
	}
}
