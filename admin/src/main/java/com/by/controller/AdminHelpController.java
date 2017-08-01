package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.model.MemberHelp;
import com.by.model.Menu;
import com.by.service.MemberHelperService;

@Controller
@RequestMapping(value = "/admin/helps")
public class AdminHelpController extends BaseController {
	private final Menu subMenu = new Menu(13);
	private final String EDIT = "admin/help/edit";
	private final String REDIRECT = "redirect:/admin/helps/";
	@Autowired
	private MemberHelperService service;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") int id, Model uiModel) {
		MemberHelp help = service.findOne(id);
		uiModel.addAttribute("help", help);
		return EDIT;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") int id, @ModelAttribute("help") MemberHelp help, BindingResult result,
			RedirectAttributes redirectAttributes, Model uiModel) {
		if (result.hasErrors()) {
			uiModel.addAttribute("help", help);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		help.setId(id);
		service.update(help);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + id;
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
