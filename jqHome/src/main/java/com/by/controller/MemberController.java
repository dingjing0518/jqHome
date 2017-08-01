package com.by.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.by.model.MemberIntro;
import com.by.model.Notify;
import com.by.service.MemberIntroService;
import com.by.service.NotifyService;

/**
 * @author dingjing
 *	会员
 */
@Controller
@RequestMapping("/vip")
public class MemberController {
	private final String VIP = "member/vip";
	@Autowired
	private MemberIntroService service;
	@Autowired
	private NotifyService notifyService;
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String memberIndex(Model uiModel) {
		MemberIntro vip = service.findAll().get(0);
		Notify notify=notifyService.findAll().get(0);
		uiModel.addAttribute("notify", notify);
		uiModel.addAttribute("vip", vip);
		return  VIP;
	}
}
