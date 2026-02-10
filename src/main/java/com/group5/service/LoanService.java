package com.group5.service;

import com.group5.model.Loan;

public interface LoanService {

	Loan findById (Long id);
	
	void addLoan (Loan loan);
	
	void deleteLoanId (Loan loan);
	
	Loan findBorrowedBook (Long id);
	
}
