package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.by.model.Clause;
import com.by.service.ClauseService;

@Controller
@RequestMapping("/clause")
public class ClauseController {
	private final String INDEX = "clause/index";
	private final String REDIRECT = "redirect:/clause";
	@Autowired
	private ClauseService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String clauseIndex(Model uiModel) {
		Clause clause = service.findAll().get(0);
		uiModel.addAttribute("clause", clause);
		return INDEX;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String clauseIndex(@PathVariable("id") String id,Model uiModel) {
		Clause clause = service.findAll().get(0);
		uiModel.addAttribute("clause", clause);
		return REDIRECT+id;
	}
}
