package org.dcistudent.banking.managers.criterias;

import lombok.Getter;
import lombok.NonNull;
import org.dcistudent.banking.exceptions.managers.criterias.IdMissingException;

import java.util.List;

@Getter
class FindByIds extends BaseCriteria {
    private final List<Integer> ids;

    @NonNull
    public FindByIds(List<Integer> ids) {
        super();
        this.ids = ids;
        this.invalidate();
    }

    protected void invalidate() {
        List<Integer> exceptions = this.ids
                .stream()
                .filter(id -> new FindById(id).needsThrow() == true)
                .toList()
        ;

        if (exceptions.isEmpty() == false) {
            this.setException(
                    new IdMissingException("Request could not be fulfilled due to missing ID. (not:fulfilled)")
            );
        }
    }

    public List<Integer> getIds() {
        return this.ids;
    }
}