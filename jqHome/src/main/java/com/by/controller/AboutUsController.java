package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.by.model.AboutUs;
import com.by.service.AboutUsService;

/**
 * @author dingjing
 * 关于我们
 */
@Controller
@RequestMapping("/aboutus")
public class AboutUsController {
	private final String ABOUT = "aboutus/aboutus";
	@Autowired
	private AboutUsService aboutUsService;
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String aboutusIndex(Model uiModel){
		AboutUs aboutUs=aboutUsService.findAll().get(0);
		uiModel.addAttribute("aboutUs", aboutUs);
		return ABOUT;
	}
}
