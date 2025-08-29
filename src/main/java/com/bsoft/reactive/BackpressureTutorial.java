package com.bsoft.reactive;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
public class BackpressureTutorial {

    public static void main(String[] args) {
        BackpressureTutorial tutorial = new BackpressureTutorial();

        log.info("01 No overflow");
        tutorial
                .createNoOverflowFlux()
                .blockLast();

        log.info("02 Overflow");
        try {
            tutorial
                    .createOverflowFlux()
                    .blockLast();
        } catch (Exception ex) {
            // continue
        }

        log.info("03 Drop elements on backpressure");
        tutorial
                .createDropOnBackpressureFlux()
                .blockLast();

        try {
            log.info("04 Buffer elements on backpressure");
            tutorial
                    .createBufferOnBackpressureFlux()
                    .blockLast();
        } catch (Exception ex) {
            // continue
        }

        log.info("05 Buffer elements on backpressure with bufferoverflow strategy");
        tutorial
                .createBufferStrategyOnBackpressureFlux()
                .blockLast();


    }

    private Flux<Long> createNoOverflowFlux() {
        return Flux.range(1, Integer.MAX_VALUE)
                .log()
                .concatMap(x -> Mono.delay(Duration.ofMillis(100)).thenReturn((long) x)) // simulate processing time
                .take(Duration.ofSeconds(5)) // Stop after 5 seconds
                .doOnComplete(() -> log.info("Flux completed after 5 seconds"));
    }

    private Flux<Long> createOverflowFlux() {
        return Flux.interval(Duration.ofMillis(1)) // every 1 ms a value is added to the flux
                .log()
                .concatMap(x -> Mono.delay(Duration.ofMillis(100))); // simulate that processing takes 100ms
    }

    /*
    In order to prevent backpressure drop elements
     */
    private Flux<Long> createDropOnBackpressureFlux() {
        return Flux.interval(Duration.ofMillis(1)) // every 1 ms a value is added to the flux
                .onBackpressureDrop()
                .log()
                .concatMap(x -> Mono.delay(Duration.ofMillis(100)).thenReturn(x)) // simulate that processing takes 100ms
                .take(Duration.ofSeconds(5)) // Stop after 5 seconds
                .doOnNext(x -> System.out.println("Element kept by consumer " + x));
    }

    /*
    In order to prevent backpressure store the elements in a buffer
     */
    private Flux<Long> createBufferOnBackpressureFlux() {
        return Flux.interval(Duration.ofMillis(1)) // every 1 ms a value is added to the flux
                .onBackpressureBuffer(50)  // will not be big enough
                .log()
                .concatMap(x -> Mono.delay(Duration.ofMillis(100)).thenReturn(x)) // simulate that processing takes 100ms
                .doOnNext(x -> System.out.println("Element kept by consumer: " + x));
    }

    /*
   In order to prevent backpressure store the elements in a buffer
    */
    private Flux<Long> createBufferStrategyOnBackpressureFlux() {
        return Flux.interval(Duration.ofMillis(1)) // every 1 ms a value is added to the flux
                .onBackpressureBuffer(50, BufferOverflowStrategy.DROP_LATEST)  // will drop the latest element on bufferoverflow
                .log()
                .concatMap(x -> Mono.delay(Duration.ofMillis(100)).thenReturn(x)) // simulate that processing takes 100ms
                .take(Duration.ofSeconds(5)) // Stop after 5 seconds
                .doOnNext(x -> System.out.println("Element kept by consumer: " + x));
    }

}
