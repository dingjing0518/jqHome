package com.by.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.by.model.ShopRule;
import com.by.service.ShopRuleService;

@Component
@Qualifier("shopRuleTimeValidator")
public class ShopRuleTimeValidator implements Validator {
	@Autowired
	private ShopRuleService service;

	@Override
	public boolean supports(Class<?> clazz) {
		return ShopRule.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ShopRule rule = (ShopRule) target;
		ShopRule r = service.findOne(rule.getId());
		if(!r.canUpdate())
			errors.rejectValue("beginTime", "Rule.canNotUpdated");
	}
}
