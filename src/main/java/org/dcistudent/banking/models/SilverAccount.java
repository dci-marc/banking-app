package org.dcistudent.banking.models;

public final class SilverAccount extends AbstractAccount {
    public static final Double LIMIT_WITHDRAWAL = 1000.00;
    public static final Double LIMIT_DEPOSIT = 1500.00;

    public SilverAccount(String accountId) {
        super(accountId, LIMIT_WITHDRAWAL, LIMIT_DEPOSIT);
    }
}
