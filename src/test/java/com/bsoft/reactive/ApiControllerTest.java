package com.bsoft.reactive;

import com.bsoft.reactive.model.Customer;
import com.bsoft.reactive.model.Order;
import com.bsoft.reactive.services.CustomerService;
import com.bsoft.reactive.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ApiControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private CustomerService customerService;

    @MockitoBean
    private OrderService orderService;

    @Test
    public void testGetAllCustomers() {
        Customer customer = new Customer("John Doe", "john@example.com", "123-456-7890", "123 Main St");
        when(customerService.getAllCustomers()).thenReturn(Flux.just(customer));

        webTestClient.get()
                .uri("/api/customers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Customer.class)
                .hasSize(1);

    }

    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer("Jane Doe", "jane@example.com", "098-765-4321", "456 Oak Ave");
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(Mono.just(customer));

        webTestClient.post()
                .uri("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(customer)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Customer.class);
    }

    @Test
    public void testGetAllOrders() {
        Order order = new Order("customer1", "Laptop", 1, new BigDecimal("999.99"));
        when(orderService.getAllOrders()).thenReturn(Flux.just(order));

        webTestClient.get()
                .uri("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Order.class)
                .hasSize(1);
    }

    @Test
    public void testCreateOrder() {
        Order order = new Order("customer1", "Mouse", 2, new BigDecimal("29.99"));
        when(orderService.saveOrder(any(Order.class))).thenReturn(Mono.just(order));

        webTestClient.post()
                .uri("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(order)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Order.class);
    }

    @Test
    public void testDeleteCustomer() {
        when(customerService.deleteCustomer(anyString())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/customers/customer1")
                .exchange()
                .expectStatus().isNoContent();
    }
}
