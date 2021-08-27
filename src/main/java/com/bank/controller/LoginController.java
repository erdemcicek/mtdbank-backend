package com.bank.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dao.UserDAO;
import com.bank.model.Role;
import com.bank.model.User;
import com.bank.model.UserRole;
import com.bank.repository.RoleRepo;
import com.bank.repository.UserRepo;
import com.bank.request.LoginForm;
import com.bank.request.SignUpForm;
import com.bank.response.LoginResponse;
import com.bank.response.Response;
import com.bank.service.AccountService;
import com.bank.service.UserService;
import com.bank.util.JwtUtil;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping("/auth")
public class LoginController {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AccountService accountService;
	
	
	@PostMapping("/register")
	public ResponseEntity<Response> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		
		Response response = new Response();
		
		// Check if User Name already exists
		if(userRepo.existsByUsername(signUpRequest.getUsername())) {
			response.setMessage("Username is already taken!");
			response.setSuccess(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		// Check if Email already exists
		if(userRepo.existsByEmail(signUpRequest.getEmail())) {
			response.setMessage("Email is already in use");
			response.setSuccess(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		// Create user's account
		
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(), 
							 signUpRequest.getDob(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
		
		Set<UserRole> userRoles = new HashSet<>();
		signUpRequest.getRole().forEach( roleName -> {
			Role role = roleRepo.findByName(roleName)
						.orElseThrow(() -> new RuntimeException("Role Not Found"));
			userRoles.add( new UserRole(user, role));
		});
		
		user.setUserRoles(userRoles);
		
		user.setAccount(accountService.createAccount());
		// "save" method will save this user object in the database
		userRepo.save(user);
		
		response.setSuccess(true);
		response.setMessage("User has been created successfully");
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginForm loginForm){
		
		// Create Authentication Object from username & password
		Authentication authentication = authenticationManager.authenticate( 
				new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
		
		// Put that authenticate object in context holder
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// Create JWT Token
		String jwtToken = jwtUtil.generateToken(authentication);
		
		User user = (User) authentication.getPrincipal();
		
		// Convert user to userdao object
		UserDAO userDAO = userService.getUserDAO(user);
		
		LoginResponse response = new LoginResponse(userDAO, jwtToken);
		
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
}
