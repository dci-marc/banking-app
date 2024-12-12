package org.dcistudent.banking.interfaces.models;

import lombok.NonNull;

import java.security.NoSuchAlgorithmException;

public interface CustomerInterface {
    public String getId();

    @NonNull
    public void setId(String id);
    public String getUsername();

    @NonNull
    public void setUsername(String username);
    public String getPassword();

    @NonNull
    public void setPassword(String password) throws NoSuchAlgorithmException;
    public String getFirstName();

    @NonNull
    public void setFirstName(String firstName);
    public AccountInterface getAccount();

    @NonNull
    public void setAccount(AccountInterface account);

    @NonNull
    public String hashPassword(String password);

    @NonNull
    public Boolean verifyPassword(String password);
}
