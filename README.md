# 🚚 Dispatch Load Balancer

A Spring Boot based **Load Balancer (Dispatch System)** that assigns orders to vehicles based on load capacity and optimizes route distances using **Google Maps Directions API**.  

Database used: **H2 (in-memory)**  
API Documentation: **Swagger (Springdoc OpenAPI)**  

---

## 📌 Features
- Assign orders to vehicles based on load capacity.  
- Unassignable orders are tracked separately.  
- Optimized distance calculation using **Google Maps Directions API**.  
- REST APIs with **Swagger UI** integration.  
- H2 in-memory database with console access.  

---

## 🛠️ Tech Stack
- **Java 17**  
- **Spring Boot 3**  
- **Spring Data JPA**  
- **H2 Database**  
- **Swagger / OpenAPI**  
- **Google Maps Directions API**  

---

## ⚙️ Setup Instructions

### 1. Clone Repository
```bash
git clone https://github.com/yourusername/loadbalancer.git
cd loadbalancer
2. Configure Database (H2 In-Memory)
The project uses H2 in-memory DB.
Console available at: http://localhost:8080/h2-console

Default Config (application.properties):

properties
Copy
Edit
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
3. Run Application
bash
Copy
Edit
./mvnw spring-boot:run
App runs on: http://localhost:8080



📡 REST APIs
1. Assign Orders to Vehicles
POST /api/plan

📥 Request Body:

json
Copy
Edit
{
  "vehicles": [
    { "vehicleId": "V1", "capacity": 100 },
    { "vehicleId": "V2", "capacity": 80 }
  ],
  "orders": [
    { "orderId": "O1", "load": 50, "destination": "Bangalore" },
    { "orderId": "O2", "load": 60, "destination": "Chennai" },
    { "orderId": "O3", "load": 90, "destination": "Hyderabad" }
  ]
}
📤 Response:

json
Copy
Edit
[
  {
    "vehicleId": "V1",
    "totalLoad": 100,
    "totalDistance": "120 km",
    "assignedOrders": [
      { "orderId": "O1", "load": 50, "destination": "Bangalore" },
      { "orderId": "O2", "load": 50, "destination": "Chennai" }
    ]
  },
  {
    "vehicleId": "V2",
    "totalLoad": 80,
    "totalDistance": "300 km",
    "assignedOrders": [
      { "orderId": "O3", "load": 80, "destination": "Hyderabad" }
    ]
  }
]
2. Get All Plans
GET /api/plans

📤 Response:

json
Copy
Edit
[
  {
    "vehicleId": "V1",
    "totalLoad": 100,
    "totalDistance": "120 km",
    "assignedOrders": [...]
  },
  {
    "vehicleId": "V2",
    "totalLoad": 80,
    "totalDistance": "300 km",
    "assignedOrders": [...]
  }
]
3. Get H2 Database Console
GET /h2-console

Use JDBC URL: jdbc:h2:mem:testdb

📊 ERD Diagram
Below is the Entity Relationship Diagram (ERD) for the project:

mermaid
Copy
Edit
erDiagram
    VEHICLE {
        string vehicleId PK
        int capacity
    }
    ORDER {
        string orderId PK
        int load
        string destination
        string status
    }
    PLAN {
        string planId PK
        string vehicleId FK
        int totalLoad
        string totalDistance
    }

    VEHICLE ||--o{ PLAN : has
    PLAN ||--o{ ORDER : assigns
🚀 Future Improvements
Real-time vehicle tracking.

Dynamic load balancing with AI/ML.

Integration with external fleet management systems.

