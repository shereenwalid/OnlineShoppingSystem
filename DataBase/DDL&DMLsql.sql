CREATE DATABASE IF NOT EXISTS OnlineShoppingSystem;
Use OnlineShoppingSystem;




-- Create the User table
CREATE TABLE User (
  userID INT PRIMARY KEY AUTO_INCREMENT,
  Fname VARCHAR(50),
  Lname VARCHAR(50),
  ssn VARCHAR(9),
  email VARCHAR(100),
  password VARCHAR(100)
);



ALTER TABLE User
ADD Role varchar(20);



UPDATE User
SET Role = 'Buyer'
WHERE userID IN (1, 2, 3);

UPDATE User
SET Role = 'Admin'
WHERE userID IN (4, 5);



select * from User;

-- Create the CreditCard table
CREATE TABLE CreditCard (
  userID INT,
  cardNumber VARCHAR(16),
  CVV VARCHAR(3),
  Balance DECIMAL(10, 2),
  ExpiryDate DATE,
  FOREIGN KEY (userID) REFERENCES User(userID)
);

-- Create the Categories table
CREATE TABLE Categories (
  CategoryID INT PRIMARY KEY,
  Name VARCHAR(50)
);

-- Create the Items table
CREATE TABLE Items (
  ItemID INT PRIMARY KEY AUTO_INCREMENT,
  ItemName VARCHAR(100),
  price DECIMAL(10, 2),
  description VARCHAR(200),
  amount INT,
  salePercent DECIMAL(5, 2),
  CategoryID INT,
  FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);

-- Create the Cart table
CREATE TABLE Cart (
  CartID INT PRIMARY KEY,
  UserID INT,
  ItemID INT,
  Quantity INT,
  FOREIGN KEY (UserID) REFERENCES User(userID),
  FOREIGN KEY (ItemID) REFERENCES Items(ItemID)
);

-- Create the Orders table
CREATE TABLE Orders (
  OrderID INT PRIMARY KEY,
  UserID INT,
  ItemID INT,
  Quantity INT,
  Total DECIMAL(10, 2),
  FOREIGN KEY (UserID) REFERENCES User(userID),
  FOREIGN KEY (ItemID) REFERENCES Items(ItemID)
);

-- Create the Feedback table
CREATE TABLE Feedback (
  UserID INT,
  ItemID INT,
  FeedBack VARCHAR(200),
  FOREIGN KEY (UserID) REFERENCES User(userID),
  FOREIGN KEY (ItemID) REFERENCES Items(ItemID)
);
-- Insertions for User table
INSERT INTO User (userID, Fname, Lname, ssn, email, password) VALUES
   (1, 'John', 'Doe', '123456789', 'john@example.com', 'password123'),
  (2, 'Alice', 'Smith', '987654321', 'alice@example.com', 'password456'),
  (3, 'Bob', 'Johnson', '111223333', 'bob@example.com', 'password789'),
  (4, 'Eva', 'Williams', '444556666', 'eva@Company.com', 'AdminPassCompany'),
  (5, 'Chris', 'Brown', '789123456', 'chris@Company.com', 'AdminPassCompany');

-- Insertions for CreditCard table
INSERT INTO CreditCard (userID, cardNumber, CVV, Balance, ExpiryDate) VALUES
(1, '1111222233334444', '123', 1000.00, '2025-01-01'),
  (2, '4444555566667777', '456', 500.00, '2024-06-01'),
  (3, '8888999900001111', '789', 200.00, '2023-12-01');
  
INSERT INTO Categories (CategoryID, Name) VALUES
  (1, 'Books'),
  (2, 'Electronics');

-- Insertions for Items table
INSERT INTO Items (ItemID, ItemName, price, description, amount, salePercent, CategoryID) VALUES
  (1, 'Atomic Habits', 19.99, 'Atomic Habits is a bestselling book by James Clear that explores the power of small habits in creating remarkable changes.', 50, 0, 1),
  (2, 'The Great Gatsby', 12.99, 'A classic novel by F. Scott Fitzgerald, capturing the spirit of the Roaring Twenties.', 30, 0, 1),
  (3, 'Smart TV', 499.99, 'High-definition smart TV with advanced features for an immersive viewing experience.', 10, 0, 2),
  (4, 'Wireless Earbuds', 79.99, 'High-quality wireless earbuds with noise cancellation for an immersive audio experience.', 15, 5, 2),
  (5, 'Harry Potter and the Sorcerer\'s Stone', 15.99, 'The first book in the Harry Potter series, a magical adventure by J.K. Rowling.', 40, 0, 1),
  (6, 'Smartphone', 599.99, 'Feature-packed smartphone with the latest technology for seamless communication.', 8, 0, 2);

-- Insertions for Cart table
INSERT INTO Cart (CartID, UserID, ItemID, Quantity) VALUES
  (1, 1, 1, 2),
  (2, 2, 3, 1),
  (3, 3, 5, 3),
  (4, 2, 2, 1),
  (5, 1, 4, 2);

-- Insertions for Orders table
INSERT INTO Orders (OrderID, UserID, ItemID, Quantity, Total) VALUES
  (1, 1, 1, 2, 39.98),
  (2, 2, 3, 1, 299.99),
  (3, 3, 5, 3, 89.97),
  (4, 2, 2, 1, 499.99),
  (5, 1, 4, 2, 29.98);

-- Insertions for Feedback table
INSERT INTO Feedback (UserID, ItemID, FeedBack) VALUES
  (1, 1, 'Great book, loved it!'),
  (2, 3, 'Sofa is very comfortable'),
  (3, 5, 'High-quality football'),
  (4, 2, 'Excellent TV with smart features'),
  (5, 4, 'Nice T-shirt, fits well');

