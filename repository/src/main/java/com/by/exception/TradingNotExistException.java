package com.by.exception;

/**
 * Created by yagamai on 15-12-1.
 */
public class TradingNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TradingNotExistException() {
		super("trading not found");
	}
}
