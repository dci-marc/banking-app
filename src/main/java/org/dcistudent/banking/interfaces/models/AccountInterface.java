package org.dcistudent.banking.interfaces.models;

import lombok.NonNull;
import org.dcistudent.banking.exceptions.validations.accounts.LimitValidationException;

public interface AccountInterface {
    public String getId();

    @NonNull
    public void setId(String id);
    public String getCustomerId();

    @NonNull
    public void setCustomerId(String customerId);
    public Integer getPin();

    @NonNull
    public void setPin(Integer pin);
    public String getAccountName();
    public Integer getAccountType();

    @NonNull
    public void setAccountType(Integer accountType);
    public Double getBalance();

    @NonNull
    public void setBalance(Double balance);
    public Double getLimitWithdrawal();

    @NonNull
    public void setLimitWithdrawal(Double limitWithdrawal);
    public Double getLimitWithdrawalCustom();

    @NonNull
    public void setLimitWithdrawalCustom(Double limitWithdrawalCustom);
    public Double getLimitDeposit();
    public Integer getOverdraftCount();

    @NonNull
    public void setOverdraftCount(Integer overdraftCount);
    public Integer getOverdraftLimit();

    @NonNull
    public AccountInterface withdraw(Double amount) throws LimitValidationException;

    @NonNull
    public AccountInterface deposit(Double amount);
}
