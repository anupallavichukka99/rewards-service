# Rewards Service

A Spring Boot application that calculates customer reward points based on transaction history and provides monthly and overall transaction summaries.

## Features

- Retrieve customer rewards by customer ID
- Calculate reward points based on transaction amount
- Generate month-wise rewards
- Generate total reward points for each customer
- Global exception handling
- Unit tests for Service, and Repository layers
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

**1. Get Rewards for All Customers over 3 month period of time**
GET /api/rewards
**Description**
Returns reward details for all customers based on transactions from the last three months.

**2. Get Month-wise Rewards for All Customers**
GET /api/monthWiseRewards?date={yyyy-MM}
Example Request - GET http://localhost:8080/api/monthWiseRewards?date=2026-05

**Description**
Returns reward details for all customers for the specified month.

**Validation**
Date must be in yyyy-MM format.
Example: 2026-01, 2026-12

**3. Get Rewards by Customer Id**
  GET /api/customerId/{id}

  **Description**
  Returns reward details for a specific customer.

## Project Structure
├── src/main/java
│   └── com.assign.Rewards
│       │
│       ├── RewardsApplication.java
│       │
│       ├── Controller
│       │   └── RewardsController.java
│       │
│       ├── Entity
│       │   ├── Customer.java
│       │   └── CustomerTransactions.java
│       │
│       ├── GlobalExceptionHandler
│       │   ├── CustomerNotFound.java
│       │   ├── TransactionsNotFound.java
│       │   └── GlobalExceptionHandler.java
│       │
│       ├── Model
│       │   ├── ErrorResponse.java
│       │   └── MonthlyRewards.java
│       │
│       ├── Repository
│       │   ├── CustomerRepository.java
│       │   └── CustomerTransactionRepository.java
│       │
│       ├── Response
│       │   ├── MonthlyRewardResponse.java
│       │   └── RewardsResponse.java
│       │
│       └── Service
│           ├── RewardService.java
│           └── RewardsServiceImpl.java
│
├── src/main/resources
│   ├── application.properties
│   ├── data.sql
│   ├── static/
│   └── templates/
│
└── src/test/java
    └── com.assign.Rewards
        ├── RewardsApplicationUnitTestCases.java
        ├── RewardsIntegrationTest.java
        └── RewardsRepositoryTest.java


## Author

Anupallavi Chukka
