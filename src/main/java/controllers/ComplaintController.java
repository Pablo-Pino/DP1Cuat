
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import domain.Complaint;

@Controller
@RequestMapping("/complaint")
public class ComplaintController extends AbstractController {

	//-----------------Services-------------------------
	@Autowired
	private ComplaintService	complaintService;


	//-----------------List----------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Complaint> res;

		res = this.complaintService.findAll();

		result = new ModelAndView("complaint/list");
		result.addObject("requestURI", "complaint/list.do");
		result.addObject("complaints", res);

		return result;
	}

	//-----------------Display-------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int complaintId) {
		ModelAndView result;
		Complaint c;
		c = this.complaintService.findOne(complaintId);
		result = new ModelAndView("complaint/display");
		result.addObject("complaint", c);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Complaint c;
		c = this.complaintService.create();

		result = this.createEditModelAndView(c);

		return result;

	}
	protected ModelAndView createEditModelAndView(final Complaint c) {
		ModelAndView result;

		result = this.createEditModelAndView(c, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Complaint complaint, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("complaint/create");
		result.addObject("complaint", complaint);
		result.addObject("message", messageCode);

		return result;
	}

}
