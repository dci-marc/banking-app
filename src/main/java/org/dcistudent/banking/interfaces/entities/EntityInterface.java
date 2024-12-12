package org.dcistudent.banking.interfaces.entities;

import lombok.NonNull;

public interface EntityInterface {
    public String getId();

    @NonNull
    public void setId(String id);

    @Override
    public String toString();
}
