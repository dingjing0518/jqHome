package com.by.exception;

public class CouponNotBelongToMemberException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CouponNotBelongToMemberException() {
		super("coupon not belong to member");
	}
}
