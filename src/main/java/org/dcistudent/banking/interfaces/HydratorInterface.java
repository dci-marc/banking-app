package org.dcistudent.banking.interfaces;

import org.dcistudent.banking.interfaces.entities.EntitiyInterface;

import java.util.Map;

public interface HydratorInterface {
    static EntitiyInterface hydrate(EntitiyInterface entity) {
        return entity;
    }

    String hydrate(Map<String, EntitiyInterface> map);
    EntitiyInterface hydrate(String[] fields);
}
