package com.by.controller;

import java.util.Calendar;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.model.Clause;
import com.by.model.Menu;
import com.by.model.User;
import com.by.service.ClauseService;

/**
 * Created by yagamai on 16-3-29.
 */
@Controller
@RequestMapping("/admin/clause")
public class AdminClauseController extends BaseController {
	private final Menu subMenu = new Menu(32);
	private final String LIST = "admin/clause/lists";
	private final String CREATE = "admin/clause/create";
	private final String EDIT = "admin/clause/edit";
	private final String REDIRECT = "redirect:/admin/clause/";
	@Autowired
	private ClauseService service;
	@Autowired
	private MessageSource messageSource;
	private static Logger log = LoggerFactory.getLogger(AdminClauseController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		List<Clause> clauses = service.findAll();
		uiModel.addAttribute("lists", clauses);
		return LIST;
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String save(Model uiModel) {
		Clause clause = new Clause();
		uiModel.addAttribute("clause", clause);
		return CREATE;
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(Model uiModel,  @ModelAttribute("clause") Clause clause,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("clause", clause);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		User user = userContext.getCurrentUser();
		clause.setCreatedBy(user.getName());
		clause.setCreatedTime(Calendar.getInstance());
		Clause a = service.save(clause);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + a.getId();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") int id, Model uiModel, 
			@ModelAttribute("clause") Clause clause, BindingResult result, RedirectAttributes redirectAttributes) {
		clause.setId(id);
		if (result.hasErrors()) {
			uiModel.addAttribute("clause", clause);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		clause.setUpdatedBy(userContext.getCurrentUser().getName());
		clause.setUpdatedTime(Calendar.getInstance());
		service.update(clause);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + id;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") int id, Model uiModel) {
		Clause clause = service.findOne(id);
		uiModel.addAttribute("clause", clause);
		return EDIT;
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
