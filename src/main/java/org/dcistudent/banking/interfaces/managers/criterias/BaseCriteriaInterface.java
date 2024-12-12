package org.dcistudent.banking.interfaces.managers.criterias;

public interface BaseCriteriaInterface {
    Boolean isInvalid();
    Exception getException();
    Boolean needsThrow();
}
