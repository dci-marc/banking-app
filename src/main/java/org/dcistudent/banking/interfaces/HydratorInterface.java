package org.dcistudent.banking.interfaces;

import org.dcistudent.banking.interfaces.entities.EntityInterface;

import java.util.Map;

public interface HydratorInterface {
    static EntityInterface hydrate(EntityInterface entity) {
        return entity;
    }

    String hydrate(Map<String, EntityInterface> map);
    EntityInterface hydrate(String[] fields);
}
