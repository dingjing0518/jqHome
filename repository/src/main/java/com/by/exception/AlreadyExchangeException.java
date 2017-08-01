package com.by.exception;

/**
 * Created by yagamai on 15-12-4.
 */
public class AlreadyExchangeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AlreadyExchangeException() {
		super("coupon already exchange");
	}
}
