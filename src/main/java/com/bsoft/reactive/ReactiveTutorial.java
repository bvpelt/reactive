package com.bsoft.reactive;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuple4;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    private Mono<List<Integer>> testCollectList() {

        Flux<Integer> flux = Flux.range(1,20);

        // From a number of elements create 1 list which contains all the elements
        return flux.collectList();
    }

    private List<Integer> testCollectList1() {

        // From a number of elements create 1 list which contains all the elements
        return testCollectList().block();
    }


    private List<Integer> testCollectList2() {
        Flux<Integer> flux = Flux.range(1,20)
                .delayElements(Duration.ofMillis(250));

        // From a number of elements create 1 list which contains all the elements
        return flux.collectList().block();
    }

    private Flux<List<Integer>> testBuffer() {
        Flux<Integer> flux = Flux.range(1,10)
                .delayElements(Duration.ofMillis(250));

        // From a number of elements create a number of lists in a buffer
        return flux.buffer();
    }

    private Flux<List<Integer>> testBuffer1(int maxelements) {
        Flux<Integer> flux = Flux.range(1,10)
                .delayElements(Duration.ofMillis(250));

        // From a number of elements create a number of lists in a buffer with maximum 3 elements
        return flux.buffer(maxelements);
    }


    private Flux<List<Integer>> testBuffer2(int delay) {
        Flux<Integer> flux = Flux.range(1,10)
                .delayElements(Duration.ofMillis(250));

        // From a number of elements create a number of lists in a buffer after delay of 500ms
        return flux.buffer(Duration.ofMillis(delay));
    }

    private Mono<Map<Integer,Integer>> testCollectMap() {
        Flux<Integer> flux = Flux.range(1,10);

        // From a number of elements create a single map with <a, a*a> for each element
        return flux.collectMap(integer -> integer, integer -> integer*integer);
    }

    private Flux<Integer> testDoOnEach() {
        Flux<Integer> flux = Flux.range(1,10);

        // From a number of elements in a Flux for each element in the flux show the signal
        return flux.doOnEach(signal -> System.out.println("Signal: " + signal));
    }

    private Flux<Integer> testDoOnEach1() {
        Flux<Integer> flux = Flux.range(1,10);

        // From a number of elements in a Flux for each element in the flux show the signal
        return flux.doOnEach(signal -> {
            if (signal.getType() == SignalType.ON_COMPLETE) {
                System.out.println("Ready");
            } else {
                System.out.println("Signal value: " + signal.get());
            }
        });
    }

    private Flux<Integer> testDoOnComplete() {
        Flux<Integer> flux = Flux.range(1,10);

        // From a number of elements in a Flux for the complete signal
        return flux.doOnComplete(() -> System.out.println("Ready"));
    }

    private Flux<Integer> testDoOnNext() {
        Flux<Integer> flux = Flux.range(1,10);

        // From a number of elements in a Flux for each value -- instead of each signal as in dooneach -- process the value
        return flux.doOnNext(integer -> System.out.println("Value: " + integer));
    }

    private Flux<Integer> testDoOnSubscribe() {
        Flux<Integer> flux = Flux.range(1,10);

        // From a number of elements in a Flux on the subscribe event do specified action
        return flux.doOnSubscribe(subscription -> System.out.println("Subscribed"));
    }

    private Flux<Integer> testDoOnCancel(int msDelay) {
        Flux<Integer> flux = Flux.range(1,10)
                .delayElements(Duration.ofMillis(msDelay));

        // From a number of elements in a Flux on the cancel event do specified action
        return flux.doOnCancel(() -> System.out.println("Cancelled"));
    }

    private Flux<Integer> testDoOnError() { // Generate exception
        Flux<Integer> flux = Flux.range(1,10)
                .map(integer -> {
                    if (integer == 5) {
                        throw new RuntimeException("Error on integer: " + integer);
                    } else {
                        return integer;
                    }
                });

        return flux;
    }

    private Flux<Integer> testDoOnError1() {
        Flux<Integer> flux = Flux.range(1,10)
                .map(integer -> {
                    if (integer == 5) {
                        throw new RuntimeException("Invalid number: " + integer);
                    } else {
                        return integer;
                    }
                });

        return flux
                .onErrorContinue((throwable, o) -> {
                    System.out.println("Although error: " + throwable.getMessage() + " occurred don't worry about: " + o);
                    log.error("Although error: {} occurred on value: {} continue", throwable.getMessage(), o);
                });
    }

    private Flux<Integer> testDoOnErrorReturn() {
        Flux<Integer> flux = Flux.range(1,10)
                .map(integer -> {
                    if (integer == 5) {
                        throw new RuntimeException("Invalid number: " + integer);
                    } else {
                        return integer;
                    }
                });

        return flux
                .onErrorReturn(-1); // return special value -1 and stop processing
    }

    private Flux<Integer> testDoOnErrorReturn1() {
        Flux<Integer> flux = Flux.range(1,10)
                .map(integer -> {
                    if (integer == 5) {
                        throw new RuntimeException("Invalid number: " + integer);
                    } if (integer == 6) {
                        throw new ArithmeticException("Arithmetic Exception: "  + integer);
                    } else {
                        return integer;
                    }
                });

        return flux
                .onErrorReturn(RuntimeException.class, -1)
                .onErrorReturn(ArithmeticException.class, -2); // return special value -1 and stop processing
    }

    private Flux<Integer> testDoOnErrorResume() {
        Flux<Integer> flux = Flux.range(1,10)
                .map(integer -> {
                    if (integer == 5) {
                        throw new RuntimeException("Invalid number: " + integer);
                    } else {
                        return integer;
                    }
                });

        return flux
                .onErrorResume(throwable ->  Flux.range(100,5));
    }

    private Flux<Integer> testDoOnErrorMap() {
        Flux<Integer> flux = Flux.range(1,10)
                .map(integer -> {
                    if (integer == 5) {
                        throw new RuntimeException("Invalid number: " + integer);
                    } else {
                        return integer;
                    }
                });

        return flux
                .onErrorMap(throwable -> new UnsupportedOperationException(throwable.getMessage()));
    }


    public static void main(String[] args) throws InterruptedException {
        ReactiveTutorial reactiveTutorial = new ReactiveTutorial();

        log.info("01 - Demo Mono test");
        reactiveTutorial.testMono(); // will nog work there is no subscription yet. When running the program exits immeadiately
                                     // the compiler gives a warning: Value is never used as Publisher

        log.info("02 - Demo Mono test subscribe");
        reactiveTutorial.testMono()
                .subscribe();        // Data is generated, but the subscriber is not using the data

        log.info("03 - Demo Mono.just");
        reactiveTutorial.testMono()
                .subscribe(System.out::println);

        log.info("04 - Demo Flux.just");
        reactiveTutorial.testFlux()
                .subscribe(System.out::println);

        log.info("05 - Demo Flux.fromIterable");
        reactiveTutorial.testFlux01()
                .subscribe(System.out::println);

        log.info("06 - Demo Flux.map -- each element to uppercase");
        reactiveTutorial.testMap()
                .subscribe(System.out::println);

        log.info("07 - Demo Flux.flatMap -- each (provider) element to lowercase");
        reactiveTutorial.testFlatMap()
                .subscribe(System.out::println);

        log.info("08 - Demo Flux.skip -- skip number of elements from provider");
        reactiveTutorial.testSkip()
                .subscribe(System.out::println);

        log.info("09 - Demo Flux.skip -- skip with delay");
        reactiveTutorial.testSkipDuration()
                .subscribe(System.out::println);
        Thread.sleep(10000);

        log.info("10 - Demo Flux.skip -- skip with delay on skip");
        reactiveTutorial.testSkipDuration01()
                .subscribe(System.out::println);
        Thread.sleep(10000);

        log.info("11 - Demo Flux.skipLast");
        reactiveTutorial.testSkipLast()
                .subscribe(System.out::println);

        log.info("12 - Demo Flux.range");
        reactiveTutorial.testSkipRange()
                .subscribe(System.out::println);

        log.info("13 - Demo Flux.skipWhile");
        reactiveTutorial.testSkipWhile()
                .subscribe(System.out::println);

        log.info("14 - Demo Flux.skipUntil");
        reactiveTutorial.testSkipUntil()
                .subscribe(System.out::println);

        log.info("15 - Demo Flux.concat");
        reactiveTutorial.testConcat()
                .subscribe(System.out::println);

        log.info("16 - Demo Flux.merge");
        reactiveTutorial.testMerge()
                .subscribe(System.out::println);

        log.info("17 - Demo Flux.merge with delay");
        reactiveTutorial.testMergeDelay()
                .subscribe(System.out::println);
        Thread.sleep(3000);

        log.info("18 - Demo Flux.zip");
        reactiveTutorial.testZip()
                .subscribe(System.out::println);

        log.info("19 - Demo Flux.zip 4 streams");
        reactiveTutorial.testZipFour()
                .subscribe(System.out::println);

        log.info("20 - Demo Flux.zip 2 flux 1 mono");
        reactiveTutorial.testComplexZip()
                .subscribe(System.out::println);

        log.info("21 - Demo Flux.collectList");
        reactiveTutorial.testCollectList()
                .subscribe(System.out::println);

        log.info("22 - Demo Flux.collectList1");
        log.info("Start collectList1");
        System.out.println(reactiveTutorial.testCollectList1());
        log.info("End   collectList1");

        log.info("23 - Demo Flux.collectList2");
        log.info("Start collectList2");
        System.out.println(reactiveTutorial.testCollectList2());
        log.info("End   collectList2");

        log.info("24 - Demo Flux.buffer");
        reactiveTutorial.testBuffer()
                .subscribe(System.out::println);
        Thread.sleep(3000);

        log.info("25 - Demo Flux.buffer with maximum value 3");
        reactiveTutorial.testBuffer1(3)
                .subscribe(System.out::println);
        Thread.sleep(3000);

        log.info("26 - Demo Flux.buffer with delay of 500ms");
        reactiveTutorial.testBuffer2(500)
                .subscribe(System.out::println);
        Thread.sleep(3000);

        log.info("27 - Demo Flux.collectMap");
        reactiveTutorial.testCollectMap()
                .subscribe(System.out::println);

        log.info("28 - Demo Flux.doOnEach");
        reactiveTutorial.testDoOnEach()
                .subscribe();

        log.info("29 - Demo Flux.doOnEach");
        reactiveTutorial.testDoOnEach1()
                .subscribe();

        log.info("30 - Demo Flux.doOnComplete");
        reactiveTutorial.testDoOnComplete()
                .subscribe(System.out::println);

        log.info("31 - Demo Flux.doOnNext");
        reactiveTutorial.testDoOnNext()
                .subscribe(System.out::println);

        log.info("32 - Demo Flux.doOnSubscribe");
        reactiveTutorial.testDoOnSubscribe()
                .subscribe(System.out::println);

        log.info("33 - Demo Flux.doOnCancel");
        Disposable disposable = reactiveTutorial.testDoOnCancel(250)  // 250 ms delay between elements
                .subscribe(System.out::println);
        Thread.sleep(752);
        disposable.dispose();

        log.info("34 - Demo Flux.doOnError");
        reactiveTutorial.testDoOnError()
                .subscribe(System.out::println);

        log.info("35 - Demo Flux.doOnError1");
        reactiveTutorial.testDoOnError1()
                .subscribe(System.out::println);

        log.info("36 - Demo Flux.doOnErrorReturn");
        reactiveTutorial.testDoOnErrorReturn()
                .subscribe(System.out::println);

        log.info("37 - Demo Flux.doOnErrorReturn1");
        reactiveTutorial.testDoOnErrorReturn1()
                .subscribe(System.out::println);

        log.info("38 - Demo Flux.doOnErrorResume");
        reactiveTutorial.testDoOnErrorResume()
                .subscribe(System.out::println);

        log.info("39 - Demo Flux.doOnErrorMap");
        reactiveTutorial.testDoOnErrorMap()
                .subscribe(System.out::println);
    }
}
