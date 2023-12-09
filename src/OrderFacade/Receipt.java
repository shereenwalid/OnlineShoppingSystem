package OrderFacade;

import Factory.Item.A_Item;

public class Receipt {
    Order order;
    int amountToRemove;

    public Receipt(Order order) {
        this.order = order;
        displayItems();
        displayFinalTotal();
    }

    void displayItems() {
        for (A_Item item: order.OrderItems()) {
            System.out.println(item.getItemName());
            System.out.println(item.getDescription());
            System.out.println(item.getPrice());
        }
    }
    int displayFinalTotal() {
        int totalDiscount = 0;

        for (A_Item item: order.OrderItems()) {
            if (item.getSalePercent() > 0) {
                totalDiscount += (int)(item.getPrice() * item.getSalePercent());
            }
        }

        return (int)(order.getTotal() - totalDiscount);
    }
}