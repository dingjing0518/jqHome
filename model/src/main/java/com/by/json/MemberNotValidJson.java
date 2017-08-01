package com.by.json;

public class MemberNotValidJson {
	private String status;
	private String message;
	private boolean valid;

	public MemberNotValidJson(String message, boolean valid) {
		super();
		this.status = "fail";
		this.message = message;
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
