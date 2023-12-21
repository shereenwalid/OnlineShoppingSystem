package Factory.Registry;
import Factory.Category.A_Item;
import java.util.Map;

public interface I_ItemRegistry {

    void addProduct();
    A_Item getProduct(String itemName);
    Map<String, A_Item> getItems();
    void removeProduct(String itemName);
}
