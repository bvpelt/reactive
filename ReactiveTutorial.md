# Tutorial

## Output

```text
8:07:00 AM: Executing ':com.bsoft.reactive.ReactiveTutorial.main()'…

> Task :generateEffectiveLombokConfig UP-TO-DATE
> Task :compileJava
> Task :processResources UP-TO-DATE
> Task :classes

> Task :com.bsoft.reactive.ReactiveTutorial.main()
```
### 01 - Demo Mono test
```bash
private Mono<String> testMono() {  // This is the publisher
  return Mono.just("Java");        // wrap the string in Mono
}

log.info("01 - Demo Mono test");
reactiveTutorial.testMono(); // will nog work there is no subscription yet. When running the program exits immeadiately
// the compiler gives a warning: Value is never used as Publisher
```

```text
08:07:03.085 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 01 - Demo Mono test
```
### 02 - Demo Mono test subscribe
```bash
private Mono<String> testMono() {  // This is the publisher
  return Mono.just("Java");        // wrap the string in Mono
}

log.info("02 - Demo Mono test subscribe");
reactiveTutorial.testMono()
        .subscribe();        // Data is generated, but the subscriber is not using the data
```

```text
08:07:03.121 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 02 - Demo Mono test subscribe
```
### 03 - Demo Mono.just
```bash
private Mono<String> testMono() {  // This is the publisher
  return Mono.just("Java");        // wrap the string in Mono
}

log.info("03 - Demo Mono.just");
reactiveTutorial.testMono()
        .subscribe(System.out::println);
```

```text
08:07:03.126 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 03 - Demo Mono.just
Java
```
### 04 - Demo Flux.just
```bash
private Flux<String> testFlux() {
    return Flux.just("Java", "Cpp", "Python", "Rust");
}

log.info("04 - Demo Flux.just");
reactiveTutorial.testFlux()
        .subscribe(System.out::println);
````

```text
08:07:03.127 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 04 - Demo Flux.just
Java
Cpp
Python
Rust
```
### 05 - Demo Flux.fromIterable
```bash
private Flux<String> testFlux01() {
    List<String> programmingLanguages = List.of("Dart", "Javascript", "COBOL", "C++");

    return Flux.fromIterable(programmingLanguages);
}

log.info("05 - Demo Flux.fromIterable");
reactiveTutorial.testFlux01()
        .subscribe(System.out::println);
````

