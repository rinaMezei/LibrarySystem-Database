create database LibrarySystem
go

use LibrarySystem
go

create table Genre(
GenreID int not null,
GenreDesc varchar(45) not null
constraint [PK_Genre] primary key (GenreID)
);

insert into Genre values(1,'Fiction')
insert into Genre values(2,'Non-Fiction')
insert into Genre values(3,'Science-Fiction')
insert into Genre values(4,'Historical-Fiction')
insert into Genre values(5,'Encyclopedia')
insert into Genre values(6,'Poetry')

create table Category(
CategoryID int not null,
CategoryDesc varchar(30) not null,
OverdueCharge as
	case CategoryDesc
	when 'Juvenile' then .05
	when 'Adult' then .10
	end
constraint [PK_Category] primary key (CategoryID),
constraint [CHK_CATEGORY] check (CategoryDesc in ('Juvenile', 'Adult', 'Reference'))
);

insert into Category values (1, 'Juvenile')
insert into Category values (2, 'Reference')
insert into Category values (3, 'Adult')

--delete from Category where CategoryDesc='Reference'

create table Author(
AuthorID int not null,
AuthorFName varchar(25) not null,
AuthorLName varchar(35) not null,
constraint [PK_Author] primary key (AuthorID)
);

insert into Author values(1,'JK', 'Rowling')
insert into Author values(2,'Dr.', 'Suess')
insert into Author values(3,'Rohl', 'Dahl')
insert into Author values(4,'William', 'Shakespeare')
insert into Author values(5,'Charles', 'Dickens')
insert into Author values(6,'John', 'Smith')


create table Publisher(
PublisherID int not null,
PublisherName varchar(45) not null,
PublisherAddress varchar(30) not null,
PublisherCity varchar(20) not null,
PublisherState varchar(30) not null,
PublisherZipCode varchar(10),
PublisherPhone varchar(10) not null,
PublisherContact varchar(35) not null,
constraint [PK_Publisher] primary key (PublisherID)
);

insert into Publisher values(1, 'Prenton House', '100 Canal St.', 'New York', 'NY', '10002', '2122222222', 'George')
insert into Publisher values(2, 'Simon and Shuter', '8 Boulevard Ave.', 'Springfield', 'RI', '10345', '2349087896', 'Archie')
insert into Publisher values(3, 'Penguin Hall', '1 Sussex Ave', 'Teaneck', 'NJ', '08624', '2016570909', 'Susan')

create table Condition(
ConditionID int not null,
ConditionDesc varchar(20) not null,
constraint [PK_CONDITION] primary key (ConditionID)
);

insert into Condition values (1, 'Poor')
insert into Condition values (2, 'New')
insert into Condition values (3, 'Used')
insert into Condition values (4, 'Good')

create table Book(
ISBN varchar(20) not null,
BookTitle varchar(45) not null,
BookCost decimal(4,2) not null,
BookPublisherID int not null,
BookCategoryID int not null,
BookGenreID int, --Genre is optional
BookConditionID int not null,
constraint [PK_Book] primary key(ISBN),
constraint [FK_Publisher] foreign key (BookPublisherID)
	references Publisher (PublisherID),
constraint [FK_Category] foreign key (BookCategoryID)
	references Category (CategoryID),
constraint [FK_Genre] foreign key (BookGenreID)
	references Genre (GenreID),
constraint [FK_CONDITION] foreign key (BookConditionID)
	references Condition (ConditionID)
);

insert into Book values ('2343224A6', 'The Cat in the Hat', 4.00, 1, 1,1,1)
insert into Book values ('4532B9003', 'Julius Caesar', 6.00, 2, 3,4,2)
insert into Book values ('898989C76', 'Harry Potter', 5.50, 3, 1,1,3)
insert into Book values ('543456D67', 'ABC Encyclopedia', 15.00, 1, 2,5,4)
insert into Book values ('809876E34', 'Charlie and the Chocolate Factory', 7.00, 2, 1,1,1)
insert into Book values ('345435F67', 'A Tale of Two Cities', 10.00, 3, 3,4,2)
insert into Book values ('454654G89', 'Horton Hears a Who', 5.00, 1, 1,1,3)
insert into Book values ('453432H89', 'Matilda', 7.00, 2, 1,1,4)
insert into Book values ('453432H15', 'Green Eggs and Ham', 3.25, 2, 1,1,1)

