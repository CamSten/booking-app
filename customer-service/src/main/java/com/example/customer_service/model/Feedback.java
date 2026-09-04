package com.example.customer_service.model;

public enum Feedback {
    OK("ok"),
    EMPTY_EMAIL("Email is required"),
    EMPTY_PASSWORD("password is required"),
    INVALID_EMAIL("The email does not exist"),
    INVALID_PASSWORD("The password is incorrect"),
    USER_EXISTS("Account with this email already exists"),
    INVALID_USER("The user does not exist"),
    HAS_ACTIVE_BOOKINGS("Cannot delete a customer with an active booking"),
    BOOKING_SERVICE_UNAVAILABLE("Booking service is unavailable");
    public final String feedback;

    Feedback(String feedback){
        this.feedback = feedback;
    }
}