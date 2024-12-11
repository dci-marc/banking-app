package org.dcistudent.banking.interfaces.models;

import org.dcistudent.banking.exceptions.validations.accounts.LimitValidationException;

public interface AccountInterface {
    public String getId();
    public void setId(String id);
    public String getCustomerId();
    public void setCustomerId(String customerId);
    public Integer getPin();
    public void setPin(Integer pin);
    public String getAccountName();
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
    public Integer getOverdraftLimit();
    public AccountInterface withdraw(Double amount) throws LimitValidationException;
    public AccountInterface deposit(Double amount);
}
