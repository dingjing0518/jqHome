package com.by.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.by.typeEnum.ValidEnum;

@Entity
@DiscriminatorValue("c")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class CardRule extends Rule {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "card_id")
	@NotNull(message = "{NotNull.cardRule.card}")
	private Card card;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@NotNull(message = "{NotNull.cardRule.ruleCategory}")
	private RuleCategory ruleCategory;

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public RuleCategory getRuleCategory() {
		return ruleCategory;
	}

	public void setRuleCategory(RuleCategory ruleCategory) {
		this.ruleCategory = ruleCategory;
	}
	
	public boolean isValidTradingRule() {
		int categoryId = this.getRuleCategory().getId();
		// 交易规则
		if (categoryId == 9) {
			Calendar today = Calendar.getInstance();
			if (this.getValid().equals(ValidEnum.INVALID))
				return false;
			if (this.getBeginTime() == null && this.getEndTime() == null)
				return true;
			return this.getBeginTime().before(today) && this.getEndTime().after(today);
		} else {
			return false;
		}

	}

}
