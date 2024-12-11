package org.dcistudent.banking.controllers;

import org.dcistudent.banking.interfaces.models.CustomerInterface;
import org.dcistudent.banking.services.BankingService;

import java.util.Scanner;

public final class BankingController {
    private final Scanner scanner;
    private Boolean loggedIn = false;
    private final BankingService bankingService;

    public static void main(String[] args) {
        new BankingController();
    }

    public BankingController() {
        this.scanner = new Scanner(System.in).useDelimiter("\n");
        this.bankingService = new BankingService(this.scanner);

        if (this.loggedIn == false) {
            this.sessionMenu();
            return;
        }

        this.customerMenu();
    }

    private void sessionMenu() {
        System.out.println("1. Signup");
        System.out.println("2. Login");
        System.out.println("3. Exit");

        try {
            this.processSessionMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.processSessionMenu();
        }
    }

    private void processSessionMenu() {
        Integer option = this.scanner.nextInt();
        CustomerInterface customer;

        switch (option) {
            case 1:
                try {
                    customer = this.bankingService.signup();
                    System.out.println("Welcome, " + customer + "! Please login now.");
                    this.sessionMenu();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    this.sessionMenu();
                }
                break;
            case 2:
                try {
                    customer = this.bankingService.login();
                    System.out.println("Welcome back, " + customer + "!");
                    this.loggedIn = true;
                    this.customerMenu();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    this.sessionMenu();
                }
                break;
            case 3:
                System.out.println("Bye, bye.");
                return;
            default:
                System.out.println("Invalid option. Please ry again.");
        }

        this.sessionMenu();
    }

    private void customerMenu() {
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Check Balance");
        System.out.println("4. Reset Password");
        System.out.println("5. Logout");

        try {
            this.processCustomerMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.processCustomerMenu();
        }
    }

    private void processCustomerMenu() {
        Integer option = this.scanner.nextInt();

//        switch (option) {
//            case 1:
//                this.bankingService.deposit();
//                break;
//            case 2:
//                this.withdraw();
//                break;
//            case 3:
//                this.transfer();
//                break;
//            case 4:
//                this.checkBalance();
//                break;
//            case 5:
//                this.logout();
//                break;
//            default:
//                System.out.println("Invalid option. :( Try again.");
//        }
//
//        this.customerMenu();
    }
}
