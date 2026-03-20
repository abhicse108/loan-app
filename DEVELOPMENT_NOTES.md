# Development Notes

## Overall Approach

The service was designed using a layered architecture to keep responsibilities clearly separated. The flow starts from the controller, which handles incoming requests, and delegates processing to the service layer. The service layer orchestrates the business logic by interacting with smaller, focused components such as risk evaluation, interest calculation, EMI calculation, and eligibility checks.

The goal was to keep the implementation simple, readable, and aligned with real-world backend practices without over-engineering the solution.

---

## Key Design Decisions

- **Layered Architecture**  
  The application is structured into controller, service, domain, and utility layers to ensure separation of concerns and maintainability.

- **DTO-based API Design**  
  Request and response objects are handled via DTOs to avoid exposing internal models and to keep the API contract clean.

- **Use of BigDecimal for Financial Calculations**  
  BigDecimal was used instead of floating-point types to ensure precision and avoid rounding errors in EMI and interest calculations.

- **Separation of Business Logic**  
  Core logic was split into smaller services:
    - RiskService (risk classification)
    - InterestService (interest calculation)
    - EligibilityService (business rule validation)
    - EmiCalculator (pure calculation logic)  
      This makes the code easier to test and maintain.

- **Validation Strategy**  
  Basic validations are handled using annotations, while business rules (like credit score, EMI thresholds) are implemented in the service layer.

- **Global Exception Handling**  
  A centralized exception handler was added to improve API responses for invalid inputs (e.g., invalid enum values).

---

## Trade-offs Considered

- **Single Service vs Multiple Services**  
  While all logic could have been placed in a single service, it was split into smaller services for better readability and testability. A full rule engine (like Drools) was avoided as it would be overkill for this scope.

- **Null Field Handling in Response**  
  Null fields are omitted using `JsonInclude.NON_NULL` to keep responses clean. However, in strict contract-based systems, returning a fixed schema with null values might be preferred.

- **No Database Persistence**  
  Although a repository layer is present, full persistence logic was not implemented to keep the focus on business rules.

---

## Assumptions Made

- Base interest rate is fixed at 12% and not configurable.
- EMI is calculated using standard reducing balance formula.
- Only a single loan offer is generated based on requested tenure.
- No external integrations (e.g., credit bureau, KYC systems) are considered.
- Input data is assumed to be provided in correct format apart from validation constraints.

---

## Improvements with More Time

- Externalize configuration (interest rates, thresholds) using config or database.
- Add Swagger/OpenAPI documentation for better API usability.
- Implement persistent storage for applications and decisions.
- Add more comprehensive test coverage, including integration tests.
- Improve error responses with more granular field-level validation messages.
- Add logging and monitoring for better observability.

---