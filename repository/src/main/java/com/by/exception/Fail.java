package com.by.exception;

import java.util.List;

import com.by.model.ReturnErrors;

//失败，附带失败信息
public class Fail extends Status {
	private List<ReturnErrors> errors;
	private String message;

	public Fail(String message) {
		super("fail");
		this.message = message;
		this.errors = null;
	}

	public Fail(List<ReturnErrors> errors) {
		super("fail");
		this.errors = errors;
	}

	public List<ReturnErrors> getErrors() {
		return errors;
	}

	public void setErrors(List<ReturnErrors> errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
