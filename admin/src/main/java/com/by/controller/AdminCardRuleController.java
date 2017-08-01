package com.by.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.exception.Success;
import com.by.form.CouponQueryForm;
import com.by.json.RuleJson;
import com.by.model.Card;
import com.by.model.CardRule;
import com.by.model.Menu;
import com.by.model.Rule;
import com.by.model.RuleCategory;
import com.by.service.CardRuleCacheService;
import com.by.service.CardRuleService;
import com.by.service.CardService;
import com.by.service.RuleService;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-21.
 */
@Controller
@RequestMapping("/admin/cardRules")
public class AdminCardRuleController extends BaseController {
	private final Menu subMenu = new Menu(4);
	private final String CREATE = "admin/cardRules/create";
	private final String EDIT = "admin/cardRules/edit";
	private final String REDIRECT = "redirect:/admin/cardRules/";
	private final String LIST = "admin/cardRules/lists";
	@Autowired
	private CardRuleService service;
	@Autowired
	private CardService cardService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private RuleService ruleService;
	@Autowired
	private CardRuleCacheService ruleCacheService;
	@Value("${cache.cardRuleUrl}")
	private String cardRuleUrl;
	@Autowired
	@Qualifier("cardRuleTimeValidator")
	private Validator validator;
	private RestTemplate restTemplate=new RestTemplate();


	@ModelAttribute("cards")
	public List<Card> findValidCard() {
		return cardService.findAll().stream().filter(i -> i.getValid().equals(ValidEnum.VALID))
				.collect(Collectors.toList());
	}

	@ModelAttribute("allCards")
	public List<Card> findAllCard() {
		return cardService.findAll().stream().collect(Collectors.toList());
	}

	@ModelAttribute("category")
	public List<RuleCategory> allCategory() {
		List<RuleCategory> results = new ArrayList<>();
		RuleCategory register = new RuleCategory(10);
		register.setName("注册");
		RuleCategory trading = new RuleCategory(9);
		trading.setName("交易");
		results.add(register);
		results.add(trading);
		return results;
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String create(Model uiModel) {
		CardRule cardRule = new CardRule();
		uiModel.addAttribute("rule", cardRule);
		return CREATE;
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String form(@Valid @ModelAttribute("rule") CardRule rule, BindingResult result, Model uiModel,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("rule", rule);
			uiModel.addAttribute("message", failMessage(messageSource));
			return CREATE;
		}
		rule.setCreatedBy(userContext.getCurrentUser().getName());
		CardRule r = service.save(rule);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		refreshCache(r.getCard().getId());
		return REDIRECT + r.getId();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") int id, Model uiModel) {
		CardRule rule = service.findOne(id);
		uiModel.addAttribute("rule", rule);
		uiModel.addAttribute("statusMessage", getStatusMessage(rule));
		return EDIT;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") int id, @Valid @ModelAttribute("rule") CardRule rule, BindingResult result,
			Model uiModel, RedirectAttributes redirectAttributes) {
		rule.setId(id);
		validator.validate(rule, result);
		if (result.hasErrors()) {
			uiModel.addAttribute("rule", rule);
			rule.setValid(ValidEnum.VALID);
			uiModel.addAttribute("statusMessage", getStatusMessage(rule));
			return EDIT;
		}
		rule.setUpdatedBy(userContext.getCurrentUser().getName());
		CardRule r = service.update(rule);
		refreshCache(id);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		redirectAttributes.addFlashAttribute("statusMessage", getStatusMessage(r));
		return REDIRECT + r.getId();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(CouponQueryForm form, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE) Pageable pageable) {
		Page<RuleJson> lists = service.findAll(form, pageable);
		int pages = computeLastPage(lists.getTotalPages());
		uiModel.addAttribute("lists", lists);
		uiModel.addAttribute("last", pages);
		uiModel.addAttribute("form", form);
		return LIST;
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Success<Page<RuleJson>> list(CouponQueryForm form,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<RuleJson> lists = service.findAll(form, pageable);
		return new Success<>(lists);
	}

	@RequestMapping(value = "/{id}/valid", method = RequestMethod.PUT)
	public String valid(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
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
		restTemplate.put(cardRuleUrl + id, null);
		ruleCacheService.refreshCache(id);
	}
}
