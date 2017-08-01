package com.by.exception;

public class NotEnoughScoreException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotEnoughScoreException() {
		super("not enough coupon");
	}

}
