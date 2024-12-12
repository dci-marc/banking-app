package org.dcistudent.banking.interfaces.managers;

import lombok.NonNull;
import org.dcistudent.banking.interfaces.HydratorInterface;
import org.dcistudent.banking.interfaces.entities.EntityInterface;
import org.dcistudent.banking.managers.criterias.FindByUuid;

import java.util.Map;

public interface AbstractManagerInterface {
    @NonNull
    public Map<String, EntityInterface> findAll(HydratorInterface hydrator);

    @NonNull
    public EntityInterface findById(HydratorInterface hydrator, FindByUuid id) throws Exception;

    @NonNull
    public void persist(HydratorInterface hydrator, Map<String, EntityInterface> map);

    @NonNull
    public void persist(HydratorInterface hydrator, EntityInterface entity);
}
