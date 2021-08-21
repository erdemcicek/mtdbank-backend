package com.bank.request;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;

//import lombok.Data;

//@Data
public class LoginForm {

	@NotBlank
	@Size(min=3, max=50)
	private String username;
	
	@NotBlank
	@Size(min=6, max=40)
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginForm [username=" + username + ", password=" + password + "]";
	}
}
