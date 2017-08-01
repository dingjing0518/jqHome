package com.by.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.by.model.Menu;
import com.by.model.ShopCategory;
import com.by.service.ShopCategoryService;

@Controller
@RequestMapping("/admin/shopCategories")
public class AdminShopCategoryController extends BaseController {
	private final String LIST = "admin/shopCategory/list";
	private final String PARENT_DETAIL = "admin/shopCategory/parentDetail";
	private final String SUB_DETAIL = "admin/shopCategory/detail";
	private final String REDIRECT = "redirect:/admin/shopCategories/";
	private final String FORM = "admin/shopCategory/create";
	private final Menu menu = new Menu(24);
	@Autowired
	private ShopCategoryService service;
	@Autowired
	private MessageSource messageSource;

	// 获取所有第一级分类
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		List<ShopCategory> categories = service.findAllFirstCategory();
		uiModel.addAttribute("list", categories);
		return LIST;
	}

	// 添加一级分类表单
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String form(Model uiModel) {
		uiModel.addAttribute("category", new ShopCategory());
		return FORM;
	}

	// 添加一个一级分类
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String insert(Model uiModel, @Valid @ModelAttribute("category") ShopCategory category, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("category", category);
			uiModel.addAttribute("message", failMessage(messageSource));
			return FORM;
		}
		category.setCreatedBy(userContext.getCurrentUser().getName());
		ShopCategory shopCategory = service.save(category);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + shopCategory.getId();
	}

	// 第一级分类详情
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") int id, Model uiModel) {
		ShopCategory category = service.findOne(id);
		List<ShopCategory> categories = service.findCategoryByParent(new ShopCategory(id));
		uiModel.addAttribute("categories", categories);
		uiModel.addAttribute("category", category);
		return PARENT_DETAIL;
	}

	// 更新第一级分类
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String edit(@PathVariable("id") int id, Model uiModel,
			@Valid @ModelAttribute("category") ShopCategory category, BindingResult result,
			RedirectAttributes redirectAttributes) {
		category.setId(id);
		if (result.hasErrors()) {
			uiModel.addAttribute("category", category);
			List<ShopCategory> categories = service.findCategoryByParent(new ShopCategory(id));
			uiModel.addAttribute("categories", categories);
			uiModel.addAttribute("message", failMessage(messageSource));
			return PARENT_DETAIL;
		}
		category.setUpdatedBy(userContext.getCurrentUser().getName());
		ShopCategory shopCategory = service.update(category);
		uiModel.addAttribute("category", shopCategory);
		List<ShopCategory> categories = service.findCategoryByParent(new ShopCategory(id));
		uiModel.addAttribute("categories", categories);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + shopCategory.getId();
	}

	/*
	 * -------------------------------------一级分类
	 * end--------------------------------------------------
	 */

	// 第二及分类详情
	@RequestMapping(value = "/{parentId}/child/{id}", method = RequestMethod.GET)
	public String subDetail(@PathVariable("id") int id, Model uiModel) {
		ShopCategory sc = service.findOne(id);
		uiModel.addAttribute("category", sc);
		return SUB_DETAIL;
	}

	// 二级分类添加表单
	@RequestMapping(value = "/{parentId}", params = "form", method = RequestMethod.GET)
	public String subDetail(Model uiModel) {
		uiModel.addAttribute("category", new ShopCategory());
		return FORM;
	}

	// 添加第二及分类
	@RequestMapping(value = "/{parentId}", params = "form", method = RequestMethod.POST)
	public String add(Model uiModel, @PathVariable("parentId") int id,
			@Valid @ModelAttribute("category") ShopCategory category, BindingResult result) {
		if (result.hasErrors()) {
			uiModel.addAttribute("category", category);
			return SUB_DETAIL;
		}
		category.setParent(new ShopCategory(id));
		ShopCategory sc = service.save(category);
		return REDIRECT + sc.getParent().getId() + "/child/" + sc.getId();
	}

	@RequestMapping(value = "/{parentId}/child/{id}", method = RequestMethod.PUT)
	public String update(Model uiModel, @PathVariable("id") int id,
			@Valid @ModelAttribute("category") ShopCategory category, BindingResult result) {
		category.setId(id);
		if (result.hasErrors()) {
			uiModel.addAttribute("category", category);
			return SUB_DETAIL;
		}
		service.update(category);
		return REDIRECT + category.getParent().getId() + "/child/" + category.getId();
	}

	@RequestMapping(value = "/{id}/valid", method = RequestMethod.PUT)
	public String valid(@PathVariable("id") int id) {
		ShopCategory category = service.valid(id);
		if (category.getParent() == null)
			return REDIRECT + id;
		return REDIRECT + category.getParent().getId() + "/child/" + category.getId();
	}

	@Override
	public Menu getSubMenu() {
		return menu;
	}
}
