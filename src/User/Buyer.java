package User;

import Database.DataBaseConnection;
import Factory.Category.A_Item;
import Interface.BuyerMainPage;
import Interface.CategorySelectionPage;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Interface.RegisterPage;
import OrderFacade.*;

import static User.ProxyUser.BuyerMap;

public class Buyer extends IUser {

        private static Connection connection = DataBaseConnection.getConnection();

        String Fname;
        Creditcard card;
        Creditcard Regcard;
        Buyer registeredBuyer;
        OrderFacade order;

        ShoppingCart cart;
        Receipt receipt;
        Map<Integer, Map<Integer, String>> ordersMap = new HashMap<>();



        private Buyer buyer;

        public Buyer(int userID, String fname, String lname, String ssn, String email, String password, String Role,Creditcard card) {
                this.UserID = userID;
                this.Fname = fname;
                this.Lname = lname;
                this.ssn = ssn;
                this.email = email;
                this.password = password;
                this.Role = Role;

                this.card = card;

        }



        @Override
        public void login(String UserName, String password) {
                buyer = (Buyer) BuyerMap.get(UserName);
                BuyerMainPage page = new BuyerMainPage(this.buyer);
                page.setVisible(true);
        }

        public void setFname(String Fname, int id) {
                try {
                        String updateQuery = "UPDATE User SET Fname = ? WHERE userID = ?";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                                preparedStatement.setString(1, Fname);
                                preparedStatement.setInt(2, id);

                                int rowsAffected = preparedStatement.executeUpdate();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }
        public void setLname(String Lname, int id) {
                try {
                        String updateQuery = "UPDATE User SET Lname = ? WHERE userID = ?";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                                preparedStatement.setString(1, Lname);
                                preparedStatement.setInt(2, id);

                                int rowsAffected = preparedStatement.executeUpdate();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }
        public void setemail(String email, int id) {
                try {
                        String updateQuery = "UPDATE User SET email = ? WHERE userID = ?";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                                preparedStatement.setString(1, email);
                                preparedStatement.setInt(2, id);

                                int rowsAffected = preparedStatement.executeUpdate();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }
        public void setpassword(String password, int id) {
                try {
                        String updateQuery = "UPDATE User SET password = ? WHERE userID = ?";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                                preparedStatement.setString(1, password);
                                preparedStatement.setInt(2, id);

                                int rowsAffected = preparedStatement.executeUpdate();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }


        public Map<Integer, Map<Integer, String>> displayPendingOrders() {
                String selectQuery = "SELECT O.OrderID, OI.ItemName, OI.ItemID FROM Orders AS O JOIN OrderItems AS OI ON O.OrderID = OI.OrderID WHERE O.UserID = ?;";

                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                        preparedStatement.setInt(1, this.UserID);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {
                                int orderID = resultSet.getInt("OrderID");
                                int itemID = resultSet.getInt("ItemID");
                                String itemInfo = resultSet.getString("ItemName");

                                // Check if the ordersMap contains the orderID
                                if (this.ordersMap.containsKey(orderID)) {
                                        // Retrieve the map for the orderID and add the itemID and itemInfo
                                        this.ordersMap.get(orderID).put(itemID, itemInfo);
                                } else {
                                        // Create a new map for the orderID and add the itemID and itemInfo
                                        Map<Integer, String> itemMap = new HashMap<>();
                                        itemMap.put(itemID, itemInfo);
                                        this.ordersMap.put(orderID, itemMap);
                                }
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return ordersMap;
        }

        public Map<Integer, Map<Integer, String>> getOrdersMap() {
                return ordersMap;
        }


        public OrderFacade getOrder() {
                return order;
        }

        public void setOrder(OrderFacade order) {
                this.order = order;
        }

        @Override
        public ShoppingCart getCart() {
                return cart;
        }

        public void setCart(ShoppingCart cart) {
                this.cart = cart;
        }

        public Receipt getReceipt() {
                return receipt;
        }

        public void setReceipt(Receipt receipt) {
                this.receipt = receipt;
        }

        public void LeaveFeedback(int userID, int itemID, String feedback) {
                String insertQuery = "INSERT INTO Feedback (UserID, ItemID, FeedBack) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                        preparedStatement.setInt(1, userID);
                        preparedStatement.setInt(2, itemID);
                        preparedStatement.setString(3, feedback);
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                                System.out.println("Feedback inserted successfully.");
                        } else {
                                System.out.println("Failed to insert feedback.");
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }


        public void browseCategories() {
                System.out.println("Here are the available categories:");
                System.out.println("1. Electronics");
                System.out.println("2. Books");

        }

        public void addToCart(A_Item item) {
                cart.addItem(item);
        }


        public void updatePersonalInfo(String fname, String lname, String ssn) {
                if (fname != null)
                {
                        this.Fname = fname;
                }

                if (lname != null)
                {
                        this.Lname = lname;
                }

                if (ssn != null)
                {
                        this.ssn = ssn;
                }
        }



        public Creditcard getCard() {
                return card;
        }



}

