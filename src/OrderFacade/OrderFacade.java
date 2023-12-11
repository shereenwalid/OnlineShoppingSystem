package OrderFacade;

import Factory.Item.A_Item;
import User.Buyer;
import User.IUser;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class OrderFacade {
    Payments payments;
    Order order;
    Receipt receipt;

    public Payments getPayments() {
        return payments;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderFacade(Buyer user) {
        payments = new Payments(user,order);
        this.order = new Order();
        this.receipt = new Receipt(order);
    }


    public void placeOrder(String cvv, String cardNumber) {
        if (payments.checkBalance() && payments.checkCVV(cvv) && payments.checkCardNumber(cardNumber) && payments.checkExpiryDate()) {
            order.ConfirmOrder();
            System.out.println("Order submitted");
        } else {
            System.out.println("Incorrect card information, please check your credit card data");
        }
    }

    public void cancelOrder() throws SQLException {
        LocalDateTime acceptanceDate = order.getAcceptanceDateFromDatabase();

        if (acceptanceDate != null && ChronoUnit.DAYS.between(acceptanceDate, LocalDateTime.now()) < 1) {
            order.deleteOrder();
        } else {
            System.out.println("Can't delete order, 1 day has passed. Please check the terms and conditions.");
        }
    }

    public void cancelOrderItem(A_Item item) throws SQLException {
        LocalDateTime acceptanceDate = order.getAcceptanceDateFromDatabase();

        if (acceptanceDate != null && ChronoUnit.DAYS.between(acceptanceDate, LocalDateTime.now()) < 1) {
            order.deleteOrderItem(item);
        } else {
            System.out.println("Can't delete order, 1 day has passed. Please check the terms and conditions.");
        }
    }
}