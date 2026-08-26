package com.example.customer_service.service;

import com.example.customer_service.config.RestTemplateConfig;
import com.example.customer_service.exception.ActiveBookingException;
import com.example.customer_service.exception.EmailExistsException;
import com.example.customer_service.model.*;
import com.example.customer_service.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;
import java.util.List;

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

    public ResponseEntity<CustomerResponseDTO> loginRequestIsValid(LoginRequestDTO requestDTO){
        return loginCustomer(requestDTO.getEmail(), requestDTO.getPassword());
    }

    public ResponseEntity<CustomerResponseDTO> signupRequestIsValid(CustomerDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().isBlank()){
            return returnInvalid(Feedback.EMPTY_EMAIL);
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()){
            return returnInvalid(Feedback.EMPTY_PASSWORD);
        }
        return createCustomer(dto);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public ResponseEntity<CustomerResponseDTO> createCustomer(CustomerDTO customerDTO) {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customerDTO.getEmail());

        if (existingCustomer.isPresent()) {
            return returnInvalid(Feedback.USER_EXISTS);
        }

        Customer newCustomer = (customerRepository.save(new Customer(
                customerDTO.getName(),
                customerDTO.getEmail(),
                customerDTO.getAddress(),
                customerDTO.getPhone(),
                passwordEncoder.encode(customerDTO.getPassword()),
                Customer.CustomerStatus.ACTIVE)));
        return ResponseEntity.status(HttpStatus.OK).body(createResponseDTOFromCustomer(newCustomer));
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

    public ResponseEntity<CustomerResponseDTO> deleteCustomer(Long customerId) throws Exception {
        boolean hasActiveBooking = hasActiveBooking(customerId);  /* bookingRepository.existsByCustomeridAndStatus(id, Booking.BookingStatus.ACTIVE); */
        if (hasActiveBooking){
            return returnInvalid(Feedback.HAS_ACTIVE_BOOKINGS);
//            throw new ActiveBookingException("Cannot delete a customer with an active booking");
        }
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            customer.setStatus(Customer.CustomerStatus.UNREGISTERED);
            customerRepository.save(customer);
            ResponseEntity.status(HttpStatus.OK).body(new CustomerResponseDTO(Feedback.OK));
        }
        return returnInvalid(Feedback.INVALID_USER);
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

    public ResponseEntity<CustomerResponseDTO> loginCustomer(String email, String password) {
        if (email == null || email.isBlank() ) {
            return returnInvalid(Feedback.EMPTY_EMAIL);
        }
        else if (password == null || password.isBlank()){
            return returnInvalid(Feedback.EMPTY_PASSWORD);
        }
        Customer savedCustomer = customerRepository.findByEmail(email).stream().findAny().orElse(null);
        if (savedCustomer != null) {
            savedCustomer = customerRepository.findByEmail(email).stream().filter(customer ->
                    passwordEncoder.matches(password, customer.getPassword())).findAny().orElse(null);
            if (savedCustomer != null) {
                CustomerResponseDTO responseDTO = new CustomerResponseDTO(savedCustomer.getId(), savedCustomer.getName(), savedCustomer.getEmail(), savedCustomer.getAddress(), savedCustomer.getPhone(), Feedback.OK);
                return ResponseEntity.status(HttpStatus.OK).body((responseDTO));
            }
            else {
                return returnInvalid(Feedback.INVALID_PASSWORD);
            }
        }
        return returnInvalid(Feedback.INVALID_EMAIL);
    }

    public boolean validateCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        return customer != null && customer.getStatus() == Customer.CustomerStatus.ACTIVE;
    }

    public boolean validateAuthorizedCustomer(Long customerId, Long loggedInCustomerId){
        return customerId != null && customerId.equals(loggedInCustomerId);
    }

    private CustomerResponseDTO createResponseDTOFromCustomer(Customer customer){
        return new CustomerResponseDTO(customer.getId(), customer.getName(), customer.getEmail(), customer.getAddress(), customer.getPhone(), Feedback.OK);
    }

    private CustomerResponseDTO validResponse(CustomerDTO customer){
        return new CustomerResponseDTO(customer.getId(), customer.getName(), customer.getEmail(), customer.getAddress(), customer.getPhone(), Feedback.OK);
    }

    private ResponseEntity<CustomerResponseDTO> returnInvalid(Feedback feedback){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomerResponseDTO(feedback));
    }
}
