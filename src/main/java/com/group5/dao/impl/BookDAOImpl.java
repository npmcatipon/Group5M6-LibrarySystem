package com.group5.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.group5.dao.BookDAO;
import com.group5.model.Book;
import com.group5.util.DBUtil;


public class BookDAOImpl implements BookDAO {

	private static final Logger logger = LoggerFactory.getLogger(BookDAOImpl.class);

	private final String SHOW_ALL_BOOKS = 
			"SELECT id, title, author, isBorrowed FROM book ORDER BY id";
	
	private final String SHOW_AVAILABLE_BOOKS = 
			"SELECT id, title, author, isBorrowed FROM book WHERE isBorrowed = false ORDER BY id";
	
	private final String SHOW_BORROWED_BOOKS = 
			"SELECT id, title, author, isBorrowed FROM book WHERE isBorrowed = true ORDER BY id";
	
	private final String ADD_BOOK = 
			"INSERT INTO book(title, author) values(?,?)";
	
	@Override
	public List<Book> getAllBooks()  {
		
		List<Book> allBooklist = new ArrayList<>();
		
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(SHOW_ALL_BOOKS);
				ResultSet rs = ps.executeQuery()) {

			while(rs.next()) {
				allBooklist.add(new Book(
						rs.getString("id"),
						rs.getString("title"),
						rs.getString("author"),
						rs.getBoolean("isBorrowed")));
			}
			
		} catch (SQLException e) {
			logger.error("Failed to load all books." + e.getMessage());
			System.out.println("Error on loading all books.");
		}
		
		return allBooklist;
	}

	@Override
	public List<Book> getAvailableBooks() {
		
		List<Book> availableBooklist = new ArrayList<>();
		
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(SHOW_AVAILABLE_BOOKS);
				ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				availableBooklist.add(new Book(
						rs.getString("id"), 
						rs.getString("title"), 
						rs.getString("author"), 
						rs.getBoolean("isBorrowed")));
			}
			
		} catch (SQLException e) {
			logger.error("Failed to load available books.");
			System.out.println("Failed to load available books." + e);
		}
		
		return availableBooklist;
		
	}

	@Override
	public List<Book> getBorrowedBooks() {
		
		List<Book> borrowedBooklist = new ArrayList<>();
		
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(SHOW_BORROWED_BOOKS);
				ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				borrowedBooklist.add(new Book(
						rs.getString("id"), 
						rs.getString("title"), 
						rs.getString("author"), 
						rs.getBoolean("isBorrowed")));
			}
			
		} catch (SQLException e) {
			logger.error("Failed to load borrowed books.");
			System.out.println("Failed to load borrowed books." + e);
		}
		
		return borrowedBooklist;
		
	}

	@Override
	public void addBook(String title, String author) {
		
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(ADD_BOOK)) {
			ps.setString(1, title);
			ps.setString(2, author);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Encountered error on adding a book." + e);
		}
	}
}
