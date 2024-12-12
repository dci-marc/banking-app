package org.dcistudent.banking.interfaces.managers.criterias;

public interface BaseCriteriaInterface {
    public Boolean isInvalid();
    public Exception getException();
    public Boolean needsThrow();
}
