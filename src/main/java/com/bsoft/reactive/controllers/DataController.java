package com.bsoft.reactive.controllers;

import com.bsoft.reactive.model.Customer;
import com.bsoft.reactive.model.Order;
import com.bsoft.reactive.repositories.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Map;

@RestController
@Slf4j
public class DataController {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private final CustomerRepository customerRepository;

    public DataController(ReactiveMongoTemplate reactiveMongoTemplate, CustomerRepository customerRepository) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.customerRepository = customerRepository;
    }

    @Tag(name = "Data", description = "Data API, create a customer")
    @Operation(
            summary = "Create a new customer",
            description = "Creates a new customer in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Customer created successfully",
                    content = @Content(schema = @Schema(implementation = Customer.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid customer data"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    @PostMapping(value = "/customer/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Customer> createCustomer(@RequestBody Customer customer) {
        log.debug("Creating customer: {}", customer);
        // Save to database
        return reactiveMongoTemplate.save(customer);
    }

    @Tag(name = "Data", description = "Data API, find a customer")
    @Operation(
            summary = "Get customer by ID",
            description = "Retrieves a customer by their unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer found",
                    content = @Content(schema = @Schema(implementation = Customer.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found"
            )
    })
    @GetMapping(value = "/customer/find-by-id",
            produces = MediaType.APPLICATION_JSON_VALUE)
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

    @Tag(name = "Data", description = "Data API, create an order")
    @Operation(
            summary = "Create a new order",
            description = "Creates a new order in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Order created successfully",
                    content = @Content(schema = @Schema(implementation = Order.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid order data"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    @PostMapping(value = "/order/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Order> createOrder(@RequestBody Order order) {
        log.debug("Creating order: {}", order);
        // Save to database
        return reactiveMongoTemplate.save(order);
    }

    /*
    Return summary consisting of:
    customer name, total order price
     */
    @Tag(name = "Data", description = "Data API, find sales per customer")
    @Operation(
            summary = "Get sales for each customer",
            description = "Retrieves a sales summary by customer"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sales summary created",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Sales summary not found"
            )
    })
    @GetMapping(value = "/sales/summary",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, Integer>> calculateSummary() {
        log.debug("Start sales summary ==========================================");
        customerRepository.findAll()
                .subscribe(customer -> {
                    log.debug("Found customer: {}", customer);
                });
        log.debug("End   sales summary ==========================================");

        return reactiveMongoTemplate.findAll(Customer.class)
                .flatMap(customer -> Mono.zip(Mono.just(customer), calculateOrderSum(customer.getId())))
                .collectMap(tuple2 -> tuple2.getT1().getName(), Tuple2::getT2)
                .log()
                ;
    }

    private Mono<Integer> calculateOrderSum(String customerId) {
        Criteria criteria = Criteria.where("customerId").is(customerId);
        return reactiveMongoTemplate.find(Query.query(criteria), Order.class)
                .map(order -> {
                    log.info("Calculating order sum: {}, total: {}, calculated: {}", order, order.getTotalAmount(), order.getPrice().intValue() * order.getQuantity().intValue());
                    return order.getPrice().intValue() * order.getQuantity().intValue();
                })
                .reduce(0, Integer::sum)
                .log()
                ;
    }

}
