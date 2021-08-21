package com.bank.response;

import com.bank.dao.UserDAO;


//import lombok.AllArgsConstructor;
//import lombok.Data;

//@Data
//@AllArgsConstructor
public class LoginResponse {

	private UserDAO user;
	private String jwt;
	public UserDAO getUser() {
		return user;
	}
	public void setUser(UserDAO user) {
		this.user = user;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public LoginResponse(UserDAO user, String jwt) {
		super();
		this.user = user;
		this.jwt = jwt;
	}
	public LoginResponse() {
		super();
	}
	@Override
	public String toString() {
		return "LoginResponse [user=" + user + ", jwt=" + jwt + "]";
	}
}
