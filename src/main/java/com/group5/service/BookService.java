package com.group5.service;

import java.util.List;

import com.group5.model.Book;

public interface BookService {
	
	List<Book> getAllBooks();

	List<Book> getAvailableBooks();

	List<Book> getBorrowedBooks();

	void addBook(Book book);

	Book findById(Long id);

	void updateBorrowBook(String bookId);

	void updateReturnBook(String bookId);

	void deleteBook(Long id);

	void updateBook(Book book);
}
