package Factory.Category;

import Database.DataBaseConnection;
import java.sql.*;
import java.sql.Connection;

public abstract class A_Item implements Cloneable {
    private int itemID;
    private String itemName;
    private double price;
    private int amount;
    private String description;
    private int salePercent;
    private int categoryID;
    private int ItemDeliveryDuration;

    private static Connection connection = DataBaseConnection.getConnection();

    public A_Item(int itemID, String itemName, double price, int amount, String description, int salePercent, int categoryID, int itemDeliveryDuration) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.price = price;
        this.amount = amount;
        this.description = description;
        this.salePercent = salePercent;
        this.categoryID = categoryID;
        this.ItemDeliveryDuration = itemDeliveryDuration;
    }

    @Override
    public A_Item clone() throws CloneNotSupportedException {
        return (A_Item) super.clone();
    }

    public void setAmount(int amount) {
        this.amount = amount;
        if (itemID >= 0) {
            String updateStatement = "UPDATE Items SET amount = ? WHERE ItemID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)) {
                preparedStatement.setDouble(1, amount);
                preparedStatement.setInt(2, this.itemID);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


        public void setPrice(double price) {
            this.price = price;
            if (itemID >= 0) {
                String updateStatement = "UPDATE Items SET price = ? WHERE ItemID = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)) {
                    preparedStatement.setDouble(1, price);
                    preparedStatement.setInt(2, this.itemID);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    public void setDescription(String description) {
        this.description = description;

        if (itemID >= 0) {
            String updateStatement = "UPDATE Items SET description = ? WHERE ItemID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)) {
                preparedStatement.setString(1, description);
                preparedStatement.setInt(2, this.itemID);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSalePercent(int salePercent) {
        this.salePercent = salePercent;

        if (itemID >= 0) {
            String updateStatement = "UPDATE Items SET salePercent = ? WHERE ItemID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)) {
                preparedStatement.setDouble(1, salePercent);
                preparedStatement.setInt(2, this.itemID);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setItemDeliveryDuration(int itemDeliveryDuration) {
        ItemDeliveryDuration = itemDeliveryDuration;

        if (itemID >= 0) {
            String updateStatement = "UPDATE Items SET itemDeliveryDuration = ? WHERE ItemID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)) {
                preparedStatement.setDouble(1, itemDeliveryDuration);
                preparedStatement.setInt(2, this.itemID);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }



    public int getSalePercent() {
        return salePercent;
    }

    public int getCategoryID() {
        return categoryID;
    }
    public int getItemDeliveryDuration() {return ItemDeliveryDuration;}

    public void applySale(double discountPercentage) {
        double discountAmount = price * (discountPercentage / 100);
        price -= discountAmount;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
