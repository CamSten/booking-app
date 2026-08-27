package com.example.bookingapp.service;

import com.example.bookingapp.config.RestTemplateConfig;
import com.example.bookingapp.model.CustomerDTO;
import com.example.bookingapp.model.CustomerResponseDTO;
import com.example.bookingapp.model.Feedback;
import com.example.bookingapp.model.LoginRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {
    private final RestTemplate restTemplate;
    private final String API_URL = "http://localhost:8081/api/customers";

    public CustomerService(RestTemplateConfig restTemplateConfig) {
        this.restTemplate = restTemplateConfig.restTemplate();
    }

    public CustomerResponseDTO loginCustomer(String email, String password){
        try {
            LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);
            return restTemplate.postForObject(API_URL + "/login", loginRequestDTO, CustomerResponseDTO.class);
        } catch (Exception e) {
            return new CustomerResponseDTO(Feedback.INVALID_PASSWORD);
        }
    }

    public CustomerResponseDTO signupCustomer(CustomerDTO customerDTO){
        try {
            return restTemplate.postForObject(API_URL, customerDTO, CustomerResponseDTO.class);
        } catch (Exception e) {
            return new CustomerResponseDTO(Feedback.USER_EXISTS);
        }
    }

    public CustomerResponseDTO updateCustomer(CustomerDTO customerDTO){
        try {
            restTemplate.put(API_URL + "/" + customerDTO.getId(), customerDTO);
            return new CustomerResponseDTO(Feedback.OK);
        } catch (Exception e) {
            return new CustomerResponseDTO(Feedback.INVALID_USER);
        }
    }

    public CustomerResponseDTO deleteCustomer(Long customerId){
        try {
            restTemplate.delete(API_URL + "/" + customerId);
            return new CustomerResponseDTO(Feedback.OK);
        } catch (Exception e) {
            return new CustomerResponseDTO(Feedback.HAS_ACTIVE_BOOKINGS);
        }
    }

    public CustomerResponseDTO getCustomerById(Long customerId){
        try {
            return restTemplate.getForObject(API_URL + "/" + customerId, CustomerResponseDTO.class);
        } catch (Exception e) {
            return new CustomerResponseDTO(Feedback.INVALID_USER);
        }
    }
}
