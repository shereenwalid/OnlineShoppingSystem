package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/OnlineShoppingSystem?user=root&password=&useSSL=false&allowPublicKeyRetrieval=true";
    private static Connection connection;

    // Private constructor to prevent instantiation from outside
    private DataBaseConnection() {
    }

    // Method to get a connection using Singleton pattern
    public static synchronized Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                // Log or handle the exception appropriately
                e.printStackTrace();
                throw new RuntimeException("Error connecting to the database.", e);
            }
        }
        return connection;
    }

    // Method to close the connection
    public static synchronized void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Log or handle the exception appropriately
                e.printStackTrace();
            } finally {
                connection = null; // Set connection to null after closing
            }
        }
    }
}
