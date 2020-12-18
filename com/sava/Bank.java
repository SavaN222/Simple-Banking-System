package com.sava;

import java.util.ArrayList;

public class Bank {
    private ArrayList<Customer> customers = new ArrayList<Customer>();

    /**
     * Print's available options in bank
     */
    public void menu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    /**
     * Create customer account, store account in array list of customers.
     */
    public void createAccount() {
        Customer customer = new Customer(generateCardNumber(), generatePinCod());
        customers.add(customer);

        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(customers.get(customers.size()-1).getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(customers.get(customers.size()-1).getPinCode());
    }

    /**
     * Generate random 4 numbers Pin Code for account
     * @return String
     */
    private String generatePinCod() {
        return concatenateDigits(generateRandomNumber(4));
    }

    /**
     * Generate 16-digit card number who start with 400000 plus 10 random numbers;
     * @return String
     */
    private String generateCardNumber() {
        String iib = "400000";
        String accountNumber = concatenateDigits(generateRandomNumber(10));

        return iib + accountNumber;
    }

    /**
     * Generate random number
     * @param length - digits in random number.
     * @return int[]
     */
   private int[] generateRandomNumber(int length) {
        int[] nums = new int[length];

        for (int i = 0; i < length; i++) {
            nums[i] = (int) (Math.random() * 10) - 1;
        }
        return nums;
   }

    /**
     * Concatenate array of numbers in one number.
     * @param digits - array of numbers to use.
     * @return String
     */
   private String concatenateDigits(int[] digits) {
        StringBuilder sb = new StringBuilder(digits.length);

        for (int digit : digits) {
            sb.append(digit);
        }
        return sb.toString();
   }
}
