package org.dcistudent.banking.interfaces.managers;

import org.dcistudent.banking.interfaces.HydratorInterface;
import org.dcistudent.banking.interfaces.entities.EntityInterface;

import java.util.Map;

public interface AbstractManagerInterface {
    public Map<String, EntityInterface> findAll(HydratorInterface hydrator);
    public EntityInterface findById(HydratorInterface hydrator, String id);
    public void persist(HydratorInterface hydrator, Map<String, EntityInterface> map);
    public void persist(HydratorInterface hydrator, EntityInterface entity);
}
