/*
 * AdministratorController.java
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

import services.ActorService;
import services.AdministratorService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}


	// Services ----------------------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-1");

		return result;
	}

	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-2");

		return result;
	}
	//---------------------------DashBoard-------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView result;
		//----------------------------------
		final Double maxFixStats;
		maxFixStats = this.actorService.fixupTasksStats().get("MAX");
		//---------------------------------

		result = new ModelAndView("administrator/display");

		result.addObject("dash1", maxFixStats);

		return result;

	}
}
