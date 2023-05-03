package com.payment_app.webapp_oop_lab1;

public class CreditCard {
    private String cardNumber;
    private int accountId;
//    private int pinCode;

    public CreditCard(String cardNumber, int accountId) {
        this.cardNumber = cardNumber;
        this.accountId = accountId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getAccountId() {
        return accountId;
    }



//    public boolean checkPinCode(int pinCode) {
//        return this.pinCode == pinCode;
//    }
}
