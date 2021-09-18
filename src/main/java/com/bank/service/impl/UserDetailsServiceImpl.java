package com.bank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bank.dao.UserDAO;
import com.bank.model.User;
import com.bank.repository.UserRepo;
import com.bank.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username).orElseThrow(() ->
			new UsernameNotFoundException("User Not Found with  username  " + username)); 
	}

	@Override
	public UserDAO getUserDAO(User user) {
		UserDAO userDAO = new UserDAO();
		userDAO.setUsername(user.getUsername());
		userDAO.setFirstName(user.getFirstName());
		userDAO.setLastName(user.getLastName());
		userDAO.setEmail(user.getEmail());
		userDAO.setPhone(user.getPhone());	 
		
		//Check User has admin role 
		boolean isAdminExist = user.getUserRoles().stream().anyMatch(userRole ->
		userRole.getRole().getName().equalsIgnoreCase("admin"));
		 		
		userDAO.setIsAdmin(isAdminExist);
		
		return userDAO;
	}

	@Override
	public UserDAO getUserDAOByName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDAO> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub

	}

}
