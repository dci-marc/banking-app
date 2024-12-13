package org.dcistudent.banking.models;

import lombok.*;
import org.dcistudent.banking.exceptions.validations.accounts.LimitValidationException;
import org.dcistudent.banking.interfaces.models.AccountInterface;

@Getter @Setter
public final class SavingsAccount extends AbstractAccount {
    private Integer accountType = 4;
    public static final Double LIMIT_WITHDRAWAL = 500.00;
    public static final Double LIMIT_DEPOSIT = 1000.00;

    @NonNull
    public SavingsAccount(String accountId) {
        super(accountId, LIMIT_WITHDRAWAL, LIMIT_DEPOSIT);
    }

    @NonNull @Override
    public AccountInterface withdraw(Double amount) throws LimitValidationException {
        if (this.getBalance() - amount < 0) {
            throw new LimitValidationException(
                    String.format("Insufficient balance. Current balance: %.2f", this.getBalance())
            );
        }

        super.withdraw(amount);
        return this;
    }
}
