package io.github.sammmm.springtutorial.productservice;

import io.github.sammmm.springtutorial.payloads.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepository {
    private List<Product> products = new ArrayList<>();

    public ProductRepository() {
        products.add(new Product(1, "Cabbage", 10));
        products.add(new Product(2, "Papaya", 20));
        products.add(new Product(3, "Pumpkin", 30));
        products.add(new Product(4, "Cucumber", 40));
        products.add(new Product(5, "Tomato", 50));
        products.add(new Product(6, "Bell pepper", 60));
        products.add(new Product(7, "Jalapeño", 70));
        products.add(new Product(8, "Peanut", 80));
        products.add(new Product(9, "Garlic", 90));
        products.add(new Product(10, " Banana", 100));
    }

    public Product byId(int i) {
        return products.get(i);
    }
}
