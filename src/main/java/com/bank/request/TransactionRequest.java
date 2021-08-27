package com.bank.request;

import javax.validation.constraints.NotBlank;

public class TransactionRequest {
	
	@NotBlank
	private double amount;
	
	private String comment;
	
	public TransactionRequest() {}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
