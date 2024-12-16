package org.dcistudent.banking.services;

import lombok.NonNull;
import org.dcistudent.banking.exceptions.validations.customers.UsernameValidationException;
import org.dcistudent.banking.facades.ScannerFacade;
import org.dcistudent.banking.hydrators.CustomerHydrator;
import org.dcistudent.banking.interfaces.models.AccountInterface;
import org.dcistudent.banking.interfaces.models.CustomerInterface;
import org.dcistudent.banking.managers.CustomerManager;
import org.dcistudent.banking.models.Customer;
import org.dcistudent.banking.renderers.ScannerRenderer;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.UUID;

public final class CustomerService {
    @NonNull
    private final AccountService accountService;
    @NonNull
    private final CustomerManager customerManager;

    public CustomerService(AccountService accountService, CustomerManager customerManager) {
        this.accountService = accountService;
        this.customerManager = customerManager;
    }

    public CustomerInterface create() throws NoSuchAlgorithmException {
        CustomerInterface customer = new Customer();
        String username;

        ScannerRenderer.renderSeparated("Customer Account Creation");
        ScannerRenderer.renderInput("Enter your username");
        username = ScannerFacade.getNonEmpty();
        try {
            this.customerManager.findByUsername(username);
            throw new UsernameValidationException("Username already taken.");
        } catch (NoSuchElementException e) {
            customer.setUsername(username);
            ScannerRenderer.renderInput("Enter your password");
            customer.setPassword(customer.hashPassword(ScannerFacade.getNonEmpty()));
            ScannerRenderer.renderInput("Enter your first name");
            customer.setFirstName(ScannerFacade.getNonEmpty());
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
            username = ScannerFacade.getNonEmpty();
            customer = CustomerHydrator.hydrate(this.customerManager.findByUsername(username));
            customer.setAccount(this.accountService.getByCustomerId(customer.getId()));

            ScannerRenderer.renderInput("Enter your password");
            password = ScannerFacade.getNonEmpty();

            if (customer.verifyPassword(password) == false) {
                throw new UsernameValidationException("Invalid login.");
            }
        } catch (NoSuchElementException e) {
            throw new UsernameValidationException("Invalid login.");
        }

        return customer;
    }

    @NonNull
    public void resetPassword(CustomerInterface customer) throws NoSuchAlgorithmException {
        String password;
        ScannerRenderer.renderSeparated("Password Reset");
        ScannerRenderer.renderInput("Enter your new password");
        password = ScannerFacade.getNonEmpty();
        if (customer.verifyPassword(password) == true) {
            throw new UsernameValidationException("New password must be different from the old one.");
        }

        customer.setPassword(customer.hashPassword(password));

        this.customerManager.persist(new CustomerHydrator(), CustomerHydrator.hydrate(customer));
    }

    @NonNull
    public void closeAccount(CustomerInterface customer) {
        Boolean choice;
        ScannerRenderer.renderSeparated("Close Account");
        ScannerRenderer.renderInput("Are you sure you want to close your account? (yes/no)");
        choice = ScannerFacade.getYesNo();
        if (choice == false) {
            ScannerRenderer.renderSeparated("Account closure cancelled.");
        }

        customer.setActive(false);
        AccountInterface account = customer.getAccount();

        this.accountService.closeAccount(account);
        this.customerManager.persist(new CustomerHydrator(), CustomerHydrator.hydrate(customer));
    }
}
