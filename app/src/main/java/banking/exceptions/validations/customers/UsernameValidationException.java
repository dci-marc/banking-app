package banking.exceptions.validations.customers;

import lombok.NonNull;

public class UsernameValidationException extends RuntimeException {
    @NonNull
    public UsernameValidationException(String message) {
        super(message);
    }
}
