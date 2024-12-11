package org.dcistudent.banking.services;

import org.dcistudent.banking.interfaces.models.CustomerInterface;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public final class BankingService {
    private final Scanner scanner;
    private final CustomerService customerService;
    private final AccountService accountService;

    public BankingService(Scanner scanner) {
        this.scanner = scanner;
        this.customerService = new CustomerService(this.scanner);
        this.accountService = new AccountService(this.scanner);
    }

    public CustomerInterface signup() throws NoSuchAlgorithmException {
        return this.customerService.create();
    }

    public CustomerInterface login() {
        return this.customerService.login();
    }
}
