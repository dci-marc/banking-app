package banking.interfaces.managers;

import lombok.NonNull;
import banking.interfaces.HydratorInterface;
import banking.interfaces.entities.EntityInterface;
import banking.managers.criterias.FindByUuid;

import java.util.Map;

public interface AbstractManagerInterface {
    @NonNull
    Map<String, EntityInterface> findAll(HydratorInterface hydrator);

    @NonNull
    EntityInterface findById(HydratorInterface hydrator, FindByUuid id) throws Exception;

    @NonNull
    void persist(HydratorInterface hydrator, Map<String, EntityInterface> map);

    @NonNull
    void persist(HydratorInterface hydrator, EntityInterface entity);
}
