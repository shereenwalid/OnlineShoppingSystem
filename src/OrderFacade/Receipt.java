package OrderFacade;

import Factory.Category.A_Item;

public class Receipt {
        private Order order;
        private int totalDiscount;

        public void setOrder(Order order) {
            this.order = order;
        }

        double total = 0.0;

        public Receipt(){

        }
        // Display items and calculate total with discounts
        public double calculateFinalTotal() {
            for (A_Item item : order.getCart().getItems()) {
                if (item.getSalePercent() > 0) {
                    // Apply discount if available
                    double discountPercentage = (item.getSalePercent() / 10.0);
                    double discountedPrice = item.getPrice() * discountPercentage;
                    this.total += discountedPrice;
                    totalDiscount += item.getPrice() - discountedPrice;
                } else {
                    this.total += item.getPrice();
                }
            }

            return this.total;
        }

    public double getTotal() {
        return total;
    }
}



