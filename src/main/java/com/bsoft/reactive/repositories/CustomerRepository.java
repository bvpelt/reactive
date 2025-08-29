package com.bsoft.reactive.repositories;

import com.bsoft.reactive.model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
    Flux<Customer> findByNameContainingIgnoreCase(String name);

    Flux<Customer> findByEmail(String email);

    Mono<Customer> findById(String id);
}
