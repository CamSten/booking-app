package com.example.reviewservice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewRequestDTO {
    private Long roomId;
    private Long customerId;
    private int rating;
    private String comment;

    public ReviewRequestDTO() {
    }
}
