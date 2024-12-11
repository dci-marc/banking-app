package org.dcistudent.banking.models;

import lombok.*;
import org.dcistudent.banking.exceptions.transfers.BankTransferException;
import org.dcistudent.banking.exceptions.validations.PinValidationException;
import org.dcistudent.banking.interfaces.models.AccountInterface;

@Getter @Setter
public abstract class AbstractAccount implements AccountInterface {
    private String id;
    private String customerId;
    private Integer pin;
    private Double balance = 0.0;
    private Double limitWithdrawal;
    private Double limitDeposit;
    private Integer overdraftCount = 0;
    private static final Integer OVERDRAFT_LIMIT = 2;
    private static final Double OVERDRAFT_FEE = 50.00;

    protected AbstractAccount(String id, Double limitWithdrawal, Double limitDeposit) {
        this.setId(id);
        this.setLimitWithdrawal(limitWithdrawal);
        this.setLimitDeposit(limitDeposit);
    }

    public void setPin(Integer pin) {
        if (pin < 999 || pin > 9999) {
            throw new PinValidationException("PIN must be a 4-digit number.");
        }
        this.pin = pin;
    }

    public AccountInterface withdraw(Double amount) {
        if (amount < 10) {
            throw new BankTransferException("Withdrawal amount must be at least 10.");
        }

        if (amount > this.getLimitWithdrawal()) {
            throw new BankTransferException(
                    String.format("Withdrawal amount of %f exceeds limit of %f.", amount, this.getLimitWithdrawal())
            );
        }

        amount = this.getOverdraftAmount(amount);
        this.setBalance(this.getBalance() - amount);

        return this;
    }

    public AccountInterface deposit(Double amount) {
        if (amount < 10) {
            throw new BankTransferException("Deposit amount must be at least  10.");
        }

        if (amount > this.getLimitDeposit()) {
            throw new BankTransferException(
                    String.format("Deposit amount of %f exceeds limit of %f.", amount, this.getLimitDeposit())
            );
        }

        this.setBalance(this.getBalance() + amount);

        return this;
    }

    private Double getOverdraftAmount(Double amount) {
        if (this.getOverdraftCount() >= OVERDRAFT_LIMIT) {
            throw new BankTransferException(
                    String.format("Overdraft limit of %d exceeded.", OVERDRAFT_LIMIT)
            );
        }

        if (this.getBalance() < 0) {
            this.setOverdraftCount(this.getOverdraftCount() + 1);
            return amount + OVERDRAFT_FEE;
        }

        return amount;
    }
}