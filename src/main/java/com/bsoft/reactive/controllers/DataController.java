package com.bsoft.reactive.controllers;

import com.bsoft.reactive.model.Customer;
import com.bsoft.reactive.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Map;

@RestController
@Slf4j
public class DataController {

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    @PostMapping("/customer/create")
    public Mono<Customer> createCustomer(@RequestBody Customer customer) {
        log.debug("Creating customer: {}", customer);
        // Save to database
        return reactiveMongoTemplate.save(customer);
    }

    @GetMapping("/customer/find-by-id")
    public Mono<Customer> findCustomerById(@RequestParam("customerId") String customerId) {
        log.debug("Find customer by id: {}", customerId);
        // Retrieve from database
        // return reactiveMongoTemplate.findById(customerId, Customer.class);
        return reactiveMongoTemplate.findById(customerId, Customer.class);
    }

    private Mono<Customer> getCustomerById(String customerId) {
        Criteria criteria = Criteria.where("id").is(customerId);
        Query query = new Query(criteria);
        return reactiveMongoTemplate.findOne(query, Customer.class);
    }

    @PostMapping("/order/create")
    public Mono<Order> createOrder(@RequestBody Order order) {
        log.debug("Creating order: {}", order);
        // Save to database
        return reactiveMongoTemplate.save(order);
    }

    /*
    Return summary consisting of:
    customer name, total order price
     */
    @GetMapping("/sales/summary")
    public Mono<Map<String, Double>> calculateSummary() {
        return reactiveMongoTemplate.findAll(Customer.class)
                .flatMap(customer -> Mono.zip(Mono.just(customer), calculateOrderSum(customer.getId())))
                .collectMap(tuple2 -> tuple2.getT1().getName(), Tuple2::getT2)
//                .log()
                ;
    }

    private Mono<Double> calculateOrderSum(String customerId) {
        Criteria criteria = Criteria.where("customerId").is(customerId);
        return reactiveMongoTemplate.find(Query.query(criteria), Order.class)
                .map(Order::getTotal)
                .reduce(0d, Double::sum)
//                .log()
                ;
    }

}
