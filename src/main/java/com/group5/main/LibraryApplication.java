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
import com.group5.service.UserService;
import com.group5.service.impl.BookServiceImpl;
import com.group5.service.impl.UserServiceImpl;
import com.group5.constants.Constants;
import com.group5.dao.impl.BookDAOImpl;
import com.group5.dao.impl.UserDAOImpl;
import com.group5.exception.InvalidBookException;
import com.group5.exception.InvalidUserException;
import com.group5.exception.UserCancelException;


public class LibraryApplication {
	private static final Logger logger =  LoggerFactory.getLogger(LibraryApplication.class);
	
	private User user;
	private LibraryService libraryService;
	UserService userService = new UserServiceImpl(new UserDAOImpl());
	
	public LibraryApplication () {

		this.user = new User();
		this.libraryService = new LibraryImpl();

	}
	
	public void start() {
		
		BookService bookService = new BookServiceImpl(new BookDAOImpl());
		
		
        Scanner input = new Scanner(System.in);
    	int rowCount;
    	

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
	            	
	            	for (Book b: bookService.getAllBooks()) {
	            		System.out.printf("%s | %s | %s%n", b.getId(), b.getTitle(), b.getAuthor());
	            	}
	            	
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;
	
	            case '2':
	            	//[2] Display Available Books
	            	System.out.println(Constants.strDISPLAY_SELECTED_OPTION2);
	            	logger.info("User {} selected option [2] Display Available Books", user.getName());
	            	
	            	for (Book b: bookService.getAvailableBooks()) {
	            		System.out.printf("%s | %s | %s%n", b.getId(), b.getTitle(), b.getAuthor());
	            	}
	            	
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;
	
	                
	            case '3':
	            	//[3] Display All Borrowed Books 
	            	System.out.println(Constants.strDISPLAY_SELECTED_OPTION3);
	            	logger.info("User {} selected option [3] Display Borrowed Books", user.getName());

	            	for (Book b: bookService.getBorrowedBooks()) {
	            		System.out.printf("%s | %s | %s | %b%n", b.getId(), b.getTitle(), b.getAuthor());
	            	}
	            	
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;
	
	                
	            case '4':
	            	//[4] Borrow Book
	            	displayLibraryMenu();
	            	System.out.println(Constants.strDISPLAY_SELECTED_OPTION4);
	            	logger.info("User {} selected option [4] Borrow Book", user.getName());
	            	
	            	rowCount = libraryService.displayAvailableBooks();
	            	
	            	if (rowCount > 0) {
		            	String bookIdChoice = askBookChoice(input);
		            	if (bookIdChoice != "") {
		            		//book found, ask user to input loan ID
		            		String createdLoanId = askLoanId(input);
		            		if (createdLoanId != "") {
		            			logger.info("User {} generated Loan ID: {}", user.getName(), createdLoanId);
			            		libraryService.borrowBook(createdLoanId, user, bookIdChoice);
		            		}
		            	}
	            	}
            		//exit to menu
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;
	
	                
	            case '5':
	            	//[5] Return Book
	            	displayLibraryMenu();
	            	System.out.println(Constants.strDISPLAY_SELECTED_OPTION5);
	            	String bookTitle = input.nextLine();
	            	
	            	logger.info("User {} selected option [5] Return Book", user.getName());
	            	rowCount = libraryService.displayAllLoans();
	            	if (rowCount > 0) {
		            	String loanChoice = askLoanIdForReturn(input);
		            	if (loanChoice != "") {
		            		//loan found, ask user to input loan ID
			            	this.libraryService.returnBook(loanChoice);
		            	}
	            	}
	            	//exit to menu
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
	            	rowCount = libraryService.displayAllBooks();
	            	if (rowCount > 0) {
		            	String bookIdChoice = askBookChoiceForRemoval(input);
		            	if (bookIdChoice != "") {
		            		//book found, delete book
		            		libraryService.deleteBook(bookIdChoice);
		            		logger.info("User {} successfully removed book with book ID: {}", user.getName(), bookIdChoice);
		            	}
	            	} 
	            	//exit to menu
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;

	                
	            case '8':
	            	//[8] Update Book
	                System.out.println(Constants.strDISPLAY_SELECTED_OPTION8);
	                logger.info("User {} selected option [8] Update Book", user.getName());
	            	rowCount = libraryService.displayAllBooks();
	            	if (rowCount > 0) {
	            		String bookIdChoice = askBookChoice(input);
		            	if (bookIdChoice != "") {
		            		//book found, ask user to input the updated book title and author
		            		Book updatedBook = askUpdatedBook(input, bookIdChoice);
		            		if (updatedBook != null) {
			            		libraryService.updateBook(updatedBook);
			            		logger.info("User {} updated the Book ID: {} to {}.", user.getName(),
			            				bookIdChoice,
			            				updatedBook.getTitle());
		            		}
		            	}
	            	}
	            	//exit to menu
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
		
		do {
			
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
			
		} while (true);
	}

