package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Referee;
import domain.Report;
import domain.Complaint;

import services.ActorService;
import services.ReportService;
import services.ComplaintService;

@Controller
@RequestMapping("report")
public class ReportController extends AbstractController {

	// Services
	
	@Autowired
	private ReportService reportService;
	@Autowired
	private ComplaintService complaintService;
	@Autowired
	private ActorService actorService;
		
	// List
	
	@RequestMapping("profile")
	public ModelAndView profile(@RequestParam(required = true) Integer reportId) {
		ModelAndView res = new ModelAndView("report/profile");
		Report report = this.reportService.findOne(reportId);
		res.addObject("report", report);
		res.addObject("requestURI", "report/profile.do");
		this.isPrincipalAuthorizedEdit(res, report);
		return res;
	}
	
	// Create
	
	@SuppressWarnings("unused")
	@RequestMapping("create")
	private ModelAndView create(@RequestParam(required = true) Integer complaintId) {
		Complaint complaint = this.complaintService.findOne(complaintId);
		Report report = this.reportService.create(complaint);
		ModelAndView res = this.createEditModelAndView(report);
		return res;
	}
	
	// Edit
	
	@SuppressWarnings("unused")
	@RequestMapping("edit")
	private ModelAndView edit(@RequestParam(required = true) Integer reportId) {
		Report report = this.reportService.findOne(reportId);
		Assert.notNull(report);
		ModelAndView res = this.createEditModelAndView(report);
		return res;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(@Valid Report report, BindingResult binding) {
		ModelAndView res = null;
		if(binding.hasErrors()) {
			this.createEditModelAndView(report);
		} else {
			try {
				this.reportService.save(report);
				res = new ModelAndView("redirect:list.do");
			} catch(Throwable t) {
				res = new ModelAndView("cannot.commit.error");
			}
		}
		return res;
	}
	
	// Delete
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	private ModelAndView delete(@Valid Report report, BindingResult binding) {
		ModelAndView res = null;
		if(binding.hasErrors()) {
			this.createEditModelAndView(report);
		} else {
			try {
				this.reportService.delete(report);
				res = new ModelAndView("redirect:list.do");
			} catch(Throwable t) {
				res = new ModelAndView("cannot.commit.error");
			}
		}
		return res;
	}
	
	// Ancillary methods
	
	private ModelAndView createEditModelAndView(Report report) {
		return createEditModelAndView(report, null);
	}
	
	private ModelAndView createEditModelAndView(Report report, String message) {
		ModelAndView res = new ModelAndView("report/edit");
		res.addObject("report", report);
		res.addObject("message", message);
		this.isPrincipalAuthorizedEdit(res, report);
		return res;
	}
	
	private void isPrincipalAuthorizedEdit(ModelAndView modelAndView, Report report) {
		Boolean res = false;
		Actor principal = this.actorService.findPrincipal();
		if(report.getDraft() && principal instanceof Referee)
			res = true;
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}
	
}
