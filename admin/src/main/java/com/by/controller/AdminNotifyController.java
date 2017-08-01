package com.by.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.NotifyJson;
import com.by.model.Brand;
import com.by.model.Menu;
import com.by.model.Notify;
import com.by.model.User;
import com.by.service.MenuService;
import com.by.service.NotifyService;
@Controller
@RequestMapping("/admin/notifys")
public class AdminNotifyController  extends BaseController {
	private final Menu subMenu = new Menu(35);
	@Autowired
	private MenuService menuService;
	@Autowired
	private NotifyService notifyService;
	@Autowired
	private MessageSource messageSource;
	@ModelAttribute("menus")
	public List<Menu> menus() {
		return menuService.findAll();
	}
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Status all() {
		return new Success<>(notifyService.toJson(notifyService.findAll(new Sort(Sort.Direction.ASC, "name"))));
	}
	//消息列表
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(required = false, value = "name") String name, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE) Pageable pageable) {
		Page<Notify> pages = notifyService.findAll(name, pageable);
		uiModel.addAttribute("lists", pages);
		uiModel.addAttribute("last", computeLastPage(pages.getTotalPages()));
		uiModel.addAttribute("name", name);
		return "admin/notify/list";
	}
	//消息列表Json
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Status list(@RequestParam(required = false, value = "name") String name,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<NotifyJson> pages = notifyService.findAllJson(name, pageable);
		return new Success<>(pages);
	}
	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
	//新增一个消息页面
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String form(Model uiModel) {
		Notify notify = new Notify();
		uiModel.addAttribute("notify", notify);
		return "admin/notify/create";
	}
	//增加一个页面
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("notify") Notify notify, BindingResult result, Model uiModel,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("notify", notify);
			uiModel.addAttribute("message", failMessage(messageSource));
			return "admin/notify/create";
		}
		Notify s = notifyService.save(notify);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return "redirect:/admin/notifys/" + s.getId();
	}
	//查看消息详情
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String view(Model uiModel, @PathVariable("id") int id) {
		Notify notify = notifyService.findOne(id);
		if (notify == null)
			throw new NotFoundException();
		uiModel.addAttribute("notify", notify);
		return "admin/notify/edit";
	}
	//修改消息
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String edit(@Valid @ModelAttribute("notify") Notify notify, BindingResult result, Model uiModel,
			@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("notify", notify);
			uiModel.addAttribute("message", failMessage(messageSource));
			return "admin/brand/edit";
		}
		notify.setId(id);
		notifyService.update(notify);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return "redirect:/admin/notifys/" + id;
	}
}
