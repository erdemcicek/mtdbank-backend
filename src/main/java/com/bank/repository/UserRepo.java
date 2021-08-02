package com.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bank.model.User;

/**
 * The UserRepo interface is used to connect user table
 */

public interface UserRepo extends CrudRepository<User, Long> {

	Optional<User> findByUserName(String username);
//	Boolean findByPassword(String password);
	
	boolean existsByUsername(String userName);
	
	boolean existsByEmail(String email);
	
	List<User> findAll();
	
	long count();
}
