# 🚚 Load Balancer - Dispatch Planning System

## 📌 Project Overview
The **Load Balancer** project is a Spring Boot application that simulates an **order dispatch planning system**.  
It assigns customer orders to available vehicles based on capacity, distance, and optimization rules.  
If some orders cannot be assigned, they are returned as **unassignable orders**.

This project demonstrates:
- Vehicle & Order management
- Dispatch planning algorithm
- Google Maps Directions API integration (for distance calculation)
- REST API with Swagger Documentation
- In-memory H2 Database

---

## 🛠️ Tech Stack
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **H2 Database (in-memory)**
- **Springdoc OpenAPI (Swagger UI)**
- **Lombok**
- **Google Maps Directions API**

---

## 🗄️ ERD (Entity Relationship Diagram)

```mermaid
erDiagram
    VEHICLE {
        String id PK
        String vehicleNumber
        Integer capacity
    }

    ORDERS {
        String id PK
        String pickupLocation
        String dropLocation
        Integer load
        String status
    }

    PLANRESPONSE {
        String vehicleId
        Integer totalLoad
        String totalDistance
    }

    VEHICLE ||--o{ ORDERS : "assigned"
    PLANRESPONSE ||--o{ ORDERS : "assignedOrders"
⚙️ Setup Instructions
1. Clone Repository
bash
Copy
Edit
git clone https://github.com/your-username/loadbalancer.git
cd loadbalancer
2. Run Application
bash
Copy
Edit
./mvnw spring-boot:run
3. Access H2 Database Console
URL: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb

Username: sa

Password: (empty)

4. Access Swagger UI
URL: http://localhost:8080/swagger-ui.html

🔗 REST APIs
Vehicle Management
➕ Add Vehicle
POST /api/vehicles

json
Copy
Edit
{
  "vehicleNumber": "KA01AB1234",
  "capacity": 100
}
📋 Get All Vehicles
GET /api/vehicles

Order Management
➕ Add Order
POST /api/orders

json
Copy
Edit
{
  "pickupLocation": "Bangalore",
  "dropLocation": "Chennai",
  "load": 40
}
📋 Get All Orders
GET /api/orders

Dispatch Planning
🚚 Generate Dispatch Plan
POST /api/plan

json
Copy
Edit
{
  "vehicleIds": ["V1", "V2"],
  "orderIds": ["O1", "O2", "O3"]
}
📦 Sample Response

json
Copy
Edit
{
  "plans": [
    {
      "vehicleId": "V1",
      "totalLoad": 80,
      "totalDistance": "350 km",
      "assignedOrders": [
        {
          "id": "O1",
          "pickupLocation": "Bangalore",
          "dropLocation": "Chennai",
          "load": 40
        }
      ]
    }
  ],
  "unassignedOrders": [
    {
      "id": "O3",
      "pickupLocation": "Hyderabad",
      "dropLocation": "Goa",
      "load": 150
    }
  ]
}
✅ Features
Assigns orders to vehicles based on capacity

Calculates distance using Google Maps Directions API

Returns unassigned orders when no vehicle can accommodate them

Interactive Swagger UI for testing APIs

In-memory DB for quick prototyping

🚀 Future Enhancements
Optimize dispatch plan using advanced algorithms (Hungarian / Linear programming)

Support for real-time GPS tracking

Multi-depot and route-optimization logic

Integration with external logistics systems
