package org.dcistudent.banking.interfaces.managers;

import lombok.NonNull;
import org.dcistudent.banking.interfaces.HydratorInterface;
import org.dcistudent.banking.interfaces.entities.EntityInterface;
import org.dcistudent.banking.managers.criterias.FindByUuid;

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
