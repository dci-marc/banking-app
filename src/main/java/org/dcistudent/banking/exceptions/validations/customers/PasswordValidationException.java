package org.dcistudent.banking.exceptions.validations.customers;

public class PasswordValidationException extends RuntimeException {
    public PasswordValidationException(String message) {
        super(message);
    }
}
