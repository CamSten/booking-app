package com.example.bookingapp.service;

import com.example.bookingapp.model.Customer;
import com.example.bookingapp.repository.BookingRepository;
import com.example.bookingapp.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private CustomerService customerService;
    private Customer customer;
    @BeforeEach
    public void setup() {
        customer = new Customer("Test Customer", "Test@mail.com", "Test Street 1", "123456", "TestPassWord");
        customer.setId(1L);
    }

    @Test
    public void getCustomerByIdReturnsCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Customer result = customerService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals("Test Customer", result.getName());

        verify(customerRepository).findById(1L);
    }

    @Test
    void updateCustomer_ShouldUpdateCustomer_WithNewPassword() {
        Customer updatedData = new Customer("TestTest Customer", "TestTest@mail.com", "TestTest Street 1", "123456", "TestPassWord");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(passwordEncoder.encode("TestPassWord")).thenReturn("TestTestPassWord");
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Customer result = customerService.updateCustomer(1L, updatedData);

        assertEquals("TestTest Customer", result.getName());
        assertEquals("TestTestPassWord", result.getPassword());

        verify(customerRepository).save(any(Customer.class));
    }
}
