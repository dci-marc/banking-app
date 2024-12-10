package org.dcistudent.banking.interfaces.models;

public interface AccountInterface {
    public String getId();
    public void setId(String id);
    public String getCustomerId();
    public void setCustomerId(String customerId);
    public Integer getPin();
    public void setPin(Integer pin);
    public Integer getAccountType();
    public void setAccountType(Integer accountType);
    public Double getBalance();
    public void setBalance(Double balance);
    public Integer getOverdraftCount();
    public void setOverdraftCount(Integer overdraftCount);
    public AccountInterface withdraw(Double amount);
    public AccountInterface deposit(Double amount);
}
