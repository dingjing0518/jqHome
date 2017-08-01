package com.by.controller;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.json.MemberIntroJson;
import com.by.model.MemberIntro;
import com.by.model.Menu;
import com.by.model.User;
import com.by.service.MemberIntroService;

/**
 * Created by yagamai on 16-3-29.
 */
@Controller
@RequestMapping("/admin/memberintro")
public class AdminMemberIntroController extends BaseController {
	private final Menu subMenu = new Menu(32);
	private final String LIST = "admin/memberintro/lists";
	private final String CREATE = "admin/memberintro/create";
	private final String EDIT = "admin/memberintro/edit";
	private final String REDIRECT = "redirect:/admin/memberintro/";
	@Autowired
	private MemberIntroService service;
	@Autowired
	private MessageSource messageSource;
	private static Logger log = LoggerFactory.getLogger(AdminMemberIntroController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		List<MemberIntro> memberIntros = service.findAll();
		uiModel.addAttribute("lists", memberIntros);
		return LIST;
	}
	//
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public List<MemberIntroJson> listJson() {
		List<MemberIntro> memberIntros = service.findAll();
		List<MemberIntroJson> jsons = convertToJson(memberIntros);
		return jsons;
	}
	
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String save(Model uiModel) {
		MemberIntro memberIntro = new MemberIntro();
		uiModel.addAttribute("memberIntro", memberIntro);
		return CREATE;
	}
	
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(Model uiModel,  @ModelAttribute("memberIntro") MemberIntro memberIntro,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("memberIntro", memberIntro);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		User user = userContext.getCurrentUser();
		memberIntro.setCreatedBy(user.getName());
		memberIntro.setCreatedTime(Calendar.getInstance());
		MemberIntro a = service.save(memberIntro);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + a.getId();
	}
	//更新会员信息
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") int id, Model uiModel, 
			@ModelAttribute("memberIntro") MemberIntro memberIntro, BindingResult result, RedirectAttributes redirectAttributes) {
		memberIntro.setId(id);
		if (result.hasErrors()) {
			uiModel.addAttribute("memberIntro", memberIntro);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		memberIntro.setUpdatedBy(userContext.getCurrentUser().getName());
		memberIntro.setUpdatedTime(Calendar.getInstance());
		service.update(memberIntro);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + id;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") int id, Model uiModel) {
		MemberIntro memberIntro = service.findOne(id);
		uiModel.addAttribute("memberIntro", memberIntro);
		return EDIT;
	}

	private List<MemberIntroJson> convertToJson(List<MemberIntro> memberIntros) {
		return memberIntros.stream().map(i -> new MemberIntroJson(i)).collect(Collectors.toList());

	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
