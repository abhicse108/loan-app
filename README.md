# Loan Application Evaluation Service

This project is a Spring Boot REST API that evaluates loan applications and determines whether a loan offer can be approved based on predefined business rules.

---

## Tech Stack

- Java 17  
- Spring Boot 4.0.4 
- Spring Web  
- Spring Data JPA  
- H2 Database  
- Lombok  
- JUnit 5  

---

## How to Run

1. Clone the repository  
2. Navigate to project directory  
3. Run:

   mvn spring-boot:run  

OR run `LoanAppApplication.java` from your IDE  

Application will start on:  
http://localhost:8080  

---

## API Endpoint

### Create Loan Application

POST /applications  

### Sample Request

```json
{
  "applicant": {
    "name": "Abhishek Singh",
    "age": 27,
    "monthlyIncome": 75000,
    "employmentType": "SALARIED",
    "creditScore": 720
  },
  "loan": {
    "amount": 500000,
    "tenureMonths": 36,
    "purpose": "PERSONAL"
  }
}
