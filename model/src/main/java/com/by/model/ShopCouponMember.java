package com.by.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.by.json.CouponJson;

/**
 * Created by yagamai on 15-12-8.
 */
@Entity
@Table(name = "by_shop_coupon_member")
public class ShopCouponMember {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coupon_id")
	private ShopCoupon coupon;

	private String code;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "exchanged_time")
	private Calendar exchangedTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "used_time")
	private Calendar usedTime;

	@ManyToOne
	@JoinColumn(name="trading_id")
	private Trading trading;

	public ShopCouponMember() {
	}

	public ShopCouponMember(Member member, ShopCoupon coupon) {
		this.member = member;
		this.coupon = coupon;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public ShopCoupon getCoupon() {
		return coupon;
	}

	public void setCoupon(ShopCoupon coupon) {
		this.coupon = coupon;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Calendar getExchangedTime() {
		return exchangedTime;
	}

	public void setExchangedTime(Calendar exchangedTime) {
		this.exchangedTime = exchangedTime;
	}

	public Calendar getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Calendar usedTime) {
		this.usedTime = usedTime;
	}

	public CouponJson toCouponJson() {
		CouponJson json = getCoupon().toCouponJson();
		json.setId(getCoupon().getId());
		json.setCode(getCode());
		return json;
	}

	public Trading getTrading() {
		return trading;
	}

	public void setTrading(Trading trading) {
		this.trading = trading;
	}

	@PrePersist
	private void prePersist() {
		this.exchangedTime = Calendar.getInstance();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ShopCouponMember that = (ShopCouponMember) o;

		return !(id != null ? !id.equals(that.id) : that.id != null);

	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
