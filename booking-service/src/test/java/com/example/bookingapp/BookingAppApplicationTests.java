package com.example.bookingapp;

import com.example.bookingapp.model.BookingDTO;
import com.example.bookingapp.model.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookingAppApplicationTests {
    @Autowired MockMvc mvc;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String bookingJson = """
                {
                "roomid": 1,
                "cost": 1000,
                "startdate": "%s",
                "enddate": "%s",
                "guestcount": 2,
                "extrabed": false
                }
                """.formatted(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));


    @Test void shouldNotAllowValidCustomerBookingUnavailableRoom() throws Exception{
        mvc.perform(post("/bookings") .param("customerId", "1").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
                .andExpect(status().isBadRequest());

    }

    @Test void shouldNotCreateBookingForInvalidCustomer() throws Exception {
       mvc.perform(post("/bookings") .param("customerId", "-1").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
                .andExpect(status().isBadRequest());
    }

    @Test void shouldCreateBookingForValidCustomer() throws Exception {
           mvc.perform(post("/bookings") .param("customerId", "1").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerid").value(1))
                .andExpect(jsonPath("$.roomid").value(1));
    }


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
