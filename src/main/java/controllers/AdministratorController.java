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
import services.CustomerService;
import services.FixupTaskService;
import services.HandyWorkerService;
import services.ReportService;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
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
	private FixupTaskService		fixupTaskService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private ReportService			reportService;


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

		final ModelAndView result = new ModelAndView("administrator/display");

		//----------------------------------

		final Double maxFixupTaskPerUser = this.actorService.fixupTasksStats().get("MAX");
		final Double minFixupTaskPerUser = this.actorService.fixupTasksStats().get("MIN");
		final Double avgFixupTaskPerUser = this.actorService.fixupTasksStats().get("AVG");
		final Double stdFixupTaskPerUser = this.actorService.fixupTasksStats().get("STD");

		final Double maxApplicationsPerFixupTask = this.fixupTaskService.appsStats().get("MAX");
		final Double minApplicationsPerFixupTask = this.fixupTaskService.appsStats().get("MIN");
		final Double avgApplicationsPerFixupTask = this.fixupTaskService.appsStats().get("AVG");
		final Double stdApplicationsPerFixupTask = this.fixupTaskService.appsStats().get("STD");

		final Double maxMaximumPriceOfFixupTasks = this.fixupTaskService.maxFixupStaskStats().get("MAX");
		final Double minMaximumPriceOfFixupTasks = this.fixupTaskService.maxFixupStaskStats().get("MIN");
		final Double avgMaximumPriceOfFixupTasks = this.fixupTaskService.maxFixupStaskStats().get("AVG");
		final Double stdMaximumPriceOfFixupTasks = this.fixupTaskService.maxFixupStaskStats().get("STD");

		final Double maxPriceOfApplications = this.applicationService.applicationPriceStats().get("MAX");
		final Double minPriceOfApplications = this.applicationService.applicationPriceStats().get("MIN");
		final Double avgPriceOfApplications = this.applicationService.applicationPriceStats().get("AVG");
		final Double stdPriceOfApplications = this.applicationService.applicationPriceStats().get("STD");

		final Double ratioPendingApplications = this.applicationService.pendingRatio().get("Ratio");
		final Double ratioAcceptedApplications = this.applicationService.acceptedRatio().get("Ratio");
		final Double ratioRejectedApplications = this.applicationService.appsRejectedRatio().get("Ratio");
		final Double ratioLateApplications = this.applicationService.lateApplicationsRatio();

		final Collection<Customer> customersMoreFixupTasksAverage = this.customerService.listCustomer10();
		final Collection<HandyWorker> handyWorkersMoreFixupTasksAverage = this.handyWorkerService.listHandyWorkerApplication();

		final Double maxComplaintsPerFixupTask = this.fixupTaskService.fixupComplaintsStats().get("MAX");
		final Double minComplaintsPerFixupTask = this.fixupTaskService.fixupComplaintsStats().get("MIN");
		final Double avgComplaintsPerFixupTask = this.fixupTaskService.fixupComplaintsStats().get("AVG");
		final Double stdComplaintsPerFixupTask = this.fixupTaskService.fixupComplaintsStats().get("STD");

		final Double maxNotesPerReport = this.reportService.refeeReportStats().get("MAX");
		final Double minNotesPerReport = this.reportService.refeeReportStats().get("MIN");
		final Double avgNotesPerReport = this.reportService.refeeReportStats().get("AVG");
		final Double stdNotesPerReport = this.reportService.refeeReportStats().get("STD");

		final Double ratioFixupTasksWithComplaints = this.fixupTaskService.getRatioFixupTasksWithComplaints().get("Ratio");

		final Collection<Customer> top3CustomersWithMoreComplaints = this.customerService.getTop3CustomerWithMoreComplaints();
		final Collection<HandyWorker> top3HandyWorkersWithMoreComplaints = this.handyWorkerService.getTop3HandyWorkerWithMoreComplaints();

		result.addObject("maxFixupTaskPerUser", maxFixupTaskPerUser);
		result.addObject("minFixupTaskPerUser", minFixupTaskPerUser);
		result.addObject("avgFixupTaskPerUser", avgFixupTaskPerUser);
		result.addObject("stdFixupTaskPerUser", stdFixupTaskPerUser);

		result.addObject("maxApplicationsPerFixupTask", maxApplicationsPerFixupTask);
		result.addObject("minApplicationsPerFixupTask", minApplicationsPerFixupTask);
		result.addObject("avgApplicationsPerFixupTask", avgApplicationsPerFixupTask);
		result.addObject("stdApplicationsPerFixupTask", stdApplicationsPerFixupTask);

		result.addObject("maxMaximumPriceOfFixupTasks", maxMaximumPriceOfFixupTasks);
		result.addObject("minMaximumPriceOfFixupTasks", minMaximumPriceOfFixupTasks);
		result.addObject("avgMaximumPriceOfFixupTasks", avgMaximumPriceOfFixupTasks);
		result.addObject("stdMaximumPriceOfFixupTasks", stdMaximumPriceOfFixupTasks);

		result.addObject("maxPriceOfApplications", maxPriceOfApplications);
		result.addObject("minPriceOfApplications", minPriceOfApplications);
		result.addObject("avgPriceOfApplications", avgPriceOfApplications);
		result.addObject("stdPriceOfApplications", stdPriceOfApplications);

		result.addObject("ratioPendingApplications", ratioPendingApplications);
		result.addObject("ratioAcceptedApplications", ratioAcceptedApplications);
		result.addObject("ratioRejectedApplications", ratioRejectedApplications);
		result.addObject("ratioLateApplications", ratioLateApplications);

		result.addObject("customersMoreFixupTasksAverage", customersMoreFixupTasksAverage);
		result.addObject("handyWorkersMoreFixupTasksAverage", handyWorkersMoreFixupTasksAverage);

		result.addObject("maxComplaintsPerFixupTask", maxComplaintsPerFixupTask);
		result.addObject("minComplaintsPerFixupTask", minComplaintsPerFixupTask);
		result.addObject("avgComplaintsPerFixupTask", avgComplaintsPerFixupTask);
		result.addObject("stdComplaintsPerFixupTask", stdComplaintsPerFixupTask);

		result.addObject("maxNotesPerReport", maxNotesPerReport);
		result.addObject("minNotesPerReport", minNotesPerReport);
		result.addObject("avgNotesPerReport", avgNotesPerReport);
		result.addObject("stdNotesPerReport", stdNotesPerReport);

		result.addObject("ratioFixupTasksWithComplaints", ratioFixupTasksWithComplaints);

		result.addObject("top3CustomersWithMoreComplaints", top3CustomersWithMoreComplaints);
		result.addObject("top3HandyWorkersWithMoreComplaints", top3HandyWorkersWithMoreComplaints);

		//---------------------------------

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
		Actor a;
		a = this.actorService.findOneByUserAccount(LoginService.getPrincipal());

		actors = this.actorService.findAllExceptMe(a);

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
