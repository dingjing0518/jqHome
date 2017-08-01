package com.by.exception;

public class OutOfStorageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutOfStorageException(){
		super("not enough coupon");
	}
}
