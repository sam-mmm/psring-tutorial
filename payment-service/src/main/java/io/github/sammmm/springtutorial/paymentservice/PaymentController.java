package io.github.sammmm.springtutorial.paymentservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @GetMapping("calc/multiply/{a}/{b}")
    public double calcMultiply(@PathVariable("a") double a, @PathVariable("b") double b) {
        return a * b;
    }
}
