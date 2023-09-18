package io.github.sammmm.springtutorial.orderservice;

import io.github.sammmm.springtutorial.payloads.Customer;
import io.github.sammmm.springtutorial.payloads.Order;
import io.github.sammmm.springtutorial.payloads.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

    private final RestTemplate restTemplate;

    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("make/an/order/{customerId}/{productId}/{qty}")
    public Order calcMultiply(@PathVariable("customerId") int customerId, @PathVariable("productId") int productId, @PathVariable("qty") int quantity) {
        Customer customer = restTemplate.getForEntity("http://CUSTOMER-SERVICE-APPLICATION/customer/by/{id}", Customer.class, customerId).getBody();
        Product product = restTemplate.getForEntity("http://PRODUCT-SERVICE-APPLICATION/product/by/{id}", Product.class, productId).getBody();
        return new Order(1, customer, product, quantity, quantity * product.price());
    }
}
