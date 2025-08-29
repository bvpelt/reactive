package com.bsoft.reactive.controllers;

import com.bsoft.reactive.model.Customer;
import com.bsoft.reactive.model.Order;
import com.bsoft.reactive.services.CustomerService;
import com.bsoft.reactive.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class WebController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    // Customer web pages
    @GetMapping("/customers")
    public String customers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }

    @GetMapping("/customers/new")
    public String newCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-form";
    }

    @GetMapping("/customers/edit/{id}")
    public String editCustomer(@PathVariable String id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customer-form";
    }

    @PostMapping("/customers/save")
    public Mono<String> saveCustomer(@ModelAttribute Customer customer) {
        return customerService.saveCustomer(customer)
                .then(Mono.just("redirect:/customers"));
    }

    @PostMapping("/customers/update/{id}")
    public Mono<String> updateCustomer(@PathVariable String id, @ModelAttribute Customer customer) {
        return customerService.updateCustomer(id, customer)
                .then(Mono.just("redirect:/customers"));
    }

    @GetMapping("/customers/delete/{id}")
    public Mono<String> deleteCustomer(@PathVariable String id) {
        return customerService.deleteCustomer(id)
                .then(Mono.just("redirect:/customers"));
    }

    @GetMapping("/customers/{id}/orders")
    public String customerOrders(@PathVariable String id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        model.addAttribute("orders", orderService.getOrdersByCustomerId(id));
        return "customer-orders";
    }

    // Order web pages
    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

    @GetMapping("/orders/new")
    public String newOrder(Model model, @RequestParam(required = false) String customerId) {
        Order order = new Order();
        if (customerId != null) {
            order.setCustomerId(customerId);
        }
        model.addAttribute("order", order);
        model.addAttribute("customers", customerService.getAllCustomers());
        return "order-form";
    }

    @GetMapping("/orders/edit/{id}")
    public String editOrder(@PathVariable String id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("customers", customerService.getAllCustomers());
        return "order-form";
    }

    @PostMapping("/orders/save")
    public Mono<String> saveOrder(@ModelAttribute Order order) {
        log.debug("Saving order 01 {} =================================", order);
        order.getTotalAmount();
        log.debug("Saving order 02 {} =================================", order);
        return orderService.saveOrder(order)
                .then(Mono.just("redirect:/orders"));
    }

    @PostMapping("/orders/update/{id}")
    public Mono<String> updateOrder(@PathVariable String id, @ModelAttribute Order order) {
        order.getTotalAmount();
        return orderService.updateOrder(id, order)
                .then(Mono.just("redirect:/orders"));
    }

    @GetMapping("/orders/delete/{id}")
    public Mono<String> deleteOrder(@PathVariable String id) {
        return orderService.deleteOrder(id)
                .then(Mono.just("redirect:/orders"));
    }
}