create table Book_Author(
AuthorID int not null,
ISBN varchar(20) not null
constraint [PK_Author_Book] primary key (AuthorID, ISBN),
constraint [FK_Author] foreign key (AuthorID)
	references Author (AuthorID),
constraint [FK_Book] foreign key (ISBN)
	references Book (ISBN)
);

insert into Book_Author values(1,'898989C76')
insert into Book_Author values(2,'454654G89')
insert into Book_Author values(2,'2343224A6')
insert into Book_Author values(3,'453432H89')
insert into Book_Author values(3,'809876E34')
insert into Book_Author values(4,'4532B9003')
insert into Book_Author values(5,'345435F67')
insert into Book_Author values(6,'543456D67')
insert into Book_Author values(2,'453432H15')

create table SalaryType(
SalaryTypeID int not null,
SalaryTypeDesc varchar(25) not null,
constraint [PK_SalaryType] primary key (SalaryTypeID),
constraint [CHK_DESC] check (SalaryTypeDesc in ('WEEKLY','BIWEEKLY', 'BIMONTHLY','MONTHLY','YEARLY','HOURLY'))
);

insert into SalaryType values (1,'YEARLY')
insert into SalaryType values (2,'HOURLY')
insert into SalaryType values (3, 'MONTHLY')
insert into SalaryType values (4, 'BIWEEKLY')
--Should we be dealing with the salary and vacation numbers?

create table EmployeeType(
EmployeeTypeID int not null,
EmployeeTypeDesc varchar(45) not null,
EmployeeSalaryTypeID int not null
constraint [PK_EMPLOYEETYPE] primary key (EmployeeTypeID), 
constraint [FK_EMPLOYEESALARYTYPE] foreign key (EmployeeSalaryTypeID)
	references SalaryType (SalaryTypeID)
);

insert into EmployeeType values(1, 'Librarian',1 )
insert into EmployeeType values(2,'IT Manager', 1)
insert into EmployeeType values(3,'Accountant', 1)
insert into EmployeeType values(4,'Network Administrator', 2)
insert into EmployeeType values(5,'Computer Programmer', 1)
insert into EmployeeType values(6,'Janitor', 2)
insert into EmployeeType values(7,'Floor Manager', 2)
insert into EmployeeType values(8,'Data Analyst', 1)


create table DegreeType(
DegreeTypeID int not null,
DegreeTypeDesc varchar(45) not null
constraint [PK_DEGREE] primary key (DegreeTypeID)
);

insert into DegreeType values(1,'Library Science')
insert into DegreeType values(2,'Accounting')
insert into DegreeType values(3,'Data Science')
insert into DegreeType values(4,'Computer Science')

create table Branch(
BranchID int not null,
BranchName varchar(45) not null,
BranchAddress varchar(35) not null,
BranchCity varchar(20) not null,
BranchState varchar (30) not null,
BranchZip varchar(10) not null,
BranchPhone varchar(10) not null,
BranchFax varchar(10) not null,
BranchHeadLibrarianID int 
constraint [PK_BRANCH] primary key (BranchID),

);

insert into Branch values (1,'Allwood Branch', '23 Allwood Pl', 'Clifton', 'NJ', '07012', '9737766787', '9737766788', null)
insert into Branch values(2,'Touro Library', '1602 Ave J', 'Brooklyn', 'NY', '11230', '7182527800', '7182527801', null)
insert into Branch values(3,'Passaic Public Library', '456 Gregory Ave', 'Passaic', 'NJ', '07055', '9734565456', '9734565457', null)
insert into Branch values(4,'Brooklyn Public Library', '21-15 Ocean Ave', 'Brooklyn', 'NY', '11229', '7183753037', '7183753038', null) 

