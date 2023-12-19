package OrderFacade;

import Database.DataBaseConnection;
import Factory.Item.*;
import User.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.*;
import java.util.ArrayList;
import java.util.Collections;

public class Order {
    private int orderID;
    private IUser user;
    private ShoppingCart cart;
    private double total;
    private int deliveryDuration;
    private static Connection connection = DataBaseConnection.getConnection();



    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    /**
     * Calculate the total cost of the order based on items in the shopping cart.
     *
     * @return The calculated total cost.
     */
    public double calculateTotal() {
        for (A_Item item: this.cart.getItems()) {
            total += item.getPrice();
        }
        return total;
    }

    /**
     * Calculate the maximum delivery duration based on items in the shopping cart.
     *
     * @return The calculated maximum delivery duration.
     */
    public int calculateDeliveryDuration() {
        ArrayList < Integer > deliveryDurations = new ArrayList < > ();
        for (A_Item item: this.cart.getItems()) {
            deliveryDurations.add(item.getItemDeliveryDuration());
        }
        deliveryDuration = Collections.max(deliveryDurations);
        return deliveryDuration;
    }



    /**
     * Delete the order if it's not older than one day since acceptance.
     */


    // Retrieve the acceptance date from the database setDate(3, Date.valueOf(LocalDate.now()));
    protected LocalDateTime getAcceptanceDateFromDatabase(int orderID) throws SQLException {
        String query = "SELECT OrderDate FROM Orders WHERE OrderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String dateString = resultSet.getString("OrderDate");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    return LocalDate.parse(dateString, formatter).atStartOfDay();
                }
            }
        }
        return null;
    }


    public void deleteOrder(int orderID) {
        try {
            // Delete the order from the database
            String deleteOrderQuery = "DELETE FROM Orders WHERE OrderID = ?";
            try (PreparedStatement deleteOrderStatement = connection.prepareStatement(deleteOrderQuery)) {
                deleteOrderStatement.setInt(1, orderID);
                int rowsAffected = deleteOrderStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Order deleted successfully.");
                } else {
                    System.out.println("No order found or order is not eligible for deletion.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderItem(int orderID, int itemId) {
        try {
            String deleteOrderItemQuery = "DELETE FROM OrderItems WHERE OrderID = ? AND ItemID = ?";
            try (PreparedStatement deleteOrderItemStatement = connection.prepareStatement(deleteOrderItemQuery)) {
                deleteOrderItemStatement.setInt(1, orderID);
                deleteOrderItemStatement.setInt(2, itemId);
                int rowsAffected = deleteOrderItemStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Order item deleted successfully.");
                } else {
                    System.out.println("order item is not eligible for deletion.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveOrder(int id, int total, int duration, ShoppingCart cart) {
        int orderID = -1;
        try {
            // Insert order information into Orders table and get the auto-generated key
            String insertOrderQuery = "INSERT INTO Orders (UserID, Total, OrderDate, DeliveryDuration) VALUES (?, ?, ?, ?)";
            try (PreparedStatement orderStatement = connection.prepareStatement(insertOrderQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                orderStatement.setInt(1, id);
                orderStatement.setDouble(2, total);
                orderStatement.setDate(3, Date.valueOf(LocalDate.now()));
                orderStatement.setInt(4, duration);
                orderStatement.executeUpdate();

                // Retrieve the auto-generated key (OrderID)
                try (ResultSet generatedKeys = orderStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderID = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve auto-generated key for Orders table.");
                    }
                }
            }

            // Insert order items into OrderItems table
            String insertOrderItemsQuery = "INSERT INTO OrderItems (OrderID, ItemID, ItemName) VALUES (?, ?, ?)";
            try (PreparedStatement orderItemsStatement = connection.prepareStatement(insertOrderItemsQuery)) {
                for (A_Item item : cart.getItems()) {
                    // Set OrderID, ItemID, and ItemName inside the loop
                    orderItemsStatement.setInt(1, orderID);
                    orderItemsStatement.setInt(2, item.getItemID());
                    orderItemsStatement.setString(3, item.getItemName());

                    // Debug information
                    System.out.println("Inserting into OrderItems: OrderID=" + orderID + ", ItemID=" + item.getItemID());

                    orderItemsStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getOrderID() {
        return orderID;
    }

    public IUser getUser() {
        return user;
    }

    public void setUser(IUser user) {
        this.user = user;
    }

    public double getTotal() {
        return total;
    }

    public int getDeliveryDuration() {
        return deliveryDuration;
    }


    public ShoppingCart getCart() {
        return cart;
    }



}
