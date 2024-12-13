package org.dcistudent.banking.managers.criterias;

import lombok.Getter;
import lombok.NonNull;
import org.dcistudent.banking.exceptions.managers.criterias.IdMissingException;
import org.dcistudent.banking.exceptions.managers.criterias.IdValidationException;

import java.util.UUID;

@Getter
public final class FindByUuid extends BaseCriteria {
    private String id = "";

    @NonNull
    public FindByUuid(String id) {
        super();
        this.id = id;
        this.invalidate();
    }

    protected void invalidate() {
        if (this.id.isEmpty()) {
            this.setException(
                    new IdMissingException("Request could not be fulfilled due to missing ID. (not:fulfilled)")
            );
            this.setInvalid();

            return;
        }

        try {
            UUID.fromString(this.id);
        } catch (IllegalArgumentException e) {
            this.setException(
                    new IdValidationException(
                            "Request could not be fulfilled due to invalid ID format. (not:fulfilled)"
                    )
            );
            this.setInvalid();
        }
    }
}
