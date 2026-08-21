# Guest House Booking System

This is a project for our course assignment where we built a web application in Java to manage a small guest house. The system is designed to handle customers, rooms, and room bookings.

## Project Overview

The application is built using Java 17 and Spring Boot (version 3.3.5). For data storage, we use a cloud-hosted MySQL database on Aiven, implemented with a code-first approach using Spring Data JPA. The frontend is built with Thymeleaf, HTML, CSS, and JavaScript. 

The codebase is strictly structured into Controller, Service, and Repository layers to keep the business logic organized and separated.

## Core Models

Our system revolves around three main entity classes in the database:
- **Customer:** Stores customer details.
- **Room:** Represents the physical rooms in the guest house.
- **Booking:** Manages the reservation of rooms by customers. We also use a `BookingDTO` for transferring data securely between the frontend and backend.

## Implemented Features

### Customers
- Register a new customer.
- Update customer information.
- Delete a customer account (the system ensures this is only possible if the customer has no active bookings).

### Rooms
- The system manages our rooms which are created and managed in the database.
- Room types include single rooms and double rooms.
- Double rooms have the option to add an extra bed during the booking process.

### Bookings
- Customers can book an available room for one or more nights.
- Existing bookings can be updated or canceled.
- The system includes validation to prevent double-booking a room on the exact same dates.

### Search Functionality
- Users can search for available rooms by specifying a single date or a date range.

## Technical Details
- **Backend:** Java 17, Spring Boot
- **Database:** MySQL (Cloud-hosted via Aiven)
- **Frontend:** Thymeleaf
- **Security:** We use `spring-security-crypto` for password hashing and security.
- **Testing:** We have written 15 unit tests across `BookingServiceTest`, `CustomerServiceTest`, and `RoomServiceTest` to verify the core business logic. This comfortably fulfills the requirement of at least 5 service-layer tests.

## How to Run
1. Clone the repository to your local machine.
2. Open the project in your IDE.
3. Ensure the environment variable `DB_PASSWORD` is set with the correct MySQL database password for our Aiven instance.
4. Run the Spring Boot application.
5. The database tables will be updated automatically via Hibernate, and the application can be accessed via the web browser.
