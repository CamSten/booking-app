package com.example.bookingapp.service;

import com.example.bookingapp.config.RestTemplateConfig;
import com.example.bookingapp.exception.ActiveBookingException;
import com.example.bookingapp.exception.EmailExistsException;
import com.example.bookingapp.model.Customer;
import com.example.bookingapp.repository.CustomerRepository;
import org.hibernate.sql.ast.tree.from.TableReference;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/*
 * To do:
 *  -  deleteCustomer(): API request for validating customer having no active bookings,
 *  -  adjust method: boolean hasActiveBookings()
 *  -  adjust delete procedure: set customer as inactive, without deleting from database?
 * */

@Service
public class CustomerService {
    private final RestTemplate restTemplate;
    private final CustomerRepository customerRepository;
    //    private final BookingRepository bookingRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, RestTemplateConfig restTemplateConfig) {
//         public CustomerService(CustomerRepository customerRepository, BookingRepository bookingRepository, PasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplateConfig.restTemplate();
        this.customerRepository = customerRepository;
//        this.bookingRepository = bookingRepository;
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
        /*
        in BookingController :
        * public List<Booking> getBookingsByCustomerId(@PathVariable Long customerid) {
        return bookingService.getBookingsByCustomerId(customerid);
    }

        * */
    }

    public Optional<Customer> loginCustomer(String email, String password) {

        if (email == null || email.isBlank()
                || password == null || password.isBlank()) {
            return Optional.empty();
        }

        return customerRepository.findByEmail(email)
                .filter(customer -> passwordEncoder.matches(password, customer.getPassword()));
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


    //Perhaps adjust: the customer has to be valid, but should also be the one who is currently logged in
    public boolean validateCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        return customer != null && customer.getStatus() == Customer.CustomerStatus.ACTIVE;
    }

    public boolean validateAuthorizedCustomer(Long customerId, Long loggedInCustomerId){
        return customerId != null && customerId.equals(loggedInCustomerId);
    }
}
