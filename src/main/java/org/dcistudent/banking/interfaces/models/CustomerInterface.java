package org.dcistudent.banking.interfaces.models;

import lombok.NonNull;

import java.security.NoSuchAlgorithmException;

public interface CustomerInterface {
    String getId();

    @NonNull
    void setId(String id);
    String getUsername();

    @NonNull
    void setUsername(String username);
    String getPassword();

    @NonNull
    void setPassword(String password) throws NoSuchAlgorithmException;
    String getFirstName();

    @NonNull
    void setFirstName(String firstName);
    Boolean getActive();

    @NonNull
    void setActive(Boolean active);
    AccountInterface getAccount();

    @NonNull
    void setAccount(AccountInterface account);

    @NonNull
    String hashPassword(String password);

    @NonNull
    Boolean verifyPassword(String password);
}
