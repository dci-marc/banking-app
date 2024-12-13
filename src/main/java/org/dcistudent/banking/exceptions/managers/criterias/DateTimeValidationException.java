package org.dcistudent.banking.exceptions.managers.criterias;

import lombok.NonNull;

public final class DateTimeValidationException extends RuntimeException {
  @NonNull
  public DateTimeValidationException(String message) {
    super(message);
  }
}