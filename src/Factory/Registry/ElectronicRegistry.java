package Factory.Registry;
import Database.DataBaseConnection;
import Factory.Item.A_Item;
import Factory.Item.Electronic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ElectronicRegistry implements I_ItemRegistry {

    private HashMap<String, A_Item> electronicRegistry;

    // Get database connection instance
    private Connection connection = DataBaseConnection.getConnection();

    public ElectronicRegistry() {
        electronicRegistry = new HashMap<>();
        addProduct();
    }

    @Override
    public void addProduct() {
        String sql = "SELECT ItemID,ItemName, price, description, amount, salePercent, ItemDeliveryDuration " +
                "FROM Items " +
                "WHERE CategoryID = 2";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ItemID");
                String itemName = resultSet.getString("ItemName");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                int amount = resultSet.getInt("amount");
                int salePercent = resultSet.getInt("salePercent");
                int itemDeliveryDuration = resultSet.getInt("itemDeliveryDuration");
                // Create a new Electronic instance and put it in the registry
                electronicRegistry.put(itemName, new Electronic(id, itemName, price, amount, description, salePercent, 2,itemDeliveryDuration));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public A_Item getProduct(String itemName) {
        A_Item item = electronicRegistry.get(itemName);

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
        return new HashMap<>(electronicRegistry);
    }

    @Override
    public void removeProduct(String itemName) {
        electronicRegistry.remove(itemName);
    }
}
