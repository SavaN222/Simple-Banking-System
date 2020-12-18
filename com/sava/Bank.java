package com.sava;

public class Bank {

    /**
     * Print's available options in bank
     */
    public void menu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    public Customer createAccount() {

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
