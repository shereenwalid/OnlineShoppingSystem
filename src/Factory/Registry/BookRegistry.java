package Factory.Registry;
import Factory.Item.A_Item;
import Factory.Item.Book;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Database.DataBaseConnection;

public class BookRegistry implements I_ItemRegistry {
    private HashMap<String, A_Item> bookRegistry;

    // Get database connection instance
    private Connection connection = DataBaseConnection.getConnection();

    public BookRegistry() {
        bookRegistry = new HashMap<>();
        addProduct();
    }

    @Override
    public void addProduct() {
        String sql = "SELECT ItemName, price, description, amount, salePercent " +
                "FROM Items " +
                "WHERE CategoryID = 1";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String itemName = resultSet.getString("ItemName");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                int amount = resultSet.getInt("amount");
                int salePercent = resultSet.getInt("salePercent");

                // Create a new Book instance and put it in the registry
                bookRegistry.put(itemName, new Book(0, itemName, price, amount, description, salePercent, 1));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }
    }

    @Override
    public A_Item getProduct(String itemName) {
        A_Item item = bookRegistry.get(itemName);

        if (item != null) {
            try {
                return (A_Item) item.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public Map<String, A_Item> getItems() {
        return new HashMap<>(bookRegistry);
    }

    @Override
    public void removeProduct(String itemName) {
        bookRegistry.remove(itemName);
    }
}
