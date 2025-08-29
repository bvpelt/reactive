package com.bsoft.reactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(exclude = "totalAmount")
@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    private String customerId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private String status;

    public Order() {
        this.id = UUID.randomUUID().toString();
        this.orderDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    public Order(String customerId, String productName, Integer quantity, BigDecimal price) {
        this();
        this.customerId = customerId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.totalAmount = this.price.multiply(new BigDecimal(quantity));
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.totalAmount = getTotalAmount();
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        this.totalAmount = getTotalAmount();
    }

}
