package org.dcistudent.banking.models;

public final class PlatinumAccount extends AbstractAccount {
    public static final Double LIMIT_WITHDRAWAL = 20000.00;
    public static final Double LIMIT_DEPOSIT = 30000.00;

    public PlatinumAccount(String accountId) {
        super(accountId, LIMIT_WITHDRAWAL, LIMIT_DEPOSIT);
    }
}
