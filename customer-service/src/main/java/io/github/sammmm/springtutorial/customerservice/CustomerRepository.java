package io.github.sammmm.springtutorial.customerservice;

import io.github.sammmm.springtutorial.payloads.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerRepository {
    private List<Customer> customers = new ArrayList<>();

    public CustomerRepository() {
        customers.add(new Customer(1, "John"));
        customers.add(new Customer(2, "Phil"));
        customers.add(new Customer(3, "Mendel"));
        customers.add(new Customer(4, "DaVinci"));
        customers.add(new Customer(5, "Johnson"));
        customers.add(new Customer(6, "Sean"));
        customers.add(new Customer(7, "Anne"));
        customers.add(new Customer(8, "Mary"));
        customers.add(new Customer(9, "Annet"));
        customers.add(new Customer(10, "Bella"));
    }

    public Customer byId(int id) {
        return customers.get(id);
    }
}