```text
08:07:03.171 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 05 - Demo Flux.fromIterable
Dart
Javascript
COBOL
C++
08:07:03.172 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 06 - Demo Flux.map -- each element to uppercase
DART
JAVASCRIPT
COBOL
C++
08:07:03.173 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 07 - Demo Flux.flatMap -- each (provider) element to lowercase
dart
javascript
cobol
c++
08:07:03.177 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 08 - Demo Flux.skip -- skip number of elements from provider
08:07:03.181 [main] INFO com.bsoft.reactive.ReactiveTutorial -- Number of elements in flux: 4
COBOL
C++
08:07:03.182 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 09 - Demo Flux.skip -- skip with delay
08:07:03.191 [main] INFO reactor.Flux.ConcatMapNoPrefetch.1 -- onSubscribe(FluxConcatMapNoPrefetch.FluxConcatMapNoPrefetchSubscriber)
08:07:03.191 [main] INFO reactor.Flux.ConcatMapNoPrefetch.1 -- request(unbounded)
08:07:04.194 [parallel-1] INFO reactor.Flux.ConcatMapNoPrefetch.1 -- onNext(Dart)
Dart
08:07:05.195 [parallel-2] INFO reactor.Flux.ConcatMapNoPrefetch.1 -- onNext(Javascript)
Javascript
08:07:06.195 [parallel-3] INFO reactor.Flux.ConcatMapNoPrefetch.1 -- onNext(COBOL)
COBOL
08:07:07.196 [parallel-4] INFO reactor.Flux.ConcatMapNoPrefetch.1 -- onNext(C++)
C++
08:07:07.197 [parallel-4] INFO reactor.Flux.ConcatMapNoPrefetch.1 -- onComplete()
08:07:13.194 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 10 - Demo Flux.skip -- skip with delay on skip
COBOL
C++
08:07:23.195 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 11 - Demo Flux.skipLast
Dart
Javascript
08:07:23.197 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 12 - Demo Flux.range
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
08:07:23.197 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 13 - Demo Flux.skipWhile
10
11
12
13
14
15
16
17
18
19
20
08:07:23.198 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 14 - Demo Flux.skipUntil
10
11
12
13
14
15
16
17
18
19
20
08:07:23.198 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 15 - Demo Flux.concat
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
101
102
103
104
105
106
107
108
109
110
111
112
113
114
115
116
117
118
119
120
08:07:23.199 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 16 - Demo Flux.merge
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
101
102
103
104
105
106
107
108
109
110
111
112
113
114
115
116
117
118
119
120
08:07:23.200 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 17 - Demo Flux.merge with delay
1
101
2
102
3
103
4
104
5
105
6
106
7
107
8
108
9
109
10
110
11
111
12
112
13
113
14
114
15
115
16
116
17
117
18
118
19
119
20
120
08:07:26.201 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 18 - Demo Flux.zip
[1,101]
[2,102]
[3,103]
[4,104]
[5,105]
[6,106]
[7,107]
[8,108]
[9,109]
[10,110]
[11,111]
[12,112]
[13,113]
[14,114]
[15,115]
[16,116]
[17,117]
[18,118]
[19,119]
[20,120]
08:07:26.202 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 19 - Demo Flux.zip 4 streams
[1,101,501,1001]
[2,102,502,1002]
[3,103,503,1003]
[4,104,504,1004]
[5,105,505,1005]
[6,106,506,1006]
[7,107,507,1007]
[8,108,508,1008]
[9,109,509,1009]
[10,110,510,1010]
[11,111,511,1011]
[12,112,512,1012]
[13,113,513,1013]
[14,114,514,1014]
[15,115,515,1015]
08:07:26.203 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 20 - Demo Flux.zip 2 flux 1 mono
[1,101,5]
08:07:26.204 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 21 - Demo Flux.collectList
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
08:07:26.205 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 22 - Demo Flux.collectList1
08:07:26.205 [main] INFO com.bsoft.reactive.ReactiveTutorial -- Start collectList1
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
08:07:26.205 [main] INFO com.bsoft.reactive.ReactiveTutorial -- End   collectList1
08:07:26.205 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 23 - Demo Flux.collectList2
08:07:26.205 [main] INFO com.bsoft.reactive.ReactiveTutorial -- Start collectList2
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
08:07:31.213 [main] INFO com.bsoft.reactive.ReactiveTutorial -- End   collectList2
08:07:31.213 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 24 - Demo Flux.buffer
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
08:07:34.214 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 25 - Demo Flux.buffer with maximum value 3
[1, 2, 3]
[4, 5, 6]
[7, 8, 9]
[10]
08:07:37.215 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 26 - Demo Flux.buffer with delay of 500ms
[1]
[2, 3]
[4, 5]
[6, 7]
[8, 9]
[10]
08:07:40.217 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 27 - Demo Flux.collectMap
{1=1, 2=4, 3=9, 4=16, 5=25, 6=36, 7=49, 8=64, 9=81, 10=100}
08:07:40.219 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 28 - Demo Flux.doOnEach
Signal: doOnEach_onNext(1)
Signal: doOnEach_onNext(2)
Signal: doOnEach_onNext(3)
Signal: doOnEach_onNext(4)
Signal: doOnEach_onNext(5)
Signal: doOnEach_onNext(6)
Signal: doOnEach_onNext(7)
Signal: doOnEach_onNext(8)
Signal: doOnEach_onNext(9)
Signal: doOnEach_onNext(10)
Signal: onComplete()
08:07:40.220 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 29 - Demo Flux.doOnEach
Signal value: 1
Signal value: 2
Signal value: 3
Signal value: 4
Signal value: 5
Signal value: 6
Signal value: 7
Signal value: 8
Signal value: 9
Signal value: 10
Ready
08:07:40.221 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 30 - Demo Flux.doOnComplete
1
2
3
4
5
6
7
8
9
10
Ready
08:07:40.222 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 31 - Demo Flux.doOnNext
Value: 1
1
Value: 2
2
Value: 3
3
Value: 4
4
Value: 5
5
Value: 6
6
Value: 7
7
Value: 8
8
Value: 9
9
Value: 10
10
08:07:40.222 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 32 - Demo Flux.doOnSubscribe
Subscribed
1
2
3
4
5
6
7
8
9
10
08:07:40.223 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 33 - Demo Flux.doOnCancel
1
2
3
Cancelled
08:07:40.977 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 34 - Demo Flux.doOnError
1
2
3
4
08:07:40.977 [main] ERROR reactor.core.publisher.Operators -- Operator called default onErrorDropped
reactor.core.Exceptions$ErrorCallbackNotImplemented: java.lang.RuntimeException: Error on integer: 5
Caused by: java.lang.RuntimeException: Error on integer: 5
	at com.bsoft.reactive.ReactiveTutorial.lambda$testDoOnError$12(ReactiveTutorial.java:280)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableSubscriber.onNext(FluxMapFuseable.java:113)
	at reactor.core.publisher.FluxRange$RangeSubscription.fastPath(FluxRange.java:131)
	at reactor.core.publisher.FluxRange$RangeSubscription.request(FluxRange.java:109)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableSubscriber.request(FluxMapFuseable.java:171)
	at reactor.core.publisher.LambdaSubscriber.onSubscribe(LambdaSubscriber.java:119)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableSubscriber.onSubscribe(FluxMapFuseable.java:96)
	at reactor.core.publisher.FluxRange.subscribe(FluxRange.java:69)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8891)
	at reactor.core.publisher.Flux.subscribeWith(Flux.java:9012)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8856)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8780)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8723)
	at com.bsoft.reactive.ReactiveTutorial.main(ReactiveTutorial.java:513)
08:07:40.978 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 35 - Demo Flux.doOnError1
1
2
3
4
Although error: Invalid number: 5 occurred don't worry about: 5
08:07:40.981 [main] ERROR com.bsoft.reactive.ReactiveTutorial -- Although error: Invalid number: 5 occurred on value: 5 continue
6
7
8
9
10
08:07:40.981 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 36 - Demo Flux.doOnErrorReturn
1
2
3
4
-1
08:07:40.982 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 37 - Demo Flux.doOnErrorReturn1
1
2
3
4
-1
08:07:40.983 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 38 - Demo Flux.doOnErrorResume
1
2
3
4
100
101
102
103
104
08:07:40.984 [main] INFO com.bsoft.reactive.ReactiveTutorial -- 39 - Demo Flux.doOnErrorMap
1
2
3
4
08:07:40.984 [main] ERROR reactor.core.publisher.Operators -- Operator called default onErrorDropped
reactor.core.Exceptions$ErrorCallbackNotImplemented: java.lang.UnsupportedOperationException: Invalid number: 5
Caused by: java.lang.UnsupportedOperationException: Invalid number: 5
	at com.bsoft.reactive.ReactiveTutorial.lambda$testDoOnErrorMap$20(ReactiveTutorial.java:362)
	at reactor.core.publisher.Flux.lambda$onErrorMap$28(Flux.java:7318)
	at reactor.core.publisher.FluxOnErrorResume$ResumeSubscriber.onError(FluxOnErrorResume.java:94)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableSubscriber.onError(FluxMapFuseable.java:142)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableSubscriber.onNext(FluxMapFuseable.java:121)
	at reactor.core.publisher.FluxRange$RangeSubscription.fastPath(FluxRange.java:131)
	at reactor.core.publisher.FluxRange$RangeSubscription.request(FluxRange.java:109)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableSubscriber.request(FluxMapFuseable.java:171)
	at reactor.core.publisher.Operators$MultiSubscriptionSubscriber.set(Operators.java:2366)
	at reactor.core.publisher.FluxOnErrorResume$ResumeSubscriber.onSubscribe(FluxOnErrorResume.java:74)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableSubscriber.onSubscribe(FluxMapFuseable.java:96)
	at reactor.core.publisher.FluxRange.subscribe(FluxRange.java:69)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8891)
	at reactor.core.publisher.Flux.subscribeWith(Flux.java:9012)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8856)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8780)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8723)
	at com.bsoft.reactive.ReactiveTutorial.main(ReactiveTutorial.java:533)

BUILD SUCCESSFUL in 40s
4 actionable tasks: 2 executed, 2 up-to-date
8:07:41 AM: Execution finished ':com.bsoft.reactive.ReactiveTutorial.main()'.
```