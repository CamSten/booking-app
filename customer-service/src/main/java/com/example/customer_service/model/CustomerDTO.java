package com.example.customer_service.model;

import lombok.Getter;

@Getter
public class CustomerDTO {
    private long id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String password;

    public CustomerDTO() {
    }

    public CustomerDTO(long id, String name, String email, String address, String phone, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.password = password;
    }

}
