package com.bsoft.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "customers")
public class Customer {

    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String job;
    private List<Order> orders = new ArrayList<>();

    public Customer() {
        this.id = UUID.randomUUID().toString();
    }

    public Customer(String name, String email, String phone, String address) {
        this();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Customer(String name, String email, String phone, String address, String job) {
        this(name, email, phone, address);
        this.job = job;
    }

}
