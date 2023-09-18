package io.github.sammmm.springtutorial.orderservice;

import io.github.sammmm.springtutorial.payloads.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("http://PRODUCT-SERVICE-APPLICATION")
public interface ProductClient {
    @RequestMapping(method = RequestMethod.GET, value = "/product/by/{productId}")
    Product byId(@PathVariable("productId") int id);
}
