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

import domain.Actor;
import domain.Section;
import domain.Tutorial;

import services.ActorService;
import services.SectionService;
import services.TutorialService;

@Controller
@RequestMapping("section")
public class SectionController extends AbstractController {

	// Services
	
	@Autowired
	private SectionService sectionService;
	@Autowired
	private TutorialService tutorialService;
	@Autowired
	private ActorService actorService;
		
	// List
	
	@RequestMapping("list")
	public ModelAndView list(@RequestParam(required = true) Integer tutorialId) {
		ModelAndView res = new ModelAndView("section/list");
		Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		Collection<Section> sections = this.sectionService.findByTutorial(tutorial);
		res.addObject("sections", sections);
		res.addObject("requestURI", "section/list.do");
		this.isPrincipalAuthorizedEdit(res, tutorialId);
		return res;
	}
	
	// Create
	
	@SuppressWarnings("unused")
	@RequestMapping("create")
	private ModelAndView create(@RequestParam(required = true) Integer tutorialId) {
		Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		Section section = this.sectionService.create(tutorial);
		ModelAndView res = this.createEditModelAndView(section);
		return res;
	}
	
	// Edit
	
	@SuppressWarnings("unused")
	@RequestMapping("edit")
	private ModelAndView edit(@RequestParam(required = true) Integer sectionId) {
		Section section = this.sectionService.findOne(sectionId);
		Assert.notNull(section);
		ModelAndView res = this.createEditModelAndView(section);
		return res;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(@Valid Section section, BindingResult binding) {
		ModelAndView res = null;
		if(binding.hasErrors()) {
			this.createEditModelAndView(section);
		} else {
			try {
				this.sectionService.save(section);
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
	private ModelAndView delete(@Valid Section section, BindingResult binding) {
		ModelAndView res = null;
		if(binding.hasErrors()) {
			this.createEditModelAndView(section);
		} else {
			try {
				this.sectionService.delete(section);
				res = new ModelAndView("redirect:list.do");
			} catch(Throwable t) {
				res = new ModelAndView("cannot.commit.error");
			}
		}
		return res;
	}
	
	// Ancillary methods
	
	private ModelAndView createEditModelAndView(Section section) {
		return createEditModelAndView(section, null);
	}
	
	private ModelAndView createEditModelAndView(Section section, String message) {
		ModelAndView res = new ModelAndView("section/edit");
		res.addObject("section", section);
		res.addObject("message", message);
		this.isPrincipalAuthorizedEdit(res, section);
		return res;
	}
	
	private void isPrincipalAuthorizedEdit(ModelAndView modelAndView, Section section) {
		Boolean res = false;
		Actor principal = this.actorService.findPrincipal();
		if(section.getTutorial().getHandyWorker().equals(principal)) {
			res = true;
		}
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}
	
	private void isPrincipalAuthorizedEdit(ModelAndView modelAndView, Integer tutorialId) {
		Boolean res = false;
		Actor principal = this.actorService.findPrincipal();
		Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		Assert.notNull(tutorial);
		if(tutorial.getHandyWorker().equals(principal)) {
			res = true;
		}
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}
	
}
