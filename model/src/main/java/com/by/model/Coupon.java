package com.by.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.by.json.CouponJson;
import com.by.typeEnum.DuplicateEnum;
import com.by.typeEnum.ValidEnum;

@Entity
@Table(name = "by_coupon")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Coupon implements TimeRange {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;

	// 有效期开始
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "begin_time")
	private Calendar beginTime;

	// 有效期结束
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	private Calendar endTime;

	// 需要的积分
	private Integer score;

	// 生成的卡券的截止日期
	@Column(name = "coupon_end_time")
	private Calendar couponEndTime;

	// 对应的金额
	private Double amount;

	// 名称
	@NotEmpty(message = "{NotEmpty.coupon.name}")
	private String name;

	// 是否有效
	@Enumerated
	private ValidEnum valid;

	// 总数
	private Integer total;

	// 是否可重复
	private DuplicateEnum duplicate;

	// 封面图片
	@Column(name = "cover_img")
	private String coverImg;

	// 详情图片
	@Column(name = "content_img")
	private String contentImg;

	// 详情
	private String summary;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Calendar createdTime;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_time")
	private Calendar updatedTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Calendar getCouponEndTime() {
		return couponEndTime;
	}

	public void setCouponEndTime(Calendar couponEndTime) {
		this.couponEndTime = couponEndTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ValidEnum getValid() {
		return valid;
	}

	public DuplicateEnum getDuplicate() {
		return duplicate;
	}

	public void setDuplicate(DuplicateEnum duplicate) {
		this.duplicate = duplicate;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getContentImg() {
		return contentImg;
	}

	public void setContentImg(String contentImg) {
		this.contentImg = contentImg;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Calendar getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Calendar updatedTime) {
		this.updatedTime = updatedTime;
	}

	public boolean noStorageLimited() {
		return this.getTotal() == null || this.getTotal() == 0;
	}

	public CouponJson toCouponJson() {
		CouponJson json = new CouponJson(this);
		json.setType("s");
		return json;
	}

	public boolean isDuplicateCoupon() {
		return this.getDuplicate().equals(DuplicateEnum.ISDUPLICATE);
	}

	public boolean isPermanent() {
		return this.getBeginTime() == null && this.getEndTime() == null;
	}

	public boolean canUpdate(Coupon coupon) {
		Calendar today = Calendar.getInstance();
		return coupon.getBeginTime().after(today);
	}

	public boolean isEffective(Calendar today) {
		if (isPermanent())
			return true;
		else if (getBeginTime().before(today) && getEndTime().after(today))
			return true;
		return true;
	}

	public boolean isBeforeCouponEndTime(Calendar today) {
		if (this.getCouponEndTime() == null)
			return true;
		if (this.getCouponEndTime().after(today))
			return true;
		return false;
	}

	public boolean notEffective(Calendar today) {
		if (getEndTime() != null)
			return getEndTime().after(today);
		else
			return true;
	}

	@Transient
	public boolean isValid() {
		return this.getValid().equals(ValidEnum.VALID) ? true : false;
	}

	public void setValid(ValidEnum valid) {
		this.valid = valid;
	}

	@PrePersist
	protected void prePersist() {
		this.createdTime = Calendar.getInstance();
		if (this.valid == null)
			this.valid = ValidEnum.VALID;
		if (this.duplicate == null)
			this.duplicate = DuplicateEnum.NOTDUPLICATE;
		if (this.getEndTime() != null) {
			Calendar time = this.getEndTime();
			time.set(Calendar.HOUR, 23);
			time.set(Calendar.MINUTE, 59);
			time.set(Calendar.SECOND, 59);
		}
		if (this.getCouponEndTime() != null) {
			Calendar time = this.getCouponEndTime();
			time.set(Calendar.HOUR, 23);
			time.set(Calendar.MINUTE, 59);
			time.set(Calendar.SECOND, 59);
		}
		if (this.getCouponEndTime() != null && this.getCouponEndTime().before(this.getEndTime())) {
			this.setCouponEndTime(this.getEndTime());
		}
	}

	@PreUpdate
	protected void preUpdate() {
		this.updatedTime = Calendar.getInstance();
		if (this.getEndTime() != null) {
			Calendar time = this.getEndTime();
			time.set(Calendar.HOUR, 23);
			time.set(Calendar.MINUTE, 59);
			time.set(Calendar.SECOND, 59);
		}
		if (this.getCouponEndTime() != null) {
			Calendar time = this.getCouponEndTime();
			time.set(Calendar.HOUR, 23);
			time.set(Calendar.MINUTE, 59);
			time.set(Calendar.SECOND, 59);
		}
		if (this.getCouponEndTime() != null && this.getCouponEndTime().before(this.getEndTime())) {
			this.setCouponEndTime(this.getEndTime());
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
		Coupon other = (Coupon) obj;
		if (id != other.id)
			return false;
		return true;
	}
}