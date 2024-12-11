package org.dcistudent.banking.models;

import lombok.*;
import org.dcistudent.banking.exceptions.transfers.BankTransferException;
import org.dcistudent.banking.exceptions.validations.accounts.LimitValidationException;
import org.dcistudent.banking.exceptions.validations.accounts.PinValidationException;
import org.dcistudent.banking.interfaces.models.AccountInterface;

@Getter @Setter
public abstract class AbstractAccount implements AccountInterface {
    private String id;
    private String customerId;
    private Integer pin;
    private Double balance = 0.0;
    private Double limitWithdrawal;
    private Double limitWithdrawalCustom;
    private Double limitDeposit;
    private Integer overdraftCount = 0;
    private static final Integer OVERDRAFT_LIMIT = 2;
    private static final Double OVERDRAFT_FEE = 50.00;

    protected AbstractAccount(String id, Double limitWithdrawal, Double limitDeposit) {
        this.setId(id);
        this.setLimitWithdrawal(limitWithdrawal);
        this.setLimitDeposit(limitDeposit);
        this.limitWithdrawalCustom = this.getLimitWithdrawal();
    }

    public void setPin(Integer pin) {
        if (pin < 999 || pin > 9999) {
            throw new PinValidationException("PIN must be a 4-digit number.");
        }
        this.pin = pin;
    }

    public void setLimitWithdrawalCustom(Double limit) {
        if(limit > this.getLimitWithdrawal()) {
            throw new LimitValidationException(
                    String.format("Withdrawal limit of %f exceeds limit of %f.", limit, this.getLimitWithdrawal())
            );
        }
        if (limit > this.getLimitWithdrawalCustom()) {
            throw new LimitValidationException(
                    String.format(
                            "Withdrawal limit of %f exceeds custom limit of %f.", limit, this.getLimitWithdrawalCustom()
                    )
            );
        }
        if (limit < 10) {
            throw new LimitValidationException("Withdrawal limit must be at least 10.");
        }
        this.limitWithdrawalCustom = limit;
    }

    public String getAccountName() {
        return this.getClass().getSimpleName();
    }

    public Integer getOverdraftLimit() {
        return OVERDRAFT_LIMIT;
    }

    public AccountInterface withdraw(Double amount) throws LimitValidationException {
        if (amount < 10) {
            throw new BankTransferException("Withdrawal amount must be at least 10.");
        }

        if (amount > this.getLimitWithdrawal()) {
            throw new BankTransferException(
                    String.format("Withdrawal amount of %f exceeds limit of %f.", amount, this.getLimitWithdrawal())
            );
        }

        amount = this.setOverdraftAmount(amount);
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

        if (this.getBalance() > 0) {
            this.setOverdraftCount(0);
        }

        return this;
    }

    private Double setOverdraftAmount(Double amount) {
        if (this.getOverdraftCount() >= OVERDRAFT_LIMIT) {
            throw new BankTransferException(
                    String.format("Overdraft limit of %d exceeded.", OVERDRAFT_LIMIT)
            );
        }

        if (this.getBalance() - amount < 0) {
            this.setOverdraftCount(this.getOverdraftCount() + 1);
            return amount + OVERDRAFT_FEE;
        }

        return amount;
    }
}