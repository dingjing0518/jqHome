package com.by.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.EventDetailsForm;
import com.by.json.EventDetailsJson;
import com.by.model.EventDetails;
import com.by.model.Menu;
import com.by.service.EventDetailsService;

@Controller
@RequestMapping("/admin/eventDetails")
public class AdminEventDetailsController  extends BaseController{
	private final Menu subMenu = new Menu(38);
	private final String LIST = "admin/eventDetails/list";
	private final String CREATE = "admin/eventDetails/create";
	private final String EDIT = "admin/eventDetails/edit";
	private final String REDIRECT = "redirect:/admin/eventDetails/";
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private EventDetailsService service;
	// 页数使用json
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Status list(EventDetailsForm form,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "eventTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<EventDetailsJson> json = service.findAll(form, pageable);
		return new Success<>(json);
	}
	// 处理修改
		@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
		public String edit(@PathVariable("id") int id, Model uiModel, @Valid EventDetails eventDetails, BindingResult result,
				RedirectAttributes redirectAttributes) {
			if (result.hasErrors()) {
				uiModel.addAttribute("eventDetails", eventDetails);
				uiModel.addAttribute("message", failMessage(messageSource));
				return EDIT;
			}
			eventDetails.setId(id);
			service.update(eventDetails);
			redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
			return REDIRECT + id;
		}
	// 获取修改
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") int id, Model uiModel) {
		EventDetails eventDetails = service.findOne(id);
		if (eventDetails == null)
			throw new NotFoundException();
		uiModel.addAttribute("eventDetails", eventDetails);
		return EDIT;
	}
	// 处理新增逻辑
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("eventDetails") EventDetails eventDetails, BindingResult result, Model uiModel,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("eventDetails", eventDetails);
			return CREATE;
		}
		EventDetails source = service.save(eventDetails);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + source.getId();
	}
	// 新增页
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String form(Model uiModel) {
		EventDetails eventDetails = new EventDetails();
		uiModel.addAttribute("eventDetails", eventDetails);
		return CREATE;
	}
	// 列表页
	@RequestMapping(method = RequestMethod.GET)
	public String list(EventDetailsForm form, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "beginTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<EventDetailsJson> lists = service.findAll(form, pageable);
		uiModel.addAttribute("lists", lists);
		uiModel.addAttribute("last", computeLastPage(lists.getTotalPages()));
		uiModel.addAttribute("form", form);
		return LIST;
	}
	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
