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
- [ ] Create user validation in Login
- [ ] Revise Option [2] Display Available Books
- [ ] Revise Option [3] Display All Borrowed Books
- [ ] Revise Option [4] Borrow Book
- [ ] Revise Option [5] Return Book
- [ ] Revise Option [6] Add Book
- [ ] Revise Option [7] Remove Book
- [ ] Revise Option [8] Update Book
