package com.bsoft.reactive;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
public class ReactiveTutorial {

    private Mono<String> testMono() {  // This is the publisher
        return Mono.just("Java")
                ; // wrap the string in Mono
    }

    private Flux<String> testFlux() {
        return Flux.just("Java", "Cpp", "Python", "Rust");
    }

    private Flux<String> testFlux01() {
        List<String> programmingLanguages = List.of("Dart", "Javascript", "COBOL", "C++");

        return Flux.fromIterable(programmingLanguages);
    }

    public static void main(String[] args) {
        ReactiveTutorial reactiveTutorial = new ReactiveTutorial();

        /*
        reactiveTutorial.testMono(); // will nog work there is no subscription yet. When running the program exits immeadiately
                                     // the compiler gives a warning: Value is never used as Publisher
         */

        /*
        reactiveTutorial.testMono()
                .subscribe();        // Data is generated, but the subscriber is not using the data
        */
        log.info("Demo Mono.just");
        reactiveTutorial.testMono()
                .subscribe(System.out::println);

        log.info("Demo Flux.just");
        reactiveTutorial.testFlux()
                .subscribe(System.out::println);

        log.info("Demo Flux.fromIterable");
        reactiveTutorial.testFlux01()
                .subscribe(System.out::println);

    }
}
