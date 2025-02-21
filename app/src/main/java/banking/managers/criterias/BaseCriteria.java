package banking.managers.criterias;

import lombok.NonNull;
import banking.exceptions.managers.criterias.CriteriaExceptionNotSet;
import banking.interfaces.managers.criterias.BaseCriteriaInterface;

public abstract class BaseCriteria implements BaseCriteriaInterface {
    private Boolean invalid = false;
    private Exception exception;

    protected BaseCriteria() {
        this.exception = new CriteriaExceptionNotSet();
    }

    protected abstract void invalidate();

    protected final void setInvalid() {
        this.invalid = true;
    }

    public final Boolean isInvalid() {
        return this.invalid;
    }

    @NonNull
    protected final void setException(Exception exception) {
        this.exception = exception;
    }

    public final Exception getException() {
        return this.exception;
    }

    public final Boolean needsThrow() {
        return this.exception instanceof CriteriaExceptionNotSet == false;
    }
}
