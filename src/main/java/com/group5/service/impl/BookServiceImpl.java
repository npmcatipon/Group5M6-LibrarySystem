package com.group5.service.impl;

import java.util.List;

import com.group5.model.Book;
import com.group5.repository.impl.BookRepositoryImpl;
import com.group5.service.BookService;

import jakarta.persistence.EntityManager;

public class BookServiceImpl implements BookService {

	private final EntityManager em;
	
	private final BookRepositoryImpl bookRepository;
	
	public BookServiceImpl (EntityManager em) {
		this.em = em;
		this.bookRepository = new BookRepositoryImpl(em);
	}
	
	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public List<Book> getAvailableBooks() {
		return bookRepository.findAvailable();
	}

	@Override
	public List<Book> getBorrowedBooks() {
		return bookRepository.findBorrowed();
	}
	
	@Override
	public void addBook(Book book) {
		bookRepository.save(book);
	}
	
	public Book findById(Long Id) {
		return bookRepository.findById(Id);
	}

	@Override
	public void updateBorrowBook(String bookID) {
	}
	
	@Override
	public void updateReturnBook(String bookId) {
	}

	@Override
	public void deleteBook(Long id) {
	}

	@Override
	public void updateBook(Book book) {
	}

	

}
