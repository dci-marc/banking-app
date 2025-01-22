package banking.exceptions.transfers;

import lombok.NonNull;

public class BankTransferException extends RuntimeException {
    @NonNull
    public BankTransferException(String message) {
        super(message);
    }
}
