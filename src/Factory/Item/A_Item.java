package Factory.Item;

public abstract class A_Item implements Cloneable {
    private int itemID;
    private String itemName;
    private double price;
    private int amount;
    private String description;
    private int salePercent;
    private int categoryID;

    public A_Item(int itemID, String itemName, double price, int amount, String description, int salePercent, int categoryID) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.price = price;
        this.amount = amount;
        this.description = description;
        this.salePercent = salePercent;
        this.categoryID = categoryID;
    }

    @Override
    public A_Item clone() throws CloneNotSupportedException {
        return (A_Item) super.clone();
    }

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public int getSalePercent() {
        return salePercent;
    }

    public int getCategoryID() {
        return categoryID;
    }
}
