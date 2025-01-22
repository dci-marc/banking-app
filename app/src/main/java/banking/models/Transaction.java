package banking.models;

import lombok.*;
import banking.interfaces.models.TransactionInterface;

@Getter @Setter
public final class Transaction implements TransactionInterface {
    @NonNull
    private String id;
    @NonNull
    private String accountId;
    @NonNull
    private Double amount = 0.0;
    @NonNull
    private Double balanceBefore = 0.0;
    @NonNull
    private Double balanceAfter = 0.0;
    @NonNull
    private String datetime;
    @NonNull
    private Boolean active = true;
}
