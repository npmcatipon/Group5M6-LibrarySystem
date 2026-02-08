package com.group5.service.impl;

import com.group5.model.Loan;
import com.group5.repository.impl.LoanRepositoryImpl;
import com.group5.service.LoanService;

import jakarta.persistence.EntityManager;

public class LoanServiceImpl implements LoanService {
	
	private final EntityManager em;
	
	private final LoanRepositoryImpl loanRepository;
	
	public LoanServiceImpl (EntityManager em) {
		this.em = em;
		this.loanRepository = new LoanRepositoryImpl(em);
	}

	@Override
	public Loan findById(Long id) {
		
		return loanRepository.findById(id);
		
	}

	@Override
	public void deleteLoanId(Loan loan) {
		
		loanRepository.delete(loan);
		
	}

	@Override
	public void addLoan(Loan loan) {
		
		loanRepository.save(loan);
		
	}
	
}
