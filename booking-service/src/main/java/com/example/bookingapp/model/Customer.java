package com.example.bookingapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String address;
    private String phone;
    private String password;
    public enum CustomerStatus {
        ACTIVE,
        UNREGISTERED,
    }
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    public Customer() {
    }

    public Customer(String name, String email, String address, String phone, String password, CustomerStatus status) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.status = status;
    }
}
