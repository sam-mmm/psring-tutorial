package io.github.sammmm.springtutorial.orderservice;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Configuration
@EnableFeignClients
public class OrderServiceApplication {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RegistryEventConsumer<CircuitBreaker> circuitBreakerEventConsumer() {
        logger.info("@@@@@@@@@@@@@@@@");
        return new RegistryEventConsumer<CircuitBreaker>() {

            @Override
            public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
                entryAddedEvent.getAddedEntry().getEventPublisher()
                        .onFailureRateExceeded(event -> logger.error("circuit breaker {} failure rate {} on {}",
                                event.getCircuitBreakerName(), event.getFailureRate(), event.getCreationTime())
                        )
                        .onSlowCallRateExceeded(event -> logger.error("circuit breaker {} slow call rate {} on {}",
                                event.getCircuitBreakerName(), event.getSlowCallRate(), event.getCreationTime())
                        )
                        .onCallNotPermitted(event -> logger.error("circuit breaker {} call not permitted {}",
                                event.getCircuitBreakerName(), event.getCreationTime())
                        )
                        .onError(event -> logger.error("circuit breaker {} error with duration {}s",
                                event.getCircuitBreakerName(), event.getElapsedDuration().getSeconds())
                        )
                        .onStateTransition(
                                event -> logger.warn("circuit breaker {} state transition from {} to {} on {}",
                                        event.getCircuitBreakerName(), event.getStateTransition().getFromState(),
                                        event.getStateTransition().getToState(), event.getCreationTime())
                        );
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {
                entryRemoveEvent.getRemovedEntry().getEventPublisher()
                        .onFailureRateExceeded(event -> logger.info("Circuit breaker event removed {}",
                                event.getCircuitBreakerName()));
            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {
                entryReplacedEvent.getNewEntry().getEventPublisher()
                        .onFailureRateExceeded(event -> logger.info("Circuit breaker event replaced {}",
                                event.getCircuitBreakerName()));
            }
        };
    }

    @Bean
    public RegistryEventConsumer<TimeLimiter> timeLimiterEventConsumer() {
        logger.info("######################");
        return new RegistryEventConsumer<TimeLimiter>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<TimeLimiter> entryAddedEvent) {
                entryAddedEvent.getAddedEntry().getEventPublisher()
                        .onTimeout(event -> logger.error("Foo time limiter {} timeout {} on {}",
                                event.getTimeLimiterName(), event.getEventType(), event.getCreationTime())
                        );
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<TimeLimiter> entryRemoveEvent) {
                entryRemoveEvent.getRemovedEntry().getEventPublisher()
                        .onTimeout(event -> logger.error("time limiter removed {}",
                                event.getTimeLimiterName())
                        );
            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<TimeLimiter> entryReplacedEvent) {
                entryReplacedEvent.getNewEntry().getEventPublisher()
                        .onTimeout(event -> logger.error("time limiter replaced {} ",
                                event.getTimeLimiterName())
                        );
            }
        };
    }

    @Bean
    public RegistryEventConsumer<RateLimiter> rateLimiterEventConsumer() {
        return new RegistryEventConsumer<RateLimiter>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<RateLimiter> entryAddedEvent) {
                entryAddedEvent.getAddedEntry().getEventPublisher()
                        .onEvent(event -> logger.error("Bar time limiter {} timeout {} on {}",
                                event.getRateLimiterName(), event.getEventType(), event.getCreationTime())
                        );
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<RateLimiter> entryRemoveEvent) {
                entryRemoveEvent.getRemovedEntry().getEventPublisher()
                        .onEvent(event -> logger.error("time limiter removed {}",
                                event.getRateLimiterName())
                        );
            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<RateLimiter> entryReplacedEvent) {
                entryReplacedEvent.getNewEntry().getEventPublisher()
                        .onEvent(event -> logger.error("time limiter replaced {} ",
                                event.getNumberOfPermits())
                        );
            }
        };
    }
}
