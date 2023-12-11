package OrderFacade;

import Database.DataBaseConnection;
import Factory.Item.*;
import Factory.*;
import Factory.Registry.*;
import User.*;

import java.sql.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Order {
    private int orderID;
    private IUser user;
    private ShoppingCart cart;
    private double total;
    private int deliveryDuration;
    private static Connection connection = DataBaseConnection.getConnection();

    public ShoppingCart getCart() {
        return cart;
    }

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

    public void ConfirmOrder() {
        total = calculateTotal();
        deliveryDuration = calculateDeliveryDuration();
        saveOrder();
    }

    /**
     * Delete the order if it's not older than one day since acceptance.
     */
    public void deleteOrder() {
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

    // Retrieve the acceptance date from the database
    protected LocalDateTime getAcceptanceDateFromDatabase() throws SQLException {
        String query = "SELECT Date FROM Orders WHERE OrderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Timestamp timestamp = resultSet.getTimestamp("AcceptanceDate");
                    return timestamp.toLocalDateTime();
                }
            }
        }
        return null;
    }

    /**
     * Delete an order item if it's older than one day since acceptance.
     *
     * @param item The item to delete.
     */
    public void deleteOrderItem(A_Item item) {

        try {
            String deleteOrderItemQuery = "DELETE FROM OrderItems WHERE OrderID = ? AND ItemID = ?";
            try (PreparedStatement deleteOrderItemStatement = connection.prepareStatement(deleteOrderItemQuery)) {
                deleteOrderItemStatement.setInt(1, orderID);
                deleteOrderItemStatement.setInt(2, item.getItemID());
                int rowsAffected = deleteOrderItemStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // To update the total
                    cart.removeItem(item);
                    calculateTotal();
                    System.out.println("Order item deleted successfully.");
                } else {
                    System.out.println("No order item found or order item is not eligible for deletion.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Store order and order items in the database.
     */
    public void saveOrder() {
        try {
            // Insert order information into Orders table and get the auto-generated key
            String insertOrderQuery = "INSERT INTO Orders (UserID, Total, DeliveryDuration, Date) VALUES (?, ?, ?, NOW())";
            try (PreparedStatement orderStatement = connection.prepareStatement(insertOrderQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                orderStatement.setInt(1, user.getUserID());
                orderStatement.setDouble(2, total);
                orderStatement.setInt(3, deliveryDuration);
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
            String insertOrderItemsQuery = "INSERT INTO OrderItems (OrderID, ItemID) VALUES (?, ?)";
            try (PreparedStatement orderItemsStatement = connection.prepareStatement(insertOrderItemsQuery)) {
                for (A_Item item: cart.getItems()) {
                    orderItemsStatement.setInt(1, orderID);
                    orderItemsStatement.setInt(2, item.getItemID());
                    orderItemsStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Return list of order items needed for displaying
     */
    public List < A_Item > OrderItems() {
        List < A_Item > items = new ArrayList < > ();
        String query = "SELECT ItemName, CategoryID FROM Items " +
                "JOIN OrderItems ON Items.ItemID = OrderItems.ItemID " +
                "WHERE OrderItems.OrderID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderID);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String itemName = resultSet.getString("ItemName");
                    int categoryID = resultSet.getInt("CategoryID");

                    if (categoryID == 1) {
                        I_ItemRegistry reg = RegistryFactory.getCategoryRegistry("Book");
                        items.add(reg.getProduct(itemName));
                    } else if (categoryID == 2) {
                        I_ItemRegistry reg = RegistryFactory.getCategoryRegistry("Electronics");
                        items.add(reg.getProduct(itemName));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
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



}