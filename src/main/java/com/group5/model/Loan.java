package com.group5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table ( name = "loan", schema = "m6")
public class Loan {

	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column ( name = "userid",
			  nullable = false,
			  columnDefinition = "BIGINT" )
	private Long userId;
	
	@Column ( name = "bookid",
			  nullable = false,
			  columnDefinition = "BIGINT" )
	private Long bookId;
	
	public Loan() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "Loan [id=" + id + ", userId=" + userId + ", bookId=" + bookId + "]";
	}
	
	
	
}