	private void validateUserLogin(Scanner input) {
		
		boolean login = false;
		
		do {
			try {
				System.out.println(Constants.strPROMPT_USERID);
				String userID = input.nextLine().trim();
				
				if (userID.trim().isEmpty() || userID == null) {
					logger.warn("User ID is not valid");
					throw new InvalidUserException("User ID is null or empty.");
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

	private String askBookChoice(Scanner input) {
		String ret = "";
		String tempInput;
    	String prompt = Constants.strPROMPT_ENTER_BOOKID;

    	//user input (Book id to be borrowed)
    	boolean bookFound = false;
    	
    	
    	do {
    		
    		tempInput = validateInput(input, prompt);
    		if (tempInput != null) {
    			
        		if (tempInput.equalsIgnoreCase("X")) {
	        		bookFound = true;
	        		System.out.println(" Going back to main menu.");
	        		logger.info("User {}, selected X in Book ID choice. Going back to main menu.", user.getName());
	        		break;

        		} else {
	            	bookFound = libraryService.findBook(tempInput);
	            	logger.info("User {} searched Book ID: {}", user.getName(), tempInput);

	            	if (bookFound) {
	        			//check if currently loaned
	            		if (libraryService.isBookBorrowed(tempInput)) {
	        				//currently borrowed
	        				System.out.print (Constants.strERROR_BOOK_OUT);
	        				logger.warn("Book ID: {} is currently borrowed.", tempInput);
	        				bookFound = false;
	        			} else {
			        		ret = tempInput;
			        		bookFound = true;
			        		break;
	        			}
	        		} else {
	        			System.out.print (Constants.strERROR_BOOK_NOT_FOUND);
	        			logger.error("Book ID: not found.", tempInput);
	        		}
	        	}
    		}
    	} while (!bookFound);

    	return ret;
	}
	
	
	private String askLoanId(Scanner input) {
		String ret = "";
		String tempInput;
		String prompt = Constants.strPROMPT_ENTER_LOANID ;
    	//user input (loan id for borrowing)
    	boolean loanSearch = false;
    	
    	
    	do {
    		tempInput = validateInput(input, prompt);
    		if (tempInput != null) {
	        	if (tempInput.equalsIgnoreCase("X")) {
	        		loanSearch = true;
	        		System.out.println("Going back to main menu.");
	        		logger.warn("User {} selected X in Loan ID. Going back to main menu.", user.getName());
	        		break;
	        	} else {
	        		loanSearch = libraryService.findLoan(tempInput);
	        		
	        		if (loanSearch) {
	        			System.out.print (Constants.strINVALID_LOAN_ID_FOUND);
	        			loanSearch = false;
	        		} else {
	        			//valid loan ID
	        			ret = tempInput;
		        		loanSearch = true;
		        		break;
	        			
	        		}
	        	}
    		}
    		
    	} while (!loanSearch);

    	return ret;

	}
	
	
	private String askLoanIdForReturn(Scanner input) {
		String ret = "";
		String tempInput;
		String prompt = Constants.strPROMPT_ENTER_LOANID ;
    	//user input (loan id for borrowing)
    	boolean loanSearch = false;
    	
    	
    	do {
    		tempInput = validateInput(input, prompt);
    		logger.info("User {}, search for Loan ID: {}.", user.getName(), tempInput);
    		if (tempInput != null) {
        		if (tempInput.equalsIgnoreCase("X")) {
	        		loanSearch = true;
	        		System.out.println("Going back to main menu.");
	        		logger.warn("User {} selected x. Going back to main menu.", user.getName());
	        		break;
	        	} else {
	        		loanSearch = libraryService.findLoan(tempInput);
	        		
	        		if (!loanSearch) {
	        			System.out.print (Constants.strINVALID_LOAN_ID);
	        			logger.warn("Loan ID: {} not found", tempInput);
	        			loanSearch = false;
	        		} else {
	        			//valid loan ID
	        			ret = tempInput;
	        			logger.info("Loan ID: {} Found.",tempInput);
		        		loanSearch = true;
		        		break;
	        		}
	        	}
    		}

    	} while (!loanSearch);

    	return ret;

	}
	
	// Updated inputNewBook method added logging and exception handling 01.19.2026
	private Book inputNewBook(Scanner input) throws InvalidBookException { // inputNewBook Method Start 01.19.2026
		
		
		Book book = null;
		boolean isInputValid = false;
		String bookId = null;
		String bookTitle = null;
		String bookAuthor = null;
		
		String tempInput;
		String prompt = "";
		
		logger.info("User {} selected option 6 Add book", user.getName());
		
		//input book ID
		prompt = Constants.strPROMPT_ENTER_BOOKID ;
		do {
			tempInput = validateInput(input, prompt);
			if (tempInput != null) {
				if (tempInput.equalsIgnoreCase("X")) {
	        		isInputValid = true;
	        		break;
				} else if (tempInput.length() > Constants.maxLenBookId) {
	        		isInputValid = false;
	        		System.out.print (Constants.strERROR_INVALID_INPUT);
	        		logger.warn("User {} entered book ID: {}, Invalid input, Book ID length cannot be more than 7", user.getName(), tempInput);  // Added logger warn 01.19.2026
	        		
	        		//throw new InvalidBookException("Book ID length cannot be more than 7"); // commented, will exit the loop 01.19.2026
	        	} else {
	        		isInputValid = libraryService.findBook(tempInput);
	            	if (isInputValid) {
	    				System.out.print (Constants.strERROR_BOOK_EXIST);
		        		logger.warn("User {} entered book ID: {}, Invalid! Book ID already exists.",user.getName(), tempInput); // added logger warn 01.19.2026
	    				isInputValid = false;
		        		//throw new InvalidBookException("Book ID "+ tempInput+" already exists"); // commented, will exit the loop 01.19.2026
	    				
	    			} else {
	    				bookId = tempInput;
	    				logger.info("User {} inputted {} for book ID",user.getName(), tempInput);
	    				isInputValid = true;
		        		break;
	    			}
	        	}
			}
		} while (!isInputValid);

        
		if (!tempInput.equalsIgnoreCase("X")) {
			
			//allow input book Title
	
	        isInputValid = false;
			prompt = Constants.strPROMPT_ENTER_BOOKTITLE ;
			
			do {
				tempInput = validateInput(input, prompt);
	
				if (tempInput != null) {
					
					if (tempInput.equalsIgnoreCase("X")) {
		        		isInputValid = true;
		        		break;
					} else {
						bookTitle = tempInput;
	    				logger.info("User {} inputted {} for book title",user.getName(), tempInput);
						isInputValid = true;
		        		break;
					}
				}
				
			} while (!isInputValid);
		}else {
			throw new InvalidBookException("Adding new book failed, user inputted "+ tempInput);
		}
		

        
		if (!tempInput.equalsIgnoreCase("X")) {
	
			//allow input book Author
	        isInputValid = false;
	        prompt = Constants.strPROMPT_ENTER_BOOKAUTHOR ;
	        
	        do {
	        	tempInput = validateInput(input, prompt);
	        	if (tempInput != null) {
					if (tempInput.equalsIgnoreCase("X")) {
		        		isInputValid = true;
		        		break;
					} else {
						bookAuthor = tempInput;
	    				logger.info("User {} inputted {} for book author",user.getName(), tempInput);
						isInputValid = true;
		        		break;
					}
	        	}
	        } while (!isInputValid);
		}else {
			throw new InvalidBookException("Adding new book failed, user inputted "+ tempInput);
		}
		
		try {
	        //create Book 
	        if ((bookId != null && bookTitle !=null && bookAuthor != null)) {
	        	if (!(bookId.equalsIgnoreCase("X") || bookTitle.equalsIgnoreCase("X") || bookAuthor.equalsIgnoreCase("X"))) {
	                book = new Book(bookId, bookTitle, bookAuthor, false);
	                // Added logger info 01.19.2026
	                logger.info("Book ID {} entitled {} created successfully by {}", bookId,  bookTitle, user.getName()); 
	        	} 
	        }else {
	        	throw new InvalidBookException("Book ID, Title or Author cannot be null or empty"); // added throw InvalidBookException for null and empty 01.19.2026
	        }
	        
		}catch(IllegalArgumentException e) {
			logger.error("Failed to create book due to invalid argument", e);
			
			throw new InvalidBookException("Adding new book failed", e);
		}
	
		return book;
	} // inputNewBook Method End
	
	
	private String askBookChoiceForRemoval(Scanner input) {
		String ret = "";
		String tempInput;
    	String prompt = Constants.strPROMPT_ENTER_BOOKID;

    	//user input (Book id to be removed)
    	boolean bookFound = false;
    	
    	
    	do {
    		
    		tempInput = validateInput(input, prompt);
    		if (tempInput != null) {
    			
        		if (tempInput.equalsIgnoreCase("X")) {
        			// Added logger for book id input to be removed and user inputted x
        			logger.info("User {} inputted character to be returned to main menu: {}", user.getName(), tempInput);
	        		bookFound = true;
	        		break;

        		} else {
	            	bookFound = libraryService.findBook(tempInput);

	            	if (bookFound) {
	            		// Added logger for book id input to be removed and book is found
	            		logger.info("User {} inputted Book ID: {} to be removed. Book is found", user.getName(), tempInput);
		        		ret = tempInput;
		        		bookFound = true;
		        		break;
	        		} else {
	        			System.out.print (Constants.strERROR_BOOK_NOT_FOUND);
	        			// Added logger for book id input to be removed and book is not found
	        			logger.info("User {} inputted Book ID: {} to be removed. Book is not found", user.getName(), tempInput);
	        		}
	        	}
    		}
    	} while (!bookFound);

    	return ret;
	}
	
	
	private Book askUpdatedBook(Scanner input, String bookId) {
		Book book = null;
		boolean isInputValid = false;
		String bookTitle = null;
		String bookAuthor = null;
		
		String tempInput;
		String prompt = "";
		
		//input book Title
        isInputValid = false;
		prompt = Constants.strPROMPT_ENTER_BOOKTITLE ;
		do {
			tempInput = validateInput(input, prompt);

			if (tempInput != null) {
				
				if (tempInput.equalsIgnoreCase("X")) {
	        		isInputValid = true;
	        		System.out.println("Going back to main menu.");
	        		logger.info("User {}, selected X in Book Title. Going back to main menu.", user.getName());
	        		break;
				} else {
					bookTitle = tempInput;
					isInputValid = false;
	        		break;
				}
			}
			
		} while (!isInputValid);

        
		if (!tempInput.equalsIgnoreCase("X")) {
	
			//allow input book Author
	        isInputValid = false;
	        prompt = Constants.strPROMPT_ENTER_BOOKAUTHOR ;
	        
	        do {
	        	tempInput = validateInput(input, prompt);
	        	if (tempInput != null) {
					if (tempInput.equalsIgnoreCase("X")) {
		        		isInputValid = true;
		        		System.out.println("Going back to main menu.");
		        		logger.info("User {}, selected X in Book Author. Going back to main menu.", user.getName());
		        		break;
					} else {
						bookAuthor = tempInput;
						isInputValid = false;
						book = new Book(bookId, bookTitle, bookAuthor, false);
						return book;
					}
	        	}
	        } while (!isInputValid);
		}
		
		return null;
	}
	
	private void inputUser (Scanner input)  {
		boolean isInputValid;
		String tempInput;
		String prompt = "";
		
		//userId
		isInputValid = false;
		prompt = Constants.strPROMPT_USERID ;
    	do {
    		tempInput = validateInput(input, prompt);
    		if (tempInput != null) {
    			isInputValid = true;
    			user.setId(tempInput);
    		}
    	} while (!isInputValid);
    	
    	//userName
    	isInputValid = false;
		prompt = Constants.strPROMPT_USERNAME ;
    	do {
    		tempInput = validateInput(input, prompt);
    		
    		if (tempInput != null) {
        		if (tempInput.length() > Constants.maxLenUserName) {
            		isInputValid = false;
            		System.out.print (Constants.strERROR_INVALID_INPUT);
        		} else {
        			isInputValid = true;
        			user.setName(tempInput);
        			logger.info("User {} has logged in.", user.getName());
        		}
    		}
    	} while (!isInputValid);

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
	
	
	private String validateInput(Scanner input, String prompt) { 
		String ret = null;
		String tempInput;
    	boolean isValid = false;
    	
        do {
        	System.out.print(prompt);
            tempInput = input.nextLine().trim();
            
        	//check the input
        	if ((tempInput == null) || (tempInput == "") ) {
        		isValid = false;
    			System.out.print (Constants.strERROR_INVALID_INPUT);
    			
    			// Added logger warn for null or empty input
    			logger.warn("Input cannot be null or empty.");
        	} else {
    			//valid input
    			ret = tempInput;
    			isValid = true;
        		break;
        	}

        } while (!isValid);

    	return ret;
		
	}
	
	
    private final static boolean isNumeric(String string){
    	final String acceptedChars = "0123456789";
		boolean result = true;

		char[] chars = string.toCharArray();
    	for (int i = 0; i < chars.length; i++){
    		char c = chars[i];
        	if(acceptedChars.indexOf((c+"").toUpperCase()) < 0){
        		result = false;
        	}
    		if(!result){
    			return false;
    		}
    	}
    	return true;
    }

    
	// add code here
	
}
