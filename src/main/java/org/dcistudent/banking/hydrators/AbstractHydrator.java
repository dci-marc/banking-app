package org.dcistudent.banking.hydrators;

import org.dcistudent.banking.interfaces.HydratorInterface;
import org.dcistudent.banking.interfaces.entities.EntityInterface;

import java.util.Map;

public abstract class AbstractHydrator implements HydratorInterface {
    public String hydrate(Map<String, EntityInterface> map) {
        return map
                .values()
                .stream()
                .map(EntityInterface::toString)
                .reduce((a, b) -> a + "\n" + b)
                .orElseThrow()
        ;
    }
}
