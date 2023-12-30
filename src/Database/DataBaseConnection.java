package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String DB_URL = "";
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

}
