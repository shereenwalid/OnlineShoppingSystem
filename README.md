# OnlineShoppingSystem

The Online Shopping System is a Java-based desktop application designed to streamline the process of buying and selling goods. \n Users can explore various product categories, add items to their cart, and place orders efficiently. The system incorporates essential features like user registration, order management, and feedback submission.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [How to Run](#how-to-run)
- [Usage](#usage)
- [Design Patterns](#design-patterns)
- [License](#license)

## Features

- **User Authentication:** Users can register or sign in, providing personal information for first-time users.

- **Category Selection:** Browse and explore various product categories, such as clothes, appliances, and accessories.

- **Item Management:** Admins can add, edit, or delete items, each with details and images. The system ensures stock availability.

- **Shopping Cart:** Users can add items to their cart, review the items, and proceed to checkout. The system checks stock availability and calculates the total amount.

- **Order Processing:** Calculate total receipt, apply discounts, and estimate delivery time. Users can cancel orders or specific items within a specified period.

- **Feedback System:** Users can leave feedback for purchased items.

## Requirements

- Java Development Kit (JDK)
- Java Swing library
- MySQL Database

## How to Run

1. Ensure you have the Java Development Kit (JDK) installed on your system.
2. Clone the repository to your local machine.
3. Set up the MySQL database with the provided schema.
4. Compile and run the `CategorySelectionPage.java` file to start the application.

```bash
javac Interface/CategorySelectionPage.java
java Interface.CategorySelectionPage
```

## Usage

1. Launch the application by running the `CategorySelectionPage.java` file.
2. Register or sign in.
3. Browse through categories, add items to the cart.
4. Review the cart, proceed to checkout, and confirm the order.
5. Leave feedback for purchased items.

## Design Patterns

### Creational Patterns

- **Singleton Pattern:** Ensures a single instance of the database connection and one cart per user.
- **Prototype Pattern:** Used to create copies of existing items.

### Structural Patterns

- **Proxy Pattern:** Authenticates users during login, redirecting based on roles.
- **Factory Pattern:** Creates category objects.

## License

This project is licensed under the [MIT License](LICENSE).
