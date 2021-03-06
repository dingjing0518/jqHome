package com.by.json;

import com.by.model.GiftCoupon;
import com.by.typeEnum.ValidEnum;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by yagamai on 16-1-4.
 */
public class GiftCouponJson {
	private int id;
	private String name;
	private Double amount;
	private Integer score;
	private String beginTime;
	private String endTime;
	private Integer total;
	private String couponEndTime;
	private String state;
	private String cssClass;

	public GiftCouponJson(GiftCoupon coupon) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar today = Calendar.getInstance();

		if (coupon.getBeginTime() == null && coupon.getEndTime() == null) {
			this.cssClass = "text-success";
			this.state = "使用中";
		} else if (coupon.getBeginTime() != null && coupon.getEndTime() != null) {
			if (coupon.getBeginTime().before(today) && coupon.getEndTime().after(today)) {
				this.cssClass = "text-success";
				this.state = "使用中";
			} else if (coupon.getBeginTime().after(today)) {
				this.cssClass = "text-primary";
				this.state = "未生效";
			} else if (coupon.getEndTime().before(today)) {
				this.cssClass = "text-muted";
				this.state = "已过期";
			}
			this.beginTime = format.format(coupon.getBeginTime().getTime());
			this.endTime = format.format(coupon.getEndTime().getTime());
		}
		if (coupon.getValid().equals(ValidEnum.INVALID)) {
			this.cssClass = "text-danger";
			this.state = "已关闭";
		}
		this.id = coupon.getId();
		this.name = coupon.getName();
		if (coupon.getCouponEndTime() != null)
			this.couponEndTime = format.format(coupon.getCouponEndTime().getTime());
		this.amount = coupon.getAmount();
		this.score = coupon.getScore();
		this.total = coupon.getTotal();
		// 未设置生效、失效日期列表显示长期有效
		if (coupon.getBeginTime() == null) {
			this.beginTime = "长期有效";
		}
		if (coupon.getEndTime() == null) {
			this.endTime = "长期有效";
		}
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getTotal() {
		return total;
	}

	public String getCouponEndTime() {
		return couponEndTime;
	}

	public void setCouponEndTime(String couponEndTime) {
		this.couponEndTime = couponEndTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
}
