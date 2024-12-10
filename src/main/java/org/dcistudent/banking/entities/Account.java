package org.dcistudent.banking.entities;

import lombok.*;
import org.dcistudent.banking.interfaces.entities.EntitiyInterface;

@Getter @Setter
public final class Account implements EntitiyInterface {
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
}
