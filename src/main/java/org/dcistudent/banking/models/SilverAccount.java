package org.dcistudent.banking.models;

import lombok.*;

@Getter @Setter
public final class SilverAccount extends AbstractAccount {
    private Integer accountType = 1;
    public static final Double LIMIT_WITHDRAWAL = 1000.00;
    public static final Double LIMIT_DEPOSIT = 1500.00;

    public SilverAccount(String accountId) {
        super(accountId, LIMIT_WITHDRAWAL, LIMIT_DEPOSIT);
    }
}
