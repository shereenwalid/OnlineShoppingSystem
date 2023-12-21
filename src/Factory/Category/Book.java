package Factory.Category;

public class Book extends A_Item {
    public Book(int bookID, String title, double price, int quantity, String description, int salePercent, int categoryID, int itemDeliveryDuration) {
        super(bookID, title, price, quantity, description, salePercent, categoryID, itemDeliveryDuration);
    }
}
