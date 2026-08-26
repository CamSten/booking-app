package com.example.bookingapp.model;

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
}