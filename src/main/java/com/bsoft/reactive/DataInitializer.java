package com.bsoft.reactive;

import com.bsoft.reactive.model.Customer;
import com.bsoft.reactive.model.Order;
import com.bsoft.reactive.repositories.CustomerRepository;
import com.bsoft.reactive.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Component
@Slf4j
public class DataInitializer  implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        // Clear existing data
        customerRepository.deleteAll().block();
        orderRepository.deleteAll().block();

        // Create sample customers
        Customer customer1 = new Customer("John Doe", "john@example.com", "123-456-7890", "123 Main St, Anytown, USA");
        Customer customer2 = new Customer("Jane Smith", "jane@example.com", "098-765-4321", "456 Oak Ave, Somewhere, USA");
        Customer customer3 = new Customer("Bob Johnson", "bob@example.com", "555-123-4567", "789 Pine Rd, Nowhere, USA");

        // Save customers
        Flux.just(customer1, customer2, customer3)
                .flatMap(customerRepository::save)
                .collectList()
                .doOnNext(customers -> {
                    System.out.println("Created " + customers.size() + " customers");

                    // Create sample orders for the customers
                    Order order1 = new Order(customers.get(0).getId(), "Laptop", 1, new BigDecimal("999.99"));
                    Order order2 = new Order(customers.get(0).getId(), "Mouse", 2, new BigDecimal("29.99"));
                    Order order3 = new Order(customers.get(1).getId(), "Monitor", 1, new BigDecimal("299.99"));
                    Order order4 = new Order(customers.get(1).getId(), "Keyboard", 1, new BigDecimal("79.99"));
                    Order order5 = new Order(customers.get(2).getId(), "Tablet", 1, new BigDecimal("199.99"));

                    // Set different statuses
                    order2.setStatus("COMPLETED");
                    order3.setStatus("SHIPPED");
                    order4.setStatus("PROCESSING");

                    // Save orders
                    Flux.just(order1, order2, order3, order4, order5)
                            .flatMap(orderRepository::save)
                            .collectList()
                            .subscribe(orders -> {
                                System.out.println("Created " + orders.size() + " orders");
                                System.out.println("Sample data initialization completed!");
                            });
                })
                .subscribe();
    }
}
