package org.dcistudent.banking.exceptions.managers.criterias;

import lombok.NonNull;

public class CriteriaNotFulFilledException extends RuntimeException {
    @NonNull
    public CriteriaNotFulFilledException(String message) {
        super(message);
    }
}
