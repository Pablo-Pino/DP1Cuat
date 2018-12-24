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
import domain.Administrator;
import domain.Referee;
import domain.Complaint;

import services.ActorService;
import services.RefereeService;
import services.ComplaintService;

@Controller
@RequestMapping("referee")
public class RefereeController extends AbstractController {

	// Services
	
	@Autowired
	private RefereeService refereeService;
	@Autowired
	private ComplaintService complaintService;
	@Autowired
	private ActorService actorService;
		
	// List
	
	@RequestMapping("profile")
	public ModelAndView profile(@RequestParam(required = true) Integer refereeId) {
		ModelAndView res = new ModelAndView("referee/profile");
		Referee referee = this.refereeService.findOne(refereeId);
		res.addObject("referee", referee);
		res.addObject("requestURI", "referee/profile.do");
		this.isPrincipalAuthorizedEdit(res, referee);
		return res;
	}
	
	// Create
	
	@SuppressWarnings("unused")
	@RequestMapping("create")
	private ModelAndView create(@RequestParam(required = true) Integer complaintId) {
		Complaint complaint = this.complaintService.findOne(complaintId);
		Referee referee = this.refereeService.create();
		ModelAndView res = this.createEditModelAndView(referee);
		return res;
	}
	
	// Edit
	
	@SuppressWarnings("unused")
	@RequestMapping("edit")
	private ModelAndView edit(@RequestParam(required = true) Integer refereeId) {
		Referee referee = this.refereeService.findOne(refereeId);
		Assert.notNull(referee);
		ModelAndView res = this.createEditModelAndView(referee);
		return res;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(@Valid Referee referee, BindingResult binding) {
		ModelAndView res = null;
		if(binding.hasErrors()) {
			this.createEditModelAndView(referee);
		} else {
			try {
				this.refereeService.save(referee);
				res = new ModelAndView("redirect:list.do");
			} catch(Throwable t) {
				res = new ModelAndView("cannot.commit.error");
			}
		}
		return res;
	}
	
	// Ancillary methods
	
	private ModelAndView createEditModelAndView(Referee referee) {
		return createEditModelAndView(referee, null);
	}
	
	private ModelAndView createEditModelAndView(Referee referee, String message) {
		ModelAndView res = new ModelAndView("referee/edit");
		res.addObject("referee", referee);
		res.addObject("message", message);
		this.isPrincipalAuthorizedEdit(res, referee);
		return res;
	}
	
	private void isPrincipalAuthorizedEdit(ModelAndView modelAndView, Referee referee) {
		Boolean res = false;
		Actor principal = this.actorService.findPrincipal();
		if(referee.getId() == 0) 
			if(principal instanceof Administrator) 
				res = true;
		else 
			if(principal.equals(referee)) 
				res = true;
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}
	
}
