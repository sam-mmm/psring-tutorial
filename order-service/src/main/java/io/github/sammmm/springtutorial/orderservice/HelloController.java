package io.github.sammmm.springtutorial.orderservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("say/hello")
    public String sayHello() {
        return "Hello Order Service";
    }
}
