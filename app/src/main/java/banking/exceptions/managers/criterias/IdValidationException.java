package banking.exceptions.managers.criterias;

import lombok.NonNull;

public class IdValidationException extends CriteriaNotFulFilledException {
    @NonNull
    public IdValidationException(String message) {
        super(message);
    }
}
