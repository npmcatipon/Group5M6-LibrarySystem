/*
 * 1. Upon application start, ask user to create one User
 * 2. Create one Library object
 * 3. Initialize 5 Book objects and add it to all Library slots
 * 4. Display options:
 * 
 * - [1] Display All Books
 * - [2] Display Available Books
 * - [3] Display All Borrowed Books
 * - [4] Borrow Book
 * - [5] Return Book
 * - [6] Exit
 * 
 * - user selects the number of the option
 * ===============================================
 * 
 *	 [1] Display All Books
 * - Display all Books (ID, Title and Author) regardless if there is a Loan existing for that Book.
 *   
 *   [2] Display Available Books
 * - Display Books that do not have a Loan slot
 * 
 *   [3] Display All Borrowed Books 
 * - Display Books that have a Loan equivalent.
 * - Display the Book title and the User name of borrower
 *   
 *	 [4] Borrow Book
 * - Displays all available books and User selects what book to borrow
 * - Create a Loan object, set Loan id set Book and set User to current user
 * 
 * 	 [5] Return Book
 * - Display all Loans, user selects the Loan and removes that from the slot
 * 
 *   [6] Exit
 * - Stops the program  
 * */

package com.group5.main;

import java.util.Scanner;
//Added import for Logger and LoggerFactory 01.19.2026
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.group5.model.User;
import com.group5.model.Book;
import com.group5.service.BookService;
import com.group5.service.LibraryImpl;
import com.group5.service.LibraryService;
import com.group5.service.LoanService;
import com.group5.service.UserService;
import com.group5.service.impl.BookServiceImpl;
import com.group5.service.impl.LoanServiceImpl;
import com.group5.service.impl.UserServiceImpl;
import com.group5.constants.Constants;
import com.group5.dao.impl.BookDAOImpl;
import com.group5.dao.impl.LoanDAOImpl;
import com.group5.dao.impl.UserDAOImpl;
import com.group5.exception.BookNotFoundException;
import com.group5.exception.DuplicateLoanIdException;
import com.group5.exception.InvalidBookException;
import com.group5.exception.InvalidLoanIdException;
import com.group5.exception.InvalidUserException;
import com.group5.exception.UserCancelException;


public class LibraryApplication {
	private static final Logger logger =  LoggerFactory.getLogger(LibraryApplication.class);
	
	private User user;
	private LibraryService libraryService;
	private final UserService userService = new UserServiceImpl(new UserDAOImpl());
	private BookService bookService = new BookServiceImpl(new BookDAOImpl());
	private LoanService loanService = new LoanServiceImpl(new LoanDAOImpl());
	
	public LibraryApplication () {

		this.user = new User();
		this.libraryService = new LibraryImpl();

	}
	
