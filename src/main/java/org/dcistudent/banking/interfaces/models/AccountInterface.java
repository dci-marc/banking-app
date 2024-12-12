package org.dcistudent.banking.interfaces.models;

import lombok.NonNull;
import org.dcistudent.banking.exceptions.validations.accounts.LimitValidationException;

public interface AccountInterface {
    String getId();

    @NonNull
    void setId(String id);
    String getCustomerId();

    @NonNull
    void setCustomerId(String customerId);
    Integer getPin();

    @NonNull
    void setPin(Integer pin);
    String getAccountName();
    Integer getAccountType();

    @NonNull
    void setAccountType(Integer accountType);
    Double getBalance();

    @NonNull
    void setBalance(Double balance);
    Double getLimitWithdrawal();

    @NonNull
    void setLimitWithdrawal(Double limitWithdrawal);
    Double getLimitWithdrawalCustom();

    @NonNull
    void setLimitWithdrawalCustom(Double limitWithdrawalCustom);
    Double getLimitDeposit();
    Integer getOverdraftCount();

    @NonNull
    void setOverdraftCount(Integer overdraftCount);
    Integer getOverdraftLimit();

    @NonNull
    AccountInterface withdraw(Double amount) throws LimitValidationException;

    @NonNull
    AccountInterface deposit(Double amount);
}
