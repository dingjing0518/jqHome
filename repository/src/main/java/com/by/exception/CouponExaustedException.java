package com.by.exception;

public class CouponExaustedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CouponExaustedException(){
		super("resource not found");
	}
}
