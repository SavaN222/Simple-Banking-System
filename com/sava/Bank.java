package com.sava;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Bank {
    private ArrayList<Customer> customers = new ArrayList<Customer>();
    private Scanner sc = new Scanner(System.in);

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
            nums[i] = (int) (Math.random() * 10);
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

    /**
     * Check customer credentials for cardNumber and pinCode
     * @param cardNumber customer input cardNumber
     * @param pin customer input cardPin
     * @return boolean -
     */
   private boolean checkCredentials(String cardNumber, String pin) {
       Iterator<Customer> i = customers.iterator();

       while (i.hasNext()) {
          Customer customer = i.next();
          if (customer.getCardNumber().equals(cardNumber) &&
                  customer.getPinCode().equals(pin)) {
              customer.setLoggedIn(true);
              return true;
          }
       }
       return false;
   }

    /**
     * Customer enter card number and PIN, if match up, allow login.
     */
   public void logIn() {
       System.out.println("Enter your card number");
       String cardNumber = sc.nextLine();
       System.out.println("Enter your PIN:");
       String pin = sc.nextLine();

       if (checkCredentials(cardNumber, pin)) {
           System.out.println("You have successfully logged in!");
           // print menu
       } else {
           System.out.println("Wrong card number or PIN!");
       }
   }

    /**
     * Print options for login menu
     */
   private void printLogMenu() {
       System.out.println("1. Balance");
       System.out.println("2. Log out");
       System.out.println("0. Exit");
   }

    /**
     * Read user option and call method for that option.
     */
   public void logMenu() {
       boolean flag = true;

       while (flag) {
           printLogMenu();
           int option = sc.nextInt();

           switch (option) {
               case 1:
                   balance();
                   break;
               case 2:
                   logOut();
                   break;
               case 3:
                   flag = false;
                   break;
           }
       }
   }
}
