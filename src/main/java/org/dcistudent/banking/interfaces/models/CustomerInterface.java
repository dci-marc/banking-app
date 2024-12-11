package org.dcistudent.banking.interfaces.models;

import java.security.NoSuchAlgorithmException;

public interface CustomerInterface {
    public String getId();
    public void setId(String id);
    public String getUsername();
    public void setUsername(String username);
    public String getPassword();
    public void setPassword(String password) throws NoSuchAlgorithmException;
    public String getFirstName();
    public void setFirstName(String firstName);
    public AccountInterface getAccount();
    public void setAccount(AccountInterface account);
    public String hashPassword(String password);
    public Boolean verifyPassword(String password);
}
