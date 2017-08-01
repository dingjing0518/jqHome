package com.by.exception;

public class ShopKeyNotMatchException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ShopKeyNotMatchException(){
		super("poster key not match");
	}
}
