package org.dcistudent.banking.exceptions.validations;

public class PinValidationException extends RuntimeException {
    public PinValidationException(String message) {
        super(message);
    }
}
