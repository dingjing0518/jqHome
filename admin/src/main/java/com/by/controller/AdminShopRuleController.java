package com.by.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.exception.AdminNotFoundException;
import com.by.exception.Success;
import com.by.form.BaseCouponForm;
import com.by.json.RuleJson;
import com.by.model.Menu;
import com.by.model.Rule;
import com.by.model.Shop;
import com.by.model.ShopRule;
import com.by.service.RuleService;
import com.by.service.ShopRuleCacheService;
import com.by.service.ShopRuleService;
import com.by.service.ShopService;
import com.by.typeEnum.ValidEnum;

@Controller
@RequestMapping("/admin/shopRules")
public class AdminShopRuleController extends BaseController {
	private final Menu subMenu = new Menu(5);
	private final String LISTS = "admin/shopRules/lists";
	private final String EDIT = "admin/shopRules/edit";
	private final String CREATE = "admin/shopRules/create";
	private final String REDIRECT = "redirect:/admin/shopRules/";
	@Autowired
	private ShopRuleService service;
	@Autowired
	private ShopService shopService;
	@Autowired
	private RuleService ruleService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ShopRuleCacheService ruleCacheService;
	@Value("${cache.shopRuleUrl}")
	private String shopRuleUrl;
	private RestTemplate restTemplate = new RestTemplate();

	@ModelAttribute("shops")
	public List<Shop> shops() {
		List<Shop> shops = shopService.findAll(new Sort(Sort.Direction.ASC, "name"));
		return shops;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(BaseCouponForm form, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE) Pageable pageable) {
		Page<RuleJson> lists = service.findAll(form, pageable);
		uiModel.addAttribute("lists", lists);
		uiModel.addAttribute("last", computeLastPage(lists.getTotalPages()));
		uiModel.addAttribute("form", form);
		return LISTS;
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String form(Model uiModel) {
		uiModel.addAttribute("rule", new ShopRule());
		return CREATE;
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("rule") ShopRule rule, BindingResult result, Model uiModel) {
		if (result.hasErrors()) {
			uiModel.addAttribute("rule", rule);
			return CREATE;
		}
		rule.setCreatedBy(userContext.getCurrentUser().getName());
		ShopRule r = service.save(rule);
		r.getShops().forEach(i -> refreshCache(i.getId()));
		return REDIRECT + r.getId();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") int id, Model uiModel) {
		ShopRule rule = service.findOne(id);
		if (rule == null)
			throw new AdminNotFoundException();
		uiModel.addAttribute("rule", rule);
		uiModel.addAttribute("statusMessage", getStatusMessage(rule));
		return EDIT;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") int id, @Valid @ModelAttribute("rule") ShopRule rule, BindingResult result,
			Model uiModel, RedirectAttributes redirectAttributes) {
		rule.setId(id);
		if (result.hasErrors()) {
			uiModel.addAttribute("rule", rule);
			rule.setValid(ValidEnum.VALID);
			uiModel.addAttribute("statusMessage", getStatusMessage(rule));
			return EDIT;
		}
		rule.setUpdatedBy(userContext.getCurrentUser().getName());
		ShopRule r = service.update(rule);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		r.getShops().forEach(i -> refreshCache(i.getId()));
		return REDIRECT + r.getId() + "?edit";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Success<Page<RuleJson>> list(BaseCouponForm form,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE) Pageable pageable) {
		Page<RuleJson> lists = service.findAll(form, pageable);
		return new Success<>(lists);
	}

	@RequestMapping(value = "/{id}/valid", method = RequestMethod.PUT)
	public String Valid(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		service.valid(id);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		refreshCache(id);
		return REDIRECT + id;
	}

	@RequestMapping(value = "/name/duplicate", method = RequestMethod.GET)
	@ResponseBody
	public boolean nameDuplicate(@RequestParam("name") String name,
			@RequestParam(value = "id", required = false) Integer id) {
		if (id == null) {
			Long count = ruleService.countByName(name);
			return count > 0 ? false : true;
		} else {
			Rule coupon = ruleService.findByName(name);
			if (coupon != null)
				return id == coupon.getId() ? true : false;
			return true;
		}
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}

	private void refreshCache(int id) {
		restTemplate.put(shopRuleUrl + id, null);
		ruleCacheService.refreshCache(id);
	}
}
