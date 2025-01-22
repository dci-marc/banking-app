package banking.exceptions.validations.accounts;

import lombok.NonNull;

public class PinValidationException extends RuntimeException {
    @NonNull
    public PinValidationException(String message) {
        super(message);
    }
}
