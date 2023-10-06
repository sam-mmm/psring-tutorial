package io.github.sammmm.springtutorial.paymentservice;

import io.github.sammmm.springtutorial.payloads.GenericResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @GetMapping("make/payment/{customerId}/{amount}")
    public GenericResponse makePayment(@PathVariable("customerId") int customerId, @PathVariable("amount") double amount) {
        GenericResponse response = null;
        if (amount > 1000) {
            response = new GenericResponse("Failed: Insufficient balance, amount is larger than 1000.");
        } else {
            response = new GenericResponse("Success.");
        }
        return response;
    }
}
