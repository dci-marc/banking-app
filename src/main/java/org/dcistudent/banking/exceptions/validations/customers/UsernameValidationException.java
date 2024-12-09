package org.dcistudent.banking.exceptions.validations.customers;

public class UsernameValidationException extends RuntimeException {
    public UsernameValidationException(String message) {
        super(message);
    }
}
