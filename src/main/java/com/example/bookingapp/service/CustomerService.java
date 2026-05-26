package com.example.bookingapp.service;

import com.example.bookingapp.exception.ActiveBookingException;
import com.example.bookingapp.exception.EmailExistsException;
import com.example.bookingapp.model.Booking;
import com.example.bookingapp.model.Customer;
import com.example.bookingapp.repository.BookingRepository;
import com.example.bookingapp.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, BookingRepository bookingRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
        this.passwordEncoder = passwordEncoder;
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
                passwordEncoder.encode(customer.getPassword()));

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

    public void deleteCustomer(Long id) {
        boolean hasActiveBooking = bookingRepository.existsByCustomeridAndStatus(id, Booking.BookingStatus.ACTIVE);

        if(hasActiveBooking){
            throw new ActiveBookingException("Cannot delete a customer with an active booking");
        }

        customerRepository.deleteById(id);
    }

    public Optional<Customer> loginCustomer(String email, String password) {

        if (email == null || email.isBlank()
                || password == null || password.isBlank()) {
            return Optional.empty();
        }

        return customerRepository.findByEmail(email)
                .filter(customer -> passwordEncoder.matches(password,customer.getPassword()));
    }
}
