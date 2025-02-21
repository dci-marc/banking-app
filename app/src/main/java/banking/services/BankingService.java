package banking.services;

import lombok.NonNull;
import banking.exceptions.validations.customers.PasswordValidationException;
import banking.interfaces.models.CustomerInterface;
import banking.models.Customer;

import java.security.NoSuchAlgorithmException;

public final class BankingService {
    @NonNull
    private final CustomerService customerService;
    @NonNull
    private final AccountService accountService;
    @NonNull
    private final TransactionService transactionService;
    @NonNull
    private CustomerInterface customer = new Customer();

    public BankingService(
            CustomerService customerService,
            AccountService accountService,
            TransactionService transactionService
    ) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.transactionService = transactionService;
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

    public void transactions() {
        this.transactionService.getTransactionsByDays(this.customer.getAccount());
    }

    public void resetPassword() throws NoSuchAlgorithmException, PasswordValidationException {
        this.customerService.resetPassword(this.customer);
    }

    public void closeAccount() {
        this.customerService.closeAccount(this.customer);
    }

    public void closeSession() {
        this.customer = new Customer();
    }
}
