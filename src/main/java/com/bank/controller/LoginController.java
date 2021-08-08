package com.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping("/auth")
public class LoginController {
	
	@GetMapping("/welcome")
	public ResponseEntity<String> hi(){
		return new ResponseEntity<>("Welcome", HttpStatus.OK);
	}


	
}
