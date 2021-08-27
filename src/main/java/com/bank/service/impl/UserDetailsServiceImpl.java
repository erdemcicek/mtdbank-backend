package com.bank.service.impl;

import static com.bank.util.DateUtil.SIMPLE_DATE_FORMAT;
import static com.bank.util.DateUtil.SIMPLE_DATE_TIME_FORMAT;
import static com.bank.util.DateUtil.getDateAsString;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bank.dao.TransactionDAO;
import com.bank.dao.UserDAO;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.model.User;
import com.bank.repository.AccountRepo;
import com.bank.repository.TransactionRepo;
import com.bank.repository.UserRepo;
import com.bank.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService{
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	TransactionRepo transactionRepo;
	
	@Autowired
	AccountRepo accountRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found" + username));
	}
	@Override
	public UserDAO getUserDAO(User user) {
		
		UserDAO userDAO = new UserDAO();
		userDAO.setUsername(user.getUsername());
		userDAO.setFirstName(user.getFirstName());
		userDAO.setLastName(user.getLastName());
		userDAO.setEmail(user.getEmail());
		userDAO.setPhone(user.getPhone());
		
		// Check if User has admin role
		boolean isAdminExist = user.getUserRoles().stream()
								.anyMatch(userRole -> userRole.getRole().getName().equalsIgnoreCase("admin"));
		userDAO.setIsAdmin(isAdminExist);
		
		if(isAdminExist) {
			
			// Get all transactions from DB
			List<Transaction> transactions = transactionRepo.findAll();
			
			// Convert Transaction Object to TransactionDAO as store in List
			List<TransactionDAO> transactionDAOs = transactions
													.stream()
													.map(this::getTransactionDAO)
													.collect(Collectors.toList());
			userDAO.setTransactions(transactionDAOs);
			
			// Set the total User count
			userDAO.setTotalUsers(userRepo.count());
			
			// Set Bank total balance
			List<Account> accounts = accountRepo.findAll();
			Double totalBalance = accounts
									.stream()
									.mapToDouble( account -> account.getAccountBalance().doubleValue())
									.sum();
			userDAO.setTotalBalance(totalBalance);
			
		}else if (user.getAccount() != null) {
			userDAO.setAccountNumber(user.getAccount().getAccountNumber());
			userDAO.setAccountBalance(user.getAccount().getAccountBalance());
			
			// Convert Transaction object to TransactionDAO as store in List
			List<TransactionDAO> transactionDAOs = user.getAccount().getTransactions()
													.stream().map( this::getTransactionDAO)
													.collect(Collectors.toList());
			userDAO.setTransactions(transactionDAOs);
		}
		
		return userDAO;
	}
	
	private TransactionDAO getTransactionDAO(Transaction transaction) {
		TransactionDAO transactionDAO = new TransactionDAO();
		transactionDAO.setId(transaction.getId());
		transactionDAO.setDate(getDateAsString(transaction.getDate(), SIMPLE_DATE_FORMAT));
		transactionDAO.setTime(getDateAsString(transaction.getDate(), SIMPLE_DATE_TIME_FORMAT));
		transactionDAO.setDescription(transaction.getDescription());
		transactionDAO.setType(transaction.getType());
		transactionDAO.setAmount(transaction.getAmount());
		transactionDAO.setAvailableBalance(transaction.getAvailableBalance());
		transactionDAO.setIsTransfer(transaction.getIsTransfer());

		return transactionDAO;
	}
	@Override
	public UserDAO getUserDAOByName(String userName) {
		
		// Initialize the UserDAO object
		UserDAO userDAO = null;
		
		// Get the User object from DB
		Optional<User> userOpt = userRepo.findByUsername(userName);
		
		// Check the User object is present or not
		if(userOpt.isPresent()) {
			
			// Convert User object to UserDAO object
			userDAO = getUserDAO(userOpt.get());
		}
		
		// return the userDAO object
		return userDAO;
	}
	@Override
	public List<UserDAO> getAllUser() {
		return null;
	}
	@Override
	public void deleteUser(Long id) {

	}

	
}
