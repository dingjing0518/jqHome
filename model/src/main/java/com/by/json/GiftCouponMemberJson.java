package com.by.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.by.model.GiftCouponMember;

/**
 * Created by yagamai on 16-3-3.
 */
public class GiftCouponMemberJson extends CouponMemberJson {
	private double amount;
	private String mobile;
	private String couponEndTime;

	public GiftCouponMemberJson(GiftCouponMember giftCouponMember) {
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.setName(giftCouponMember.getCoupon().getName());
		this.amount = giftCouponMember.getCoupon().getAmount();
		if (giftCouponMember.getCoupon().getCouponEndTime() != null)
			this.couponEndTime = simpleDateFormat.format(giftCouponMember.getCoupon().getCouponEndTime().getTime());
		this.setCode(giftCouponMember.getCode());
		this.setExchangedTime(simpleDateFormat.format(giftCouponMember.getExchangedTime().getTime()));
		if (giftCouponMember.getUsedTime() != null)
			this.setUsedTime(simpleDateFormat.format(giftCouponMember.getUsedTime().getTime()));
		this.mobile = giftCouponMember.getMember().getName();
		setCode(giftCouponMember.getCode());
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCouponEndTime() {
		return couponEndTime;
	}

	public void setCouponEndTime(String couponEndTime) {
		this.couponEndTime = couponEndTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
