package com.by.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.CardJson;
import com.by.json.CardTemplateJson;
import com.by.json.RuleJson;
import com.by.model.Card;
import com.by.model.CardRule;
import com.by.model.Menu;
import com.by.model.RuleCategory;
import com.by.service.CardRuleService;
import com.by.service.CardService;
import com.by.service.MemberService;
import com.by.typeEnum.ValidEnum;

@Controller
@RequestMapping("/admin/cards")
public class AdminCardController extends BaseController {
	private final String CREATE = "admin/card/create";
	private final String REDIRECT = "redirect:/admin/cards/";
	private final String EDIT = "admin/card/edit";
	private final String LISTS = "admin/card/lists";
	private final RuleCategory SIGNUP_CATEGORY = new RuleCategory(10);
	private final RuleCategory TRADING_CATEGORY = new RuleCategory(9);
	private final Menu subMenu = new Menu(1);
	@Autowired
	private CardService service;
	@Autowired
	private MemberService memberService;
	@Autowired
	private CardRuleService cardRuleService;
	@Autowired
	@Qualifier("cardNameValidator")
	private Validator cardNameValidator;
	@Autowired
	@Qualifier("cardNameUniqueValidator")
	private Validator cardNameUniqueValidator;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		List<Card> validLists = service.findByValid(ValidEnum.VALID);
		List<Card> inValidLists = service.findByValid(ValidEnum.INVALID);
		
		//set members count of each card contains
		int cardId = 0;
		for (Card card : validLists) {
			cardId = card.getId();
			Long members = memberService.countByCard(new Card(cardId));
			card.setMembers(members);
		}
		
		for (Card card : inValidLists) {
			cardId = card.getId();
			Long members = memberService.countByCard(new Card(cardId));
			card.setMembers(members);
		}
		
		uiModel.addAttribute("valid", validLists);
		uiModel.addAttribute("inValid", inValidLists);
		return LISTS;
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public List<CardJson> list(@RequestParam("valid") String valid,
			@PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		ValidEnum validEnum = ValidEnum.fromString(valid);
		List<CardJson> cards = service.findByValid(validEnum, pageable).getContent().stream().map(CardJson::new)
				.collect(Collectors.toList());
		return cards;
	}

	@RequestMapping(value = "/{id}/json", method = RequestMethod.GET)
	@ResponseBody
	public Status get(@PathVariable("id") Integer id) {
		Card card = service.findOne(id);
		Long count = memberService.countByCard(new Card(id));
		List<CardRule> signUpRules = cardRuleService.findByRuleCategoryAndCard(SIGNUP_CATEGORY, card);
		List<CardRule> tradingRules = cardRuleService.findByRuleCategoryAndCard(TRADING_CATEGORY, card);
		CardTemplateJson json = new CardTemplateJson(card, count.intValue());
		json.setRegister(signUpRules.stream().map(r -> new RuleJson(r)).collect(Collectors.toList()));
		json.setTrading(tradingRules.stream().map(r -> new RuleJson(r)).collect(Collectors.toList()));
		return new Success<>(json);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getOne(@PathVariable("id") Integer id, Model uiModel) {
		Card card = service.findOne(id);
		uiModel.addAttribute("card", card);
		return EDIT;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") Integer id, Model uiModel, @Valid @ModelAttribute Card card,
			BindingResult result, RedirectAttributes redirectAttributes) {
		card.setId(id);
		cardNameValidator.validate(card, result);
		if (result.hasErrors()) {
			uiModel.addAttribute("card", card);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		card.setUpdatedBy(userContext.getCurrentUser().getUpdatedBy());
		Card source = service.update(card);
		service.refreshCache();
		return REDIRECT + source.getId();
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("card") Card card, BindingResult result, Model uiModel,
			RedirectAttributes redirectAttributes) {
		cardNameUniqueValidator.validate(card, result);
		if (result.hasErrors()) {
			uiModel.addAttribute("card", card);
			uiModel.addAttribute("message", failMessage(messageSource));
			return CREATE;
		}
		card.setCreatedBy(userContext.getCurrentUser().getCreatedBy());
		Card source = service.save(card);
		service.refreshCache();
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + source.getId();
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String save(Model uiModel) {
		uiModel.addAttribute("card", new Card());
		return CREATE;
	}

	@RequestMapping(value = "/name/duplicate", method = RequestMethod.GET)
	@ResponseBody
	public boolean nameDuplicate(@RequestParam("name") String name,
			@RequestParam(value = "id", required = false) Integer id) {
		if (id == null) {
			Long count = service.countByName(name);
			return count > 0 ? false : true;
		} else {
			Card card = service.findByName(name);
			if (card != null)
				return id.equals(card.getId()) ? true : false;
			return true;
		}
	}

	@RequestMapping(value = "/{id}/valid", method = RequestMethod.PUT)
	public String valid(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		Card card = new Card(id);
		service.validOrInValid(card);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + card.getId();
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
