package com.by.exception;

/**
 * Created by yagamai on 15-11-30.
 */
public class CouponOutOfDateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponOutOfDateException() {
		super("coupon out of date");
	}
}
