package io.github.sammmm.springtutorial.orderservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping(value = "/api")
public class Resilience4jController {
    private static final String RESILIENCE4J_INSTANCE_NAME = "example";
    private static final String FALLBACK_METHOD = "fallback";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(
            value = "/timeout/{timeout}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    @TimeLimiter(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public Mono<Response<Boolean>> timeout(@PathVariable int timeout) {
        return Mono.just(toOkResponse())
                .delayElement(Duration.ofSeconds(timeout));
    }

    @GetMapping(value = "/timeDelay/{delay}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public Mono<Response<Boolean>> timeDelay(@PathVariable int delay) {
        logger.info("In timeDelay");
        return Mono.just(toOkResponse())
                .delayElement(Duration.ofSeconds(delay));
    }

    @GetMapping(
            value = "/error/{valid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public Mono<Response<Boolean>> error(@PathVariable boolean valid) {
        return Mono.just(valid)
                .flatMap(this::toOkResponse);
    }

    public Mono<Response<Boolean>> fallback(Exception ex) {
        return Mono.just(toResponse(HttpStatus.INTERNAL_SERVER_ERROR, Boolean.FALSE))
                .doOnNext(result -> logger.warn("fallback executed"));
    }

    private Mono<Response<Boolean>> toOkResponse(boolean valid) {
        if (!valid) {
            return Mono.just(toOkResponse());
        }
        return Mono.error(new RuntimeException("error"));
    }

    private Response<Boolean> toOkResponse() {
        return toResponse(HttpStatus.OK, Boolean.TRUE);
    }

    private Response<Boolean> toResponse(HttpStatus httpStatus, Boolean result) {
        Response<Boolean> response = new Response<>(httpStatus.value(), httpStatus.getReasonPhrase(), result);
        return response;
    }
}
