package com.by.model;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("p")
public class ParkingCoupon extends Coupon {
	@OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY)
	private List<ParkingCouponMember> members;

	public ParkingCoupon() {
		super();
	}

	public ParkingCoupon(int id) {
		setId(id);
	}

	public static ParkingCoupon minScoreCoupon(List<ParkingCoupon> lists) {
		Calendar today = Calendar.getInstance();
		List<ParkingCoupon> results = lists.stream().filter(i -> i.isValid())
				.filter(i -> i.isEffective(today)).collect(Collectors.toList());
		if (results.size() == 0)
			return null;
		results.sort((r1, r2) -> {
			if (r1.getScore() > r2.getScore())
				return 1;
			return -1;
		});
		return results.get(0);
	}

	public List<ParkingCouponMember> getMembers() {
		return members;
	}

	public void setMembers(List<ParkingCouponMember> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "ParkingCoupon [id=" + id + "]";
	}
}
