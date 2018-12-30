
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
import services.CategoryService;
import services.ComplaintService;
import services.CustomerService;
import services.FixupTaskService;
import services.WarrantyService;
import services.WorkPlanService;
import domain.Application;
import domain.Category;
import domain.Complaint;
import domain.Customer;
import domain.FixupTask;
import domain.Warranty;
import domain.WorkPlan;

@Controller
@RequestMapping("/fixupTask/customer")
public class FixupTaskController extends AbstractController {

	@Autowired
	FixupTaskService	fixupTaskService;

	@Autowired
	CustomerService		customerService;

	@Autowired
	WarrantyService		warrantyService;

	@Autowired
	CategoryService		categoryService;

	@Autowired
	WorkPlanService		workPlanService;

	@Autowired
	ComplaintService	complaintService;

	@Autowired
	ApplicationService	applicationService;


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

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FixupTask fixupTask;

		fixupTask = this.fixupTaskService.create();
		result = this.createEditModelAndView(fixupTask);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int fixupTaskId) {
		ModelAndView result;
		FixupTask f;
		f = this.fixupTaskService.findOne(fixupTaskId);
		result = new ModelAndView("fixupTask/display");
		result.addObject("fixupTask", f);

		return result;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int fixupTaskId) {
		ModelAndView result;
		FixupTask fixupTask;

		fixupTask = this.fixupTaskService.findOne(fixupTaskId);
		Assert.notNull(fixupTask);
		result = this.createEditModelAndView(fixupTask);

		return result;
	}
	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FixupTask fixupTask, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(fixupTask);
		else
			try {
				this.fixupTaskService.save(fixupTask);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(fixupTask, "fixupTask.commit.error");
			}
		return result;
	}

	//delete

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final FixupTask fixupTask, final BindingResult binding) {
		ModelAndView result;
		try {
			this.fixupTaskService.delete(fixupTask);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(fixupTask, "fixupTask.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final FixupTask fixupTask) {
		ModelAndView result;

		result = this.createEditModelAndView(fixupTask, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FixupTask fixupTask, final String messageCode) {
		final ModelAndView result;
		Collection<Warranty> warranties;
		Collection<Category> categories;
		Collection<WorkPlan> workplans;
		Collection<Customer> customers;
		Collection<Complaint> complaints;
		Collection<Application> applications;

		warranties = this.warrantyService.findAll();
		categories = this.categoryService.findAll();
		workplans = this.workPlanService.findAll();
		customers = this.customerService.findAll();
		complaints = this.complaintService.findAll();
		applications = this.applicationService.findAll();

		result = new ModelAndView("fixupTask/edit");
		result.addObject("fixupTask", fixupTask);
		result.addObject("warranties", warranties);
		result.addObject("categories", categories);
		result.addObject("workplans", workplans);
		result.addObject("complaints", complaints);
		result.addObject("customers", customers);
		result.addObject("applications", applications);

		result.addObject("message", messageCode);

		return result;
	}

	//	<input type ="button"
	//		name="cancel"
	//		value="<spring:message code="fixupTask.edit.cancel"/>"
	//		onclick = "javascript:
	//				relativeRedir('fixupTask/customer/list.do');" />
}
