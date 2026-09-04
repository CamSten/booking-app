package com.example.bookingapp.service;

import com.example.bookingapp.model.*;
import com.example.bookingapp.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerService customerService;
    private final BookingService bookingService;

    public ReviewService(ReviewRepository reviewRepository, CustomerService customerService, BookingService bookingService) {
        this.reviewRepository = reviewRepository;
        this.customerService = customerService;
        this.bookingService = bookingService;
    }

    public void saveReview(ReviewRequestDTO request) {
    }

    public ReviewCollectionDTO getReviewsForRoom(Long roomId) {
        return new ReviewCollectionDTO();
    }
}
