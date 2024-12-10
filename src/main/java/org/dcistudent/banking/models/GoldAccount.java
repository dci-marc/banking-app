package org.dcistudent.banking.models;

import lombok.*;

@Getter @Setter
public final class GoldAccount extends AbstractAccount {
    private Integer accountType = 2;
    public static final Double LIMIT_WITHDRAWAL = 10000.00;
    public static final Double LIMIT_DEPOSIT = 15000.00;

    public GoldAccount(String accountId) {
        super(accountId, LIMIT_WITHDRAWAL, LIMIT_DEPOSIT);
    }
}
