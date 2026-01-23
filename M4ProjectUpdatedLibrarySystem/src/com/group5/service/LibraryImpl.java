package com.group5.service;

import com.group5.model.*;

import java.util.ArrayList;
import java.util.List;

import com.group5.constants.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryImpl implements LibraryService {
	
	private static final Logger logger =  LoggerFactory.getLogger(LibraryImpl.class);

	//TODO change back to 5
	private static final int initialbookcnt = 1;

	private static List<Book> bookList;
	private static List<Loan> loanList;

	public Library library;
	

	private static final int DISPLAY_ALL_BOOKS       =  1;
	private static final int DISPLAY_AVAILABLE_BOOKS =  2;
	private static final int DISPLAY_BORROWED_BOOKS  =  3;
	private static final int DISPLAY_ALL_LOANS       =  4;



	@Override
	public Library initializeList() {
		// TODO Auto-generated method stub
		bookList = new ArrayList<>();
		Book book = null;

		//initialize 5 books
		for (int i = 0; i < initialbookcnt; i++) {
			switch (i) {
				case 0: book = new Book("B1", "And Then There Were None",        "Agatha Christie",    false); break;
				case 1: book = new Book("B2", "The Big Sleep",                   "Raymond Chandler",   false); break;
				case 2: book = new Book("B3", "The Hound of the Baskervilles",   "Arthur Conan Doyle", false); break;
				case 3: book = new Book("B4", "The Da Vinci Code",               "Dan Brown" ,         false); break;
				case 4: book = new Book("B5", "The Girl with the Dragon Tattoo", "Stieg Larsson",      false); break;
			}
			bookList.add(book);
		}

		//empty loan list
		loanList = new ArrayList<>();

		this.library = new Library();
		this.library.setBookList(bookList);
		this.library.setLoanList(loanList);
		return this.library;
	}


	@Override
	public int displayAllBooks () {
		// TODO Auto-generated method stub
    	System.out.println(Constants.strDISPLAY_ALL_BOOKS );

		//print table header
		displayTableHeader(DISPLAY_ALL_BOOKS);

		//print book list
		int rowCount = displayTableDetails(DISPLAY_ALL_BOOKS);
		if (rowCount <= 0) {
			System.out.println(Constants.strNORECORDFOUND );
		}

		//print table footer
		displayTableLine(DISPLAY_ALL_BOOKS);
		System.out.println();
		System.out.println();

		return rowCount;

	}


	@Override
	public int displayAvailableBooks() {
    	System.out.println(Constants.strDISPLAY_AVAILABLE_BOOKS );

		//print table header
		displayTableHeader(DISPLAY_AVAILABLE_BOOKS);

		//print book list
		int rowCount = displayTableDetails(DISPLAY_AVAILABLE_BOOKS);

		if (rowCount <= 0) {
			System.out.println(Constants.strERROR_NO_BOOKS_AVAILABLE);
			logger.warn(Constants.strERROR_NO_BOOKS_AVAILABLE);
		}	

		//print table footer
		displayTableLine(DISPLAY_AVAILABLE_BOOKS);
		System.out.println();
		System.out.println();

		return rowCount;
	}


	@Override
	public int displayAllBorrowedBooks(User user) {
		// Added Logger Info - Jimboy Llagono
		
    	System.out.println(Constants.strDISPLAY_BORROWED_BOOKS );
    	logger.info("User {} selected option 3: display borrowed books", user.getName());
    	
		//print table header
		displayTableHeader(DISPLAY_BORROWED_BOOKS);

		//print book list
		int rowCount = displayTableDetails(DISPLAY_BORROWED_BOOKS);
		logger.info("Borrow books displayed. There are currently {} borrowed books", rowCount);
		if (rowCount <= 0) {
			System.out.println(Constants.strNORECORDFOUND );
	    	logger.info("There are currently no borrowed books. No record found");
		}
		

		//print table footer
		displayTableLine(DISPLAY_BORROWED_BOOKS);
		System.out.println();
		System.out.println();

		return rowCount;
	}


	@Override
	public int displayAllLoans() {
		// TODO Auto-generated method stub
    	System.out.println(Constants.strDISPLAY_BORROWED_BOOKS );

		//print table header
		displayTableHeader(DISPLAY_ALL_LOANS);

		//print loan list
		int rowCount = displayTableDetails(DISPLAY_ALL_LOANS);
		if (rowCount <= 0) {
			System.out.println(Constants.strNORECORDFOUND );
		}

		//print table footer
		displayTableLine(DISPLAY_ALL_LOANS);
		System.out.println();
		System.out.println();

		return rowCount;
	}


	@Override
	public Library borrowBook(String loanId, User user, String bookIdChoice) {
		System.out.println(Constants.strPROCESSLOADING);

		for (int j = 0 ; j < bookList.size(); j++) {
			Book book = bookList.get(j);
			if (book.getId().equalsIgnoreCase(bookIdChoice)) {
				book.setIsBorrowed(true);

				Loan newLoan = new Loan();
				newLoan.setBook(book);
				newLoan.setUser(user);
				newLoan.setLoanId(loanId);

				bookList.set(j, book);
				loanList.add(newLoan);

				System.out.printf(" User %s have successfully loaned the book: %s ", user.getName(), book.getTitle());
				logger.info("User {} successfully loaned the Book: {} with Loan ID: {}.", user.getName(), book.getTitle(), newLoan.getLoanId());

				break;
			}
		}

		this.library.setBookList(bookList);
		this.library.setLoanList(loanList);
		return this.library;

	}


	@Override
	public boolean findBook(String input) {
		boolean isFound = false;
		for (Book book : bookList) {
			if (book.getId().equalsIgnoreCase(input)) {
    			isFound = true;
    			break;
    		}
    	}
		return isFound;
	}


	@Override
	public boolean isBookBorrowed(String bookId) {
		boolean isBorrowed = false;
		for (Book book : bookList) {
			if (book.getId().equalsIgnoreCase(bookId)) {
				isBorrowed =  book.isBorrowed();
    			break;
    		}
    	}
		return isBorrowed;
	}


	@Override
	public boolean findLoan(String input) {
		// TODO Auto-generated method stub
		boolean isFound = false;
		for (Loan loan : loanList) {
			if (loan.getLoanId().equalsIgnoreCase(input)) {
    			isFound = true;
    		}
    	}
		return isFound;
	}


	@Override
	public Library returnBook(String loanChoice) {
		// TODO Auto-generated method stub
		System.out.println(Constants.strPROCESSLOADING );
		for (int i = 0 ; i < loanList.size(); i++) {
			Loan loan = loanList.get(i);
			if (loan.getLoanId().equalsIgnoreCase(loanChoice)) {
				Book book = loan.getBook();
				book.setIsBorrowed(false);

				loanList.remove(i);
				System.out.println(" You have successfully returned the book entitled " + book.getTitle());
				break;
			}
		}
		this.library.setBookList(bookList);
		this.library.setLoanList(loanList);
		return this.library;
	}


	@Override
	public Library addBook(Book newBook, User user) {
		

			
		bookList.add(newBook);
		library.setBookList(bookList);
		System.out.println("You have successfully added book with title " + newBook.getTitle());
		
		//Added logger info Jimboy Llagono 01.19.2026
		logger.info("User {} have successfully added book with title {}", user.getName() ,newBook.getTitle());

		
		return this.library;
		

	}


	@Override
	public Library deleteBook(String bookfordeletion) {
		// TODO Auto-generated method stub
		System.out.println(Constants.strPROCESSLOADING );
		for (int j = 0 ; j < bookList.size(); j++) {
			Book book = bookList.get(j);
			if (book.getId().equalsIgnoreCase(bookfordeletion)) {
				//remove the book
				bookList.remove(j);

				//remove any existing loan
				for (int i = 0 ; i < loanList.size(); i++) {
					Loan loan = loanList.get(i);
					if (loan.getBook().getId().equalsIgnoreCase(bookfordeletion)) {
						//System.out.println(loan.getLoanId());
						//System.out.println(loan.getBook().getId());
						//remove from loans
						loanList.remove(i);
						break;
					}
				}
				System.out.println(" You have successfully removed the book entitled " + book.getTitle());
				break;
			}
		}
		library.setBookList(bookList);
		library.setLoanList(loanList);
		return this.library;
	}


	@Override
	public Library updateBook(Book updatedBook) {
		// TODO Auto-generated method stub
		System.out.println(Constants.strPROCESSLOADING );
		for (int j = 0 ; j < bookList.size(); j++) {
			Book book = bookList.get(j);
			if (book.getId().equalsIgnoreCase(updatedBook.getId())) {
				bookList.set(j, updatedBook);
				System.out.println(" You have successfully updated the book with ID " + updatedBook.getId());
				break;
			}
		}
		library.setBookList(bookList);
		library.setLoanList(loanList);
		return this.library;
	}




	/*
	 * parameter input
	 * displayType:
	 * 1 - all books
	 * 2 - available books
	 * 3 - borrowed books
	 *
	 */

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
		int rowCount = 0;
		String userBorrower = "";

		if (displayType == DISPLAY_ALL_BOOKS || displayType == DISPLAY_AVAILABLE_BOOKS || displayType == DISPLAY_BORROWED_BOOKS) {
			for (Book book : bookList) {
				if (displayType == DISPLAY_ALL_BOOKS) {  //all books
					rowCount++;
					System.out.println("" +
							Constants.strTableColumnDelimiter + padRight(book.getId(),     Constants.maxLenBookId,     " ") +
							Constants.strTableColumnDelimiter + padRight(book.getTitle(),  Constants.maxLenBookTitle,  " ") +
							Constants.strTableColumnDelimiter + padRight(book.getAuthor(), Constants.maxLenBookAuthor, " ") +
							Constants.strTableColumnDelimiter);
				} else if (displayType == DISPLAY_AVAILABLE_BOOKS) { //available books
					if (!book.isBorrowed()) {
						rowCount++;
						System.out.println("" +
								Constants.strTableColumnDelimiter + padRight(book.getId()    , Constants.maxLenBookId,     " ") +
								Constants.strTableColumnDelimiter + padRight(book.getTitle() , Constants.maxLenBookTitle,  " ") +
								Constants.strTableColumnDelimiter + padRight(book.getAuthor(), Constants.maxLenBookAuthor, " ") +
								Constants.strTableColumnDelimiter);
					}
				} else if (displayType == DISPLAY_BORROWED_BOOKS) {  //borrowed books
					if (book.isBorrowed()) {
						rowCount++;
						for (Loan loan : loanList) {
							if (book.getId().equalsIgnoreCase(loan.getBook().getId())) {
								userBorrower = loan.getUser().getName();
								break;
							}
						}
						System.out.println("" +
								Constants.strTableColumnDelimiter + padRight(book.getId(),     Constants.maxLenBookId,     " ") +
								Constants.strTableColumnDelimiter + padRight(book.getTitle(),  Constants.maxLenBookTitle,  " ") +
								Constants.strTableColumnDelimiter + padRight(book.getAuthor(), Constants.maxLenBookAuthor, " ") +
								Constants.strTableColumnDelimiter + padRight(userBorrower,     Constants.maxLenUserName,   " ") +
								Constants.strTableColumnDelimiter);
					}
				}
			}
		} else if (displayType == DISPLAY_ALL_LOANS) { // all loan records
			for (Loan loan : loanList) {
				rowCount++;
				System.out.println("" +
						Constants.strTableColumnDelimiter + padRight(loan.getLoanId(),          Constants.maxLenLoanId,    " ") +
						Constants.strTableColumnDelimiter + padRight(loan.getBook().getTitle(), Constants.maxLenBookTitle, " ") +
						Constants.strTableColumnDelimiter + padRight(loan.getUser().getName(),  Constants.maxLenUserName,  " ") +
						Constants.strTableColumnDelimiter);
			}
		}
		return rowCount;
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
