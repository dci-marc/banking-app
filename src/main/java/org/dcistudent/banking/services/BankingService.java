package org.dcistudent.banking.services;

import lombok.NonNull;
import org.dcistudent.banking.exceptions.validations.customers.PasswordValidationException;
import org.dcistudent.banking.interfaces.models.CustomerInterface;
import org.dcistudent.banking.models.Customer;

import java.security.NoSuchAlgorithmException;

public final class BankingService {
    @NonNull
    private final CustomerService customerService;
    @NonNull
    private final AccountService accountService;
    @NonNull
    private CustomerInterface customer = new Customer();

    public BankingService() {
        this.customerService = new CustomerService();
        this.accountService = new AccountService();
    }

    public CustomerInterface signup() throws NoSuchAlgorithmException {
        return this.customerService.create();
    }

    public CustomerInterface login() {
        this.customer = this.customerService.login();
        return this.customer;
    }

    public Double checkBalance() {
        return this.customer.getAccount().getBalance();
    }

    public void deposit() {
        this.accountService.deposit(this.customer);
    }

    public void withdraw() throws InterruptedException {
        this.accountService.withdraw(this.customer);
    }

    public void transfer() {
        this.accountService.transfer(this.customer);
    }

    public void resetPassword() throws NoSuchAlgorithmException, PasswordValidationException {
        this.customerService.resetPassword(this.customer);
    }

    public void closeSession() {
        this.customer = new Customer();
    }
}