create table Employee(
EmployeeID int not null,
EmployeeFName varchar(20) not null,
EmployeeLName varchar(35) not null,
EmployeeAddress varchar(35) not null,
EmployeeCity varchar(20) not null,
EmployeeState varchar(30) not null,
EmployeeZip varchar(10) not null,
EmployeeDOB date not null,
EmployeeHireDate date not null,
EmployeeVacaHours int default(112) not null,
EmployeeTypeID int not null,
EmployeeBranchID int not null,
EmployeeSalary decimal (12,2) not null,
constraint [PK_EMPLOYEE] primary key (EmployeeID),
constraint [FK_Type] foreign key (EmployeeTypeID)
	references EmployeeType (EmployeeTypeID),
constraint [FK_BRANCH] foreign key (EmployeeBranchID)
	references Branch (BranchID)
);

insert into Employee (EmployeeID, EmployeeFName, EmployeeLName, EmployeeAddress, EmployeeCity, EmployeeState, EmployeeZip, EmployeeDOB, 
EmployeeHireDate, EmployeeTypeID, EmployeeBranchID, EmployeeSalary)
values (1, 'Tehila', 'Raful', '1617 East 23rd St', 'Brooklyn','NY', '11229', '11-28-2000',GETDATE(),1,4,'50400'),
	(2, 'Rina', 'Mezei', '215 Aycrigg Ave', 'Passaic','NJ', '07055', '09-04-2000',GETDATE()-1,2,3, '100000'),
	(3, 'Chavie', 'Gross', '38 Woodward Ave', 'Clifton','NJ', '07012', '11-02-2000',GETDATE()-2,1,1,'50700'),
	(4, 'Donald', 'Trump', '1600 Pennsylvania Ave', 'Washington','DC', '20500', '06-14-1946',GETDATE()-10,1,2,'50300'),
	(5, 'Bill', 'Clinton', '55 West 125th St', 'New York','NY', '10027', '08-19-1946',GETDATE()-20,1,3,'50400'),
	(6, 'Joe', 'Biden', '34 Churchill Ave', 'Greenville','DE', '11329', '11-20-1942',GETDATE()-30,6,4,'20.00'),
	(7, 'Emily', 'Johnson', '1607 Ave J', 'Brooklyn','NY', '11230', '01-20-1990',GETDATE()-60,1,2,'40000')


create table HourLog(
EmployeeID int not null,
DateWorked date not null,
HoursWorked decimal(5,1) not null,
constraint [PK_HOURLOG] primary key (EmployeeID, DateWorked),
constraint [FK_EMPLOYEE] foreign key (EmployeeID)
	references Employee (EmployeeID)
);


insert into HourLog values (6, GETDATE(), 7)
insert into HourLog values (1, GETDATE(), 6)
insert into HourLog values (6, GETDATE()-1, 6)
insert into HourLog values (2, GETDATE(), 5)
insert into HourLog values (3, GETDATE(), 8)
insert into HourLog values (4, GETDATE(), 9)
insert into HourLog values (5, GETDATE(), 6)
insert into HourLog values (7, GETDATE(), 10)
insert into HourLog values (4, GETDATE()-2, 4)


create table EmployeeDegree(
EmployeeID int not null,
DegreeTypeID int not null,
constraint [PK_EMPLOYEEDEGREE] primary key (EmployeeID, DegreeTypeID),
constraint [FK_EMPLOYEE_DEGREE] foreign key (EmployeeID)
	references Employee (EmployeeID),
constraint [FK_DEGREE] foreign key (DegreeTypeID)
	references DegreeType (DegreeTypeID)
);

insert into EmployeeDegree values (1,1)
insert into EmployeeDegree values (2,4)
insert into EmployeeDegree values (3,1)
insert into EmployeeDegree values (4,1)
insert into EmployeeDegree values (5,1)
insert into EmployeeDegree values (7,1)

