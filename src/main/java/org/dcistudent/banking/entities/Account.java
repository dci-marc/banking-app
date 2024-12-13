package org.dcistudent.banking.entities;

import lombok.*;
import org.dcistudent.banking.interfaces.entities.EntityInterface;

@Getter @Setter
public final class Account implements EntityInterface {
    @NonNull
    private String id;
    @NonNull
    private String customerId;
    @NonNull
    private Integer pin;
    @NonNull
    private Integer accountType;
    @NonNull
    private Double balance = 0.0;
    @NonNull
    private Integer overdraftCount = 0;
    @NonNull
    private Boolean active = true;

    @Override
    public String toString() {
        return this.getId() +
                "," + this.getCustomerId() +
                "," + this.getPin() +
                "," + this.getAccountType() +
                "," + this.getBalance() +
                "," + this.getOverdraftCount() +
                "," + this.getActive()
        ;
    }
}
