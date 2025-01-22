package banking.exceptions.validations.customers;

import lombok.NonNull;

public class PasswordValidationException extends RuntimeException {
    @NonNull
    public PasswordValidationException(String message) {
        super(message);
    }
}
