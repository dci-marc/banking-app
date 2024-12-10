package org.dcistudent.banking.interfaces.managers;

import org.dcistudent.banking.interfaces.HydratorInterface;
import org.dcistudent.banking.interfaces.entities.EntitiyInterface;

import java.util.Map;

public interface AbstractManagerInterface {
    public Map<String, EntitiyInterface> findAll(HydratorInterface hydrator);
    public EntitiyInterface findById(HydratorInterface hydrator, String id);
    public void persist(HydratorInterface hydrator, Map<String, EntitiyInterface> map);
    public void persist(HydratorInterface hydrator, EntitiyInterface entity);
}
