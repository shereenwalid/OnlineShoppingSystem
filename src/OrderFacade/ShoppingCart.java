package OrderFacade;

import Factory.Item.A_Item;

import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList < A_Item > selectedItems = new ArrayList < > ();

    public void addItem(A_Item item) {
        selectedItems.add(item);
    }

    public void removeItem(A_Item item) {
        selectedItems.remove(item);
    }

    public ArrayList < A_Item > getItems() {
        return selectedItems;
    }
}