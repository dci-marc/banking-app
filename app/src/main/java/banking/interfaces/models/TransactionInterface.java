package banking.interfaces.models;

import lombok.NonNull;

public interface TransactionInterface {
    String getId();

    @NonNull
    void setId(String id);
    String getAccountId();

    @NonNull
    void setAccountId(String accountId);
    Double getAmount();

    @NonNull
    void setAmount(Double amount);
    Double getBalanceBefore();

    @NonNull
    void setBalanceBefore(Double balanceBefore);
    Double getBalanceAfter();

    @NonNull
    void setBalanceAfter(Double balanceAfter);
    String getDatetime();

    @NonNull
    void setDatetime(String datetime);
    Boolean getActive();

    @NonNull
    void setActive(Boolean active);
}
