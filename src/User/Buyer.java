package User;

import Database.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class Buyer extends IUser {
        Creditcard card;
        private static Connection connection = DataBaseConnection.getConnection();
        private int userID;

        public Buyer(int userID, String fname, String lname, String ssn, String email, String password, String Role) {
                UserID = userID;
                Fname = fname;
                Lname = lname;
                this.ssn = ssn;
                this.email = email;
                this.password = password;
                this.Role = Role;

                getCard();
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
        void login(String UserName, String password) {
                //Open the browsing page
        }

        public Creditcard getCard() {
                // Modify the SQL query to include a placeholder for userID
                String query = "SELECT cardNumber, CVV, Balance, ExpiryDate FROM CreditCard WHERE userID = ?";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                        // Set the value for the placeholder
                        statement.setInt(1, this.userID);

                        // Execute the query
                        try (ResultSet resultSet = statement.executeQuery()) {
                                while (resultSet.next()) {
                                        String cardNumber = resultSet.getString("cardNumber");
                                        String CVV = resultSet.getString("CVV");
                                        double balance = resultSet.getDouble("Balance");
                                        LocalDate expiryDate = resultSet.getDate("ExpiryDate").toLocalDate();

                                        // Initialize the card object with the retrieved information
                                        this.card = new Creditcard(this.userID, cardNumber, CVV, balance, expiryDate);
                                }
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return card;
        }

}