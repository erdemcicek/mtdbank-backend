package com.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bank.dao.UserDAO;
import com.bank.model.User;
import com.bank.repository.UserRepo;
import com.bank.service.impl.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

	
	@Autowired
	UserRepo userRepo;
	
//	@Autowired
//	TransactionRepo transactionRepo;
//	
//	@Autowired
//	AccountRepo accountRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUserName(username).
			orElseThrow(() -> new UsernameNotFoundException("User Not Found with username " + username));
	}

	@Override
	public UserDAO getUserDAO(User user) {
		return null;
	}

	@Override
	public UserDAO getUserDAOByName(String userName) {
		return null;
	}

	@Override
	public List<UserDAO> getAllUsers() {
		return null;
	}

	@Override
	public void deleteUser(Long id) {
		
	}

}
