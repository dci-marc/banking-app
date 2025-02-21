package banking.hydrators;

import lombok.NonNull;
import banking.entities.Customer;
import banking.interfaces.models.CustomerInterface;

import java.security.NoSuchAlgorithmException;

public final class CustomerHydrator extends AbstractHydrator {
    @NonNull
    public Customer hydrate(String[] fields) {
        Customer customer = new Customer();
        customer.setId(String.valueOf(fields[0]));
        customer.setUsername(String.valueOf(fields[1]));
        customer.setPassword(String.valueOf(fields[2]));
        customer.setFirstName(String.valueOf(fields[3]));
        customer.setActive(Boolean.valueOf(fields[4]));

        return customer;
    }

    @NonNull
    public static CustomerInterface hydrate(Customer customerEntity) {
        CustomerInterface customer = new banking.models.Customer();
        customer.setId(customerEntity.getId());
        customer.setUsername(customerEntity.getUsername());
        try {
            customer.setPassword(customerEntity.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        customer.setFirstName(customerEntity.getFirstName());
        customer.setActive(customerEntity.getActive());

        return customer;
    }

    @NonNull
    public static Customer hydrate(CustomerInterface customer) {
        Customer entity = new Customer();
        entity.setId(customer.getId());
        entity.setUsername(customer.getUsername());
        entity.setPassword(customer.getPassword());
        entity.setFirstName(customer.getFirstName());
        entity.setActive(customer.getActive());

        return entity;
    }
}
