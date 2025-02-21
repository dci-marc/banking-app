package banking.entities;

import lombok.*;
import banking.interfaces.entities.EntityInterface;

import java.time.LocalDateTime;

@Getter @Setter
public final class Transaction implements EntityInterface {
    @NonNull
    private String id;
    @NonNull
    private String accountId;
    @NonNull
    private Double amount;
    @NonNull
    private Double balanceBefore;
    @NonNull
    private Double balanceAfter;
    @NonNull
    private String datetime;
    @NonNull
    private Boolean active = true;

    public Transaction() {
        this.setDatetime(LocalDateTime.now().toString());
    }

    @Override
    public String toString() {
        return String.join(
                ",",
                this.getId(),
                this.getAccountId(),
                this.getAmount().toString(),
                this.getBalanceBefore().toString(),
                this.getBalanceAfter().toString(),
                this.getDatetime(),
                this.getActive().toString()
        );
    }
}
