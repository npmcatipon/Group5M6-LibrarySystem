package com.group5.service;

import java.util.List;

import com.group5.model.Book;

public interface BookService {
	
	List<Book> getAllBooks();
	List<Book> getAvailableBooks();
	List<Book> getBorrowedBooks();
	void addBook(String title, String author);
	Book findById(String bookId);
	void updateBorrowBook(String bookID);
	void deleteBook(String bookID);
}
