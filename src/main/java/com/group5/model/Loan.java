package com.group5.model;

public class Loan {

	private String loanId;
	private String userId;
	private String bookId;
	
	public Loan() {}
	
	public Loan(String loanId, String userId, String bookId) {
		super();
		this.loanId = loanId;
		this.userId = userId;
		this.bookId = bookId;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
}
