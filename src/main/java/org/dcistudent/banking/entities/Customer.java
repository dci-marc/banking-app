package org.dcistudent.banking.entities;

import lombok.*;
import org.dcistudent.banking.interfaces.entities.EntityInterface;

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

    @Override
    public String toString() {
        return this.getId() +
                "," + this.getUsername() +
                "," + this.getPassword() +
                "," + this.getFirstName()
        ;
    }
}
