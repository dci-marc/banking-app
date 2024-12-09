package org.dcistudent.banking.models;

public final class GoldAccount extends AbstractAccount {
    public static final Double LIMIT_WITHDRAWAL = 10000.00;
    public static final Double LIMIT_DEPOSIT = 15000.00;

    public GoldAccount(String accountId) {
        super(accountId, LIMIT_WITHDRAWAL, LIMIT_DEPOSIT);
    }
}
