/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.CustomerService;
import services.SocialProfileService;
import domain.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}


	//-----------------Services-------------------------

	@Autowired
	CustomerService			customerService;

	@Autowired
	ActorService			actorService;

	@Autowired
	SocialProfileService	socialProfileService;


	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("customer/action-1");

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("customer/action-2");

		return result;
	}

	//-----------------Display-------------------------

	//display creado para mostrar al customer logueado
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Customer customer;

		customer = (Customer) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		result = new ModelAndView("customer/display");
		result.addObject("customer", customer);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Customer customer) {
		ModelAndView result;

		result = this.createEditModelAndView(customer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Customer customer, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("educationRecord/edit");
		result.addObject("customer", customer);
		result.addObject("message", messageCode);

		return result;
	}

}
