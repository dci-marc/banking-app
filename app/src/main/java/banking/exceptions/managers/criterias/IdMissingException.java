package banking.exceptions.managers.criterias;

import lombok.NonNull;

public class IdMissingException extends CriteriaNotFulFilledException {
    @NonNull
    public IdMissingException(String message) {
        super(message);
    }
}
