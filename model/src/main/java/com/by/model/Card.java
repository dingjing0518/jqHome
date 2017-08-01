package com.by.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import com.by.typeEnum.ValidEnum;

@Entity
@Table(name = "by_card")
@Audited
public class Card implements Serializable {
	private static final long serialVersionUID = 5923421594823016150L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty(message = "{NotEmpty.card.name}")
	private String name;

	@OneToMany(mappedBy = "card", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.DETACH })
	private List<CardRule> rules = new ArrayList<>();

	@Enumerated
	private ValidEnum valid;

	@Column(name = "init_score")
	@Min(value = 0, message = "{Min.card.initScore}")
	private Integer initScore;

	private String summary;

	@Column(name = "img_href")
	private String imgHref;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Calendar createdTime;

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_time")
	private Calendar updatedTime;

	@Column(name = "updated_by")
	private String updatedBy;

	//set member count of each card contains
	@Transient
	private Long members;
	
	public Long getMembers() {
		return members;
	}

	public void setMembers(Long members) {
		this.members = members;
	}

	public Card() {
	}

	public Card(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CardRule> getRules() {
		return rules;
	}

	public void setRules(List<CardRule> rules) {
		this.rules = rules;
	}

	public ValidEnum getValid() {
		return valid;
	}

	public void setValid(ValidEnum valid) {
		this.valid = valid;
	}

	public Integer getInitScore() {
		return initScore;
	}

	public void setInitScore(Integer initScore) {
		this.initScore = initScore;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Calendar getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Calendar updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImgHref() {
		return imgHref;
	}

	public void setImgHref(String imgHref) {
		this.imgHref = imgHref;
	}

	public void addRule(CardRule rule) {
		this.rules.add(rule);
	}

	public static double getMaxRate(List<? extends Rule> rules) {
		Calendar today = Calendar.getInstance();
		List<Double> scoreList = rules.stream().filter(i -> {
			if (i.getBeginTime() != null && i.getEndTime() != null)
				return i.getBeginTime().before(today) && i.getEndTime().after(today);
			return true;
		}).map(Rule::getRate).collect(Collectors.toList());
		if (scoreList.size() > 0)
			return Collections.max(scoreList);
		else
			return 0;
	}

	public static int getMaxScore(List<? extends Rule> rules) {
		Calendar today = Calendar.getInstance();
		List<Integer> scoreList = rules.stream().filter(i -> {
			if (i.getBeginTime() != null && i.getEndTime() != null)
				return i.getBeginTime().before(today) && i.getEndTime().after(today);
			return true;
		}).map(Rule::getScore).collect(Collectors.toList());
		if (scoreList.size() == 0)
			return 0;
		return Collections.max(scoreList);
	}

	@PrePersist
	protected void prePersist() {
		if (StringUtils.isEmpty(this.imgHref))
			this.imgHref = "/img/default_card.png";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
