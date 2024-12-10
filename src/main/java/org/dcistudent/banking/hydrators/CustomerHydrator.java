package org.dcistudent.banking.hydrators;

import org.dcistudent.banking.entities.Customer;
import org.dcistudent.banking.interfaces.models.CustomerInterface;

public final class CustomerHydrator extends AbstractHydrator {
    public Customer hydrate(String[] fields) {
        Customer customer = new Customer();
        customer.setId(String.valueOf(fields[0]));
        customer.setUsername(String.valueOf(fields[1]));
        customer.setPassword(String.valueOf(fields[2]));
        customer.setFirstName(String.valueOf(fields[3]));

        return customer;
    }

    public static CustomerInterface hydrate(Customer customerEntity) {
        CustomerInterface customer = new org.dcistudent.banking.models.Customer();
        customer.setId(customerEntity.getId());
        customer.setUsername(customerEntity.getUsername());
        try {
            customer.setPassword(customerEntity.getPassword());
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        customer.setFirstName(customerEntity.getFirstName());

        return customer;
    }
}
