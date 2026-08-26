package com.example.customer_service.model;

import lombok.Getter;

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

    public CustomerResponseDTO(long id, String name, String email, String address, String phone, Feedback feedback) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.feedback = feedback;
    }

    public CustomerResponseDTO(Feedback feedback){
        this.feedback = feedback;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}