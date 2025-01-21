package org.dcistudent.banking.controllers;

import org.dcistudent.banking.exceptions.validations.customers.PasswordValidationException;
import org.dcistudent.banking.facades.ScannerFacade;
import org.dcistudent.banking.interfaces.models.CustomerInterface;
import org.dcistudent.banking.loggers.JsonLogger;
import org.dcistudent.banking.managers.AccountManager;
import org.dcistudent.banking.managers.CustomerManager;
import org.dcistudent.banking.managers.TransactionManager;
import org.dcistudent.banking.renderers.ScannerRenderer;
import org.dcistudent.banking.services.AccountService;
import org.dcistudent.banking.services.BankingService;
import org.dcistudent.banking.services.CustomerService;
import org.dcistudent.banking.services.TransactionService;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public final class BankingController {
    private final BankingService bankingService;
    private final Logger logger = JsonLogger.getLogger();
    private Boolean loggedIn = false;
    private static Integer loginAttempts = 1;
    private static final Integer MAX_LOGIN_ATTEMPTS = 3;

    public static void main(String[] args) {
        new BankingController();
    }

    public BankingController() {
        TransactionManager transactionManager = new TransactionManager();
        AccountService accountService = new AccountService(
                new AccountManager(), new TransactionService(transactionManager)
        );

        this.bankingService = new BankingService(
                new CustomerService(accountService, new CustomerManager()),
                accountService,
                new TransactionService(transactionManager)
        );

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
            this.logger.severe(e.getMessage());
            ScannerRenderer.renderSeparated(e.getMessage());
            this.processSessionMenu();
        }
    }

    private void processSessionMenu() {
        Integer option;
        CustomerInterface customer;

        ScannerRenderer.renderInputChoice();
        try {
            option = ScannerFacade.getInt();
        } catch (IllegalArgumentException e) {
            this.logger.severe(e.getMessage());
            ScannerRenderer.renderSeparated(e.getMessage());
            this.sessionMenu();
            return;
        }

        switch (option) {
            case 1 -> {
                try {
                    customer = this.bankingService.signup();
                    ScannerRenderer.renderSeparated("Welcome, " + customer + "! Please login now.");
                    this.sessionMenu();
                } catch (Exception e) {
                    this.logger.severe(e.getMessage());
                    ScannerRenderer.renderSeparated(e.getMessage());
                    this.sessionMenu();
                }
            }
            case 2 -> {
                try {
                    customer = this.bankingService.login();
                    ScannerRenderer.renderSeparated("Welcome back, " + customer + "!");
                    this.loggedIn = true;
                    this.customerMenu();
                } catch (Exception e) {
                    this.logger.severe(e.getMessage());
                    loginAttempts++;
                    ScannerRenderer.renderSeparated(e.getMessage());
                    if (loginAttempts > MAX_LOGIN_ATTEMPTS) {
                        this.logger.severe("Too many login attempts. Exiting.");
                        ScannerRenderer.renderSeparated("Bye, bye hacker!.");
                        System.exit(42);
                    }

                    this.sessionMenu();
                }
            }
            case 3 -> {
                ScannerRenderer.renderSeparated("Bye, bye.");
                System.exit(0);
            }
            default -> {
                ScannerRenderer.renderSeparated("Invalid option. Please try again.");
            }
        }

        this.sessionMenu();
    }

    private void customerMenu() {
        System.out.println("1. Show Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Reset Password");
        System.out.println("6. View Transactions");
        System.out.println("7. Close Account");
        System.out.println("8. Logout");

        try {
            this.processCustomerMenu();
        } catch (Exception e) {
            this.logger.severe(e.getMessage());
            ScannerRenderer.renderSeparated(e.getMessage());
            this.processCustomerMenu();
        }
    }

    private void processCustomerMenu() {
        Integer option;

        ScannerRenderer.renderInputChoice();
        try {
            option = ScannerFacade.getInt();
        } catch (IllegalArgumentException e) {
            this.logger.severe(e.getMessage());
            ScannerRenderer.renderSeparated(e.getMessage());
            this.customerMenu();
            return;
        }

        switch (option) {
            case 1 -> ScannerRenderer.renderSeparated(
                    String.format("Your balance is: %.2f", this.bankingService.checkBalance())
            );
            case 2 -> {
                try {
                    this.bankingService.deposit();
                } catch (Exception e) {
                    this.logger.severe(e.getMessage());
                    ScannerRenderer.renderSeparated(e.getMessage());
                    this.customerMenu();
                }

                ScannerRenderer.renderSeparated(
                        String.format("Success! Your new balance is: %.2f", this.bankingService.checkBalance())
                );
            }
            case 3 -> {
                try {
                    this.bankingService.withdraw();
                } catch (Exception e) {
                    this.logger.severe(e.getMessage());
                    ScannerRenderer.renderSeparated(e.getMessage());
                    this.customerMenu();
                }

                ScannerRenderer.renderSeparated(
                        String.format("Success! Your new balance is: %.2f", this.bankingService.checkBalance())
                );
            }
            case 4 -> {
                try {
                    this.bankingService.transfer();
                } catch (Exception e) {
                    this.logger.severe(e.getMessage());
                    ScannerRenderer.renderSeparated(e.getMessage());
                    this.customerMenu();
                }

                ScannerRenderer.renderSeparated(
                        String.format("Success! Your new balance is: %.2f", this.bankingService.checkBalance())
                );
            }
            case 5 -> {
                try {
                    this.bankingService.resetPassword();
                } catch (NoSuchAlgorithmException | PasswordValidationException e) {
                    this.logger.severe(e.getMessage());
                    ScannerRenderer.renderSeparated(e.getMessage());
                    this.customerMenu();
                }

                this.loggedIn = false;
                this.bankingService.closeSession();
                ScannerRenderer.renderSeparated("Success! Please login again.");

                this.sessionMenu();
            }
            case 6 -> this.bankingService.transactions();
            case 7 -> {
                this.bankingService.closeAccount();
                this.loggedIn = false;
                this.bankingService.closeSession();
                ScannerRenderer.renderSeparated("Success! Your account is now closed.");

                this.sessionMenu();
            }
            case 8 -> {
                this.loggedIn = false;
                this.bankingService.closeSession();
                ScannerRenderer.renderSeparated("Bye, bye.");

                this.sessionMenu();
            }
            default -> ScannerRenderer.renderSeparated("Invalid option. Please Try again.");
        }

        this.customerMenu();
    }
}
