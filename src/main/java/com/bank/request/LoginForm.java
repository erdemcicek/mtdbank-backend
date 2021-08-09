package com.bank.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginForm {

	@NotBlank
	@Size(min=3, max=50)
	private String username;
	
	@NotBlank
	@Size(min=6, max=40)
	private String password;
}
