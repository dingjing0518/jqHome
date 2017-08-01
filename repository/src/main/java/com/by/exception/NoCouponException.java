package com.by.exception;

public class NoCouponException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoCouponException() {
		super("resource not found");
	}

}
