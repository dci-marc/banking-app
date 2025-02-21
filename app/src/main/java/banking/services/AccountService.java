package banking.services;

import lombok.NonNull;
import banking.exceptions.transfers.BankTransferException;
import banking.exceptions.validations.accounts.LimitValidationException;
import banking.exceptions.validations.accounts.PinValidationException;
import banking.facades.ScannerFacade;
import banking.factories.AccountFactory;
import banking.hydrators.AccountHydrator;
import banking.interfaces.models.AccountInterface;
import banking.interfaces.models.CustomerInterface;
import banking.interfaces.models.TransactionInterface;
import banking.managers.AccountManager;
import banking.managers.criterias.FindByUuid;
import banking.models.CheckingAccount;
import banking.models.Transaction;
import banking.renderers.ScannerRenderer;

import java.util.InputMismatchException;
import java.util.Optional;

public final class AccountService {
    @NonNull
    private final AccountManager accountManager;
    @NonNull
    private final TransactionService transactionService;

    public AccountService(AccountManager accountManager, TransactionService transactionService) {
        this.accountManager = accountManager;
        this.transactionService = transactionService;
    }

    @NonNull
    public AccountInterface create(CustomerInterface customer) {
        Optional<AccountInterface> account = Optional.empty();
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
            account = Optional.of(AccountFactory.create(accountType));
        } catch (IllegalArgumentException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.create(customer);
        }

        account.ifPresent(obj -> {
            obj.setCustomerId(customer.getId());
            customer.setAccount(obj);

            this
                    .createAccountSecurity(customer)
                    .createAccountInitialDeposit(customer)
                    .createAccountWithdrawLimit(customer)
            ;

            this.accountManager.persist(new AccountHydrator(), AccountHydrator.hydrate(obj));
        });

        if (account.isPresent() == false) {
            return this.create(customer);
        }

        return account.get();
    }

    @NonNull
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

    @NonNull
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

    @NonNull
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

    @NonNull
    public AccountInterface getById(String id) throws Exception {
        return AccountHydrator.hydrate(this.accountManager.findById(new FindByUuid(id)));
    }

    @NonNull
    public AccountInterface getByCustomerId(String customerId) {
        return AccountHydrator.hydrate(this.accountManager.findByCustomerId(new FindByUuid(customerId)));
    }

    @NonNull
    public void deposit(CustomerInterface customer) {
        AccountInterface account = customer.getAccount();
        TransactionInterface transaction = new Transaction();
        Double amount = 0.0;

        transaction.setAccountId(account.getId());
        transaction.setBalanceBefore(account.getBalance());

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

        transaction.setAmount(amount);
        transaction.setBalanceAfter(account.getBalance());

        this.accountManager.persist(new AccountHydrator(), AccountHydrator.hydrate(account));
        this.transactionService.registerTransaction(transaction);
    }

    @NonNull
    public void withdraw(CustomerInterface customer) throws InterruptedException {
        AccountInterface account = customer.getAccount();
        TransactionInterface transaction = new Transaction();
        Double amount = 0.0;

        transaction.setAccountId(account.getId());
        transaction.setBalanceBefore(account.getBalance());

        ScannerRenderer.renderSeparated(
                String.format("Withdrawal (Limit: %.2f)", account.getLimitWithdrawalCustom())
        );

        ScannerRenderer.renderInput("Enter amount to withdraw");
        try {
            amount = ScannerFacade.getDouble();
        } catch (IllegalArgumentException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.withdraw(customer);
            return;
        }
        this.transferPinValidation(account);

        if (account.getAccountName().equals(CheckingAccount.class.getSimpleName())) {
            System.out.printf("%nInstant card balance validation");

            Thread.sleep(1000);
            for (int i = 0; i < 3; i++) {
                System.out.print(".");
                Thread.sleep(1000);
            }
        }

        try {
            account.withdraw(amount);
        } catch (BankTransferException | LimitValidationException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.withdraw(customer);
        }

        transaction.setAmount(amount);
        transaction.setBalanceAfter(account.getBalance());

        this.accountManager.persist(new AccountHydrator(), AccountHydrator.hydrate(account));
        this.transactionService.registerTransaction(transaction);
    }

    @NonNull
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

        System.out.printf("%nTransferring %.2f to account '%s'.", amount, recipient.getId());
    }

    @NonNull
    public void closeAccount(AccountInterface account) {
        account.setActive(false);

        this.accountManager.persist(new AccountHydrator(), AccountHydrator.hydrate(account));
    }

    @NonNull
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
