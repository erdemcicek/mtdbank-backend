package com.bank.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.model.Role;
import com.bank.model.User;
import com.bank.model.UserRole;
import com.bank.repository.RoleRepo;
import com.bank.repository.UserRepo;
import com.bank.request.SignUpForm;
import com.bank.response.Response;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping("/auth")
public class LoginController {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RoleRepo roleRepo;

	@PostMapping("/register")
	public ResponseEntity<Response> registerUser(@Valid @RequestBody SignUpForm signUpRequest){
		Response response = new Response();
		
		// Check User Name if it already exists
		if(userRepo.existsByUsername(signUpRequest.getUsername())) {
			response.setSuccess(false);
			response.setMessage("User Name already exists...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		if(userRepo.existsByEmail(signUpRequest.getEmail())) {
			response.setSuccess(false);
			response.setMessage("Email already exists...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		// Creating User's Account
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), 
				signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getDob(), 
				signUpRequest.getPassword());
		Set<UserRole> userRoles = new HashSet<>();
		signUpRequest.getRole().forEach(roleName -> {
			Role role = roleRepo.findByName(roleName).orElseThrow(() -> new RuntimeException("User Not Found"));
		});
		user.setUserRoles(userRoles);
		
		// Save method will save this user object in the database
		userRepo.save(user);
		response.setSuccess(true);
		response.setMessage("User has been successfully created");
		
		return new ResponseEntity(response, HttpStatus.OK);
		
	}
	
}
