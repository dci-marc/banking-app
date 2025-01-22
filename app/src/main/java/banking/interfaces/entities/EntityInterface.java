package banking.interfaces.entities;

import lombok.NonNull;

public interface EntityInterface {
    String getId();

    @NonNull
    void setId(String id);

    Boolean getActive();
    @NonNull
    void setActive(Boolean active);

    @Override
    String toString();
}
