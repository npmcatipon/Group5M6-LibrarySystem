package com.group5.service.impl;

import java.util.List;

import com.group5.model.Book;
import com.group5.repository.impl.BookRepositoryImpl;
import com.group5.service.BookService;

import ch.qos.logback.classic.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

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
		
		EntityTransaction tx = em.getTransaction();
		
		try {
			
			tx.begin();
			
			bookRepository.save(book);
			
			tx.commit();
			
		} catch (Exception e) {
			
			if (tx.isActive()) {
				
				tx.rollback();
				
			}
			
			throw e;
		}
		
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
		EntityTransaction tx = em.getTransaction();
		
		try {
			
			tx.begin();
			
			bookRepository.deleteById(id);
			
			tx.commit();
			
		} catch (Exception e) {
			
			if (tx.isActive()) {
				
				tx.rollback();
			}
			
			System.out.println(e.getMessage());
			
		}
		
	}

	@Override
	public void updateBook(Book book) {
	}

	

}
