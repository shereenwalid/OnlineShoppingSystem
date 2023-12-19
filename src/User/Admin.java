package User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Database.DataBaseConnection;
import Interface.CategorySelectionPage;
import OrderFacade.*;
import Factory.Item.*;
import Factory.Registry.*;

public class Admin extends IUser{
    private int adminID;
    private String adminName;
    private String password;

    private List < Order > orders;

    private List < String > categories;

    private static List < A_Item > itemsInStock;

    private ElectronicRegistry electronicRegistry;
    private BookRegistry bookRegistry;
    private static Connection connection = DataBaseConnection.getConnection();



    public Admin(int userID, String fname, String lname, String ssn, String email, String password, String Role) {
        UserID = userID;
        Fname = fname;
        Lname = lname;
        this.ssn = ssn;
        this.email = email;
        this.password = password;
        this.Role = Role;

        this.categories = new ArrayList < > ();
        this.orders = new ArrayList < > ();
        this.itemsInStock = new ArrayList <A_Item>();
    }

    public ElectronicRegistry getElectronicRegistry() {
        return electronicRegistry;
    }

    public void setElectronicRegistry(ElectronicRegistry electronicRegistry) {
        this.electronicRegistry = electronicRegistry;
    }

    public BookRegistry getBookRegistry() {
        return bookRegistry;
    }

    public void setBookRegistry(BookRegistry bookRegistry) {
        this.bookRegistry = bookRegistry;
    }

    public void addCategory(String name) {

        if (categories.contains(name)) {
            System.out.println("Category already exists.");
        } else {

            categories.add(name);

            String query = "INSERT INTO Categories (CategoryID, Name) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
//                int categoryID = generateNewCategoryID();
//                statement.setInt(1, categoryID);
                statement.setString(2, name);
                statement.executeUpdate();
                System.out.println("Category '" + name + " added successfully.");
            } catch (SQLException e) {
                System.out.println("Failed to add category '" + name + "'. Please try again.");
                e.printStackTrace();
            }
        }
    }

    public void deleteCategory(String name) {

        if (categories.contains(name)) {
            String query = "DELETE FROM Categories WHERE Name = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                statement.executeUpdate();
                System.out.println("Category deleted from the database.");
            } catch (SQLException e) {
                System.out.println("Failed to delete category from the database.");
                e.printStackTrace();
            }
            categories.remove(name);
        } else {
            System.out.println("Category does not exist.");
        }

    }

    public static void addItem(A_Item item) {

        if (itemsInStock.contains(item.getItemName())) {
            System.out.println("Item already exists");
        } else {

            itemsInStock.add(item);
            String query = "INSERT INTO Items (ItemName, price, description, amount, salePercent, CategoryID) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

                statement.setString(1, item.getItemName());
                statement.setDouble(2, item.getPrice());
                statement.setString(3, item.getDescription());
                statement.setInt(4, item.getAmount());
                statement.setDouble(5, item.getSalePercent());
                statement.setInt(6, item.getCategoryID());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Item added successfully.");
                } else {
                    System.out.println("Failed to add item.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Category has been updated");
        }
    }

    public void checkItemsInStock(int itemId) {
        boolean itemInStock = false;

        for (A_Item item: itemsInStock) {
            if (item.getItemID() == itemId) {
                itemInStock = true;
                break;
            }
        }

        if (itemInStock) {
            System.out.println("Item with ID " + itemId + " is in stock.");
        } else {
            System.out.println("Item with ID " + itemId + " is out of stock.");
        }
    }

    public void addSale(A_Item item, double discountPercentage) {
        item.applySale(discountPercentage);
    }



    public void deleteUser(String Fname) {

        if (ProxyUser.BuyerMap.containsKey(Fname)) {
            String query = "DELETE FROM User WHERE userName = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, Fname);

                statement.executeUpdate();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }

//     show category and items it includes
    public void browseCategories(String CategoryName) {
        if(CategoryName.equalsIgnoreCase("electronic")){
            electronicRegistry.getItems();
        }
        else if(CategoryName.equalsIgnoreCase("book")){
            bookRegistry.getItems();
        }
        else {
            System.out.println("we have don't that category");
        }
    }


    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List < Order > getOrders() {
        return orders;
    }

    public void setOrders(List < Order > orders) {
        this.orders = orders;
    }

    public List < String > getCategories() {
        return categories;
    }

    public void setCategories(List < String > categories) {
        this.categories = categories;
    }

        @Override
    public void login(String UserName, String password) {
//         will use the UserName for hello msg
//            Make the parameter of the dashboard page Admin
//            Dashboard page = new Dashboard(this);
//            page.setVisible(true);

    }
}
