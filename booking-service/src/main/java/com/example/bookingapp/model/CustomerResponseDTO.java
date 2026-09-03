package com.example.bookingapp.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerResponseDTO {
    private long id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private Feedback feedback;

    public CustomerResponseDTO() {
    }

    public CustomerResponseDTO(Feedback feedback){
        this.feedback = feedback;
    }

    public CustomerResponseDTO(CustomerDTO customer, Feedback feedback) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.address = customer.getAddress();
        this.phone = customer.getPhone();
        this.feedback = feedback;
    }
}