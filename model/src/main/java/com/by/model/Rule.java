package com.by.model;

import com.by.typeEnum.ValidEnum;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "by_rule")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public abstract class Rule implements TimeRange {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private Double rate;

	private String summary;

	@NotEmpty(message = "{NotEmpty.rule.name}")
	private String name;

	@Enumerated
	private ValidEnum valid;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar beginTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar endTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Calendar createdTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_time")
	private Calendar updatedTime;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	private Integer score;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public ValidEnum getValid() {
		return valid;
	}

	public void setValid(ValidEnum valid) {
		this.valid = valid;
	}

	public Calendar getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Calendar beginTime) {
		this.beginTime = beginTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	public Calendar getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Calendar updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public boolean isValidRule() {
		Calendar today = Calendar.getInstance();
		if (this.getValid().equals(ValidEnum.INVALID))
			return false;
		if (this.getBeginTime() == null && this.getEndTime() == null)
			return true;
		return this.getBeginTime().before(today) && this.getEndTime().after(today);
	}

	public boolean canUpdate() {
		Calendar today = Calendar.getInstance();
		return this.getValid().equals(ValidEnum.VALID) && this.getBeginTime().after(today)
				&& this.getEndTime().after(today);
	}
	
	@PrePersist
	protected void prePersist(){
		Calendar endTime = this.getEndTime();
		if (endTime != null) {
			endTime.set(Calendar.HOUR, 23);
			endTime.set(Calendar.MINUTE, 59);
			endTime.set(Calendar.SECOND, 59);
		}
		this.setValid(ValidEnum.VALID);
	}
	
	@PreUpdate
	protected void preUpdate(){
		Calendar endTime = this.getEndTime();
		if (endTime != null) {
			endTime.set(Calendar.HOUR, 23);
			endTime.set(Calendar.MINUTE, 59);
			endTime.set(Calendar.SECOND, 59);
		}
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
		Rule other = (Rule) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
