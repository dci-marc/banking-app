package org.dcistudent.banking.interfaces.entities;

import lombok.NonNull;

public interface EntityInterface {
    String getId();

    @NonNull
    void setId(String id);

    @Override
    String toString();
}
