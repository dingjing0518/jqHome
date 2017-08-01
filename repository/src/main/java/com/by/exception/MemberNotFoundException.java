package com.by.exception;

public class MemberNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MemberNotFoundException() {
		super("member not found exception");
	}

}
