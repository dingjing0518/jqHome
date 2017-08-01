package com.by.controller;
import java.util.List;
import java.util.stream.Collectors;
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
import com.by.json.AboutUsJson;
import com.by.model.AboutUs;
import com.by.model.Menu;
import com.by.service.AboutUsService;
@Controller
@RequestMapping("/admin/aboutus")
public class AdminAboutUsController extends BaseController{
	private final Menu subMenu = new Menu(36);
	private final String LIST = "admin/aboutus/lists";
	private final String CREATE = "admin/aboutus/create";
	private final String EDIT = "admin/aboutus/edit";
	private final String REDIRECT = "redirect:/admin/aboutus/";
	@Autowired
	private AboutUsService service;
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		List<AboutUs> aboutUss = service.findAll();
		uiModel.addAttribute("lists", aboutUss);
		return LIST;
	}
	
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public List<AboutUsJson> listJson() {
		List<AboutUs> aboutUss = service.findAll();
		List<AboutUsJson> jsons = convertToJson(aboutUss);
		return jsons;
	}
	
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String save(Model uiModel) {
		AboutUs aboutUs = new AboutUs();
		uiModel.addAttribute("aboutUs", aboutUs);
		return CREATE;
	}
	
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(Model uiModel,  @ModelAttribute("aboutUs") AboutUs aboutUs,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("aboutUs", aboutUs);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		AboutUs a = service.save(aboutUs);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + a.getId();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") int id, Model uiModel, 
			@ModelAttribute("aboutUs") AboutUs aboutUs, BindingResult result, RedirectAttributes redirectAttributes) {
		aboutUs.setId(id);
		if (result.hasErrors()) {
			uiModel.addAttribute("aboutUs", aboutUs);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		service.update(aboutUs);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + id;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") int id, Model uiModel) {
		AboutUs aboutUs = service.findOne(id);
		uiModel.addAttribute("aboutUs", aboutUs);
		return EDIT;
	}

	private List<AboutUsJson> convertToJson(List<AboutUs> aboutUss) {
		return aboutUss.stream().map(i -> new AboutUsJson(i)).collect(Collectors.toList());

	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}

}
