package OrderFacade;

import Database.DataBaseConnection;
import Factory.Item.A_Item;
import User.Buyer;
import User.IUser;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;

public class OrderFacade {
    Payments payments;
    Buyer buyer;
    Order order;
    Receipt receipt;


    private static Connection connection = DataBaseConnection.getConnection();


    public void setPayments(Payments payments) {
        this.payments = payments;
    }

    public Payments getPayments() {
        return payments;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    Buyer user;

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public OrderFacade() {
        payments = new Payments();
        this.order = new Order();
        this.receipt = new Receipt();
    }

    public int displayDurationBeforeSubmitting(){
        return order.calculateDeliveryDuration();
    }

    public int displayTotalBeforeSubmitting(){
        receipt.setOrder(order);
        return (int) receipt.calculateFinalTotal();
    }

    public boolean placeOrder(String cvv, String cardNumber,int id,int total,int duration,ShoppingCart cart) {
        if (payments.checkCVV(cvv) & payments.checkCardNumber(cardNumber)) {
            order.saveOrder(id,total,duration,cart);
            System.out.println("Order submitted");
            return true;
        } else {
            System.out.println("Incorrect card information, please check your credit card data");
            return false;
        }
    }

public void cancelOrder(int orderid) throws SQLException {
    Map<Integer, Map<Integer, String>> orders = buyer.getOrdersMap();
    LocalDate acceptanceDate = LocalDate.from(order.getAcceptanceDateFromDatabase(orderid)); // Assuming acceptanceDate is a LocalDate

    LocalDateTime acceptanceDateTime = acceptanceDate.atStartOfDay();

    System.out.println("Acceptance Date: " + acceptanceDateTime);
    System.out.println("Current Date: " + LocalDateTime.now());

    if (acceptanceDate != null) {
        long hoursDifference = ChronoUnit.DAYS.between(acceptanceDate, LocalDateTime.now());
        System.out.println(hoursDifference);
        if (hoursDifference <= 1) {
            order.deleteOrder(orderid);
            orders.remove(orderid);
        } else {
            System.out.println("Can't delete order, less than 24 hours have passed. If you think it's a mistake, please contact the call center on 911");
        }
    }
    }

    public void cancelOrderItem(int orderID, int itemID) throws SQLException {
        Map<Integer, Map<Integer, String>> orders = buyer.getOrdersMap();
        LocalDateTime acceptanceDate = order.getAcceptanceDateFromDatabase(orderID);

        if (acceptanceDate != null) {
            long hoursDifference = ChronoUnit.DAYS.between(acceptanceDate, LocalDateTime.now());
            System.out.println("Hours difference: " + hoursDifference);

            if (hoursDifference <= 1) {
                // Delete the item from the database
                order.deleteOrderItem(orderID, itemID);

                // Update the orders map after deleting the item
                if (orders.containsKey(orderID)) {
                    Map<Integer, String> orderItems = orders.get(orderID);
                    orderItems.remove(itemID);

                    // If the order map is empty, remove it from the orders map
                    if (orderItems.isEmpty()) {
                        orders.remove(orderID);
                    }
                } else {
                    System.out.println("Order not found in the orders map.");
                }
            } else {
                System.out.println("Can't delete order item, more than 1 day has passed.");
            }
        } else {
            System.out.println("Can't delete order item, acceptance date not found.");
        }
    }



    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}