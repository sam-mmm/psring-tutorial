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
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    public OrderController(RestTemplate restTemplate, CustomerClient customerClient, ProductClient productClient) {
        this.restTemplate = restTemplate;
        this.customerClient = customerClient;
        this.productClient = productClient;
    }

    @GetMapping("make/an/order/with/at/load/balance/{customerId}/{productId}/{qty}")
    public Order makeAnOrderWithAtLoadBalance(@PathVariable("customerId") int customerId, @PathVariable("productId") int productId, @PathVariable("qty") int quantity) {
        Customer customer = null;
        try {
            customer = restTemplate.getForEntity("http://CUSTOMER-SERVICE-APPLICATION/customer/by/{id}", Customer.class, customerId).getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("SUCCESS::" + customer);
        Product product = restTemplate.getForEntity("http://PRODUCT-SERVICE-APPLICATION/product/by/{id}", Product.class, productId).getBody();
        return new Order(1, customer, product, quantity, quantity * product.price());
    }

    @GetMapping("make/an/order/with/feign/client/{customerId}/{productId}/{qty}")
    public Order makeAnOrderWithAtFeignClient(@PathVariable("customerId") int customerId, @PathVariable("productId") int productId, @PathVariable("qty") int quantity) {
        Customer customer = customerClient.byId(customerId);
        Product product = productClient.byId(productId);
        return new Order(1, customer, product, quantity, quantity * product.price());
    }

}
