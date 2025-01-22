package banking.managers.criterias;

import lombok.Getter;
import lombok.NonNull;
import banking.exceptions.managers.criterias.IdMissingException;

@Getter
public class FindById extends BaseCriteria{
    private Integer id = 0;

    @NonNull
    public FindById(Integer id) {
        super();
        this.id = id;
        this.invalidate();
    }

    protected void invalidate() {
        if (this.id == 0) {
            this.setException(
                    new IdMissingException("Request could not be fulfilled due to missing ID. (not:fulfilled)")
            );

            this.setInvalid();
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.getId());
    }
}
