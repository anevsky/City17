/**
 * 
 */
package com.alexnevsky.web.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the user.
 * 
 * @author Alex Nevsky
 * 
 */
@Controller
public class UserController {

	/**
	 * Simply selects the login view to render by returning its name.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		return "sections/auth/login";
	}

	/**
	 * Simply selects the signup view to render by returning its name.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/signup", method = RequestMethod.GET)
	public String signup(Locale locale, Model model) {
		return "sections/auth/signup";
	}

	/**
	 * Simply selects the remind password view to render by returning its name.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/remind-password", method = RequestMethod.GET)
	public String remindPassword(Locale locale, Model model) {
		return "sections/auth/remind-password";
	}

	/**
	 * Simply selects the logout view to render by returning its name.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	public String logout(Locale locale, Model model) {
		return "sections/auth/logout";
	}

	/**
	 * Process doLogin user's action and returns view to render.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/doLogin", method = RequestMethod.POST)
	public String doLogin(Locale locale, Model model) {
		return "home";
	}

}