create table Librarian (
EmployeeID int not null,
LibrarianCellNum varchar(10) not null,
constraint [PK_LIBRARIAN] primary key (EmployeeID),
constraint [FK_LIBRARIAN_EMPLOYEE] foreign key (EmployeeID)
	references Employee (EmployeeID),
);

insert into Librarian values (1, '3477656789')
insert into Librarian values (3, '2013454565')
insert into Librarian values (4, '8005555566')
insert into Librarian values (5, '3477999988')
insert into Librarian values (7, '7185454545')

alter table Branch 
add constraint [FK_HEADLIBRARIAN] foreign key (BranchHeadLibrarianID)
	references Librarian (EmployeeID)

update Branch 
set BranchHeadLibrarianID = 1
where BranchID = 4

update Branch 
set BranchHeadLibrarianID = 3
where BranchID = 1

update Branch 
set BranchHeadLibrarianID = 4
where BranchID = 2

update Branch 
set BranchHeadLibrarianID = 5
where BranchID = 3

create table BorrowerGuardian(
GuardianID int not null,
GuardianFName varchar(25) not null,
GuardianLName varchar(35) not null,
GuardianAddress varchar(35) not null,
GuardianCity varchar (30) not null,
GuardianState varchar(20) not null,
GuardianZip varchar(10) not null,
GuardianPhoneNum varchar(10) not null,
constraint [PK_Guardian] primary key(GuardianID),
);

insert into BorrowerGuardian values (1, 'John','Smith', '45 Garden Pl', 'Lawrence', 'NY', '51660', '5166789878')
insert into BorrowerGuardian values (2, 'Jake','Rodriguez', '8 Waverly Way', 'Paramus', 'NJ', '21360', '2017899878')

create table Borrower(
BorrowerID int not null,
BorrowerFName varchar(20) not null,
BorrowerLName varchar(35) not null,
BorrowerAddress varchar(35) not null,
BorowerCity varchar (30) not null,
BorrowerState varchar(20) not null,
BorrowerZip varchar(10) not null,
BorrowerPhoneNum varchar(10) not null,
BorrowerDOB date not null,
GuardianID int
constraint [PK_BORROWER] primary key (BorrowerID),
constraint [FK_GUARDIAN] foreign key (GuardianID)
	references BorrowerGuardian (GuardianID),
constraint [CHK_Guardian] check (
  ( datediff(year, borrowerdob, getdate()) < 18 and  GuardianID is not null) OR
( datediff(year, borrowerdob, getdate()) >=18  and GuardianID is null)
)
);

insert into Borrower values (1, 'Jennie', 'Jameson', '67 Grand Ave', 'Monticello', 'NY', '56453', '7183454345', '08-17-2000',null)
insert into Borrower values (2, 'Ashley', 'Jameson', '67 Grand Ave', 'Monticello', 'NY', '56453', '7183454345', '07-10-1998',null)
insert into Borrower values (3, 'Joe', 'Smith', '45 Garden Pl', 'Lawrence', 'NY', '51660', '5166789878', '03-10-2010',1)
insert into Borrower values (4, 'Emily','Rodriguez', '8 Waverly Way', 'Paramus', 'NJ', '21360', '2017899878', '05-06-2008', 2)

--add constraint to check DOB of borrower
--How do we check for overdue charges on expired library cards?

create table LibraryCard(
LibraryCardID int not null,
DateIssued date not null,
ExpDate as dateadd(year,10, dateIssued),
BalanceDue decimal (5,2),
BorrowerID int
constraint [PK_LIBRARYCARD] primary key (LibraryCardID),
constraint [FK_BORROWER] foreign key (BorrowerID)
	references Borrower (BorrowerID)
);

insert into LibraryCard values (1,GETDATE()-40, 0,1)
insert into LibraryCard values (2,GETDATE()-30, 0,2)
insert into LibraryCard values (3,GETDATE()-10, 0,3)
insert into LibraryCard values (4,GETDATE()-20, 0,4)


