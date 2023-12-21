package User;

import Database.DataBaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.Temporal;

public class Creditcard {
    private static Connection connection = DataBaseConnection.getConnection();
    private int userID;
    private String CardNumber;
    private String Cvv;
    private double Balance;

    //    LocalDate provide immutability and clarity for handling date without time
    private LocalDate ExpiryDate;

    public Creditcard(int userID, String cardNumber, String cvv) {
        this.userID = userID;
        this.CardNumber = cardNumber;
        this.Cvv = cvv;
    }

    public int getUserID() {
        return userID;
    }
    public String getCardNumber() {
        return CardNumber;
    }

    public String getCvv() {
        return Cvv;
    }

    public double getBalance() {
        return Balance;
    }

    public LocalDate getExpiryDate() {
        return ExpiryDate;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setCardNumber(String CardNumber) {
        this.CardNumber = CardNumber;
    }

    public void setCvv(String Cvv) {
        this.Cvv = Cvv;
    }

    public void setBalance(double Balance) {
        this.Balance = Balance;
    }

    public void setExpiryDate(LocalDate ExpiryDate) {
        this.ExpiryDate = ExpiryDate;
    }

    public void setcardNumber(String cardNumber, int id) {
        try {
            String updateQuery = "UPDATE User SET cardNumber = ? WHERE userID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, cardNumber);
                preparedStatement.setInt(2, id);

                int rowsAffected = preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setcvv(String cvv, int id) {
        try {
            String updateQuery = "UPDATE User SET cvv = ? WHERE userID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, cvv);
                preparedStatement.setInt(2, id);

                int rowsAffected = preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}