package org.dcistudent.banking.exceptions.transfers;

public class BankTransferException extends RuntimeException {
    public BankTransferException(String message) {
        super(message);
    }
}
