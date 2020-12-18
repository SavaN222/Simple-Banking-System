package com.sava;

public class Customer {
    private String cardNumber;
    private String pinCode;

    public Customer(String cardNumber, String pinCode) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }
}
