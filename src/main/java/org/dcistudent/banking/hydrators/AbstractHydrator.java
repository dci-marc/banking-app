package org.dcistudent.banking.hydrators;

import org.dcistudent.banking.interfaces.HydratorInterface;
import org.dcistudent.banking.interfaces.entities.EntitiyInterface;

import java.util.Map;

public abstract class AbstractHydrator implements HydratorInterface {
    public String hydrate(Map<String, EntitiyInterface> map) {
        return map
                .values()
                .stream()
                .map(EntitiyInterface::toString)
                .reduce((a, b) -> a + "\n" + b)
                .orElseThrow()
        ;
    }
}
