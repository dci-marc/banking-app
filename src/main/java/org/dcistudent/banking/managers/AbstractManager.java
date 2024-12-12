package org.dcistudent.banking.managers;

import lombok.NonNull;
import org.dcistudent.banking.interfaces.HydratorInterface;
import org.dcistudent.banking.interfaces.entities.EntityInterface;
import org.dcistudent.banking.interfaces.managers.AbstractManagerInterface;
import org.dcistudent.banking.managers.criterias.FindByUuid;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class AbstractManager implements AbstractManagerInterface {
    @NonNull
    private final String filePath;

    protected AbstractManager(String filePath) {
        this.filePath = filePath;
    }

    @NonNull
    public Map<String, EntityInterface> findAll(HydratorInterface entityHydrator) {
        Map<String, EntityInterface> map = new HashMap<>();

        this.getReader().lines().forEach(line -> {
            String[] fields = line.split(",");
            map.put(
                    fields[0],
                    entityHydrator.hydrate(fields)
            );
        });

        return map;
    }

    @NonNull
    public EntityInterface findById(HydratorInterface entityHydrator, FindByUuid criteria) throws Exception {
        if (criteria.isInvalid() == true) {
            throw criteria.getException();
        }

        return this
                .findAll(entityHydrator)
                .values()
                .stream()
                .filter(entity -> entity.getId().equals(criteria.getId()))
                .findFirst()
                .orElseThrow()
        ;
    }

    @NonNull
    public void persist(HydratorInterface entityHydrator, Map<String, EntityInterface> map) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath))) {
            writer.write(entityHydrator.hydrate(map));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public void persist(HydratorInterface entityHydrator, EntityInterface entity) {
        Map<String, EntityInterface> map = this.findAll(entityHydrator);

        try {
            this.findById(entityHydrator, new FindByUuid(entity.getId()));

            map.forEach((k, v) -> {
                if (v.getId().equals(entity.getId())) {
                    map.put(k, entity);
                }
            });
        } catch (NoSuchElementException e) {
            map.put(entity.getId(), entity);
            this.persist(entityHydrator, map);
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.persist(entityHydrator, map);
    }

    protected void createFile() {
        try {
            File file = new File(this.filePath);
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    protected BufferedReader getReader() {
        try {
            return new BufferedReader(new FileReader(this.filePath));
        } catch (FileNotFoundException e) {
            this.createFile();
            return this.getReader();
        }
    }
}
