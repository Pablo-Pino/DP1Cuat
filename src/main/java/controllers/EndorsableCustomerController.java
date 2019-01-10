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

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import domain.Customer;
import domain.FixupTask;

@Controller
@RequestMapping("/endorsable")
public class EndorsableCustomerController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public EndorsableCustomerController() {
		super();
	}


	//-----------------Services-------------------------

	@Autowired
	CustomerService	customerService;


	//-----------------Display-------------------------

	//display creado para mostrar al customer desde handyworker
	@RequestMapping(value = "/customer/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int customerId) {
		ModelAndView result;
		Customer customer;
		Collection<FixupTask> fixupTasks = new ArrayList<>();

		customer = this.customerService.findOne(customerId);
		fixupTasks = customer.getFixupTasks();
		result = new ModelAndView("customer/display");
		result.addObject("customer", customer);
		result.addObject("fixupTasks", fixupTasks);

		return result;
	}

}
