package org.dcistudent.banking.services;

import org.dcistudent.banking.models.Customer;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public final class BankingService {
    private final Scanner scanner;
    private final CustomerService customerService;

    public BankingService(Scanner scanner) {
        this.scanner = scanner;
        this.customerService = new CustomerService(this.scanner);
    }

    public Customer signup() throws NoSuchAlgorithmException {
        return this.customerService.create();
    }

    public Customer login() {
        System.out.println("Enter your username: ");
        String username = this.scanner.next();
        System.out.println("Enter your password: ");
        String password = this.scanner.next();

        // @TODO: Implement login logic here
        return new Customer();
    }
}
