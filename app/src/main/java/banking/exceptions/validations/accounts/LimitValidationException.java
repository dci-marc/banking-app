package banking.exceptions.validations.accounts;

import lombok.NonNull;

public class LimitValidationException extends RuntimeException {
    @NonNull
    public LimitValidationException(String message) {
        super(message);
    }
}
