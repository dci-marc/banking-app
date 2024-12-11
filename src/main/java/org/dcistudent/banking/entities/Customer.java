package org.dcistudent.banking.entities;

import lombok.*;
import org.dcistudent.banking.interfaces.entities.EntityInterface;
import org.dcistudent.banking.interfaces.models.AccountInterface;

@Getter @Setter
public final class Customer implements EntityInterface {
    @NonNull
    private String id;
    @NonNull
    private String username;
    @NonNull
    private String password = "";
    @NonNull
    private String firstName;
    @NonNull
    private AccountInterface account;

    @Override
    public String toString() {
        return this.getId() +
                "," + this.getUsername() +
                "," + this.getPassword() +
                "," + this.getFirstName() +
                "," + this.getAccount().getId()
        ;
    }
}
