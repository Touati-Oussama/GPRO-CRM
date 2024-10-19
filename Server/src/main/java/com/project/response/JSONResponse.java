package com.project.response;

public class JSONResponse {

	private String message;

	private boolean success;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "JSONResponse [message=" + message + "]";
	}

	public JSONResponse(String message) {
		super();
		this.message = message;
	}

	public JSONResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}