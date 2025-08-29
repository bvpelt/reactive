package com.bsoft.reactive.services;

import com.bsoft.reactive.model.Order;
import com.bsoft.reactive.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public Flux<Order> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Mono<Order> saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Mono<Order> updateOrder(String id, Order order) {
        return orderRepository.findById(id)
                .flatMap(existingOrder -> {
                    existingOrder.setProductName(order.getProductName());
                    existingOrder.setQuantity(order.getQuantity());
                    existingOrder.setPrice(order.getPrice());
                    existingOrder.setStatus(order.getStatus());
                    return orderRepository.save(existingOrder);
                });
    }

    public Mono<Void> deleteOrder(String id) {
        return orderRepository.deleteById(id);
    }
}
