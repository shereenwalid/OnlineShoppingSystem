/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;

/**
 *
 * @author USER
 */
public class Card {
private String CardNumber;
private String Cvv;
private double Balance;
private String ExpireyDate;

    public String getCardNumber() {
        return CardNumber;
    }

    public String getCvv() {
        return Cvv;
    }

    public double getBalance() {
        return Balance;
    }

    public String getExpireyDate() {
        return ExpireyDate;
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

    public void setExpireyDate(String ExpireyDate) {
        this.ExpireyDate = ExpireyDate;
    }



}