--Do we need a constraint that BalanceDue is figured out automatically?
--Do we need to figure out if the library card is allowed to borrow a book or it owes more than 10?


create table BookBranch(
BookID int not null,
BranchID int not null,
ISBN varchar(20) not null,
isBorrowed bit default (0)not null,
constraint [PK_BOOKBRANCH] primary key (BookID),
constraint [FK_BRANCH_BOOK] foreign key (BranchID)
	references Branch (BranchID),
constraint [FK_BOOK_BRANCH] foreign key (ISBN)
	references Book (ISBN)
);



create table BorrowedBook(
BookID int not null,
LibraryCardID int not null,
DateCheckedOut date not null,
DateDue as dateadd(day,14, DateCheckedOut),
DateReturned date,
constraint [PK_BORROWEDBOOK] primary key (BookID, LibraryCardID, DateCheckedOut),
constraint [FK_LIBRARYCARD] foreign key (LibraryCardID)
	references LibraryCard (LibraryCardID),
constraint [FK_BORROWEDBOOK] foreign key (BookID)
	references BookBranch (BookID)
);


--USE [LibrarySystem1]
--GO
/****** Object:  Trigger [dbo].[CheckOutBook]    Script Date: 12/28/2020 5:03:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================

--when someone borrows a book or returns a book 
--then we want to make sure book isnt borroweed 
--if not than can check but change to 1 
--if returning then set date returned and is boroowed to 0 
CREATE TRIGGER [dbo].[CheckOutBook] 
   ON  [dbo].[BorrowedBook]
   AFTER  INSERT, DELETE 
AS 
BEGIN
	declare @BookID int;
	declare @LibraryCard int;
	declare @dateCheckedOut date;
	declare @DateDue date;
	declare @DateReturned date; 

	if exists (select * from inserted) --if check out a book
	BEGIN 
		select @BookID = BookID
		from inserted

		select @DateReturned = DateReturned
		from inserted

		if (select isBorrowed from BookBranch where @BookID = bookbranch.BookID) = 0  -- can be checked out 
			 BEGIN
			 if @DateReturned is null 
				BEGIN
					update BookBranch
					set isBorrowed = 1
					where BookID = @BookID
				END
			 END 

		else 
			BEGIN;
				 throw 60001 , 'Book already checked out', 1;
			END 
	END 

	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for trigger here

END


--USE [LibrarySystem1]
--GO
/****** Object:  Trigger [dbo].[ReturnBorrowBook]    Script Date: 12/28/2020 5:04:04 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE TRIGGER [dbo].[ReturnBorrowBook] 
   ON  [dbo].[BorrowedBook]
   AFTER UPDATE -- update means return a book 
AS 
BEGIN
	declare @BookID int;

	if exists (select * from inserted) --update 
	BEGIN 
		select @BookID = BookID
		from inserted
		
		if exists (select * from deleted) -- if returning book 
		 BEGIN 
			select @BookID = BookID
			from deleted

			update BookBranch
			set isBorrowed = 0
			where BookID = @BookID
		
		 END 
	END 
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for trigger here

END

--WE TRIED TO MAKE A TRIGGER FOR CHARGING OVERDUE BOOKS-IT WORKED THE FIRST FEW TIMES WE TRIED BUT THEN IT STOPPED WORKING, SO WE COMMENTED IT OUT


--SET ANSI_NULLS ON
--GO
--SET QUOTED_IDENTIFIER ON
--GO
---- =============================================
---- Author:		<Author,,Name>
---- Create date: <Create Date,,>
---- Description:	<Description,,>
---- =============================================
--CREATE TRIGGER [dbo].[ChargeOverDueBook] 
--   ON  [dbo].[BorrowedBook]
--   AFTER UPDATE -- update means return a book 
--AS 
--BEGIN
	
--	declare @BookID int;
--	declare @LibraryCard int;
--	declare @DateDue date;
--	declare @DateReturned date; 
--	declare @BookCategory int; 
--	declare @OverdueCharge decimal(5,2);
--	declare @BalanceDue decimal(5,2);

--	if exists (select * from inserted) --update 
--	BEGIN 
--		select @BookID = BookID
--		from inserted

--		select @LibraryCard = LibraryCardID
--		from inserted
--		where bookId = @BookID
			
--		select @DateReturned = DateReturned
--		from inserted
--		where bookId = @BookID
				
--		select @DateDue = DateDue
--		from inserted
--		where bookId = @BookID

--		if exists (select * from deleted) -- if returning book 
--		 BEGIN 
			
--			--if returned book 
--			if @DateReturned is not null
--			BEGIN 
			
--				--if there is a date diff then need to charge  
--				if DATEDIFF(day, @DateReturned, @DateDue) < 0 
--				BEGIN 

--				--inner join to get category of the borrowed book 
--				select @BookCategory = CategoryID
--				from Category
--					inner join Book 
--						on Category.CategoryID = Book.BookCategoryID
--							inner join BookBranch 
--								on Book.ISBN = BookBranch.ISBN
--				where BookBranch.BookID = @BookID

--				--get overdue charges of that category 
--				select @OverdueCharge = OverDueCharge 
--				from Category
--				where CategoryID = @BookCategory
			 
--				--get entire charge 
--				update LibraryCard 
--				set BalanceDue = BalanceDue + ( @OverdueCharge * ( (DATEDIFF(day, @DateReturned , @DateDue)) * (-1)))
--				where LibraryCardID =  @LibraryCard 
--				END
				
--			END 
		
--		 END 
--	END 
--	-- SET NOCOUNT ON added to prevent extra result sets from
--	-- interfering with SELECT statements.
--	SET NOCOUNT ON;

--    -- Insert statements for trigger here

--END


go
insert into BookBranch values(1, 1, '2343224A6', 0)
insert into BookBranch values(2, 2, '345435F67', 0)
insert into BookBranch values(3, 2, '4532B9003', 0)
insert into BookBranch values(4, 3, '453432H89', 0)
insert into BookBranch values(5, 4, '454654G89', 0)
insert into BookBranch values(6, 4, '543456D67', 0)
insert into BookBranch values(7, 4, '809876E34', 0)
insert into BookBranch values(8, 4, '898989C76', 0)
insert into BookBranch values(9, 3, '898989C76', 0)
insert into BookBranch values(10, 4, '898989C76', 0)
insert into BookBranch values(11, 1, '453432H15',0)

insert into BorrowedBook values (2, 1, '10-10-2020','10-20-2020')
insert into BorrowedBook values (3, 1, '10-10-2020','10-20-2020')
insert into BorrowedBook values (4, 1, '10-10-2020','10-20-2020')
insert into BorrowedBook values (5, 1, '10-10-2020','10-20-2020')
insert into BorrowedBook values (7, 1, '10-10-2020','10-20-2020')
insert into BorrowedBook values (8, 1, '10-10-2020','10-20-2020')
insert into BorrowedBook values (9, 1, '10-10-2020','10-20-2020')
insert into BorrowedBook values (3, 2, '09-20-2020','09-30-2020')
insert into BorrowedBook values (2, 2, '09-20-2020','09-30-2020')
insert into BorrowedBook values (8, 2, '09-20-2020','09-30-2020')
insert into BorrowedBook values (8, 3, '07-10-2020','07-24-2020')
insert into BorrowedBook values (4, 3, '07-10-2020','07-20-2020')
insert into BorrowedBook values (6, 3, '07-10-2020','07-20-2020')
insert into BorrowedBook values (2, 1, '10-21-2020','10-31-2020')
insert into BorrowedBook values (1, 1, GETDATE(),null)
insert into BorrowedBook values (2, 1, GETDATE(),null)
insert into BorrowedBook values (3, 1, GETDATE(),null)
insert into BorrowedBook values (1, 4, GETDATE(),null)
insert into BorrowedBook values (4, 3, GETDATE(),null)
