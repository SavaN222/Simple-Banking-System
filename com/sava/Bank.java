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

        System.out.println("\nYour card has been created");
        System.out.println("Your card number:");
        System.out.println(customers.get(customers.size()-1).getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(customers.get(customers.size()-1).getPinCode());
        System.out.println(" "); // new line fix
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
     * @return right Customer -
     */
   private Customer checkCredentials(String cardNumber, String pin) {
       Iterator<Customer> i = customers.iterator();

       while (i.hasNext()) {
          Customer customer = i.next();
          if (customer.getCardNumber().equals(cardNumber) &&
                  customer.getPinCode().equals(pin)) {
              customer.setLoggedIn(true);
              return customer;
          }
       }
       return null;
   }

    /**
     * Customer enter card number and PIN, if match up, allow login.
     */
   public void logIn() {
       System.out.println("\nEnter your card number");
       String cardNumber = sc.nextLine();
       System.out.println("Enter your PIN:");
       String pin = sc.nextLine();
       Customer customer = checkCredentials(cardNumber, pin);

       if (customer != null) {
           System.out.println("\nYou have successfully logged in!\n");
           logMenu(customer);
       } else {
           System.out.println("\nWrong card number or PIN!\n");
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
     * Read user options and call method for that option.
     */
   public void logMenu(Customer customer) {
       boolean flag = true;

       while (flag) {
           printLogMenu();
           int option = sc.nextInt();

           switch (option) {
               case 1:
                   balance(customer);
                   break;
               case 2:
                   logOut(customer);
                   flag = false;
                   break;
               case 3:
                   flag = false;
                   break;
           }
       }
   }

    /**
     * Write logged in customer balance
     * @param customer logged in customer
     */
   private void balance(Customer customer) {
       System.out.println("\nBalance: " + customer.getBalance() + "\n");
   }

    /**
     * Log out customer from bank system
     * @param customer logged in customer
     */
   private void logOut(Customer customer) {
       System.out.println("\nYou have successfully logged out!\n");
       customer.setLoggedIn(false);
   }
}