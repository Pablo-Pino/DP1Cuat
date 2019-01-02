
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

import services.ActorService;
import services.SectionService;
import services.TutorialService;
import domain.Actor;
import domain.Section;
import domain.Tutorial;
import domain.Url;

@Controller
@RequestMapping("section")
public class SectionController extends AbstractController {

	// Services

	@Autowired
	private SectionService	sectionService;
	@Autowired
	private TutorialService	tutorialService;
	@Autowired
	private ActorService	actorService;


	// List

	@RequestMapping("actor/list")
	public ModelAndView list(@RequestParam(required = true) final Integer tutorialId) {
		final ModelAndView res = new ModelAndView("section/list");
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		final Collection<Section> sections = this.sectionService.findByTutorial(tutorial);
		res.addObject("sections", sections);
		res.addObject("tutorialId", tutorialId);
		res.addObject("requestURI", "section/actor/list.do");
		this.isPrincipalAuthorizedEdit(res, tutorialId);
		return res;
	}

	// Create

	@SuppressWarnings("unused")
	@RequestMapping("handyworker/create")
	private ModelAndView create(@RequestParam(required = true) final Integer tutorialId) {
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		final Section section = this.sectionService.create(tutorial);
		final ModelAndView res = this.createEditModelAndView(section);
		return res;
	}

	// Edit

	@SuppressWarnings("unused")
	@RequestMapping("handyworker/edit")
	private ModelAndView edit(@RequestParam(required = true) final Integer sectionId) {
		final Section section = this.sectionService.findOne(sectionId);
		Assert.notNull(section);
		final ModelAndView res = this.createEditModelAndView(section);
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "handyworker/edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(@Valid final Section section, final BindingResult binding) {
		ModelAndView res = null;
		if (binding.hasErrors())
			this.createEditModelAndView(section);
		else
			try {
				this.sectionService.save(section);
				res = new ModelAndView("redirect:");
			} catch (final Throwable t) {
				res = this.createEditModelAndView(section, "cannot.commit.error");
			}
		return res;
	}

	// Delete

	@SuppressWarnings("unused")
	@RequestMapping(value = "handyworker/edit", method = RequestMethod.POST, params = "delete")
	private ModelAndView delete(final Section section, final BindingResult binding) {
		ModelAndView res = null;
		try {
			this.sectionService.delete(section);
			res = new ModelAndView("redirect:actor/list.do?tutorialId=" + String.valueOf(section.getTutorial().getId()));
		} catch (final Throwable t) {
			res = this.createEditModelAndView(section, "cannot.commit.error");
		}
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "handyworker/edit", method = RequestMethod.POST, params = "addPicture")
	private ModelAndView addPicture(final Section section, final BindingResult binding) {
		ModelAndView res = null;
		try {
			final Url picture = new Url();
			picture.setUrl("");
			if (section.getPictures() == null)
				section.setPictures(new ArrayList<Url>());
			section.getPictures().add(picture);
			res = new ModelAndView("redirect:actor/list.do?tutorialId=" + String.valueOf(section.getTutorial().getId()));
		} catch (final Throwable t) {
			res = this.createEditModelAndView(section, "cannot.commit.error");
		}
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "handyworker/edit", method = RequestMethod.POST, params = "removePicture")
	private ModelAndView removePicture(final Section section, final BindingResult binding) {
		ModelAndView res = null;
		try {
			section.getPictures().remove(section.getPictures().size() - 1);
			res = this.createEditModelAndView(section);
		} catch (final Throwable t) {
			res = this.createEditModelAndView(section, "cannot.commit.error");
		}
		return res;
	}

	// Ancillary methods

	private ModelAndView createEditModelAndView(final Section section) {
		return this.createEditModelAndView(section, null);
	}

	private ModelAndView createEditModelAndView(final Section section, final String message) {
		final ModelAndView res = new ModelAndView("section/edit");
		res.addObject("section", section);
		res.addObject("message", message);
		this.isPrincipalAuthorizedEdit(res, section);
		return res;
	}

	private void isPrincipalAuthorizedEdit(final ModelAndView modelAndView, final Section section) {
		Boolean res = false;
		final Actor principal = this.actorService.findPrincipal();
		if (section.getTutorial().getHandyWorker().equals(principal))
			res = true;
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}

	private void isPrincipalAuthorizedEdit(final ModelAndView modelAndView, final Integer tutorialId) {
		Boolean res = false;
		final Actor principal = this.actorService.findPrincipal();
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		Assert.notNull(tutorial);
		if (tutorial.getHandyWorker().equals(principal))
			res = true;
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}

}
