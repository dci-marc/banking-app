package banking.exceptions.managers.criterias;

public class CriteriaExceptionNotSet extends RuntimeException {
    public CriteriaExceptionNotSet() {
        super("Criteria was invalidated, but no concrete exception is set.");
    }
}
