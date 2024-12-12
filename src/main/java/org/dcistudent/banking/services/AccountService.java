package org.dcistudent.banking.services;

import org.dcistudent.banking.exceptions.transfers.BankTransferException;
import org.dcistudent.banking.exceptions.validations.accounts.LimitValidationException;
import org.dcistudent.banking.exceptions.validations.accounts.PinValidationException;
import org.dcistudent.banking.facades.ScannerFacade;
import org.dcistudent.banking.factories.AccountFactory;
import org.dcistudent.banking.hydrators.AccountHydrator;
import org.dcistudent.banking.interfaces.models.AccountInterface;
import org.dcistudent.banking.interfaces.models.CustomerInterface;
import org.dcistudent.banking.managers.AccountManager;
import org.dcistudent.banking.managers.criterias.FindByUuid;
import org.dcistudent.banking.models.CheckingAccount;
import org.dcistudent.banking.renderers.ScannerRenderer;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public final class AccountService {
    private final AccountManager accountManager;
    private static final String GIRO_ACCOUNT;

    static {
        GIRO_ACCOUNT = CheckingAccount.class.getSimpleName();
    }

    public AccountService() {
        this.accountManager = new AccountManager();
    }

    public AccountInterface create(CustomerInterface customer) {
        AccountInterface account = null;
        Integer accountType = 0;

        ScannerRenderer.renderSeparated("Bank Account Creation");
        System.out.println("Select account type:");
        for (Integer key : AccountFactory.ACCOUNT_TYPES.keySet()) {
            System.out.println(key + ". " + AccountFactory.ACCOUNT_TYPES.get(key));
        }
        ScannerRenderer.renderInputChoice();
        try {
            accountType = ScannerFacade.getInt();
        } catch (InputMismatchException e) {
            ScannerRenderer.renderSeparated("Invalid account type.");
            this.create(customer);
        }

        try {
            account = AccountFactory.create(accountType);
        } catch (IllegalArgumentException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.create(customer);
        }
        account.setCustomerId(customer.getId());
        customer.setAccount(account);

        this
                .createAccountSecurity(customer)
                .createAccountInitialDeposit(customer)
                .createAccountWithdrawLimit(customer)
        ;

        this.accountManager.persist(new AccountHydrator(), AccountHydrator.hydrate(account));

        return account;
    }

    private AccountService createAccountSecurity(CustomerInterface customer) {
        AccountInterface account = customer.getAccount();

        ScannerRenderer.renderSeparated("Bank Account Security");
        ScannerRenderer.renderInput("Enter your 4-digit PIN code");
        try {
            account.setPin(ScannerFacade.getInt());
            customer.setAccount(account);
        } catch (PinValidationException | IllegalArgumentException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.createAccountSecurity(customer);
        } catch (InputMismatchException e) {
            ScannerRenderer.renderSeparated("Invalid PIN code.");
            this.createAccountSecurity(customer);
        }

        return this;
    }

    private AccountService createAccountInitialDeposit(CustomerInterface customer) {
        AccountInterface account = customer.getAccount();
        Double amount = 0.0;

        ScannerRenderer.renderSeparated(
                String.format("Initial Deposit (Limit: %.2f)", account.getLimitDeposit())
        );
        ScannerRenderer.renderInput("Enter amount to deposit");
        try {
            amount = ScannerFacade.getDouble();
        } catch (IllegalArgumentException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.createAccountInitialDeposit(customer);
        }

        try {
            account.deposit(amount);
            customer.setAccount(account);
        } catch (BankTransferException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.createAccountInitialDeposit(customer);
        }

        return this;
    }

    private void createAccountWithdrawLimit(CustomerInterface customer) {
        AccountInterface account = customer.getAccount();
        Double amount = 0.0;

        ScannerRenderer.renderSeparated(
                String.format("Withdraw Limit (Limit: %.2f)", account.getLimitWithdrawal())
        );
        ScannerRenderer.renderInput("Enter amount to set as custom withdrawal limit");
        try {
            amount = ScannerFacade.getDouble();
        } catch (IllegalArgumentException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.createAccountWithdrawLimit(customer);
        }

        try {
            account.setLimitWithdrawalCustom(amount);
            customer.setAccount(account);
        } catch (LimitValidationException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.createAccountWithdrawLimit(customer);
        }
    }

    public AccountInterface getById(String id) throws Exception {
        return AccountHydrator.hydrate(this.accountManager.findById(new FindByUuid(id)));
    }

    public AccountInterface getByCustomerId(String customerId) {
        return AccountHydrator.hydrate(this.accountManager.findByCustomerId(new FindByUuid(customerId)));
    }

    public void deposit(CustomerInterface customer) {
        AccountInterface account = customer.getAccount();
        Double amount = 0.0;

        ScannerRenderer.renderSeparated(
                String.format("Deposition (Limit: %.2f)", account.getLimitDeposit())
        );
        ScannerRenderer.renderInput("Enter amount to deposit");
        try {
            amount = ScannerFacade.getDouble();
        } catch (IllegalArgumentException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.deposit(customer);
        }

        try {
            account.deposit(amount);
        } catch (BankTransferException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.deposit(customer);
        }

        this.accountManager.persist(new AccountHydrator(), AccountHydrator.hydrate(account));
    }

    public void withdraw(CustomerInterface customer) {
        AccountInterface account = customer.getAccount();
        Double amount;

        ScannerRenderer.renderSeparated(
                String.format("Withdrawal (Limit: %.2f)", account.getLimitWithdrawalCustom())
        );

        if (account.getOverdraftCount() >= account.getOverdraftLimit()) {
            throw new BankTransferException(
                    String.format(
                            "Overdraft limit of %d exceeded. Balance must be over 0 again for reset of overdraft limit.",
                            account.getOverdraftLimit()
                    )
            );
        }

        ScannerRenderer.renderInput("Enter amount to withdraw");
        try {
            amount = ScannerFacade.getDouble();
        } catch (IllegalArgumentException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.withdraw(customer);
            return;
        }
        this.transferPinValidation(account);

        if (account.getAccountName().equals(GIRO_ACCOUNT)) {
            System.out.println("Instant card balance validation...");
        }

        try {
            account.withdraw(amount);
        } catch (BankTransferException | LimitValidationException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.withdraw(customer);
        }

        this.accountManager.persist(new AccountHydrator(), AccountHydrator.hydrate(account));
    }

    public void transfer(CustomerInterface customer) {
        AccountInterface account = customer.getAccount();
        AccountInterface recipient;
        Double amount;

        ScannerRenderer.renderSeparated("Transfer");
        ScannerRenderer.renderInput("Enter recipient's account number");
        try {
            recipient = this.getById(ScannerFacade.getNonEmpty());
        } catch (Exception e) {
            ScannerRenderer.renderSeparated("Recipient account not found.");
            this.transfer(customer);
            return;
        }

        ScannerRenderer.renderInput("Enter amount to transfer");
        try {
            amount = ScannerFacade.getDouble();
        } catch (IllegalArgumentException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.transfer(customer);
            return;
        }
        this.transferPinValidation(account);

        try {
            account.withdraw(amount);
            recipient.deposit(amount);
        } catch (BankTransferException | LimitValidationException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.transfer(customer);
        }

        this.accountManager.persist(new AccountHydrator(), AccountHydrator.hydrate(account));
        this.accountManager.persist(new AccountHydrator(), AccountHydrator.hydrate(recipient));
    }

    private void transferPinValidation(AccountInterface account) {
        Integer pin = 0;

        ScannerRenderer.renderSeparated("Transfer Security");
        ScannerRenderer.renderInput("Enter recipient's 4-digit PIN code");
        try {
            pin = ScannerFacade.getInt();
        } catch (IllegalArgumentException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.transferPinValidation(account);
        }

        if (account.getPin().equals(pin) == false) {
            ScannerRenderer.renderSeparated("Invalid PIN code.");
            this.transferPinValidation(account);
        }
    }
}
