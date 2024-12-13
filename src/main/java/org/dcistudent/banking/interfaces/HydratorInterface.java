package org.dcistudent.banking.interfaces;

import lombok.NonNull;
import org.dcistudent.banking.interfaces.entities.EntityInterface;

import java.util.Map;

public interface HydratorInterface {
    @NonNull
    static EntityInterface hydrate(EntityInterface entity) {
        return entity;
    }

    @NonNull
    String hydrate(Map<String, EntityInterface> map);

    @NonNull
    EntityInterface hydrate(String[] fields);
}
