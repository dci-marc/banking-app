package org.dcistudent.banking.services;

import org.dcistudent.banking.exceptions.validations.PinValidationException;
import org.dcistudent.banking.factories.AccountFactory;
import org.dcistudent.banking.hydrators.AccountHydrator;
import org.dcistudent.banking.interfaces.models.AccountInterface;
import org.dcistudent.banking.managers.AccountManager;
import org.dcistudent.banking.models.*;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public final class AccountService {
    private final Scanner scanner;
    private final AccountManager accountManager;

    public AccountService(Scanner scanner) {
        this.scanner = scanner;
        this.accountManager = new AccountManager();
    }

    public AccountInterface create(Customer customer) {
        AccountInterface account = null;

        System.out.println("Select account type:");
        System.out.println("1. Silver");
        System.out.println("2. Gold");
        System.out.println("3. Platinum");
        Integer accountType = scanner.nextInt();

        try {
            account = AccountFactory.create(accountType);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            this.create(customer);
        }
        account.setCustomerId(customer.getId());

        System.out.println("Enter your 4-digit PIN code:");
        try {
            account.setPin(scanner.nextInt());
        } catch (PinValidationException e) {
            System.out.println(e.getMessage());
            this.create(customer);
        } catch (InputMismatchException e) {
            System.out.println("Invalid PIN code.");
            this.create(customer);
        } catch (Exception e) {
            System.out.println(e);
            this.create(customer);
        }

        this.accountManager.persist(new AccountHydrator(), AccountHydrator.hydrate(account));

        return account;
    }
}
