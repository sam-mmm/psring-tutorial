package io.github.sammmm.springtutorial.orderservice;

import io.github.sammmm.springtutorial.payloads.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("http://CUSTOMER-SERVICE-APPLICATION")
public interface CustomerClient {
    @RequestMapping(method = RequestMethod.GET, value = "/customer/by/{customerId}")
    Customer byId(@PathVariable("customerId") int id);
}
