package org.dcistudent.banking.services;

import org.dcistudent.banking.exceptions.validations.customers.UsernameValidationException;
import org.dcistudent.banking.hydrators.CustomerHydrator;
import org.dcistudent.banking.managers.CustomerManager;
import org.dcistudent.banking.models.Customer;

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

    public Customer create() throws NoSuchAlgorithmException {
        Customer customer = new Customer();
        String username;

        System.out.println("Enter your username:");
        username = String.valueOf(this.scanner.next());
        try {
            this.customerManager.findByUsername(username);
            throw new UsernameValidationException("Username already exists.");
        } catch (NoSuchElementException e) {
            customer.setUsername(username);
            System.out.println("Enter your password:");
            customer.setPassword(String.valueOf(this.scanner.next()));
            System.out.println("Enter your first name:");
            customer.setFirstName(String.valueOf(this.scanner.next()));
            customer.setId(UUID.randomUUID().toString());
            customer.setAccount(this.accountService.create(customer));

            this.customerManager.persist(new CustomerHydrator(), CustomerHydrator.hydrate(customer));
        }

        return customer;
    }
}
