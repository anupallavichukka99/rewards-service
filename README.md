# Rewards Service

A Spring Boot application that calculates customer reward points based on transaction history and provides monthly and overall transaction summaries.

## Features

- Retrieve customer rewards by customer ID. Rewards are calculated based on customer transactions from the previous three complete months.
- Calculate reward points based on transaction amount.
- Generate month-wise rewards
- Generate total reward points for each customer based on customer transactions from the previous three complete months.
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

**Reward Calculation Period**
Rewards are calculated based on customer transactions from the previous three complete months.

The current running month is excluded from reward calculations.
Only transactions that fall within the last three completed calendar months are considered.
Example:
If the application is executed in June 2026, transactions from March 2026, April 2026, and May 2026 are considered.
Transactions from June 2026 are excluded.

**Customer Eligibility**
Only customers with at least one transaction are included in the rewards summary returned by the GET /api/rewards endpoint.
Customers without any transactions are excluded from the response.

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
  Rewards are calculated based on customer transactions from the previous three complete months.



## Testing

The application includes Unit Tests, Integration Tests, and Repository Tests to validate business logic, API behavior, and database interactions.

### Unit Tests (JUnit 5 + Mockito)

Unit tests are implemented for the service layer using Mockito to mock repository dependencies.
Covered scenarios:
* Retrieve rewards for a specific customer
* Retrieve rewards for all customers
* Generate month-wise reward summaries
* Filter transactions within the previous three completed months
* Generate customer reward response
* Reward points calculation logic
* Customer not found exception handling

### Integration Tests (Spring Boot Test + MockMvc)

Integration tests verify the end-to-end behavior of REST APIs.
Covered scenarios:
* Retrieve rewards for all customers
* Retrieve rewards by customer ID
* Retrieve month-wise rewards
* Customer not found exception
* Transactions not found exception
* Request parameter validation
* Constraint violation validation
* Empty result scenarios
* Application context loading

### Repository Tests (@DataJpaTest)

Repository tests validate JPA queries against the H2 in-memory database.
Covered scenarios:
* Retrieve all transactions
* Find customer by ID
* Customer not found scenario
* Find transactions by month and year

### Test Frameworks
* JUnit 5
* Mockito
* Spring Boot Test
* MockMvc
* H2 In-Memory Database


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
│       ├── dto
│       │   ├── ErrorResponse.java
│       │   └── MonthlyRewards.java
│       │   ├── MonthlyRewardResponse.java
│       │   └── RewardsResponse.java
│       │
│       ├── Repository
│       │   ├── CustomerRepository.java
│       │   └── CustomerTransactionRepository.java 
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
