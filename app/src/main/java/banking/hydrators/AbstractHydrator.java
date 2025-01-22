package banking.hydrators;

import lombok.NonNull;
import banking.interfaces.HydratorInterface;
import banking.interfaces.entities.EntityInterface;

import java.util.Map;

public abstract class AbstractHydrator implements HydratorInterface {
    @NonNull
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
