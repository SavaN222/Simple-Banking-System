package com.sava;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean flag = true;
        Bank bank = new Bank();
        Scanner sc = new Scanner(System.in);
        while (flag) {
            bank.menu();
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    bank.createAccount();
                    break;
                case 2:
                    bank.logIn();
                    break;
                case 0:
                    System.out.println("\nBye!");
                    flag = false;
                    break;
            }
        }
    }
}