package com.bank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dao.UserDAO;
import com.bank.model.User;
import com.bank.request.TransactionRequest;
import com.bank.response.TransactionResponse;
import com.bank.service.AccountService;
import com.bank.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	UserService userService;
	
	@Autowired
	AccountService accountService;
	
	@PostMapping("/deposit")
	public ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody TransactionRequest request){
		
		TransactionResponse response = new TransactionResponse();
		
		// Get the Use object from Security Context
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// Accomplish the deposit
		accountService.deposit(request, user);
		
		// Set the response message
		response.setMessage("Amount successfully deposited");
		response.setSuccess(true);
		
		// Get the latest user information
		UserDAO userDAO = userService.getUserDAOByName(user.getUsername());
		response.setUser(userDAO);
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	@PostMapping("/withdraw")
	public ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody TransactionRequest request){
		
		TransactionResponse response = new TransactionResponse();
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// Check the Bank Balance
		if(user.getAccount().getAccountBalance().doubleValue() >= request.getAmount()) {
			// Accomplish the withdrawal
			accountService.withdraw(request, user);
			response.setMessage("Amount successfully withdrawn");
			response.setSuccess(true);
			// Get the latest user information
			UserDAO userDAO = userService.getUserDAOByName(user.getUsername());
			response.setUser(userDAO);
			
		} else {
			// Set the error message
			response.setMessage("Sorry! You do not have sufficient balance");
			response.setSuccess(false);
		}
		 
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
