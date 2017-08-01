package com.by.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value = "r")
public class RuleCategory extends Category {

	@OneToMany(mappedBy = "ruleCategory", fetch = FetchType.LAZY)
	private List<CardRule> rules;

	public RuleCategory() {
	}

	public RuleCategory(int id) {
		this.id = id;
	}

	public List<CardRule> getRules() {
		return rules;
	}

	public void setRules(List<CardRule> rules) {
		this.rules = rules;
	}
}
