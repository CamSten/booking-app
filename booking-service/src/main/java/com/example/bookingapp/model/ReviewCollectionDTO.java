package com.example.bookingapp.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class ReviewCollectionDTO {
    private double average;
    private int totalReviews;
    private List<ReviewDTO> reviews;

    public ReviewCollectionDTO() {
    }
}
