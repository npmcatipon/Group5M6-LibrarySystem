package com.group5.repository.impl;

import java.util.List;

import com.group5.model.Book;
import com.group5.repository.Repository;

import jakarta.persistence.EntityManager;

public class BookRepositoryImpl implements Repository<Book, Long> {

	private final EntityManager em;
	
	public BookRepositoryImpl (EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Book save(Book book) {
		
		if (book.getId() == null) {
			em.persist(book);
		} else {
			em.merge(book);
		}
		
		return book;
	}

	@Override
	public void delete(Book book) {
		
		em.remove(em.contains(book) ? book : em.merge(book));
		
	}

	@Override
	public void deleteById(Long id) {
		
		Book book = findById(id);
		
		if (book != null ) {
			delete(book);
		}
		
	}

	@Override
	public Book findById(Long id) {
		
		return em.find(Book.class, id);
		
	}

	@Override
	public List<Book> findAll() {
		
		return em.createQuery("SELECT b FROM book b", Book.class).getResultList();
		
	}
	
	public List<Book> findAvailable() {
		
		return em.createQuery("Select b FROM book b WHERE b.is_borrowed = false", Book.class).getResultList();
		
	}
	
	public List<Book> findBorrowed() {
		
		return em.createQuery("Select b from book b WHERE b.is_borrowed = true", Book.class).getResultList();
		
	}

}
