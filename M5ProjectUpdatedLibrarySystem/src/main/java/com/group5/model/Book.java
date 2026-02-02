package com.group5.model;

public class Book {
	
	private String id;
	private String title;
	private String author;
	private boolean isBorrowed;

	public Book () {
	}

	public Book (String id, String title, String author, boolean isBorrowed) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.isBorrowed = isBorrowed;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public boolean isBorrowed() {
		return isBorrowed;
	}
	public void setIsBorrowed(boolean isBorrowed) {
		this.isBorrowed = isBorrowed;
	}
	
}
