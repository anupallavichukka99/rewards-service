# Rewards Service

A Spring Boot application that calculates customer reward points based on transaction history and provides monthly and overall transaction summaries.

## Features

- Retrieve customer transactions by customer ID
- Calculate reward points based on transaction amount
- Generate month-wise transactions
- Generate total reward points for each customer
- Global exception handling
- Unit tests for Controller, Service, and Repository layers
- Integration testing for API and service layer validation

## Reward Calculation Rules

- No reward points for transactions up to $50
- 1 reward point for every dollar spent over $50
- 2 reward points for every dollar spent over $100
- Example:
  - Transaction Amount = $120
  - Points = 50 + (20 × 2) = 90

## Technology Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database
- Maven
- JUnit 5
- Mockito

## API Endpoints

### Get Transactions by Customer ID
GET api/customerId/{id}

### Get Monthly Transactions
GET api/monthly/{date}

Example:
GET api/monthly/2026-05

### Get Customer Rewards Summary over 3 months time period
GET api/rewards

## Project Structure
   src
   ├── main
   │   ├── java
   │   │   └── com.assign.Rewards
   │   │       ├── Controller
   │   │       ├── Service
   │   │       ├── Repository
   │   │       ├── Model
   │   │       ├── Response
   │   │       ├── GlobalExceptionHandler
   │   │       └── RewardsApplication
   │   └── resources
   │       └── application.properties
   └── test
       └── java
           └── com.assign.Rewards
               ├── RewardsApplicationUnitTestCases
               ├── RewardsControllerTest
               ├── RewardsIntegrationTest
               └── RewardsRepositoryTest


## Author

Anupallavi Chukka
