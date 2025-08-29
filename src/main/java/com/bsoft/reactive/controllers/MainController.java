package com.bsoft.reactive.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class MainController {

    @GetMapping("/home")
    public Mono<String> handleMain() {
        return Mono.just("home");
    }
}
