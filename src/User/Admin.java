package User;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Database.DataBaseConnection;
import Factory.Category.*;
import OrderFacade.*;
//import Factory.Category.*;
import Factory.Registry.*;
import Interface.*;
import java.lang.reflect.*;
import java.util.Map;
import static User.ProxyUser.AdminMap;

public class Admin extends IUser
{
    private int adminID;
    private String adminName;
    private String password;

    private List < Order > orders;

    private List < String > categories;
    private static List <String> itemsInStock;

    private ElectronicRegistry electronicRegistry;
    private BookRegistry bookRegistry;
    Admin admin;
    private static Connection connection = DataBaseConnection.getConnection();



    public Admin(int userID, String fname, String lname, String ssn, String email, String password, String Role) {
        UserID = userID;
        Fname = fname;
        Lname = lname;
        this.ssn = ssn;
        this.email = email;
        this.password = password;
        this.Role = Role;

        this.orders = new ArrayList < > ();

        this.itemsInStock = getAllItems();
        this.categories = FillCategories();
    }


    public void addCategory(String name) {
        if (categories.contains(name)) {
            System.out.println("Category already exists.");
        } else {
            categories.add(name);

            String query = "INSERT INTO Categories(Name) VALUES(?);";
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, name);
                int rowsAffected = statement.executeUpdate();
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
                int rowsAffected = statement.executeUpdate();

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

    public static void addItem(String itemName, int price, String description, int amount, float salePercent, int CategoryID) {

        if (itemsInStock.contains(itemName)) {
            System.out.println("Item already exists");
        } else {

            itemsInStock.add(itemName);
            String query = "INSERT INTO Items (ItemName, price, description, amount, salePercent, CategoryID) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, itemName);
                statement.setInt(2, price);
                statement.setString(3, description);
                statement.setInt(4, amount);
                statement.setFloat(5, salePercent);
                statement.setInt(6, CategoryID);

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



    public void deleteItem(String item) {
        if (itemsInStock.contains(item)) {
            itemsInStock.remove(item);
            String query = "DELETE FROM Items WHERE ItemName = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, item);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Item deleted successfully.");
                } else {
                    System.out.println("Failed to delete item.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Item not found.");
        }
    }


    public List<String> getAllItems() {
        List<String> items = new ArrayList<>();
        String query = "SELECT ItemName FROM Items";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String itemName = resultSet.getString("ItemName");
                items.add(itemName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }


    public ArrayList<String> FillCategories() {
        String query = "SELECT Name FROM Categories";
        ArrayList<String> categories = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String categoryName = resultSet.getString("Name");
                categories.add(categoryName);
            }

            System.out.println("Categories filled successfully.");

        } catch (SQLException e) {
            System.out.println("Failed to fill categories. Please try again.");
            e.printStackTrace();
        }
        return categories;
    }


    public List<Order> showOrderWithInPeriod(LocalDate startDate, LocalDate endDate) {
        String sqlQuery = "SELECT * FROM Orders WHERE OrderDate >= ? AND OrderDate <= ?";

        List<Order> ordersWithInPeriod = new ArrayList<>(); // Initialize the list

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(2, java.sql.Date.valueOf(endDate));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setOrderID(resultSet.getInt("OrderID"));
                    order.setUserID(resultSet.getInt("UserID"));
                    order.setTotal(resultSet.getDouble("Total"));
                    order.setOrderDate(resultSet.getDate("OrderDate").toLocalDate());
                    order.setDeliveryDuration(resultSet.getInt("DeliveryDuration"));

                    ordersWithInPeriod.add(order);
                    System.out.println(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersWithInPeriod;
    }



    public void deleteUser(String Fname) {

        if (ProxyUser.BuyerMap.containsKey(Fname)) {
            String query = "DELETE FROM User WHERE Fname = ?";

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


    public void EditItemSalePercent(String ItemName, int sale) {
        try {
            String updateQuery = "UPDATE Items SET salePercent = ? WHERE ItemName = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, sale);
                preparedStatement.setString(2, ItemName);

                int rowsAffected = preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void EditItemPrice(String ItemName, int price) {
        try {
            String updateQuery = "UPDATE Items SET price = ? WHERE ItemName = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, price);
                preparedStatement.setString(2, ItemName);

                int rowsAffected = preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public void login(String UserName, String password) {
        admin = (Admin) AdminMap.get(UserName);
        AdminPage page = new AdminPage(admin);
        page.setVisible(true);
    }



}
