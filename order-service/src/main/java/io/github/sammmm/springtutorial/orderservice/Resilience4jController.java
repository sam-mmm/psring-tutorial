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

    @GetMapping("/circuit-breaker")
    public String circuitBreaker() {
        return demoService.circuitBreaker();
    }

    @GetMapping("/bulkhead")
    public String bulkhead() {
        return demoService.bulkHead();
    }

    @GetMapping("/time-limiter")
    public CompletableFuture<String> timeLimiter() {
        return demoService.timeLimiter();
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
//io.github.sammmm.springtutorial:payloads:jar:0.0.1
//io/github/sammmm/springtutorial/payloads/0.0.1/payloads-0.0.1.jarcd 