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

import java.util.ArrayList;
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

import security.LoginService;
import services.ActorService;
import services.AdministratorService;
import services.ApplicationService;
import services.HandyWorkerService;
import domain.Actor;
import domain.Administrator;
import domain.HandyWorker;

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

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private HandyWorkerService		handyWorkerService;


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
		final Double maxFixStats = null;
		Double ratioElapsed;
		Collection<HandyWorker> handyWorkerMostComplaints = new ArrayList<HandyWorker>();
		ratioElapsed = this.actorService.fixupTasksStats().get("MAX");
		ratioElapsed = this.applicationService.lateApplicationsRatio();
		handyWorkerMostComplaints = this.handyWorkerService.getTop3HandyWorkerWithMoreComplaints();
		//---------------------------------

		result = new ModelAndView("administrator/display");

		result.addObject("dash1", maxFixStats);
		result.addObject("dash16", ratioElapsed);
		result.addObject("dash29", handyWorkerMostComplaints);

		return result;

	}

	//-------------------------------Aqui acaba el dashboard----------------------

	//-----------------Display-------------------------

	//display creado para mostrar al customer logueado
	@RequestMapping(value = "/display2", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Administrator admin;

		admin = (Administrator) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", admin);

		return result;
	}

	//------------------Edit---------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int adminId) {
		ModelAndView result;
		Administrator admin;

		admin = this.administratorService.findOne(adminId);
		Assert.notNull(admin);
		result = this.createEditModelAndView(admin);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Administrator admin, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(admin);
		else
			try {
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:display2.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(admin, "administrator.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator admin) {
		ModelAndView result;

		result = this.createEditModelAndView(admin, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator admin, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", admin);
		result.addObject("message", messageCode);

		return result;
	}

	//-----------------List----------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listActors() {
		ModelAndView result;
		Collection<Actor> actors;

		actors = this.actorService.findAll();

		result = new ModelAndView("actor/list");
		result.addObject("requestURI", "administrator/list.do");
		result.addObject("actors", actors);

		return result;
	}

	//------------------BAN---------------------------------------------
	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//	public ModelAndView ban(@RequestParam final int actorId) {
	//		ModelAndView result;
	//		Assert.notNull(actorId);
	//		final Actor actor = this.actorService.findOne(actorId);
	//		final UserAccount ua = actor.getUserAccount();
	//		if (ua.getBanned() == true) {
	//			ua.setBanned(false);
	//			result = new ModelAndView("redirect:administrator/list.do");
	//
	//		}
	//		if (ua.getBanned() == false) {
	//			ua.setBanned(true);
	//			result = new ModelAndView("redirect:administrator/list.do");
	//
	//		}
	//
	//		result = this.createEditModelAndView2(actor);
	//
	//		return result;
	//
	//	}
	//	protected ModelAndView createEditModelAndView2(final Actor admin) {
	//		ModelAndView result;
	//
	//		result = this.createEditModelAndView2(admin, null);
	//
	//		return result;
	//	}
	//
	//	protected ModelAndView createEditModelAndView2(final Actor admin, final String messageCode) {
	//		final ModelAndView result;
	//
	//		result = new ModelAndView("administrator/edit");
	//		result.addObject("administrator", admin);
	//		result.addObject("message", messageCode);
	//
	//		return result;
	//	}
}
