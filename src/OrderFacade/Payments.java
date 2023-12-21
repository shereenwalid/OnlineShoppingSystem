package OrderFacade;

import User.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Payments {
    Buyer user;

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