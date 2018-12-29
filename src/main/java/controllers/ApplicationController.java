
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import domain.Application;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	//-----------------Services-------------------------
	@Autowired
	private ApplicationService	applicationService;


	//-----------------List----------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Application> applications;

		applications = this.applicationService.findAll();

		result = new ModelAndView("application/list");
		result.addObject("requestURI", "application/list.do");
		result.addObject("applications", applications);

		return result;
	}

	//	//-----------------Display-------------------------
	//
	//	@RequestMapping(value = "/display", method = RequestMethod.GET)
	//	public ModelAndView display(@RequestParam final int categoryId) {
	//		ModelAndView result;
	//		Category c;
	//		c = this.categoryService.findOne(categoryId);
	//		result = new ModelAndView("category/display");
	//		result.addObject("category", c);
	//
	//		return result;
	//	}
	//
	//	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//	public ModelAndView create() {
	//		ModelAndView result;
	//		Category c;
	//		System.out.println("Pasa por aqui");
	//		c = this.categoryService.create();
	//		System.out.println("Pasa por aqui tb");
	//
	//		result = this.createEditModelAndView(c);
	//		System.out.println("Pasa por aqui tb tb");
	//
	//		return result;
	//
	//	}
	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//	public ModelAndView edit(@RequestParam final int categoryId) {
	//		ModelAndView result;
	//		Category c;
	//
	//		c = this.categoryService.findOne(categoryId);
	//		Assert.notNull(c);
	//		result = this.createEditModelAndView(c);
	//
	//		return result;
	//
	//	}
	//
	//	protected ModelAndView createEditModelAndView(final Category c) {
	//		ModelAndView result;
	//
	//		result = this.createEditModelAndView(c, null);
	//
	//		return result;
	//	}
	//
	//	protected ModelAndView createEditModelAndView(final Category category, final String messageCode) {
	//		final ModelAndView result;
	//
	//		result = new ModelAndView("category/edit");
	//		result.addObject("category", category);
	//		result.addObject("message", messageCode);
	//
	//		return result;
	//	}
	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(@Valid final Category category, final BindingResult binding) {
	//		ModelAndView result;
	//
	//		if (binding.hasErrors())
	//			result = this.createEditModelAndView(category);
	//		else
	//			try {
	//				this.categoryService.save(category);
	//				result = new ModelAndView("redirect:list.do");
	//			} catch (final Throwable oops) {
	//				result = this.createEditModelAndView(category, "category.commit.error");
	//			}
	//		return result;
	//	}
	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	//	public ModelAndView delete(final Category category, final BindingResult binding) {
	//		ModelAndView result;
	//		try {
	//			System.out.println("Pasa por el try");
	//			this.categoryService.delete(category);
	//			result = new ModelAndView("redirect:list.do");
	//		} catch (final Throwable oops) {
	//			System.out.println("Pasa por el catch");
	//			result = this.createEditModelAndView(category, "category.commit.error");
	//
	//		}
	//		return result;
	//	}
}
