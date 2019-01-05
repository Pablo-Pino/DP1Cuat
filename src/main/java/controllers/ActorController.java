
package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/actor")
public class ActorController {

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
	//}
}
