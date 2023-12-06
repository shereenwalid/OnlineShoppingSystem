package Factory.Item;

public class Book extends A_Item {
    public Book(int bookID, String title, double price, int quantity, String description, int salePercent, int categoryID) {
        super(bookID, title, price, quantity, description, salePercent, categoryID);
    }
}
