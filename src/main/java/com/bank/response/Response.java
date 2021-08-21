package com.bank.response;

//import lombok.Data;

//@Data
public class Response {
	boolean isSuccess;
	String message;
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Response [isSuccess=" + isSuccess + ", message=" + message + "]";
	}
}