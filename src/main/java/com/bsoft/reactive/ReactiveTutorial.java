package com.bsoft.reactive;

import reactor.core.publisher.Mono;

public class ReactiveTutorial {

    private Mono<String> testMono() {  // This is the publisher
        return Mono.just("Java")
                ; // wrap the string in Mono
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

        reactiveTutorial.testMono()
                .subscribe(data -> System.out.println(data));
    }
}
