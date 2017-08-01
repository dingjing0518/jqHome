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

import com.by.json.MallIntroJson;
import com.by.model.MallIntro;
import com.by.model.Menu;
import com.by.model.User;
import com.by.service.MallIntroService;

/**
 * Created by yagamai on 16-3-29.
 */
@Controller
@RequestMapping("/admin/mallintro")
public class AdminMallIntroController extends BaseController {
	private final Menu subMenu = new Menu(33);
	private final String LIST = "admin/mallintro/lists";
	private final String CREATE = "admin/mallintro/create";
	private final String EDIT = "admin/mallintro/edit";
	private final String REDIRECT = "redirect:/admin/mallintro/";
	@Autowired
	private MallIntroService service;
	@Autowired
	private MessageSource messageSource;
	private static Logger log = LoggerFactory.getLogger(AdminMallIntroController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		List<MallIntro> mallIntros = service.findAll();
		uiModel.addAttribute("lists", mallIntros);
		return LIST;
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public List<MallIntroJson> listJson() {
		List<MallIntro> mallIntros = service.findAll();
		List<MallIntroJson> jsons = convertToJson(mallIntros);
		return jsons;
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String save(Model uiModel) {
		MallIntro mallIntro = new MallIntro();
		uiModel.addAttribute("mallIntro", mallIntro);
		return CREATE;
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(Model uiModel,  @ModelAttribute("mallIntro") MallIntro mallIntro,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("mallIntro", mallIntro);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		User user = userContext.getCurrentUser();
		mallIntro.setCreatedBy(user.getName());
		mallIntro.setCreatedTime(Calendar.getInstance());
		MallIntro a = service.save(mallIntro);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + a.getId();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") int id, Model uiModel, 
			@ModelAttribute("mallIntro") MallIntro mallIntro, BindingResult result, RedirectAttributes redirectAttributes) {
		mallIntro.setId(id);
		if (result.hasErrors()) {
			uiModel.addAttribute("mallIntro", mallIntro);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		mallIntro.setUpdatedBy(userContext.getCurrentUser().getName());
		mallIntro.setUpdatedTime(Calendar.getInstance());
		service.update(mallIntro);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + id;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") int id, Model uiModel) {
		MallIntro mallIntro = service.findOne(id);
		uiModel.addAttribute("mallIntro", mallIntro);
		return EDIT;
	}

	private List<MallIntroJson> convertToJson(List<MallIntro> mallIntros) {
		return mallIntros.stream().map(i -> new MallIntroJson(i)).collect(Collectors.toList());

	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
