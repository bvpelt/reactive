package com.bsoft.reactive.services;

import com.bsoft.reactive.model.Customer;
import com.bsoft.reactive.repositories.CustomerRepository;
import com.bsoft.reactive.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Flux<Customer> getAllCustomers() {
        return customerRepository.findAll()
                .flatMap(customer -> orderRepository.findByCustomerId(customer.getId())
                        .collectList()
                        .map(orders -> new Customer(
                                customer.getId(),
                                customer.getName(),
                                customer.getEmail(),
                                customer.getPhone(),
                                customer.getAddress(),
                                customer.getJob(),
                                orders
                        ))
                );
    }

    public Mono<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public Mono<Customer> getCustomerWithOrders(String id) {
        return customerRepository.findById(id)
                .flatMap(customer -> {
                    return orderRepository.findByCustomerId(id)
                            .collectList()
                            .map(orders -> {
                                customer.setOrders(orders);
                                return customer;
                            });
                });
    }

    public Mono<Customer> saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Mono<Customer> updateCustomer(String id, Customer customer) {
        return customerRepository.findById(id)
                .flatMap(existingCustomer -> {
                    existingCustomer.setName(customer.getName());
                    existingCustomer.setEmail(customer.getEmail());
                    existingCustomer.setPhone(customer.getPhone());
                    existingCustomer.setAddress(customer.getAddress());
                    return customerRepository.save(existingCustomer);
                });
    }

    public Mono<Void> deleteCustomer(String id) {
        return orderRepository.findByCustomerId(id)
                .flatMap(order -> orderRepository.delete(order))
                .then(customerRepository.deleteById(id));
    }

    public Flux<Customer> searchCustomers(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name);
    }
}
