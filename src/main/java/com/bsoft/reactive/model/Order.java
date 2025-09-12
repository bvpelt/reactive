package com.bsoft.reactive.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
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
    @Schema(description = "Unique identifier for the order",
            example = "007f1f77bcf86cd799439051",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Unique identifier for the customer",
            example = "007f1f77bcf86cd799439051",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String customerId;

    @Schema(description = "Name of the product",
            example = "Laptop",
            required = true,
            minLength = 2,
            maxLength = 100)
    private String productName;

    @Schema(description = "Number of the products",
            example = "2",
            required = true)
    private Integer quantity;

    @Schema(description = "Price of the products",
            example = "599.99",
            required = true)
    private BigDecimal price;

    @Schema(description = "Total price of the product. This is price * quantity",
            example = "599.99",
            required = true)
    private BigDecimal totalAmount;

    @Schema(description = "Timestamp when the order was created",
            example = "2025-01-15T10:30:00")
    private LocalDateTime orderDate;

    @Schema(description = "Status of the order",
            example = "PENDING, PROCESSING, SHIPPED, DELIVERED, COMPLETED, CANCELED",
            accessMode = Schema.AccessMode.READ_ONLY)
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
        getTotalAmount();
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        getTotalAmount();
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        getTotalAmount();
    }

    public BigDecimal getTotalAmount() {
        if (this.totalAmount == null) {
            if ((this.quantity != null) && (this.price != null)) {
                this.totalAmount = this.price.multiply(new BigDecimal(quantity));
            }
        }
        return this.totalAmount;
    }
}
