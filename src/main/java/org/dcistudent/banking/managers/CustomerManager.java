package org.dcistudent.banking.managers;

import lombok.NonNull;
import org.dcistudent.banking.entities.Customer;
import org.dcistudent.banking.hydrators.CustomerHydrator;
import org.dcistudent.banking.interfaces.entities.EntityInterface;

import java.util.HashMap;
import java.util.Map;

public final class CustomerManager extends AbstractManager {
    private static final String FILE_PATH = "src/main/resources/customers.csv";

    public CustomerManager() {
        super(FILE_PATH);
    }

    @NonNull
    public Map<String, Customer> findAll() {
        Map<String, EntityInterface> map = super.findAll(new CustomerHydrator());
        Map<String, Customer> customers = new HashMap<>();
        map.forEach((k, v) -> customers.put(k, (Customer) v));

        return customers;
    }

    @NonNull
    public Customer findByUsername(String username) {
        return this
                .findAll()
                .values()
                .stream()
                .filter(entity -> entity.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow()
        ;
    }
}
