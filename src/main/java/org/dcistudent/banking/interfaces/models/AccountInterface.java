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
    public Double getLimitWithdrawal();
    public void setLimitWithdrawal(Double limitWithdrawal);
    public Double getLimitWithdrawalCustom();
    public void setLimitWithdrawalCustom(Double limitWithdrawalCustom);
    public Double getLimitDeposit();
    public Integer getOverdraftCount();
    public void setOverdraftCount(Integer overdraftCount);
    public AccountInterface withdraw(Double amount);
    public AccountInterface deposit(Double amount);
}
