package User;

import Database.DataBaseConnection;
import Interface.CategorySelectionPage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import OrderFacade.*;

public class Buyer extends IUser {

        private static Connection connection = DataBaseConnection.getConnection();
        Creditcard card;

        OrderFacade order;

        ShoppingCart cart;
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


        //        private List<ShoppingCart> carts;
        //        private List<Order> Orders;

        public void browseCategories() {

        }

        public void addToCart() {

        }

        public void CancelOrder() {

        }
        public void GetFeedBack() {

        }

        public void updatePersonalInfo() {

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

        @Override
        public void login(String UserName, String password) {
                // will use the UserName for hello msg
                CategorySelectionPage page = new CategorySelectionPage(this);
                page.setVisible(true);
        }


        public Creditcard getCard() {
                return card;
        }
}




//        public void Card(int userID) {
//                String query = "SELECT cardNumber, CVV, Balance, ExpiryDate FROM CreditCard WHERE userID = ?";
//                try (PreparedStatement statement = connection.prepareStatement(query)) {
//                        // Set the parameter for the prepared statement
//                        statement.setInt(1, userID);
//
//                        try (ResultSet resultSet = statement.executeQuery()) {
//                                if (resultSet.next()) {
//                                        String cardNumber = resultSet.getString("cardNumber");
//                                        String CVV = resultSet.getString("CVV");
//                                        double balance = resultSet.getDouble("Balance");
//                                        LocalDate expiryDate = resultSet.getDate("ExpiryDate").toLocalDate();
//
//                                        setCard(new Creditcard(0, cardNumber, CVV, balance, expiryDate));
//                                }
//
//                        }
//                } catch (SQLException e) {
//                        e.printStackTrace();
//                }
//
//        }


