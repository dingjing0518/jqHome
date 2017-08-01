package com.by.exception;

/**
 * Created by yagamai on 15-11-26.
 */

public class NotValidException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotValidException(){
		super("not valid");
	}
}
