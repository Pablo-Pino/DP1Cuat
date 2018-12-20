
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.FixupTaskService;
import domain.FixupTask;

@Controller
@RequestMapping("/fixupTask/customer")
public class FixupTaskController extends AbstractController {

	@Autowired
	FixupTaskService	fixupTaskService;
	CustomerService		customerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<FixupTask> fixupTasks;

		fixupTasks = this.fixupTaskService.findAll();

		result = new ModelAndView("fixupTask/list");
		result.addObject("fixupTasks", fixupTasks);
		result.addObject("requestURI", "fixupTask/customer/list.do");

		return result;
	}
}
