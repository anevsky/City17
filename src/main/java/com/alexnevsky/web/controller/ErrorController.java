/**
 * 
 */
package com.alexnevsky.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Alex Nevsky
 * 
 */
@Controller
public class ErrorController {

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String displayErrorPage(HttpServletRequest request, Model model) {
		String errorDetailsMessage = ":(";

		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

		model.addAttribute("errorCode", statusCode);
		model.addAttribute("errorDetailsMessage", errorDetailsMessage);

		return "error";
	}
}
