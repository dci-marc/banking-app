package org.dcistudent.banking.models;

import lombok.*;

@Getter @Setter
public final class PlatinumAccount extends AbstractAccount {
    private Integer accountType = 3;
    public static final Double LIMIT_WITHDRAWAL = 20000.00;
    public static final Double LIMIT_DEPOSIT = 30000.00;

    @NonNull
    public PlatinumAccount(String accountId) {
        super(accountId, LIMIT_WITHDRAWAL, LIMIT_DEPOSIT);
    }
}
