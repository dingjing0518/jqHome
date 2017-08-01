package com.by.exception;

public class MemberAlreadyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MemberAlreadyExistException() {
		super("resource not found");
	}
}
