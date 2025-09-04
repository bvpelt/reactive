package com.bsoft.reactive.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Customer entity representing a customer in the system")
public class Customer {

    @Id
    @Schema(description = "Unique identifier for the customer",
            example = "507f1f77bcf86cd799439011",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Customer's full name",
            example = "John Doe",
            required = true,
            minLength = 2,
            maxLength = 100)
    private String name;

    @Schema(description = "Customer's email address",
            example = "john.doe@example.com",
            required = true,
            pattern = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$")
    private String email;

    @Schema(description = "Customer's phone number",
            example = "+1-555-123-4567",
            pattern = "^\\+?[1-9]\\d{1,14}$")
    private String phone;

    @Schema(description = "Customer's address",
            example = "Laan van westenenk 701 5301 AP Apeldoorn")
    private String address;

    @Schema(description = "Customer's job title",
            example = "Software Engineer",
            maxLength = 100)
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
