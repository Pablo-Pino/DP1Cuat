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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.CustomerService;
import services.SocialProfileService;
import services.UserAccountService;
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

	@Autowired
	UserAccountService		userAccountService;


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

	//------------------Edit---------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int customerId) {
		ModelAndView result;
		Customer customer;

		customer = this.customerService.findOne(customerId);
		Assert.notNull(customer);
		result = this.createEditModelAndView(customer);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Customer customer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			for (final ObjectError error : binding.getAllErrors())
				System.out.println(error);
			result = this.createEditModelAndView(customer);
			result.addObject("message", "customer.commit.error");
		} else

			try {

				this.customerService.save(customer);
				result = new ModelAndView("redirect:display.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(customer, "customer.commit.error");
			}

		return result;
	}

	//---------------------------create------------------------------------------------------
	// Creation
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Customer customer;

		customer = this.customerService.create();

		result = new ModelAndView("customer/create");
		result.addObject("customer", customer);
		return result;
	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(@Valid final Customer customer, final BindingResult br) {
	//		ModelAndView result;
	//		Customer a = new Customer();
	//		try {
	//			a = (Customer) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
	//		} catch (final org.springframework.dao.DataIntegrityViolationException e) {
	//
	//		}
	//		if (a != null)
	//			try {
	//				//					final UserAccount uA = this.userAccountService.save(customer.getUserAccount());
	//				//					customer.setUserAccount(uA);
	//				this.customerService.save(customer);
	//				result = new ModelAndView("redirect:display.do");
	//			} catch (final Throwable ops) {
	//				result = new ModelAndView("customer/create");
	//				result.addObject("customer", customer);
	//				result.addObject("message", "customer.commit.error");
	//			}
	//		//			result = new ModelAndView("customer/create");
	//		//			result.addObject("customer", customer);
	//		//			result.addObject("message", "customer.commit.error");
	//		else {
	//			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
	//			customer.getUserAccount().setPassword(encoder.encodePassword(customer.getUserAccount().getPassword(), null));
	//			if (br.hasErrors()) {
	//				result = this.createEditModelAndView(customer);
	//				result.addObject("customer", customer);
	//			} else
	//				try {
	//					//					final UserAccount uA = this.userAccountService.save(customer.getUserAccount());
	//					//					customer.setUserAccount(uA);
	//					this.customerService.save(customer);
	//					result = new ModelAndView("redirect:display.do");
	//				} catch (final Throwable ops) {
	//					result = new ModelAndView("customer/create");
	//					result.addObject("customer", customer);
	//					result.addObject("message", "customer.commit.error");
	//				}
	//
	//		}
	//
	//		return result;
	//
	//	}

	//---------------------------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Customer customer) {
		ModelAndView result;

		result = this.createEditModelAndView(customer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Customer customer, final String messageCode) {
		final ModelAndView result;
		UserAccount userAccount = new UserAccount();
		userAccount = customer.getUserAccount();

		result = new ModelAndView("customer/edit");
		result.addObject("customer", customer);
		result.addObject("userAccount", userAccount);
		result.addObject("message", messageCode);

		return result;
	}

}
