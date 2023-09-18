package io.github.sammmm.springtutorial.customerservice;

import io.github.sammmm.springtutorial.payloads.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("customer/by/{id}")
    public Customer customerById(@PathVariable("id") int id) {
        return repository.byId(id);
    }
}