# 🚚 Load Balancer – Dispatch Planning System

This project is a **Load Balancer for Freight Dispatching**, built with **Spring Boot**.  
It optimally assigns **orders to vehicles** based on **capacity, distance, and load constraints**, while also handling **unassignable orders**.  
The system integrates with **Google Maps Directions API** for distance calculation and exposes REST APIs with **Swagger UI** documentation.

---

## ✨ Features
- Vehicle and Order management
- Load balancing logic:
  - Assigns orders to vehicles based on available capacity
  - Calculates total load & travel distance per vehicle
  - Handles **unassignable orders**
- RESTful APIs with **Swagger UI**
- In-memory **H2 database** with console support
- Modular, testable architecture with JUnit & Mockito

---

## 🛠️ Tech Stack
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA (Hibernate)**
- **H2 Database (in-memory)**
- **SpringDoc OpenAPI (Swagger UI)**
- **Google Maps Directions API** (for distance calculation)
- **JUnit 5 & Mockito** (for testing)

---

## 📂 Project Structure
loadbalancer/
├── src/main/java/com/freightfox/loadbalancer/
│ ├── controller/ # REST controllers
│ ├── service/ # Business logic
│ ├── repository/ # Spring Data JPA Repositories
│ ├── model/ # Entities & DTOs (Vehicle, Order, PlanResponse)
│ └── LoadBalancerApplication.java
├── src/test/java/com/freightfox/loadbalancer/ # Unit & Integration tests
├── src/main/resources/
│ ├── application.properties
│ └── data.sql (optional test data)
└── README.md

yaml
Copy
Edit

---

## 🗄️ Database (H2)
The project uses **in-memory H2 DB**.

**Config:** (from `application.properties`)
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
👉 Access H2 Console at:
http://localhost:8080/h2-console
Use JDBC URL: jdbc:h2:mem:testdb

📖 API Documentation (Swagger)
Swagger UI is enabled via SpringDoc.

API Docs (JSON): http://localhost:8080/v3/api-docs

Swagger UI: http://localhost:8080/swagger-ui.html

📊 ERD Diagram
Below is a simplified Entity Relationship Diagram (ERD):

mermaid
Copy
Edit
erDiagram
    VEHICLE {
        String vehicle_id PK
        Integer capacity
    }

    ORDERS {
        String order_id PK
        Integer weight
        String destination
    }

    PLAN_RESPONSE {
        String vehicle_id FK
        Integer total_load
        String total_distance
    }

    VEHICLE ||--o{ PLAN_RESPONSE : has
    ORDERS ||--o{ PLAN_RESPONSE : assigned
▶️ How to Run
1. Clone the repo
bash
Copy
Edit
git clone https://github.com/your-username/loadbalancer.git
cd loadbalancer
2. Build & Run (Maven)
bash
Copy
Edit
mvn spring-boot:run
3. Access APIs
Swagger UI → http://localhost:8080/swagger-ui.html

H2 Console → http://localhost:8080/h2-console

✅ Example API Responses
Assign Orders API Response
json
Copy
Edit
{
  "vehicleId": "V1",
  "totalLoad": 200,
  "totalDistance": "25 km",
  "assignedOrders": [
    { "orderId": "O1", "weight": 100, "destination": "Bangalore" },
    { "orderId": "O2", "weight": 100, "destination": "Mysore" }
  ]
}
Unassignable Orders
json
Copy
Edit
{
  "unassignedOrders": [
    { "orderId": "O5", "weight": 400, "destination": "Chennai" }
  ]
}
📌 Future Enhancements
Integrate real-time traffic data with Google Maps API

Support for multiple dispatch strategies (Greedy, Round Robin, Cost-based)

Vehicle route optimization

yaml
Copy
Edit

---

👉 You can copy this directly into your `README.md`.  

Do you also want me to add **example API endpoints list** (like `/api/orders`, `/api/vehicles`, `/api/plan`) to
