package banking;

import java.sql.SQLException;
import java.util.Scanner;

public class Bank {
    private Scanner sc = new Scanner(System.in);
    private Customer customer = null;

    public Bank() {
        boolean flag = true;
        while (flag) {
          if (customer == null || !customer.isLoggedIn()) {
              printMenu();
          } else {
              printLogMenu();
          }
           int num = sc.nextInt();
           sc.nextLine();
           if (num == 0) {
               System.out.println("Bye!");
               flag = false;
               break;
           }
           if (customer == null || !customer.isLoggedIn()) {
               mainMenu(num);
           } else {
               subMenu(num);
           }
        }
    }

    private void subMenu(int num) {
        switch (num) {
            case 1:
                balance();
                break;
            case 2:
                addIncome();
                break;
            case 3:
                transferMoney();
                break;
            case 4:
                //close acc
                break;
            case 5:
                logOut();
                break;
        }
    }

    private void addIncome() {
        System.out.println("Enter income:");
        int income = sc.nextInt();
        try {
            Database.addIncome(income, customer.getCardNumber());
            customer.addIncome(income);
            System.out.println("Income was added");
        } catch (SQLException e) {
            System.out.println("ERROR INCOME: " + e.getMessage());
        }
    }

    private void transferMoney() {
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        String cardNumber = sc.nextLine();
        if (!isLuhnNumber(cardNumber)) {
            System.out.println("Probably you made mistake in the card number. Please try again!");
            return;
        }

        String recipient = Database.accountExists(cardNumber);

        if (recipient.isEmpty()) {
            System.out.println("Such a card does not exist.");
            return;
        }

        System.out.println("Enter how much money you want to transfer:");
        int amount = sc.nextInt();
        if (customer.getBalance() < amount) {
            System.out.println("Not enough money!");
            return;
        }
        try {
            Database.transferMoney(customer.getCardNumber(), recipient, amount);
            customer.addIncome(-amount);
            System.out.println("Success!");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }



    }

    private void mainMenu(int num) {
        switch (num) {
            case 1:
                createAccount();
                break;
            case 2:
                logIn();
                if (customer == null) {
                    System.out.println("\nWrong card number or PIN!\n");
                    break;
                }
                System.out.println("\nYou have successfully logged in!\n");
                break;
        }
    }

    /**
     * Print's available options in bank
     */
    private void printMenu() {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
        }
    /**
     * Create customer account, store account in array list of customers.
     */
    private void createAccount() {
        Database.createAccount(generateCardNumber(), generatePinCod());
    }

    /**
     * Generate random 4 numbers Pin Code for account
     * @return String
     */
    private String generatePinCod() {
        return concatenateDigits(generateRandomNumber(4));
    }

    /**
     * Generate 16-digit card number who start with 400000 plus 9 random numbers;
     *  16 digit is luhnAlgorithm number
     * @return String
     */
    private String generateCardNumber() {
        String iib = "400000";
        String accountNumber = concatenateDigits(generateRandomNumber(9));
        String checkSum = iib + accountNumber;

        return luhnAlgorithm(checkSum);
    }

    /**
     * Luhn algorithm implementation
     * @param checkSum 15-digit card Number
     * @return 16-digit card number
     */
    private String luhnAlgorithm(String checkSum) {
        String[] accNum = checkSum.split("");
        /**
         * Multiply odd digits by 2
         * Substract 9 to numbers over 9
         */
        for (int i = 0; i < accNum.length; i+=2) {
            int temp = Integer.parseInt(accNum[i]);
            temp *= 2;
            if (temp > 9) {
                temp -= 9;
            }
            accNum[i] = String.valueOf(temp);
        }

        /**
         * Add all numbers
         */
        int sum = 0;
        for (int i = 0; i < accNum.length; i++){
            sum += Integer.parseInt(accNum[i]);
        }

        int lastDigit = (10 - sum % 10) % 10;

        return checkSum + String.valueOf(lastDigit);
    }

    private boolean isLuhnNumber(String cardNumber) {
        String[] accNum = cardNumber.split(""); // 16 numbers
        int length = accNum.length - 1; // 15 numbers
        /**
         * Multiply odd digits by 2
         * Substract 9 to numbers over 9
         */
        for (int i = 0; i < length; i+=2) {
            int temp = Integer.parseInt(accNum[i]);
            temp *= 2;
            if (temp > 9) {
                temp -= 9;
            }
            accNum[i] = String.valueOf(temp);
        }

        /**
         * Add all numbers
         */
        int sum = 0;
        for (int i = 0; i < length; i++){
            sum += Integer.parseInt(accNum[i]);
        }

        char lastDigit = Character.forDigit((10 - sum % 10) % 10, 10);
        return lastDigit == cardNumber.charAt(cardNumber.length()-1);

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

    private void logIn() {
        System.out.println("\nEnter your card number:");
        String cardNumber = sc.nextLine();
        System.out.println("Enter your PIN:");
        String pin = sc.nextLine();
        customer = Database.logUser(cardNumber, pin);
    }

    /**
     * Print options for login menu
     */
    private void printLogMenu() {
        System.out.println("1. Balance");
        System.out.println("2. Add Income");
        System.out.println("3. Do Transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");

    }

    /**
     * Write logged in customer balance
     */
    private void balance() {
        System.out.println("\nBalance: " + customer.getBalance() + "\n");
    }

    /**
     * Log out customer from bank system
     */
    private void logOut() {
        System.out.println("\nYou have successfully logged out!\n");
        customer.setLoggedIn(false);
    }
}