package com.group5.service;

import java.util.ArrayList;

import com.group5.model.*;


public interface LibraryService {
	
	public Library initializeList();
	
	public int displayAllBooks();
	
	public int displayAvailableBooks();
	
	public int displayAllBorrowedBooks();
	
	public int displayAllLoans();
	
	public Library borrowBook(String loanId, User user, String bookId);
	
	public Library returnBook(String loanChoice);
	
	public Library addBook(Book book);

	public Library deleteBook(String bookId);

	public Library updateBook(Book book);

	public boolean findBook(String bookId);

	public boolean isBookBorrowed(String bookId);

	public boolean findLoan(String loanId);

		
}
