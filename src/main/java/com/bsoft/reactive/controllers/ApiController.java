package com.bsoft.reactive.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import com.bsoft.reactive.model.Customer;

import com.bsoft.reactive.model.Order;
import com.bsoft.reactive.services.CustomerService;
import com.bsoft.reactive.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ApiController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Tag(name = "Customers", description = "Customer management API")
    @Operation(
            summary = "Get all customers",
            description = "Retrieves all customers from the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of customers",
                    content = @Content(schema = @Schema(implementation = Customer.class))
            )
    })
    @GetMapping(value = "/customers",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @Tag(name = "Customers", description = "Customer management API")
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
    @GetMapping(value = "/customers/{customerId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Customer> getCustomer(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }

    @Tag(name = "Customers", description = "Customer management API")
    @GetMapping("/customers/{id}/with-orders")
    public Mono<Customer> getCustomerWithOrders(@PathVariable String id) {
        return customerService.getCustomerWithOrders(id);
    }

    @Tag(name = "Customers", description = "Customer management API")
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
    @PostMapping(value = "/customers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Customer> createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @Tag(name = "Customers", description = "Customer management API")
    @Operation(
            summary = "Update customer",
            description = "Updates an existing customer"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer updated successfully",
                    content = @Content(schema = @Schema(implementation = Customer.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid customer data"
            )
    })
    @PutMapping(value = "/customers/{customerId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Customer> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    /**
     * Handles the HTTP DELETE request for deleting a customer.
     *
     * @param customerId The ID of the customer to delete.
     * @return A Mono of ResponseEntity<Void> with an HTTP status.
     */
    @Tag(name = "Customers", description = "Customer management API")
    @Operation(
            summary = "Delete customer",
            description = "Deletes a customer by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @DeleteMapping("/customers/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCustomer(@PathVariable String id) {
        return customerService.deleteCustomer(id);
    }




    // Order REST endpoints
    @Tag(name = "Orders", description = "Order management API")
    @Operation(
            summary = "Get all orders",
            description = "Retrieves all orders from the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of orders",
                    content = @Content(schema = @Schema(implementation = Order.class))
            )
    })
    @GetMapping(value = "/orders",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @Tag(name = "Orders", description = "Order management API")
    @Operation(
            summary = "Get order by ID",
            description = "Retrieves a order by their unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order found",
                    content = @Content(schema = @Schema(implementation = Order.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found"
            )
    })
    @GetMapping(value = "/orders/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Order> getOrder(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    @Tag(name = "Orders", description = "Order management API")
    @GetMapping(value = "/customers/{customerId}/orders",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Order> getCustomerOrders(@PathVariable String customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }

    @Tag(name = "Orders", description = "Order management API")
    @Operation(
            summary = "Get all orders",
            description = "Retrieves all orders from the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of orders",
                    content = @Content(schema = @Schema(implementation = Order.class))
            )
    })
    @PostMapping(value = "/orders",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Order> createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @Tag(name = "Orders", description = "Order management API")
    @Operation(
            summary = "Update order",
            description = "Updates an existing order"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order updated successfully",
                    content = @Content(schema = @Schema(implementation = Order.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid order data"
            )
    })
    @PutMapping(value ="/orders/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Order> updateOrder(@PathVariable String id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @Tag(name = "Orders", description = "Order management API")
    @Operation(
            summary = "Delete order",
            description = "Deletes a order by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @DeleteMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteOrder(@PathVariable String id) {
        return orderService.deleteOrder(id);
    }

    // Health check endpoint
    @Tag(name = "Management", description = "Management API")
    @Operation(
            summary = "Health check",
            description = "Check if the API is running"
    )
    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    @Tag(name = "Health")
    public Mono<String> health() {
        return Mono.just("{\"status\":\"UP\",\"timestamp\":\"" +
                java.time.Instant.now() + "\"}");
    }
}