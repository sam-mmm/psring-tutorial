package io.github.sammmm.springtutorial.customerservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("say/hello")
    public String sayHello() {
        return "Hello Customer Service";
    }
}
