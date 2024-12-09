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
    private String accountType;
}
