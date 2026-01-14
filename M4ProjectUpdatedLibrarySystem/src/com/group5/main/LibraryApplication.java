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

import com.group5.model.*;
import com.group5.service.LibraryImpl;
import com.group5.service.LibraryService;
import com.group5.constants.Constants;


public class LibraryApplication {
	
	private User user;
//	private Loan loan;
	
//	private Library library;
	private LibraryService libraryService;
	
	public LibraryApplication () {
		// initial user creation
		this.user = new User();
//		this.loan = new Loan();

		// initial library creation
//		this.library = new Library();
		this.libraryService = new LibraryImpl();
		libraryService.initializeList();
		

	}
	
	
	
	// Main Application Logic, call this in your Main.java
	public void start() {
		
		// add code here
		
        Scanner input = new Scanner(System.in);
    	int rowCount;
    	

    	inputUser(input);
    	welcomeMenuChoice();
    	
    	
    	//initially display the menu
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
	            	libraryService.displayAllBooks();
	            	//exit to menu
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;
	
	                
	            case '2':
	            	//[2] Display Available Books
	            	System.out.println(Constants.strDISPLAY_SELECTED_OPTION2);
	            	libraryService.displayAvailableBooks();
	            	//exit to menu
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;
	
	                
	            case '3':
	            	//[3] Display All Borrowed Books 
	            	System.out.println(Constants.strDISPLAY_SELECTED_OPTION3);
	            	libraryService.displayAllBorrowedBooks();
	            	//exit to menu
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;
	
	                
	            case '4':
	            	//[4] Borrow Book
	            	displayLibraryMenu();
	            	System.out.println(Constants.strDISPLAY_SELECTED_OPTION4);
	            	rowCount = libraryService.displayAvailableBooks();
	            	if (rowCount > 0) {
		            	String bookIdChoice = askBookChoice(input);
		            	if (bookIdChoice != "") {
		            		//book found, ask user to input loan ID
		            		String createdLoanId = askLoanId(input);
		            		if (createdLoanId != "") {
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
	                System.out.println(Constants.strDISPLAY_SELECTED_OPTION6);
	                Book book = inputNewBook(input);
	                if (book != null) {
		                libraryService.addBook(book);
	                }
	                //exit to menu
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;

	                
	            case '7':
	            	//[7] Remove Book
	                System.out.println(Constants.strDISPLAY_SELECTED_OPTION7);
	            	rowCount = libraryService.displayAllBooks();
	            	if (rowCount > 0) {
		            	String bookIdChoice = askBookChoiceForRemoval(input);
		            	if (bookIdChoice != "") {
		            		//book found, delete book
		            		libraryService.deleteBook(bookIdChoice);
		            	}
	            	} 
	            	//exit to menu
	            	displayLibraryMenu();
	            	askMenuChoice();
	                break;

	                
	            case '8':
	            	//[8] Update Book
	                System.out.println(Constants.strDISPLAY_SELECTED_OPTION8);
	            	rowCount = libraryService.displayAllBooks();
	            	if (rowCount > 0) {
	            		String bookIdChoice = askBookChoice(input);
		            	if (bookIdChoice != "") {
		            		//book found, ask user to input the updated book title and author
		            		Book updatedBook = askUpdatedBook(input, bookIdChoice);
		            		if (updatedBook != null) {
			            		libraryService.updateBook(updatedBook);
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
	        		break;

        		} else {
	            	bookFound = libraryService.findBook(tempInput);

	            	if (bookFound) {
	        			//check if currently loaned
	            		if (libraryService.isBookBorrowed(tempInput)) {
	        				//currently borrowed
	        				System.out.print (Constants.strERROR_BOOK_OUT);
	        				bookFound = false;
	        			} else {
			        		ret = tempInput;
			        		bookFound = true;
			        		break;
	        			}
	        		} else {
	        			System.out.print (Constants.strERROR_BOOK_NOT_FOUND);
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
    		if (tempInput != null) {
        		if (tempInput.equalsIgnoreCase("X")) {
	        		loanSearch = true;
	        		break;
	        	} else {
	        		loanSearch = libraryService.findLoan(tempInput);
	        		
	        		if (!loanSearch) {
	        			System.out.print (Constants.strINVALID_LOAN_ID);
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
	
	
	private Book inputNewBook(Scanner input) {
		Book book = null;
		boolean isInputValid = false;
		String bookId = null;
		String bookTitle = null;
		String bookAuthor = null;
		
		String tempInput;
		String prompt = "";
		
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
	        	} else {

	        		isInputValid = libraryService.findBook(tempInput);

	            	if (isInputValid) {
	    				System.out.print (Constants.strERROR_BOOK_EXIST);
	    				isInputValid = false;
	            		
	    			} else {
	    				bookId = tempInput;
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
						isInputValid = false;
		        		break;
					}
				}
				
			} while (!isInputValid);
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
						isInputValid = false;
		        		break;
					}
	        	}
	        } while (!isInputValid);
		}

        //create Book 
        if ((bookId != null && bookTitle !=null && bookAuthor != null)) {
        	if (!(bookId.equalsIgnoreCase("X") || bookTitle.equalsIgnoreCase("X") || bookAuthor.equalsIgnoreCase("X"))) {
                book = new Book(bookId, bookTitle, bookAuthor, false);
        	}
        }
		
		return book;
	}
	
	
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
	        		bookFound = true;
	        		break;

        		} else {
	            	bookFound = libraryService.findBook(tempInput);

	            	if (bookFound) {
		        		ret = tempInput;
		        		bookFound = true;
		        		break;
	        		} else {
	        			System.out.print (Constants.strERROR_BOOK_NOT_FOUND);
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
		        		break;
					} else {
						bookAuthor = tempInput;
						isInputValid = false;
		        		break;
					}
	        	}
	        } while (!isInputValid);
		}

        //update Book 
        if ((bookTitle !=null && bookAuthor != null)) {
        	if (!(bookTitle.equalsIgnoreCase("X") || bookAuthor.equalsIgnoreCase("X"))) {
                book = new Book(bookId, bookTitle, bookAuthor, false);
        	}
        }
		return book;
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
