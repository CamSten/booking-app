package com.example.customer_service.service;

import com.example.customer_service.model.Customer;
import com.example.customer_service.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        customer = new Customer("Test Customer", "Test@mail.com", "Test Street 1", "123456", "TestPassWord", Customer.CustomerStatus.ACTIVE);
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
        Customer updatedData = new Customer("TestTest Customer", "TestTest@mail.com", "TestTest Street 1", "123456", "TestPassWord", Customer.CustomerStatus.ACTIVE);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(passwordEncoder.encode("TestPassWord")).thenReturn("TestTestPassWord");
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Customer result = customerService.updateCustomer(1L, updatedData);

        assertEquals("TestTest Customer", result.getName());
        assertEquals("TestTestPassWord", result.getPassword());

        verify(customerRepository).save(any(Customer.class));
    }
}
