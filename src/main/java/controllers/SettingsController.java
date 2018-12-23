package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Settings;

import services.SettingsService;

@Controller
@RequestMapping("settings")
public class SettingsController extends AbstractController {

	// Services
	
	@Autowired
	private SettingsService settingsService;
	
	// Edit
	
	@SuppressWarnings("unused")
	@RequestMapping("edit")
	private ModelAndView edit() {
		Settings settings = this.settingsService.findSettings();
		Assert.notNull(settings);
		ModelAndView res = this.createEditModelAndView(settings);
		return res;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(@Valid Settings settings, BindingResult binding) {
		ModelAndView res = null;
		if(binding.hasErrors()) {
			this.createEditModelAndView(settings);
		} else {
			try {
				this.settingsService.save(settings);
				res = new ModelAndView("redirect:list.do");
			} catch(Throwable t) {
				res = new ModelAndView("cannot.commit.error");
			}
		}
		return res;
	}
	
	// Ancillary methods
	
	private ModelAndView createEditModelAndView(Settings settings) {
		return createEditModelAndView(settings, null);
	}
	
	private ModelAndView createEditModelAndView(Settings settings, String message) {
		ModelAndView res = new ModelAndView("settings/edit");
		res.addObject("settings", settings);
		res.addObject("message", message);
		return res;
	}
	
}
