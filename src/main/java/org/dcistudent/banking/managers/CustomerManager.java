package org.dcistudent.banking.managers;

import org.dcistudent.banking.entities.Customer;
import org.dcistudent.banking.hydrators.CustomerHydrator;
import org.dcistudent.banking.interfaces.entities.EntitiyInterface;

import java.util.HashMap;
import java.util.Map;

public final class CustomerManager extends AbstractManager {
    private static final String FILE_PATH = "src/main/resources/customers.json";

    public CustomerManager() {
        super(FILE_PATH);
    }

    public Map<String, Customer> findAll() {
        Map<String, EntitiyInterface> map = super.findAll(new CustomerHydrator());
        Map<String, Customer> customers = new HashMap<>();
        map.forEach((k, v) -> customers.put(k, (Customer) v));

        return customers;
    }

    public Customer findByUsername(String username) {
        return this
                .findAll()
                .values()
                .stream()
                .filter(entity -> entity.getUsername().equals(username))
                .findFirst()
                .orElseThrow()
        ;
    }
}
