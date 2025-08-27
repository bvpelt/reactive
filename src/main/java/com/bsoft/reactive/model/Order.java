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
public class Order {
    @Id
    private String id;
    private String customerId;
    private Double total;
    private Double discount;

    public Order(String customerId, Double total, Double discount) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.total = total;
        this.discount = discount;
    }
}
