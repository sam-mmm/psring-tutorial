package io.github.sammmm.springtutorial.orderservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/resilience")
public class Resilience4jController {
    private final Resilience4jService demoService;

    public Resilience4jController(Resilience4jService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/rate-limiter")
    public String rateLimiter() {
        return demoService.rateLimiter();
    }

    @GetMapping("/retry")
    public String retry() {
        return demoService.retry();
    }
}