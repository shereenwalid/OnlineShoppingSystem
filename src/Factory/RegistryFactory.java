package Factory;
import Factory.Registry.*;

public class RegistryFactory {
    public static I_ItemRegistry getCategoryRegistry(String categoryName) {
        if (categoryName.equalsIgnoreCase("Books") || categoryName.equalsIgnoreCase("Book")) {
            return new BookRegistry();
        } else if (categoryName.equalsIgnoreCase("Electronics") || categoryName.equalsIgnoreCase("Electronic")) {
            return new ElectronicRegistry();
        } else {
            throw new IllegalArgumentException("Unknown category: " + categoryName);
        }
    }

}
