package User;

import Database.DataBaseConnection;
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
                // will use the UserName for hello msg
                BuyerMainPage page = new BuyerMainPage(this.buyer);
                page.setVisible(true);
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
                        // Handle the exception as needed
                }
        }

        // Setters for update information
        // to dipslay and delete if he wants
        //        private List<Order> Orders;

        public void browseCategories() {
          System.out.println("Here are the available categories:");
            System.out.println("1. Electronics");
               System.out.println("2. Books");
   
        }

        public void addToCart(Item item) {
            cart.additem(item);
        }

        public void CancelOrder() {
          Orders.deleteOrder();
        }
        public void GetFeedBack() {
          feedback.GetFeedBack();
        }

        public void updatePersonalInfo(String fname, String lname, String ssn) {
            if (fname != null) 
       {
           this.fname = fname;
       }
       
       if (lname != null) 
       {
           this.lname = lname;
       }

        if (ssn != null) 
       {
           this.ssn = ssn;
       }
        }

        public void setCard(Creditcard card) {
                this.card = card;
        }

        //
        //        public <any> getCarts() {
        //            return carts;
        //        }
        //
        //
        //        public <any> getOrders() {
        //            return Orders;
        //        }

        //        public void setCarts(<any> carts) {
        //            this.carts = carts;
        //        }
        //
        //
        //        public void setOrders(<any> Orders) {
        //            this.Orders = Orders;
        //        }

//        public static Map< String, IUser > BuyerMap;


        public void leaveFeedback(){

        }


        public Creditcard getCard() {
                return card;
        }
}



//        public void updatePersonalInfo() {
//                String query = "UPDATE users SET firstName = ?, lastName = ?, email = ? WHERE userID = ?";
//                try (PreparedStatement statement = connection.prepareStatement(query)) {
//                        statement.setString(1, Fname);
//                        statement.setString(2, Lname);
//                        statement.setString(3, email);
//                        statement.setInt(4, userID);
//
//                        // Execute the query
//                        int rowsAffected = statement.executeUpdate();
//
//                        if (rowsAffected > 0) {
//                                System.out.println("Personal information updated successfully.");
//                        } else {
//                                System.out.println("Failed to update personal information. Please try again.");
//                        }
//                } catch (SQLException e) {
//                        e.printStackTrace();
//                }
//        }
