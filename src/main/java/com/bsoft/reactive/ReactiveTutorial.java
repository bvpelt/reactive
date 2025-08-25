package com.bsoft.reactive;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuple4;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

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

    private Flux<String> testMap() {

        Flux<String> flux = Flux.just("Dart", "Javascript", "COBOL", "C++");

        // for each element of the flux execute a function
        return flux.map(s -> s.toUpperCase(Locale.ROOT));
    }

    private Flux<String> testFlatMap() {

        Flux<String> flux = Flux.just("Dart", "Javascript", "COBOL", "C++");

        // for each element of the flux execute a function on a publisher
        // instead of a blocking operation (such as a database query) use a publisher
        return flux.flatMap(s -> Mono.just(s.toLowerCase(Locale.ROOT)));
    }

    private Flux<String> testSkip() {

        Flux<String> flux = Flux.just("Dart", "Javascript", "COBOL", "C++");

        log.info("Number of elements in flux: {}", flux.count().block());

        // Skip the first two elements of the Flux
        return flux.skip(2);
    }

    private Flux<String> testSkipDuration() {

        Flux<String> flux = Flux.just("Dart", "Javascript", "COBOL", "C++");

        return flux.delayElements(Duration.ofSeconds(1))
                .log();
    }

    private Flux<String> testSkipDuration01() {

        Flux<String> flux = Flux.just("Dart", "Javascript", "COBOL", "C++")
                .delayElements(Duration.ofSeconds(1));

        return flux.skip(Duration.ofMillis(2010));

    }

    private Flux<String> testSkipLast() {

        Flux<String> flux = Flux.just("Dart", "Javascript", "COBOL", "C++");

        // Skip the last two elements of the Flux
        return flux.skipLast(2);
    }

    private Flux<Integer> testSkipRange() {

        Flux<Integer> flux = Flux.range(1,20);

        // return range
        return flux;
    }

    private Flux<Integer> testSkipWhile() {

        Flux<Integer> flux = Flux.range(1,20);

        // Skip all elements which apply to the condition
        return flux.skipWhile(i -> i < 10);
    }

    private Flux<Integer> testSkipUntil() {

        Flux<Integer> flux = Flux.range(1,20);

        // Skip all elements which apply to the condition
        return flux.skipUntil(i -> i == 10);
    }

    private Flux<Integer> testConcat() {

        Flux<Integer> flux1 = Flux.range(1,20);
        Flux<Integer> flux2 = Flux.range(101,20);

        // Concat the fluxes
        return Flux.concat(flux1, flux2);
    }

    private Flux<Integer> testMerge() {

        Flux<Integer> flux1 = Flux.range(1,20);
        Flux<Integer> flux2 = Flux.range(101,20);

        // Merge the fluxes
        return Flux.merge(flux1, flux2);
    }

    private Flux<Integer> testMergeDelay() {

        Flux<Integer> flux1 = Flux.range(1,20).delayElements(Duration.ofMillis(20));
        Flux<Integer> flux2 = Flux.range(101,20).delayElements(Duration.ofMillis(20));

        // Merge the fluxes
        return Flux.merge(flux1, flux2);
    }

    private Flux<Tuple2<Integer, Integer>> testZip() {

        Flux<Integer> flux1 = Flux.range(1,20);
        Flux<Integer> flux2 = Flux.range(101,20);

        // Zip the fluxes, both fluxes need to have the same number of elements.
        // The output will stop when one of the fluxes has no more data to zip.
        return Flux.zip(flux1, flux2);
    }

    private Flux<Tuple4<Integer, Integer, Integer, Integer>> testZipFour() {

        Flux<Integer> flux1 = Flux.range(1,20);
        Flux<Integer> flux2 = Flux.range(101,22);
        Flux<Integer> flux3 = Flux.range(501,18);
        Flux<Integer> flux4 = Flux.range(1001,15);

        // Zip the fluxes, both fluxes need to have the same number of elements.
        // The output will stop when one of the fluxes has no more data to zip.
        return Flux.zip(flux1, flux2, flux3, flux4);
    }

    private Flux<Tuple3<Integer, Integer, Integer>> testComplexZip() {

        Flux<Integer> flux1 = Flux.range(1,20);
        Flux<Integer> flux2 = Flux.range(101,22);
        Mono<Integer> mono = Mono.just(5);


        // Zip the fluxes, both fluxes need to have the same number of elements.
        // The output will stop when one of the fluxes has no more data to zip.
        return Flux.zip(flux1, flux2, mono);
    }





    public static void main(String[] args) throws InterruptedException {
        ReactiveTutorial reactiveTutorial = new ReactiveTutorial();

        /*
        reactiveTutorial.testMono(); // will nog work there is no subscription yet. When running the program exits immeadiately
                                     // the compiler gives a warning: Value is never used as Publisher
         */

        /*
        reactiveTutorial.testMono()
                .subscribe();        // Data is generated, but the subscriber is not using the data
        */

        /*
        log.info("Demo Mono.just");
        reactiveTutorial.testMono()
                .subscribe(System.out::println);

        log.info("Demo Flux.just");
        reactiveTutorial.testFlux()
                .subscribe(System.out::println);

        log.info("Demo Flux.fromIterable");
        reactiveTutorial.testFlux01()
                .subscribe(System.out::println);

        log.info("Demo Flux.map -- each element to uppercase");
        reactiveTutorial.testMap()
                .subscribe(System.out::println);

        log.info("Demo Flux.flatMap -- each (provider) element to lowercase");
        reactiveTutorial.testFlatMap()
                .subscribe(System.out::println);

        log.info("Demo Flux.skip -- skip number of elements from provider");
        reactiveTutorial.testSkip()
                .subscribe(System.out::println);

        log.info("Demo Flux.skip -- skip with delay");
        reactiveTutorial.testSkipDuration()
                .subscribe(System.out::println);

        Thread.sleep(10000);

        log.info("Demo Flux.skip -- skip with delay on skip");
        reactiveTutorial.testSkipDuration01()
                .subscribe(System.out::println);

        Thread.sleep(10000);

        log.info("Demo Flux.skipLast");
        reactiveTutorial.testSkipLast()
                .subscribe(System.out::println);

        log.info("Demo Flux.range");
        reactiveTutorial.testSkipRange()
                .subscribe(System.out::println);

        log.info("Demo Flux.skipWhile");
        reactiveTutorial.testSkipWhile()
                .subscribe(System.out::println);

        log.info("Demo Flux.skipUntil");
        reactiveTutorial.testSkipUntil()
                .subscribe(System.out::println);

        log.info("Demo Flux.concat");
        reactiveTutorial.testConcat()
                .subscribe(System.out::println);

         */

        log.info("Demo Flux.merge");
        reactiveTutorial.testMerge()
                .subscribe(System.out::println);

        log.info("Demo Flux.merge with delay");
        reactiveTutorial.testMergeDelay()
                .subscribe(System.out::println);
        Thread.sleep(3000);

        log.info("Demo Flux.zip");
        reactiveTutorial.testZip()
                .subscribe(System.out::println);

        log.info("Demo Flux.zip 4 streams");
        reactiveTutorial.testZipFour()
                .subscribe(System.out::println);

        log.info("Demo Flux.zip 2 flux 1 mono");
        reactiveTutorial.testComplexZip()
                .subscribe(System.out::println);
    }
}
