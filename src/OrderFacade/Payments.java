package OrderFacade;

import User.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Payments {
    Buyer user;
    Order order;

    Receipt receipt;

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setUser(Buyer user) {
        this.user = user;
    }

    public Buyer getUser() {
        return user;
    }

    public boolean checkCVV(String cvv) {
        if (this.user.getCard().getCvv().equalsIgnoreCase(cvv)) {
            return true;
        }
        return false;
    }

    public boolean checkCardNumber(String cardNumber) {
        if (this.user.getCard().getCardNumber().equalsIgnoreCase(cardNumber)) {
            return true;
        }
        return false;
    }
}