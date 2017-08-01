package com.by.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminLoginController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Model uiModel) {
		return "admin/login";
	}

	@RequestMapping(value = "/login", params = "error", method = RequestMethod.GET)
	public String loginFail(Model uiModel) {
		uiModel.addAttribute("error", true);
		return "admin/login";
	}
}
