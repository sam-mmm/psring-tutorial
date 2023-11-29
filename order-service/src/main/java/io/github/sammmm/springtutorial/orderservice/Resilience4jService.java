package io.github.sammmm.springtutorial.orderservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class Resilience4jService {

    @RateLimiter(name = "example")
    public String rateLimiter() {
        return "Executing Rate Limited Method";
    }

    @TimeLimiter(name = "example")
    public CompletableFuture<String> timeLimiter() {
        return CompletableFuture.supplyAsync(this::timeLimiterRemoteCall);
    }

    private String timeLimiterRemoteCall() {
        //Will fail 50% of the time
        double random = Math.random();
        if (random < 0.5) {
            return "Executing Time Limited Call...";
        } else {
            try {
                System.out.println("Delaying Execution");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Exception Will be Raised";
    }

    @Retry(name = "example")
    public String retry() {
        return retryRemoteCall();
    }

    private String retryRemoteCall() {
        //will fail 80% of the time
        double random = Math.random();
        if (random <= 0.8) {
            throw new RuntimeException("Retry Remote Call Fails");
        }

        return "Executing Retry Remote Call";
    }

    @Bulkhead(name = "example", type = Bulkhead.Type.THREADPOOL)
    public String bulkHead() {
        return "Executing Bulk Head Remote call";
    }
}