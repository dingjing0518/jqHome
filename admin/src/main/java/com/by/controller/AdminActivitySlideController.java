package com.by.controller;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.by.form.ActivityForm;
import com.by.model.ActivitySlide;
import com.by.model.Brand;
import com.by.model.Menu;
import com.by.service.ActivitySildeService;

/**
 * Created by yagamai on 16-3-29.
 */
@Controller
@RequestMapping("/admin/slides/activity")
public class AdminActivitySlideController extends BaseController {
	private final String LIST = "admin/slide/activity/list";
	private final String CREATE = "admin/slide/activity/create";
	private final String EDIT = "admin/slide/activity/edit";
	private final String REDIRECT = "redirect:/admin/slides/activity/";

	private final Menu subMenu = new Menu(28);
	
	@Autowired
	private ActivitySildeService service;
	@Autowired
	private MessageSource messageSource;
	//获取活动视频列表
	@RequestMapping(method = RequestMethod.GET)
	public String list(ActivityForm form,Model uiModel) {
		List<ActivitySlide> activitySlides = service.findAll(form);
		uiModel.addAttribute("lists", activitySlides);
		uiModel.addAttribute("form", form);
		return LIST;
	}
	//新增一个活动视频页面
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String save(Model uiModel) {
		ActivitySlide detail = new ActivitySlide();
		uiModel.addAttribute("activitySlide", detail);
		return CREATE;
	}
	//新增一个活动视频
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(Model uiModel,  @ModelAttribute("activitySlide") ActivitySlide act,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("activitySlide", act);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		act.setStatus(0);
		ActivitySlide a = service.save(act);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + a.getId();
	}
	//修改活动视频
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") int id, Model uiModel, @ModelAttribute("activitySlide") ActivitySlide act, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("activitySlide", act);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		act.setId(id);
		act.setUpdatedBy(userContext.getCurrentUser().getName());
		act.setUpdatedTime(Calendar.getInstance());
		service.update(act);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + id;
	}
	//查看视频详情
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") int id, Model uiModel) {
		ActivitySlide act = service.findOne(id);
		uiModel.addAttribute("activitySlide", act);
		return EDIT;
	}
	//修改视频的状态值
		@RequestMapping(value="/status", method = RequestMethod.POST)
		@ResponseBody
		public String updataActivitityStatus(@RequestParam("data") String data,@RequestParam("id") String id){
			ActivitySlide	 act=service.findOne(new Integer(id));
			act.setStatus(new Integer(data));
			act.setUpdatedBy(userContext.getCurrentUser().getName());
			service.update(act);
			return "success";
			}
	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
