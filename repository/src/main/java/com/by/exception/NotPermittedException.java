package com.by.exception;

public class NotPermittedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotPermittedException() {
		super("not permitted");
	}

}
