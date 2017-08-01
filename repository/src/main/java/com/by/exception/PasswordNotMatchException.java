package com.by.exception;

/**
 * Created by yagamai on 15-12-3.
 */
public class PasswordNotMatchException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public PasswordNotMatchException(){
		super("password not match");
	}
}
