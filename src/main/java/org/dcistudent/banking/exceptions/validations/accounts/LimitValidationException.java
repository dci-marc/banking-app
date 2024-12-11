package org.dcistudent.banking.exceptions.validations.accounts;

public class LimitValidationException extends RuntimeException {
    public LimitValidationException(String message) {
        super(message);
    }
}
