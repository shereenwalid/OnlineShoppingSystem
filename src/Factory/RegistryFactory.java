package Factory;
import Factory.Registry.*;
import Factory.Item.*;
public class RegistryFactory {
    public I_ItemRegistry getCategoryRegistry(String categoryName) {
        if (categoryName == null) {
            throw new IllegalArgumentException("Category name cannot be null");
        }

        if (categoryName.equalsIgnoreCase("Books") || categoryName.equalsIgnoreCase("Book")) {
            return new BookRegistry();
        } else if (categoryName.equalsIgnoreCase("Electronics") || categoryName.equalsIgnoreCase("Electronic")) {
            return new ElectronicRegistry();
        } else {
            throw new IllegalArgumentException("Unknown category: " + categoryName);
            // we can return a default registry
        }
    }

}
