package com.example.reviewservice.service;

import com.example.reviewservice.model.*;
import com.example.reviewservice.repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RestTemplate restTemplate;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        this.restTemplate = new RestTemplate();
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

            try {
                ResponseEntity<CustomerDTO> response = restTemplate.getForEntity("http://customer-service:8081/api/customers/" + r.getCustomerId(), CustomerDTO.class);
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    dto.setCustomer(response.getBody().getName());
                } else {
                    dto.setCustomer("Unknown Customer");
                }
            } catch (Exception e) {
                dto.setCustomer("Unknown Customer");
            }

            BookingDTO latestBooking = null;
            try {
                ResponseEntity<BookingDTO[]> bookingResponse = restTemplate.getForEntity("http://booking-service:8080/bookings/customer/" + r.getCustomerId(), BookingDTO[].class);
                if (bookingResponse.getStatusCode().is2xxSuccessful() && bookingResponse.getBody() != null) {
                    List<BookingDTO> bookings = Arrays.asList(bookingResponse.getBody());
                    for (BookingDTO b : bookings) {
                        if (b.getRoomid().equals(roomId)) {
                            if (latestBooking == null || b.getStartdate().isAfter(latestBooking.getStartdate())) {
                                latestBooking = b;
                            }
                        }
                    }
                }
            } catch (Exception e) {
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
