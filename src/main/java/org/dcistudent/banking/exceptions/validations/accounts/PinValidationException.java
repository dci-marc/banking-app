package org.dcistudent.banking.exceptions.validations.accounts;

public class PinValidationException extends RuntimeException {
    public PinValidationException(String message) {
        super(message);
    }
}
