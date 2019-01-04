
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

}
