package OrderFacade;

import User.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Payments {
    Buyer user;
    Order order;
    Creditcard card;

    List<String> userCreditCardData = new ArrayList<>();

    public Payments(Buyer user,Order order) {
        this.user = user;
        this.order = order;
        this.card = user.getCard();
    }



    void loadUserCreditCards() {
        userCreditCardData.add(String.valueOf(card.getCardNumber()));
        userCreditCardData.add(String.valueOf(card.getCvv()));
    }
    boolean checkCVV(String cvv) {
        if (userCreditCardData.contains(cvv)) {
            return true;
        }
        return false;
    }

    boolean checkCardNumber(String CardNumber) {
        if (userCreditCardData.contains(CardNumber)) {
            return true;
        }
        return false;
    }

    boolean checkBalance() {
        if (card.getBalance() >= order.getTotal()) {
            return true;
        }
        return false;
    }

    boolean checkExpiryDate() {
        if (ChronoUnit.DAYS.between(card.getExpiryDate(), LocalDate.now()) > 1) {
            return true;
        }
        return false;
    }
}