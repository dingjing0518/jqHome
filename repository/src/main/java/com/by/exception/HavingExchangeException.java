package com.by.exception;

/**
 * Created by yagamai on 15-11-30.
 */
public class HavingExchangeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public HavingExchangeException(){
		super("already exchanged");
	}
}