	public void start() {
		
        Scanner input = new Scanner(System.in);

    	validateUserLogin(input);
    	
    	welcomeMenuChoice();
    	
    	displayLibraryMenu();
    	askMenuChoice();

    	char option;
        do {
        	
        	option = getMenuChoice(input);
        	
        	System.out.println();
        	//check option
            switch (option) {
            
	            case '1':
	            	//[1] Display All Books
	            	System.out.println(Constants.strDISPLAY_SELECTED_OPTION1);
	            	logger.info("User {} selected option [1] Display All Books", user.getName());
	            	
	            	libraryService.displayAllBooks();
	            	
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;
	
	            case '2':
	            	//[2] Display Available Books
	            	System.out.println(Constants.strDISPLAY_SELECTED_OPTION2);
	            	logger.info("User {} selected option [2] Display Available Books", user.getName());
	            	
	            	libraryService.displayAvailableBooks();
	            	
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;
	
	                
	            case '3':
	            	//[3] Display All Borrowed Books 
	            	System.out.println(Constants.strDISPLAY_SELECTED_OPTION3);
	            	logger.info("User {} selected option [3] Display Borrowed Books", user.getName());

	            	libraryService.displayAllBorrowedBooks();
	            	
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;
	
	                
	            case '4':
	            	//[4] Borrow Book
	            	displayLibraryMenu();
	            	System.out.println(Constants.strDISPLAY_SELECTED_OPTION4);
	            	logger.info("User {} selected option [4] Borrow Book", user.getName());
	            	
	            	libraryService.displayAvailableBooks();

	            	try {
	            		Book book = validateBookId(input);
	            		validateLoanId(input, book);
	            		
	            	} catch (UserCancelException e) {
	            		logger.warn(e.getMessage());
	            		System.out.println(e.getMessage());
	            	}
	            	
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;
	
	                
	            case '5':
	            	//[5] Return Book
	            	// TODO: code revision
	            	displayLibraryMenu();
	            	System.out.println(Constants.strDISPLAY_SELECTED_OPTION5);
	            	logger.info("User {} selected option [5] Return Book", user.getName());

	            	libraryService.displayAllBorrowedBooks();

	            	try {
	            		
	            		String bookId = validateBorrowedBook(input);
	            		
	            	} catch (UserCancelException e) {
	            		logger.error(e.getMessage());
	            	}
	            	
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;
	                
	                
	            case '6':
	            	//[6] Add Book
	            	
	            	logger.info("User {} selected option [6] Add Book", user.getName());
	            	
	            	do {
	            		
	            		try {
		            		
		            		String title = validateTitle(input);
		            		String author = validateAuthor(input);
		            		
		            		bookService.addBook(title, author);
		            	
		            	} catch (InvalidBookException e) {
		            		
		            		System.out.println(e.getMessage());
		            		continue;
		            		
		            	} catch (UserCancelException e) {
		            		
		            		System.out.println(e.getMessage());
		            		break;
						}
	            		
	            		break;
	            		
	            	} while (true);
	            	
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;

	                
	            case '7':
	            	//[7] Remove Book
	                System.out.println(Constants.strDISPLAY_SELECTED_OPTION7);
	                logger.info("User {} selected option [7] Remove Book", user.getName());
	                
	                libraryService.displayAvailableBooks();
	                
	                try {
	                	Book book = validateBookId(input);
	                	
	                	logger.info("Deleting Book ID: {} by User {}", book.getId(), user.getName());
	                	
	                	bookService.deleteBook(String.valueOf(book.getId()));
	                	
	                	System.out.printf("User {} has deleted Book ID: {}.", user.getName(), book.getId());
	                	logger.info("Book {}, has been deleted by {}.", book.getTitle(), user.getName());
	                	
	                } catch (UserCancelException e) {
	                	System.out.println(e.getMessage());
	                }
	                
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;

	                
	            case '8':
	            	//[8] Update Book
	                System.out.println(Constants.strDISPLAY_SELECTED_OPTION8);
	                logger.info("User {} selected option [8] Update Book", user.getName());

	                libraryService.displayAllBooks();
	                
	                try {
	                	
	                	Book book = validateBookId(input);
	                	logger.info("User {} is updating Book ID: {}.", user.getName(), book.getId());
	                	
	                	Book updatedBook = askUpdateBook(input, book);
	                	
	                	bookService.updateBook(updatedBook);
	                	logger.info("Successfully updated Book ID: {}", book.getId());
	                	System.out.printf("Successfully updated Book Title: %s to %s%n", 
	                			book.getTitle(), 
	                			updatedBook.getTitle());
	                	
	                } catch (UserCancelException e) {
	                	System.out.println(e.getMessage());
	                	logger.error(e.getMessage());
	                }
	                
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;
	                
	            case '0':
	            	//[0] Exit
	                System.out.println(Constants.strDISPLAY_SELECTED_OPTION0);
	                logger.info("User {} has logged out.", user.getName());
	                break;

	            default:
	            	displayLibraryMenu();
	                System.out.println(Constants.strINVALID_MENU_CHOICE);
	            	askMenuChoice();
	                
            } // end of switch statement

        } while (option != '0');
        

        //close the Scanner
        input.close();
		
	}
	
private String validateBorrowedBook(Scanner input) throws UserCancelException {
	do {
		try {
			System.out.println("Please enter Book ID: ");
			String bookId = input.nextLine();
			
			if (bookId.equalsIgnoreCase("x")) {
				throw new UserCancelException("User " + user.getName() + " selected x. Going back to main menu.");
			}
			
			if (bookId.trim().isEmpty()) {
				throw new InvalidBookException("Book ID cannot be null or empty.");
			}
			
			if (!bookId.matches("\\d+")) {
				throw new NumberFormatException("Book ID must be numeric.");
			}
			
			String loanId = loanService.findReturnBookId(bookId);
			System.out.println("loan id is " + loanId);
			
		} catch (InvalidBookException e) {
			System.out.println(e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
	} while (true);
}

private Book askUpdateBook(Scanner input, Book book) 
		throws UserCancelException {
	
	do {
		try {

			System.out.println("Please enter new book title: ");
			String newBookTitle = input.nextLine();

			if (newBookTitle.equalsIgnoreCase("x")) {
				throw new UserCancelException("User " + user.getName() + " selected x. Going back to main menu.");
			}

			if (newBookTitle.trim().isEmpty()) {
				throw new InvalidBookException("Book Title cannot be null or empty.");
			}

			System.out.println("Please enter new book author: ");
			String newBookAuthor = input.nextLine();

			if (newBookAuthor.equalsIgnoreCase("x")) {
				throw new UserCancelException("User {} selected x. Going back to main menu.");
			}

			if (newBookAuthor.trim().isEmpty()) {
				throw new InvalidBookException("Book Title cannot be null or empty.");
			}

			Book newBook = new Book(book.getId(), newBookTitle, newBookAuthor, book.isBorrowed());
			return newBook;
			
		} catch (InvalidBookException e) {
			System.out.println(e.getMessage());
		}

	} while (true);
	
}

private void validateLoanId(Scanner input, Book book) throws UserCancelException {
		do {
			
			System.out.println(Constants.strPROMPT_ENTER_LOANID);
	    	String loanId = input.nextLine();
			
	    	try {
				
	    		if (loanId.equalsIgnoreCase("x")) {
		    		logger.error("User {} selected x. Going back to main menu.", user.getName());
		    		throw new UserCancelException(Constants.strERROR_MAIN_MENU);
		    	}
		    	
		    	if (loanId.trim().isEmpty()) {
		    		logger.error("Loan ID cannot be null or empty.");
		    		throw new InvalidLoanIdException("Loan ID cannot be null or empty.");
		    	}
		    	
		    	if (!loanId.matches("\\d+")) {
		    		logger.error("Loan ID must be numeric.");
		    		throw new NumberFormatException("Loan ID must be numeric");
		    	}
		    	
		    	logger.info("User {}, input Loan ID: {}", user.getName(), loanId);
	    		String loan = loanService.findLoanId(loanId);
	    		
	    		logger.info("User {}, adding {} with loan ID: {}.", user.getName(), book.getTitle(), loanId);
	    		loanService.addLoanBook(loan, String.valueOf(book.getId()), String.valueOf(user.getId()));
	    		
	    		logger.info("Updating Book {} to be borrowed by {}", book.getTitle(), user.getName());
	    		bookService.updateBorrowBook(book.getId());
	    		
	    		break;
		    	
			} catch (InvalidLoanIdException e) {
				System.out.println(e.getMessage());
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
			} catch (DuplicateLoanIdException e) {
				System.out.println(e.getMessage());
			}
	    	
		} while (true);

}

private Book validateBookId(Scanner input) throws UserCancelException {
		do {
			try {
				System.out.println(Constants.strPROMPT_ENTER_BOOKID);
		    	String bookId = input.nextLine();
		    	
		    	if (bookId.equalsIgnoreCase("x")) {
		    		logger.error("User {} selected x. Going back to main menu.", user.getName());
		    		throw new UserCancelException(Constants.strERROR_MAIN_MENU);
		    	}
		    	
				if (bookId.trim().isEmpty()) {
					logger.error("Book ID cannot be empty or null.");
					throw new BookNotFoundException("Book ID cannot be empty or null.");
				}
				
				if (!bookId.matches("\\d+")) {
					logger.error("Book ID must be numeric.");
					throw new NumberFormatException("Book ID must be numeric.\n");
				}
				
				logger.info("User {} searched for Book ID: {}", user.getName(), bookId);
				Book findBookId = bookService.findById(bookId);
				
				if (findBookId == null ) {
					logger.error("Invalid Book ID number.");
					throw new BookNotFoundException("Invalid Book ID number.");
				}
				
				return findBookId;
		    	
			} catch (BookNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
			}
			
		} while (true);
		
	}

	private String validateAuthor(Scanner input) throws InvalidBookException, UserCancelException {
		
		do {
			
			System.out.println(Constants.strPROMPT_ENTER_BOOKAUTHOR);
			String author = input.nextLine();
			
			if (author.trim().isEmpty() || author == null) {
				logger.warn("Author cannot be null.");
				throw new InvalidBookException(Constants.strERROR_NULL_AUTHOR);
			}
			
			if (author.equalsIgnoreCase("x")) {
				logger.warn("User {} selected x. Going back to main menu.", user.getName());
				throw new UserCancelException(Constants.strERROR_MAIN_MENU);
			}
			
			return author;
			
		} while (true);
	}

	private String validateTitle(Scanner input) throws InvalidBookException, UserCancelException {
		
		System.out.println(Constants.strPROMPT_ENTER_BOOKTITLE);
		String title = input.nextLine();

		if (title.trim().isEmpty() || title == null) {
			logger.warn("Title cannot be null.");
			throw new InvalidBookException("Title cannot be null or empty");
		}

		if (title.equalsIgnoreCase("x")) {
			logger.warn("User {} selected x. Going back to main menu.", user.getName());
			throw new UserCancelException(Constants.strERROR_MAIN_MENU);
		}

		return title;

	}

	private void validateUserLogin(Scanner input) {
		
		boolean login = false;
		
		do {
			try {
				System.out.println(Constants.strPROMPT_USERID);
				String userID = input.nextLine().trim();
				
				if (userID.trim().isEmpty() || userID == null) {
					throw new InvalidUserException("User ID is null or empty.");
				}
				
				if (!userID.matches("\\d+")) {
					throw new InvalidUserException("User ID must be numeric.");
				}
				
				System.out.println(Constants.strPROMPT_USERNAME);
				String username = input.nextLine().trim();
				
				if (username.trim().isEmpty() || username == null) {
					logger.warn("Username is not valid");
					throw new InvalidUserException("Username is null or empty.");
				}
				
				User userLogin = userService.isUserExisting(userID, username);
				
				if (userLogin == null) { 
					logger.error("Invalid User ID and/or Username.");
					throw new InvalidUserException("Invalid User ID and/or Username.");
				}
				
				user = userLogin;
				login = true;
				logger.info("{} successfully login.", user.getName());
				
			} catch (InvalidUserException e) {
				System.out.println(e.getMessage());
				logger.error(e.getMessage());
			}

		} while (!login);
		
	}



	private void displayLibraryMenu() {
		System.out.println(Constants.strDISPLAY_MENU);
	}
	
	
	private void welcomeMenuChoice() {
    	System.out.print  (" Hello, " + user.getName() + "!");
	}
	
	private void askMenuChoice() {
    	System.out.print(Constants.strPROMPT_CHOICE);
	}

	private char getMenuChoice (Scanner input) {
		String tempInput = input.nextLine().trim();
    	char option;
    	//check the encoded option
    	if ((tempInput == null) || (tempInput.length() != 1) ) {
    		//set to X to make it an invalid choice
    		option = "X".charAt(0);
    	} else {
    		option = tempInput.charAt(0);
    	} 
    	
    	return option;
	}
	
}
