package com.by.exception;

/**
 * Created by yagamai on 15-12-1.
 */
public class TradingAlreadyBindException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TradingAlreadyBindException(){
		super("trading already bind");
	}
}
