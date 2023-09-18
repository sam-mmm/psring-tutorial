package io.github.sammmm.springtutorial.productservice;

import io.github.sammmm.springtutorial.payloads.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("product/by/{id}")
    public Product productById(@PathVariable("id") int id) {
        return repository.byId(id);
    }
}
