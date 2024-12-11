package org.dcistudent.banking.services;

import org.dcistudent.banking.exceptions.validations.customers.UsernameValidationException;
import org.dcistudent.banking.hydrators.CustomerHydrator;
import org.dcistudent.banking.interfaces.models.CustomerInterface;
import org.dcistudent.banking.managers.CustomerManager;
import org.dcistudent.banking.models.Customer;
import org.dcistudent.banking.renderers.ScannerRenderer;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.UUID;

public final class CustomerService {
    private final Scanner scanner;
    private final AccountService accountService;
    private final CustomerManager customerManager;

    public CustomerService(Scanner scanner) {
        this.scanner = scanner;
        this.accountService = new AccountService(this.scanner);
        this.customerManager = new CustomerManager();
    }

    public CustomerInterface create() throws NoSuchAlgorithmException {
        CustomerInterface customer = new Customer();
        String username;

        ScannerRenderer.renderSeparated("Customer Account Creation");
        ScannerRenderer.renderInput("Enter your username");
        username = String.valueOf(this.scanner.next());
        try {
            this.customerManager.findByUsername(username);
            throw new UsernameValidationException("Username already exists.");
        } catch (NoSuchElementException e) {
            customer.setUsername(username);
            ScannerRenderer.renderInput("Enter your password");
            customer.setPassword(customer.hashPassword(String.valueOf(this.scanner.next())));
            ScannerRenderer.renderInput("Enter your first name");
            customer.setFirstName(String.valueOf(this.scanner.next()));
            customer.setId(UUID.randomUUID().toString());
            customer.setAccount(this.accountService.create(customer));

            this.customerManager.persist(new CustomerHydrator(), CustomerHydrator.hydrate(customer));
        }

        return customer;
    }

    public CustomerInterface login() {
        CustomerInterface customer;
        String username;
        String password;

        ScannerRenderer.renderSeparated("Enter login credentials.");
        try {
            ScannerRenderer.renderInput("Enter your username");
            username = String.valueOf(this.scanner.next());
            customer = CustomerHydrator.hydrate(this.customerManager.findByUsername(username));
            customer.setAccount(this.accountService.getByCustomerId(customer.getId()));

            ScannerRenderer.renderInput("Enter your password");
            password = String.valueOf(this.scanner.next());

            if (customer.verifyPassword(password) == false) {
                throw new UsernameValidationException("Invalid login.");
            }
        } catch (NoSuchElementException e) {
            throw new UsernameValidationException("Invalid login.");
        }

        return customer;
    }

    public void resetPassword(CustomerInterface customer) throws NoSuchAlgorithmException {
        String password;
        ScannerRenderer.renderSeparated("Password Reset");
        ScannerRenderer.renderInput("Enter your new password");
        password = this.scanner.next();
        if (customer.verifyPassword(password)) {
            throw new UsernameValidationException("New password must be different from the old one.");
        }

        customer.setPassword(customer.hashPassword(password));

        this.customerManager.persist(new CustomerHydrator(), CustomerHydrator.hydrate(customer));
    }
}
