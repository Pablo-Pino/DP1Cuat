
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

import antlr.debug.NewLineListener;

import services.HandyWorkerService;
import services.WarrantyService;
import domain.Warranty;

@Controller
@RequestMapping("warranty/administrator")
public class WarrantyController extends AbstractController {

	Boolean				draft	= true;
	//Services
	@Autowired
	HandyWorkerService	handyWorkerService;

	@Autowired
	WarrantyService		warrantyService;


	//create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Warranty warranty;
		warranty = this.warrantyService.create();
		this.draft = true;
		res = this.createEditModelAndView(warranty);

		return res;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int warrantyId) {
		ModelAndView res;
		Warranty warranty;

		warranty = this.warrantyService.findOne(warrantyId);
		Assert.notNull(warranty);
		this.draft = warranty.getDraft();
		res = this.createEditModelAndView(warranty);

		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Warranty warranty, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()){
			result = this.createEditModelAndView(warranty);}
		else
			try {if(this.draft==true){
				final Warranty aud = this.warrantyService.saveDraft(warranty);}
			else
				throw new Exception("cannot.commit.error");
				this.draft=false;
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(warranty, "cannot.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Warranty warranty, final BindingResult binding) {
		ModelAndView result;
		try {
			if (this.draft == true)
				this.warrantyService.deleteDraft(warranty);
			else
				this.warrantyService.delete(warranty);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(warranty, "warranty.commit.error");
		}
		return result;
	}
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(@Valid final Warranty warranty, final BindingResult binding) {
	//		ModelAndView res;
	//
	//		if (binding.hasErrors())
	//			res = this.createEditModelAndView(warranty);
	//		else
	//			try {
	//				this.handyWorkerService.save(warranty);
	//				res = new ModelAndView("redirect:list.do");
	//			} catch (final Throwable oops) {
	//				res = this.createEditModelAndView(warranty, "warranty.commit.error");
	//
	//			}
	//		return res;
	//	}
	//display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int warrantyId) {
		final ModelAndView result;
		Warranty warranty;

		warranty = this.warrantyService.findOne(warrantyId);
		result = new ModelAndView("warranty/display");
		result.addObject("warranty", warranty);

		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Warranty> warranties = new ArrayList<>();

		res = new ModelAndView("warranty/list");
		warranties = this.warrantyService.findAll(); //TODO probar con el servicio que da directamente los warranties del warranty

		res.addObject("warranties", warranties);
		res.addObject("requestURI", "warranty/administrator/list.do");

		return res;
	}

	//Ancillary methods

	private ModelAndView createEditModelAndView(final Warranty warranty) {
		final ModelAndView res;

		res = this.createEditModelAndView(warranty, null);

		return res;

	}

	protected ModelAndView createEditModelAndView(final Warranty warranty, final String message) {
		ModelAndView result;

		result = new ModelAndView("warranty/edit");
		result.addObject("warranty", warranty);

		return result;
	}

	//	private ModelAndView createEditModelAndView(final Warranty warranty, final String string) {
	//		final Date date = new SimpleDateFormat("MM/dd/yyyy").getCalendar().getTime();
	//		ModelAndView res;
	//		final Warranty warranty;
	//		final Trip trip;
	//		Date momentOfCreate = date;
	//		String title;
	//		String description;
	//		Collection<URL> attachments;
	//
	//		if (warranty.getWarranty() == null) {
	//
	//			momentOfCreate = null;
	//			title = null;
	//			description = null;
	//			attachments = null;
	//
	//			res = new ModelAndView("warranty/edit");
	//
	//		} else {
	//			momentOfCreate = warranty.getMomentOfCreate();
	//			title = warranty.getTitle();
	//			description = warranty.getDescription();
	//			attachments = warranty.getAttachments();
	//
	//			res = new ModelAndView("warranty/edit");
	//		}
	//		res.addObject("warranty", warranty);

	//		res.addObject("momentOfCreate", momentOfCreate);
	//		res.addObject("title", title);
	//		res.addObject("description", description);
	//		res.addObject("attachments", attachments);
	//
	//		res.addObject("message", string);

	//		return res;
	//	}

}
