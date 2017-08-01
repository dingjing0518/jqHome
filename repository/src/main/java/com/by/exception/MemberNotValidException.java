package com.by.exception;

/**
 * Created by yagamai on 15-12-2.
 */
public class MemberNotValidException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MemberNotValidException() {
		super("member not valid");
	}
}
