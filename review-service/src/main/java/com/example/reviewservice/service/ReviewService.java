package com.example.reviewservice.service;

import com.example.reviewservice.model.*;
import com.example.reviewservice.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        Review review = new Review();
        review.setRoomId(request.getRoomId());
        review.setCustomerId(request.getCustomerId());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setSubmitDate(LocalDate.now());
        reviewRepository.save(review);
    }

    public ReviewCollectionDTO getReviewsForRoom(Long roomId) {
        List<Review> reviews = reviewRepository.findByRoomId(roomId);
        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        double totalRating = 0;

        for (Review r : reviews) {
            ReviewDTO dto = new ReviewDTO();
            dto.setStars(r.getRating());
            dto.setComments(r.getComment());
            dto.setSubmitdate(r.getSubmitDate());

            CustomerResponseDTO cr = customerService.getCustomerById(r.getCustomerId());
            if (cr.getCustomerDTO() != null) {
                dto.setCustomer(cr.getCustomerDTO().getName());
            } else {
                dto.setCustomer("Unknown Customer");
            }

            List<BookingDTO> bookings = bookingService.getBookingsByCustomerId(r.getCustomerId());
            BookingDTO latestBooking = null;
            for (BookingDTO b : bookings) {
                if (b.getRoomid().equals(roomId)) {
                    if (latestBooking == null || b.getStartdate().isAfter(latestBooking.getStartdate())) {
                        latestBooking = b;
                    }
                }
            }
            if (latestBooking != null) {
                dto.setStartdate(latestBooking.getStartdate());
                dto.setEnddate(latestBooking.getEnddate());
            }

            reviewDTOs.add(dto);
            totalRating += r.getRating();
        }

        ReviewCollectionDTO collection = new ReviewCollectionDTO();
        collection.setReviews(reviewDTOs);
        collection.setTotalReviews(reviews.size());
        if (!reviews.isEmpty()) {
            collection.setAverage(totalRating / reviews.size());
        } else {
            collection.setAverage(0);
        }

        return collection;
    }
}
