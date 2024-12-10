package org.dcistudent.banking.managers;

import lombok.NonNull;
import org.dcistudent.banking.interfaces.HydratorInterface;
import org.dcistudent.banking.interfaces.entities.EntitiyInterface;
import org.dcistudent.banking.interfaces.managers.AbstractManagerInterface;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class AbstractManager implements AbstractManagerInterface {
    private final String filePath;

    protected AbstractManager(@NonNull String filePath) {
        this.filePath = filePath;
    }

    public Map<String, EntitiyInterface> findAll(HydratorInterface entityHydrator) {
        Map<String, EntitiyInterface> map = new HashMap<>();

        this.getReader().lines().forEach(line -> {
            String[] fields = line.split(",");
            map.put(
                    fields[0],
                    entityHydrator.hydrate(fields)
            );
        });

        return map;
    }

    public EntitiyInterface findById(HydratorInterface entityHydrator, String id) {
        return this
            .findAll(entityHydrator)
            .values()
            .stream()
            .filter(entity -> entity.getId().equals(id))
            .findFirst()
            .orElseThrow();
    }

    public void persist(HydratorInterface entityHydrator, Map<String, EntitiyInterface> map) {
        BufferedWriter writer;

        try(FileWriter writerFile = new FileWriter(this.filePath)) {
            writer = new BufferedWriter(writerFile);
            writer.write(entityHydrator.hydrate(map));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void persist(HydratorInterface entityHydrator, EntitiyInterface entity) {
        Map<String, EntitiyInterface> map = this.findAll(entityHydrator);

        try {
            this.findById(entityHydrator, entity.getId());

            map.forEach((k, v) -> {
                if (v.getId().equals(entity.getId())) {
                    map.put(k, entity);
                }
            });
        } catch(NoSuchElementException e) {
            map.put(entity.getId(), entity);
            this.persist(entityHydrator, map);
            return;
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

    protected BufferedReader getReader() {
        try {
            return new BufferedReader(new FileReader(this.filePath));
        } catch (FileNotFoundException e) {
            this.createFile();
            return this.getReader();
        }
    }
}
