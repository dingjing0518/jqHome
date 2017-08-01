package com.by.exception;

public class LicenseCannotBeNull extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LicenseCannotBeNull() {
		super("resource not found");
	}
}
