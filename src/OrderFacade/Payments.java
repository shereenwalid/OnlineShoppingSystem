package OrderFacade;

import User.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Payments {
    Buyer user;
    Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setUser(Buyer user) {
        this.user = user;
    }

    Creditcard card;

    List<String> userCreditCardData = new ArrayList<>();

    public Payments(Buyer user,Order order) {
        this.user = user;
        this.order = order;
        setCard(user.getCard());
    }

    public void setCard(Creditcard card) {
        this.card = user.getCard();
    }

    void loadUserCreditCards() {

        userCreditCardData.add(String.valueOf(this.card.getCardNumber()));
        userCreditCardData.add(String.valueOf(this.card.getCvv()));
    }
    boolean checkCVV(String cvv) {
        if (this.userCreditCardData.contains(cvv)) {
            return true;
        }
        return false;
    }

    boolean checkCardNumber(String CardNumber) {
        if (this.userCreditCardData.contains(CardNumber)) {
            return true;
        }
        return false;
    }

    boolean checkBalance() {
        if (this.card.getBalance() >= this.order.getTotal()) {
            return true;
        }
        return false;
    }

    boolean checkExpiryDate() {
        if (ChronoUnit.DAYS.between(this.card.getExpiryDate(), LocalDate.now()) > 1) {
            return true;
        }
        return false;
    }
}