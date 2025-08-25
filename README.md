# 🚚 Dispatch Load Balancer

A Spring Boot based **Load Balancer** system designed for **vehicle dispatching and load optimization**.  
This project assigns customer orders to vehicles based on **capacity, load balancing strategy, and distance optimization (Google Maps Directions API)**.  

It also provides REST APIs for creating orders, assigning them to vehicles, and retrieving dispatch plans.  

---

## ✨ Features

- 🚛 Assigns orders to available vehicles based on load capacity  
- 📦 Handles **unassignable orders** gracefully  
- 🗺️ **Google Maps Directions API** integration for distance calculation  
- 📊 **H2 Database** for testing and in-memory persistence  
- 📖 REST APIs documented with **Swagger**  
- 🛠️ Easily extensible for real-world logistics/dispatching systems  

---

## 🏗️ Project Structure

loadbalancer/
├── src/main/java/com/freightfox/loadbalancer/
│ ├── controller/ # REST controllers
│ ├── service/ # Business logic
│ ├── repository/ # Data access layer
│ ├── model/ # Entity & DTO models
│ └── config/ # Swagger, API, and other configurations
├── src/test/java/... # Unit & integration tests
├── resources/
│ ├── application.properties
│ └── data.sql # Initial test data (optional)
└── README.md

yaml
Copy
Edit

---

## ⚙️ Tech Stack

- **Java 17**  
- **Spring Boot 3.x**  
- **Spring Data JPA (Hibernate)**  
- **H2 Database**  
- **Swagger (Springdoc OpenAPI)**  
- **Google Maps Directions API**  

---

## 🔧 Setup & Run

1. **Clone the repo**  
   ```bash
   git clone https://github.com/your-username/loadbalancer.git
   cd loadbalancer
Configure Google Maps API Key
Add your API key in application.properties:

properties
Copy
Edit
google.maps.api.key=YOUR_API_KEY
Run the application

bash
Copy
Edit
mvn spring-boot:run
Access the application

Swagger UI → http://localhost:8080/swagger-ui.html

H2 Console → http://localhost:8080/h2-console

🛢️ Database (H2)
Default H2 in-memory database is used.

Connection Details:

URL: jdbc:h2:mem:testdb

Username: sa

Password: (empty)

Console: http://localhost:8080/h2-console

📖 REST APIs
1️⃣ Orders API
Create Order
POST /api/orders

json
Copy
Edit
{
  "orderId": "ORD001",
  "weight": 50,
  "pickupLocation": "Hyderabad",
  "dropLocation": "Bangalore"
}
Get All Orders
GET /api/orders

2️⃣ Vehicles API
Create Vehicle
POST /api/vehicles

json
Copy
Edit
{
  "vehicleId": "VEH001",
  "capacity": 200
}
Get All Vehicles
GET /api/vehicles

3️⃣ Dispatch API
Generate Dispatch Plan
POST /api/dispatch/plan

json
Copy
Edit
{
  "strategy": "BALANCED"   // Options: BALANCED, CAPACITY_FIRST, DISTANCE_FIRST
}
Response Example

json
Copy
Edit
[
  {
    "vehicleId": "VEH001",
    "totalLoad": 150,
    "totalDistance": "450 km",
    "assignedOrders": [
      { "orderId": "ORD001", "weight": 50 },
      { "orderId": "ORD002", "weight": 100 }
    ]
  },
  {
    "vehicleId": "UNASSIGNED",
    "totalLoad": 0,
    "totalDistance": "0 km",
    "assignedOrders": [
      { "orderId": "ORD005", "weight": 400 }
    ]
  }
]
📊 ERD (Entity Relationship Diagram)
mermaid
Copy
Edit
erDiagram
    VEHICLE ||--o{ ORDER : "assigned"
    VEHICLE {
        string vehicleId PK
        int capacity
    }
    ORDER {
        string orderId PK
        int weight
        string pickupLocation
        string dropLocation
    }
    PLAN {
        string vehicleId FK
        int totalLoad
        string totalDistance
    }
🧪 Running Tests
Run unit & integration tests:

bash
Copy
Edit
mvn test
