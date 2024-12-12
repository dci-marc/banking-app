package org.dcistudent.banking.hydrators;

import lombok.NonNull;
import org.dcistudent.banking.entities.Customer;
import org.dcistudent.banking.interfaces.models.CustomerInterface;

import java.security.NoSuchAlgorithmException;

public final class CustomerHydrator extends AbstractHydrator {
    @NonNull
    public Customer hydrate(String[] fields) {
        Customer customer = new Customer();
        customer.setId(String.valueOf(fields[0]));
        customer.setUsername(String.valueOf(fields[1]));
        customer.setPassword(String.valueOf(fields[2]));
        customer.setFirstName(String.valueOf(fields[3]));

        return customer;
    }

    @NonNull
    public static CustomerInterface hydrate(Customer customerEntity) {
        CustomerInterface customer = new org.dcistudent.banking.models.Customer();
        customer.setId(customerEntity.getId());
        customer.setUsername(customerEntity.getUsername());
        try {
            customer.setPassword(customerEntity.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        customer.setFirstName(customerEntity.getFirstName());

        return customer;
    }

    @NonNull
    public static Customer hydrate(CustomerInterface customer) {
        Customer entity = new Customer();
        entity.setId(customer.getId());
        entity.setUsername(customer.getUsername());
        entity.setPassword(customer.getPassword());
        entity.setFirstName(customer.getFirstName());

        return entity;
    }
}
