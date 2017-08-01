package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.by.model.Clause;
import com.by.model.MallIntro;
import com.by.service.ClauseService;
import com.by.service.MallIntroService;

@Controller
@RequestMapping("/mall")
public class MallController {
	private final String INDEX = "mall/index";
	@Autowired
	private MallIntroService service;
	@Autowired
	private ClauseService clauseService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String mallIndex(Model uiModel) {
		MallIntro mall = service.findAll().get(0);
		Clause clause = clauseService.findAll().get(0);
		uiModel.addAttribute("mall", mall);
		uiModel.addAttribute("clause", clause);	
		return INDEX;
	}
}
