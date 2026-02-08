package com.group5.service;

import com.group5.model.Book;
import com.group5.model.Library;
import com.group5.service.impl.BookServiceImpl;
import com.group5.util.EntityManagerUtil;

import jakarta.persistence.EntityManager;

import com.group5.constants.Constants;

public class LibraryImpl implements LibraryService {
	
	public Library library;
	
	private final EntityManager em = EntityManagerUtil.getInstance().createEntityManager();
	
	private BookService bookService = new BookServiceImpl(em);

	private static final int DISPLAY_ALL_BOOKS       =  1;
	private static final int DISPLAY_AVAILABLE_BOOKS =  2;
	private static final int DISPLAY_BORROWED_BOOKS  =  3;
	private static final int DISPLAY_ALL_LOANS       =  4;
	
	@Override
	public void displayAllBooks() {
		System.out.println("List of All Books.");
    	for (Book b: bookService.getAllBooks()) {
    		System.out.printf("%s | %s | %s%n", b.getId(), b.getTitle(), b.getAuthor());
    	}
	}


	@Override
	public void displayAvailableBooks() {
		System.out.println("List of Available Books.");
    	for (Book b: bookService.getAvailableBooks()) {
    		System.out.printf("%s | %s | %s%n", b.getId(), b.getTitle(), b.getAuthor());
    	}
	}


	@Override
	public void displayAllBorrowedBooks() {
		System.out.println("List of Borrowed Books.");
    	for (Book b: bookService.getBorrowedBooks()) {
    		System.out.printf("%s | %s | %s%n", b.getId(), b.getTitle(), b.getAuthor());
    	}
    }


	private static void displayTableHeader(int displayType) {
		//header

		displayTableLine(displayType);

		if (displayType == DISPLAY_ALL_BOOKS || displayType == DISPLAY_AVAILABLE_BOOKS) {
			System.out.println("" +
					Constants.strTableColumnDelimiter + padRight(Constants.strBOOK_COLUMN_ID,       Constants.maxLenBookId,     " ") +
					Constants.strTableColumnDelimiter + padRight(Constants.strBOOK_COLUMN_TITLE,    Constants.maxLenBookTitle,  " ") +
					Constants.strTableColumnDelimiter + padRight(Constants.strBOOK_COLUMN_AUTHOR,   Constants.maxLenBookAuthor, " ") +
					Constants.strTableColumnDelimiter);

		} else if (displayType == DISPLAY_BORROWED_BOOKS) {
			System.out.println("" +
					Constants.strTableColumnDelimiter + padRight(Constants.strBOOK_COLUMN_ID,        Constants.maxLenBookId,     " ") +
					Constants.strTableColumnDelimiter + padRight(Constants.strBOOK_COLUMN_TITLE,     Constants.maxLenBookTitle,  " ") +
					Constants.strTableColumnDelimiter + padRight(Constants.strBOOK_COLUMN_AUTHOR,    Constants.maxLenBookAuthor, " ") +
					Constants.strTableColumnDelimiter + padRight(Constants.strBOOK_COLUMN_BORROWER,  Constants.maxLenUserName,   " ") +
					Constants.strTableColumnDelimiter);

		} else if (displayType == DISPLAY_ALL_LOANS) {
			System.out.println("" +
					Constants.strTableColumnDelimiter + padRight(Constants.strLOAN_COLUMN_ID,        Constants.maxLenBookId,     " ") +
					Constants.strTableColumnDelimiter + padRight(Constants.strBOOK_COLUMN_TITLE,     Constants.maxLenBookTitle,  " ") +
					Constants.strTableColumnDelimiter + padRight(Constants.strBOOK_COLUMN_BORROWER,  Constants.maxLenUserName,   " ") +
					Constants.strTableColumnDelimiter);

		}
		displayTableLine(displayType);

	}


	/*
	 * parameter input
	 * displayType:
	 * 1 - all books
	 * 2 - available books
	 * 3 - borrowed books
	 *
	 */
	private static int displayTableDetails(int displayType) {
		return 0;
	}


	private static void displayTableLine (int displayType) {
		//header

		if (displayType == DISPLAY_ALL_BOOKS || displayType == DISPLAY_AVAILABLE_BOOKS) {
			System.out.println(" " +
					padRight("", Constants.maxLenBookId + Constants.strTableColumnDelimiter.length(),     "=") +
					padRight("", Constants.maxLenBookTitle + Constants.strTableColumnDelimiter.length(),  "=") +
					padRight("", Constants.maxLenBookAuthor + Constants.strTableColumnDelimiter.length(), "=") +
					padRight("", 1, "=")
					);
		} else if (displayType == DISPLAY_BORROWED_BOOKS) {
			System.out.println(" " +
					padRight("", Constants.maxLenBookId + Constants.strTableColumnDelimiter.length(),     "=") +
					padRight("", Constants.maxLenBookTitle + Constants.strTableColumnDelimiter.length(),  "=") +
					padRight("", Constants.maxLenBookAuthor + Constants.strTableColumnDelimiter.length(), "=") +
					padRight("", Constants.maxLenUserName + Constants.strTableColumnDelimiter.length(),   "=") +
					padRight("", 1, "=")
					);
		} else if (displayType == DISPLAY_ALL_LOANS) {
			System.out.println(" " +
					padRight("", Constants.maxLenLoanId + Constants.strTableColumnDelimiter.length(),    "=") +
					padRight("", Constants.maxLenBookTitle + Constants.strTableColumnDelimiter.length(), "=") +
					padRight("", Constants.maxLenUserName + Constants.strTableColumnDelimiter.length(),  "=") +
					padRight("", 1, "=")
					);
		}
	}

	private static String padRight(String param, int length, String padValue) {
		String returnValue = "";
		String temp  = "";

		if (param.equals("")) {
			temp = param;

			for (int i = 0; i < length; i++) {
				temp = padValue + "" + temp;
			}
			returnValue = temp;

		}

		int difference = length - param.length();

		if (param.length() < length) {
			temp = param;
			for (int i = 0; i < difference; i++) {
				temp += padValue;
			}
			returnValue = temp;

		} else if (param.length() > length) {
			returnValue = param.substring(0, length);

		} else if (param.length() == length) {
			returnValue = param;
		}

		return returnValue;
	}






}
