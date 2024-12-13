package org.dcistudent.banking.entities;

import lombok.*;
import org.dcistudent.banking.interfaces.entities.EntityInterface;

import java.time.LocalDateTime;

@Getter @Setter
public final class Transaction implements EntityInterface {
    @NonNull
    private String id;
    @NonNull
    private String accountId;
    @NonNull
    private Double amount;
    @NonNull
    private Double balanceBefore;
    @NonNull
    private Double balanceAfter;
    @NonNull
    private String datetime;
    @NonNull
    private Boolean active = true;

    public Transaction() {
        this.setDatetime(LocalDateTime.now().toString());
    }

    @Override
    public String toString() {
        return this.getId() +
                "," + this.getAccountId() +
                "," + this.getAmount() +
                "," + this.getBalanceBefore() +
                "," + this.getBalanceAfter() +
                "," + this.getDatetime() +
                "," + this.getActive()
        ;
    }
}
