package com.example.bookingapp.service;

import com.example.bookingapp.exception.ActiveBookingException;
import com.example.bookingapp.model.Booking;
import com.example.bookingapp.model.Customer;
import com.example.bookingapp.repository.BookingRepository;
import com.example.bookingapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final BookingRepository bookingRepository;

    public CustomerService(CustomerRepository customerRepository, BookingRepository bookingRepository) {
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        Customer newCustomer = new Customer(customer.getName(),customer.getEmail(),customer.getAddress(),customer.getPhone(),customer.getPassword());
        return customerRepository.save(newCustomer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        Customer updatedCustomer = customerRepository.findById(id).orElse(null);

        if(updatedCustomer != null){
            updatedCustomer.setName(customer.getName());
            updatedCustomer.setEmail(customer.getEmail());
            updatedCustomer.setAddress(customer.getAddress());
            updatedCustomer.setPhone(customer.getPhone());
            updatedCustomer.setPassword(customer.getPassword());
            return customerRepository.save(updatedCustomer);
        } else {
            return null;
        }
    }

    public void deleteCustomer(Long id) {
        boolean hasActiveBooking = bookingRepository.existsByCustomeridAndStatus(id, Booking.BookingStatus.ACTIVE);

        if(hasActiveBooking){
            throw new ActiveBookingException("Cannot delete a customer with an active booking");
        }
        customerRepository.deleteById(id);
    }
}
