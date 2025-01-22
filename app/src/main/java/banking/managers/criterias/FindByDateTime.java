package banking.managers.criterias;

import lombok.Getter;
import lombok.NonNull;
import banking.exceptions.managers.criterias.DateTimeMissingException;
import banking.exceptions.managers.criterias.DateTimeValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Getter
public final class FindByDateTime extends BaseCriteria {
    @NonNull
    private final String dateTime;

    public FindByDateTime(String dateTime) {
        super();
        this.dateTime = dateTime;
        this.invalidate();
    }

    @Override
    protected void invalidate() {
        if (this.dateTime.isEmpty()) {
            this.setException(
                    new DateTimeMissingException("Request could not be fulfilled due to missing DateTime. (not:fulfilled)")
            );
            this.setInvalid();

            return;
        }

        try {
            LocalDateTime.parse(this.dateTime);
        } catch (DateTimeParseException e) {
            this.setException(
                    new DateTimeValidationException(
                            "Request could not be fulfilled due to invalid DateTime format. (not:fulfilled)"
                    )
            );
            this.setInvalid();
        }
    }
}
