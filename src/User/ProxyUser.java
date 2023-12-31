package User;
import Database.*;
import Interface.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ProxyUser extends IUser {
    private static Connection connection = DataBaseConnection.getConnection();
    public static Map<String, IUser> AdminMap;
    public static Map<String, IUser> BuyerMap;

    public ProxyUser() {
        BuyerMap = new HashMap<>();
        AdminMap = new HashMap<>();
        FillUserMap();
    }

    private void FillUserMap() {
        String query = "SELECT U.*, C.cardNumber, C.CVV FROM User U LEFT JOIN CreditCard C ON U.userID = C.userID";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int userID = resultSet.getInt("userID");
                String Fname = resultSet.getString("Fname");
                String Lname = resultSet.getString("Lname");
                String ssn = resultSet.getString("ssn");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("Role");

                String cardNumber = resultSet.getString("cardNumber");
                String CVV = resultSet.getString("CVV");
                if (role.equals("Admin")) {
                    AdminMap.put(Fname, new Admin(userID, Fname, Lname, ssn, email, password, role));
                } else {
                    // Instantiate a Creditcard object
                    Creditcard card = new Creditcard(userID, cardNumber, CVV);
                    // Instantiate a Buyer object and pass the Creditcard object
                    Buyer buyer = new Buyer(userID, Fname, Lname, ssn, email, password, role, card);
                    // Put the Buyer object in the BuyerMap
                    BuyerMap.put(Fname, buyer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void login(String Fname, String password) {
        if (AdminMap.containsKey(Fname)) {
            Admin admin = (Admin) AdminMap.get(Fname);
            if (admin.getPassword().equals(password)) {
                admin.login(Fname, password);
            } else {
                System.out.println("Invalid password for admin.");
            }
        } else if (BuyerMap.containsKey(Fname)) {
            Buyer buyer = (Buyer) BuyerMap.get(Fname);
            if (buyer.getPassword().equals(password)) {
                buyer.login(Fname, password);
            }
        } else if(!(BuyerMap.containsKey(Fname))){
            new RegisterPage();
        }
    }



    public void BuyerRegister(String Fname, String Lname, String ssn, String email, String password, String Role,
                              String CardNumber, String Cvv) {
        try {
            String insertQuery = "INSERT INTO User (Fname, Lname, ssn, email, password, Role) VALUES (?, ?, ?, ?, ?, ?)";
            // To return any auto-generated keys after the execution of the query
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                // Set parameters for the new buyer
                insertStatement.setString(1, Fname);
                insertStatement.setString(2, Lname);
                insertStatement.setString(3, ssn);
                insertStatement.setString(4, email);
                insertStatement.setString(5, password);
                insertStatement.setString(6, Role);

                // Execute the insertion
                int rowsAffected = insertStatement.executeUpdate();

                // Check if the insertion was successful
                if (rowsAffected > 0) {
                    // Retrieve the generated keys auto-increment ID column
                    try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int newBuyerID = generatedKeys.getInt(1);

                            // Create a new Creditcard object
                            Creditcard card = new Creditcard(newBuyerID, CardNumber, Cvv);

                            // Create a new Buyer object
                            Buyer newBuyer = new Buyer(newBuyerID, Fname, Lname, ssn, email, password, Role,card);

                            // Add the new buyer to the BuyerMap
                            BuyerMap.put(Fname, newBuyer);

                            // Insert the new card information into the CreditCard table
                            insertCardIntoDatabase(card);

                            System.out.println("Buyer registered successfully with a new card.");
                        }
                    }
                } else {
                    System.out.println("Failed to register the buyer.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Call the login method with the provided credentials
        login(Fname, password);
    }

    // Method to insert card information into the database
    private void insertCardIntoDatabase(Creditcard card) {
        try {
            String insertCardQuery = "INSERT INTO CreditCard (userID, cardNumber, CVV) VALUES (?, ?,?)";
            try (PreparedStatement insertCardStatement = connection.prepareStatement(insertCardQuery)) {
                // Set parameters for the new card
                insertCardStatement.setInt(1, card.getUserID());
                insertCardStatement.setString(2, card.getCardNumber());
                insertCardStatement.setString(3, card.getCvv());

                // Execute the insertion
                int rowsAffected = insertCardStatement.executeUpdate();

                // Check if the insertion was successful
                if (rowsAffected > 0) {
                    System.out.println("Card information inserted into the database successfully.");
                } else {
                    System.out.println("Failed to insert card information into the database.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateCardInfo(String newCardNumber, String newCvv) {
        String cardQuery = "UPDATE CreditCard SET cardNumber = ?, CVV = ? WHERE userID = ?";
        try (PreparedStatement cardStatement = connection.prepareStatement(cardQuery)) {
            cardStatement.setString(1, newCardNumber);
            cardStatement.setString(2, newCvv);
            cardStatement.setInt(3, UserID);

            // Execute the query
            int rowsAffected = cardStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Card information updated successfully.");
            } else {
                System.out.println("Failed to update card information. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


