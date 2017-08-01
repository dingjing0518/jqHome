package com.by.controller;

import java.util.Calendar;
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
import com.by.json.ActivityJson;
import com.by.model.Activity;
import com.by.model.Menu;
import com.by.model.User;
import com.by.service.ActivityService;

/**
 * Created by yagamai on 16-3-29.
 */
@Controller
@RequestMapping("/admin/activity")
public class AdminActivityController extends BaseController {
	private final Menu subMenu = new Menu(37);
	private final String LIST = "admin/activity/lists";
	private final String CREATE = "admin/activity/create";
	private final String EDIT = "admin/activity/edit";
	private final String REDIRECT = "redirect:/admin/activity/";
	@Autowired
	private ActivityService service;
	@Autowired
	private MessageSource messageSource;
	//活动列表
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		List<Activity> activitys = service.findAll();
		uiModel.addAttribute("lists", activitys);
		return LIST;
	}
	
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public List<ActivityJson> listJson() {
		List<Activity> activitys = service.findAll();
		List<ActivityJson> jsons = convertToJson(activitys);
		return jsons;
	}
	//增加一个活动幻灯片页面
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String save(Model uiModel) {
		Activity activity = new Activity();
		uiModel.addAttribute("activity", activity);
		return CREATE;
	}
	
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(Model uiModel,  @ModelAttribute("activity") Activity activity,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("activity", activity);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		User user = userContext.getCurrentUser();
		activity.setCreatedBy(user.getName());
		activity.setCreatedTime(Calendar.getInstance());
		Activity a = service.save(activity);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + a.getId();
	}
	//
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") int id, Model uiModel, 
			@ModelAttribute("activity") Activity activity, BindingResult result, RedirectAttributes redirectAttributes) {
		activity.setId(id);
		if (result.hasErrors()) {
			uiModel.addAttribute("activity", activity);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		activity.setUpdatedBy(userContext.getCurrentUser().getName());
		activity.setUpdatedTime(Calendar.getInstance());
		service.update(activity);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + id;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") int id, Model uiModel) {
		Activity activity = service.findOne(id);
		uiModel.addAttribute("activity", activity);
		return EDIT;
	}

	private List<ActivityJson> convertToJson(List<Activity> activitys) {
		return activitys.stream().map(i -> new ActivityJson(i)).collect(Collectors.toList());

	}
	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
