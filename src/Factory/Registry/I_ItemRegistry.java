package Factory.Registry;
import Factory.Item.A_Item;
import java.util.Map;

public interface I_ItemRegistry {

    // Add a product to the registry
    void addProduct();

    // Get a product from the registry by name
    A_Item getProduct(String itemName);

    // Get all items from the registry
    Map<String, A_Item> getItems();

    // Remove a product from the registry by name
    void removeProduct(String itemName);
}
