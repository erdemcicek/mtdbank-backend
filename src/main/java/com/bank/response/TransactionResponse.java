package com.bank.response;

import com.bank.dao.UserDAO;

public class TransactionResponse {
	
	boolean isSuccess;
	String message;
	UserDAO user;
	
	public TransactionResponse() {}

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

	public UserDAO getUser() {
		return user;
	}

	public void setUser(UserDAO user) {
		this.user = user;
	}

}
