package com.bsoft.reactive.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Document
public class Customer {

    @Id
    private String id;
    private String name;
    private String job;

    public Customer(String name, String job) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.job = job;
    }

}
