package org.dcistudent.banking.services;

import org.dcistudent.banking.exceptions.validations.PinValidationException;
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

        ScannerRenderer.renderInput("Enter your 4-digit PIN code");
        try {
            account.setPin(scanner.nextInt());
        } catch (PinValidationException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.create(customer);
        } catch (InputMismatchException e) {
            ScannerRenderer.renderSeparated("Invalid PIN code.");
            this.create(customer);
        } catch (Exception e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.create(customer);
        }

        this.accountManager.persist(new AccountHydrator(), AccountHydrator.hydrate(account));

        return account;
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
}
