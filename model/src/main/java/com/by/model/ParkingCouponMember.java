package com.by.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.by.json.CouponJson;

@Entity
@Table(name = "by_parking_coupon_member")
@IdClass(MemberCouponId.class)
public class ParkingCouponMember {
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coupon_id")
	private ParkingCoupon coupon;

	private int total;

	public ParkingCouponMember() {
	}

	public ParkingCouponMember(Member member, ParkingCoupon coupon) {
		this.member = member;
		this.coupon = coupon;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public ParkingCoupon getCoupon() {
		return coupon;
	}

	public void setCoupon(ParkingCoupon coupon) {
		this.coupon = coupon;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public CouponJson toCouponJson() {
		CouponJson j = new CouponJson();
		j.setId(getCoupon().getId());
		j.setTotal(getTotal());
		j.setName(getCoupon().getName());
		j.setCoverImg(getCoupon().getCoverImg());
		j.setType("p");
		return j;
	}

	public static List<ParkingCouponMember> findWhichToDelete(int total, List<ParkingCouponMember> pcm) {
		List<ParkingCouponMember> results = new ArrayList<>();
		int init = 0;
		for (ParkingCouponMember s : pcm) {
			if (init < total) {
				init += s.getTotal();
				results.add(s);
			} else {
				break;
			}
		}
		return results;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof ParkingCouponMember) {
			ParkingCouponMember that = (ParkingCouponMember) o;
			return this.member.getId().equals(that.member.getId()) && this.coupon.getId() == that.coupon.getId();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return member.hashCode() + coupon.hashCode();
	}

}
