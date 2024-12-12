package org.dcistudent.banking.services;

import org.dcistudent.banking.exceptions.validations.customers.PasswordValidationException;
import org.dcistudent.banking.facades.ScannerFacade;
import org.dcistudent.banking.interfaces.models.CustomerInterface;
import org.dcistudent.banking.models.Customer;

import java.security.NoSuchAlgorithmException;

public final class BankingService {
    private final CustomerService customerService;
    private final AccountService accountService;
    private CustomerInterface customer;

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

    public void withdraw() {
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
