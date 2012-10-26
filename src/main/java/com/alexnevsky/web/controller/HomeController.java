/**
 * 
 */
package com.alexnevsky.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 * 
 * @author Alex Nevsky
 */
@Controller
public class HomeController {

	@RequestMapping("/")
	public String root() {
		return "redirect:/greeting";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "redirect:/news";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/greeting", method = RequestMethod.GET)
	public String greeting() {
		return "greeting";
	}

}
