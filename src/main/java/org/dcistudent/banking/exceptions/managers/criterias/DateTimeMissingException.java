package org.dcistudent.banking.exceptions.managers.criterias;

import lombok.NonNull;

public final class DateTimeMissingException extends CriteriaNotFulFilledException {
  @NonNull
  public DateTimeMissingException(String message) {
    super(message);
  }
}