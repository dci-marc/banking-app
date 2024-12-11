package org.dcistudent.banking.services;

import org.dcistudent.banking.exceptions.transfers.BankTransferException;
import org.dcistudent.banking.exceptions.validations.accounts.LimitValidationException;
import org.dcistudent.banking.exceptions.validations.accounts.PinValidationException;
import org.dcistudent.banking.factories.AccountFactory;
import org.dcistudent.banking.hydrators.AccountHydrator;
import org.dcistudent.banking.interfaces.models.AccountInterface;
import org.dcistudent.banking.interfaces.models.CustomerInterface;
import org.dcistudent.banking.managers.AccountManager;
import org.dcistudent.banking.renderers.ScannerRenderer;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class AccountService {
    private final Scanner scanner;
    private final AccountManager accountManager;

    public AccountService(Scanner scanner) {
        this.scanner = scanner;
        this.accountManager = new AccountManager();
    }

    public AccountInterface create(CustomerInterface customer) {
        AccountInterface account = null;
        Integer accountType;

        ScannerRenderer.renderSeparated("Bank Account Creation");
        System.out.println("Select account type:");
        System.out.println("1. Silver");
        System.out.println("2. Gold");
        System.out.println("3. Platinum");
        ScannerRenderer.renderInputChoice();
        accountType = scanner.nextInt();

        try {
            account = AccountFactory.create(accountType);
        } catch (IllegalArgumentException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.create(customer);
        }
        account.setCustomerId(customer.getId());
        customer.setAccount(account);

        this
                .createAccountSecurity(customer)
                .createAccountInitialDeposit(customer)
                .createAccountWithdrawLimit(customer)
        ;

        this.accountManager.persist(new AccountHydrator(), AccountHydrator.hydrate(account));

        return account;
    }

    private AccountService createAccountSecurity(CustomerInterface customer) {
        AccountInterface account = customer.getAccount();

        ScannerRenderer.renderSeparated("Bank Account Security");
        ScannerRenderer.renderInput("Enter your 4-digit PIN code");
        try {
            account.setPin(scanner.nextInt());
            customer.setAccount(account);
        } catch (PinValidationException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.createAccountSecurity(customer);
        } catch (InputMismatchException e) {
            ScannerRenderer.renderSeparated("Invalid PIN code.");
            this.createAccountSecurity(customer);
        }

        return this;
    }

    private AccountService createAccountInitialDeposit(CustomerInterface customer) {
        AccountInterface account = customer.getAccount();
        Double amount;

        ScannerRenderer.renderSeparated(
                String.format("Initial Deposit (Limit: %f)", account.getLimitDeposit())
        );
        ScannerRenderer.renderInput("Enter amount to deposit");
        amount = scanner.nextDouble();

        try {
            account.deposit(amount);
            customer.setAccount(account);
        } catch (BankTransferException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.createAccountInitialDeposit(customer);
        }

        return this;
    }

    private void createAccountWithdrawLimit(CustomerInterface customer) {
        AccountInterface account = customer.getAccount();
        Double amount;

        ScannerRenderer.renderSeparated(
                String.format("Withdraw Limit (Limit: %f)", account.getLimitWithdrawal())
        );
        ScannerRenderer.renderInput("Enter amount to set as custom withdrawal limit");
        amount = scanner.nextDouble();

        try {
            account.setLimitWithdrawalCustom(amount);
            customer.setAccount(account);
        } catch (LimitValidationException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.createAccountWithdrawLimit(customer);
        }
    }

    public AccountInterface getByCustomerId(String customerId) {
        return AccountHydrator.hydrate(this.accountManager.findByCustomerId(customerId));
    }

    public void deposit(CustomerInterface customer) {
        AccountInterface account = customer.getAccount();
        Double amount;

        ScannerRenderer.renderSeparated("Deposition");
        ScannerRenderer.renderInput("Enter amount to deposit");
        amount = scanner.nextDouble();
        account.deposit(amount);

        this.accountManager.persist(new AccountHydrator(), AccountHydrator.hydrate(account));
    }

    public void withdraw(CustomerInterface customer) {
        AccountInterface account = customer.getAccount();
        Double amount;

        ScannerRenderer.renderSeparated("Withdrawal");
        ScannerRenderer.renderInput("Enter amount to withdraw");
        amount = scanner.nextDouble();
        account.withdraw(amount);

        this.accountManager.persist(new AccountHydrator(), AccountHydrator.hydrate(account));
    }
}
