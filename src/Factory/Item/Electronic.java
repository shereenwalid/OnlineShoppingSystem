package Factory.Item;

public class Electronic extends A_Item {
    public Electronic(int electronicID, String itemName, double price, int quantity, String description, int salePercent, int categoryID,int itemDeliveryDuration) {
        super(electronicID, itemName, price, quantity, description, salePercent, categoryID,itemDeliveryDuration);
    }
}
