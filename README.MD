# Group 5 Library System  
## How to create database, table and insert dummy data.
```sql
psql.exe -U postgres -f create-database.sql
psql.exe -U postgres -d group5 -f create-table.sql
psql.exe -U postgres -d group5 -f insert-data.sql
```  
The command above will create the following tables.
- users
- book
- loan

Things to do:
- [x] Create SQL script for creation of database, table and insert data.
- [x] Create pom.xml.
- [x] Create connection to database.
- [x] Initialize Books.
- [x] Create user validation in Login
- [x] Revise Option [1] Display All Books
- [x] Revise Option [2] Display Available Books
- [x] Revise Option [3] Display All Borrowed Books
- [x] Revise Option [4] Borrow Book
- [x] Revise Option [5] Return Book
- [x] Revise Option [6] Add Book
- [x] Revise Option [7] Remove Book
- [x] Revise Option [8] Update Book
