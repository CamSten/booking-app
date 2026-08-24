package com.example.customer_service.service;

import com.example.customer_service.config.RestTemplateConfig;
import com.example.customer_service.exception.ActiveBookingException;
import com.example.customer_service.exception.EmailExistsException;
import com.example.customer_service.model.Customer;
import com.example.customer_service.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    //private final BookingRepository bookingRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(RestTemplateConfig restTemplateConfig, CustomerRepository customerRepository, /*BookingRepository bookingRepository,*/ PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        //this.bookingRepository = bookingRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplateConfig.restTemplate();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customer.getEmail());

        if (existingCustomer.isPresent()) {
            throw new EmailExistsException("Email already exists");
        }

        Customer newCustomer = new Customer(
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhone(),
                passwordEncoder.encode(customer.getPassword()),
                Customer.CustomerStatus.ACTIVE);

        return customerRepository.save(newCustomer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = customerRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        Optional<Customer> emailOwner = customerRepository.findByEmail(customer.getEmail());

        if (emailOwner.isPresent() && emailOwner.get().getId() != id) {
            throw new EmailExistsException("Email already exists");
        }

        existing.setName(customer.getName());
        existing.setEmail(customer.getEmail());
        existing.setAddress(customer.getAddress());
        existing.setPhone(customer.getPhone());
        if (customer.getPassword() != null && !customer.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(customer.getPassword()));
        }

        return customerRepository.save(existing);
    }

    public void deleteCustomer(Long customerId) {
        try {
            boolean hasActiveBooking = hasActiveBooking(customerId);  /* bookingRepository.existsByCustomeridAndStatus(id, Booking.BookingStatus.ACTIVE); */
            if (hasActiveBooking){
                throw new ActiveBookingException("Cannot delete a customer with an active booking");
            }
            customerRepository.deleteById(customerId);

        }
        catch (Exception e){
            // something has gone wrong in retrieval, handle error code, return feedback
        }
    }

    public boolean hasActiveBooking(Long customerId) throws Exception {
        Boolean result = restTemplate.getForObject("http://localhost:8080/bookings/customer/" + customerId
                + "/has-active-booking", Boolean.class
        );

        if (result != null){
            return result;
        }
        // flesh out, specify
        throw new Exception();
    }

    public Optional<Customer> loginCustomer(String email, String password) {

        if (email == null || email.isBlank()
                || password == null || password.isBlank()) {
            return Optional.empty();
        }

        return customerRepository.findByEmail(email)
                .filter(customer -> passwordEncoder.matches(password,customer.getPassword()));
    }

    public boolean validateCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        return customer != null && customer.getStatus() == Customer.CustomerStatus.ACTIVE;
    }

    public boolean validateAuthorizedCustomer(Long customerId, Long loggedInCustomerId){
        return customerId != null && customerId.equals(loggedInCustomerId);
    }
}
