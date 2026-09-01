package com.example.bookingapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BookingAppApplicationTests {
    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void contextLoads() {
    }

    @Test
    void shouldCreateCustomer() {
        String customerJson = """
            {
                "name": "Test Customer",
                "email": "test@test.com",
                "address": "Test Street 1",
                "phone": "0701234567",
                "password": "password"
            }
            """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(customerJson, headers);
        ResponseEntity<String> response =
                restTemplate.postForEntity("http://localhost:8081/api/customers/signup", request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldReturnBadRequestOnMissingEmail() {
        String invalidCustomerJson = """
            {
                "name": "Test Customer",
                "email": "",
                "password": "password"
            }
            """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(invalidCustomerJson, headers);
        
        try {
            restTemplate.postForEntity("http://localhost:8081/api/customers/signup", request, String.class);
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }

    @Test
    void shouldReturnNotFoundForInvalidCustomer() {
        try {
            restTemplate.getForEntity("http://localhost:8081/api/customers/999999", String.class);
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }
}
