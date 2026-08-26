package com.example.bookingapp.service;

import com.example.bookingapp.config.RestTemplateConfig;
import com.example.bookingapp.model.CustomerDTO;
import com.example.bookingapp.model.CustomerResponseDTO;
import com.example.bookingapp.model.LoginRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {
    private final RestTemplate restTemplate;

    public CustomerService(RestTemplateConfig restTemplateConfig) {
        this.restTemplate = restTemplateConfig.restTemplate();
    }


    public CustomerResponseDTO loginCustomer(String email, String password){
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);
        return restTemplate.getForObject("http://localhost:8081/customer/login=" + loginRequestDTO, CustomerResponseDTO.class);
    }

    public CustomerResponseDTO signupCustomer(CustomerDTO customerDTO){
        return restTemplate.getForObject("http://localhost:8081/customer/signup=" + customerDTO, CustomerResponseDTO.class);
    }

    public CustomerResponseDTO updateCustomer(CustomerDTO customerDTO){
        return restTemplate.getForObject("http://localhost:8081/customer/update=" + customerDTO, CustomerResponseDTO.class);
    }

    public CustomerResponseDTO deleteCustomer(Long customerId){
        return restTemplate.getForObject("http://localhost:8081/customer/delete=" + customerId, CustomerResponseDTO.class);
    }

    public CustomerResponseDTO getCustomerById(Long customerId){
        return restTemplate.getForObject("http://localhost:8081/customer/id=" + customerId, CustomerResponseDTO.class);
    }
}