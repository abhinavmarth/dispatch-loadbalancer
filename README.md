# 🚚 Load Balancer - Dispatch Planning System

## 📌 Project Overview
The **Load Balancer** project is a Spring Boot application that simulates an **order dispatch planning system**.  
It assigns customer orders to available vehicles based on capacity, distance, and optimization rules.  
If some orders cannot be assigned, they are returned as **unassignable orders**.

## 🧠 Project Overview

This service allows:
- 📦 Ingesting delivery orders
- 🚗 Ingesting available vehicles
- 🧮 Generating an optimal dispatch plan based on proximity and capacity
- ⚖️ Prioritizing HIGH > MEDIUM > LOW order assignments

---

## 📐 ERD Diagram

```mermaid
erDiagram
    Order {
        Long id
        String orderId
        Double latitude
        Double longitude
        Integer weight
        Enum priority
        String address
    }

    Vehicle {
        Long id
        String vehicleId
        Integer capacity
        Double currLatitude
        Double currLongitude
        String currAddress
    }

    OrdersRequest ||--o{ Order : contains
    VehicleRequest ||--o{ Vehicle : contains
🔌 API Endpoints
Method	Endpoint	Description
POST	/api/dispatch/orders	Submit list of delivery orders
POST	/api/dispatch/vehicles	Submit list of available vehicles
GET	/api/dispatch/plan	Get optimized dispatch plan

📤 Sample Requests
1. POST /api/dispatch/orders
json
Copy code
{
  "orders": [
    {
      "orderId": "O1",
      "latitude": 28.5,
      "longitude": 77.0,
      "packageWeight": 10,
      "priority": "HIGH",
      "address": "Hyderabad"
    }
  ]
}
2. POST /api/dispatch/vehicles
json
Copy code
{
  "vehicles": [
    {
      "vehicleId": "V1",
      "capacity": 50,
      "currentLatitude": 28.6,
      "currentLongitude": 77.3,
      "currentAddress": "Delhi"
    }
  ]
}
3. GET /api/dispatch/plan
json
Copy code
{
  "dispatchPlan": [
    {
      "vehicleId": "V1",
      "totalLoad": 10,
      "totalDistance": "33.2 km",
      "assignedOrders": [
        {
          "orderId": "O1",
          "latitude": 28.5,
          "longitude": 77.0,
          "weight": 10,
          "priority": "HIGH",
          "address": "Hyderabad"
        }
      ]
    }
  ]
}
⚙️ Tech Stack
Java 17+

Spring Boot 3+

Spring Web

Spring Data JPA

H2 / MySQL (JPA Compatible)

Lombok

Validation API (Jakarta)

JUnit 5

Mockito

🧪 Testing
Run unit tests:

bash
Copy code
./mvnw test
All core business logic is covered with unit tests in:

Copy code
LoadBalancerServiceTest.java
🚀 Running the Application
Prerequisites:
JDK 17+

Maven

Steps:
bash
Copy code
# Clone the repository
git clone https://github.com/your-org/freightfox-loadbalancer.git
cd freightfox-loadbalancer

# Build and run the app
./mvnw spring-boot:run
The application will be running at:

arduino
Copy code
http://localhost:8080
📦 Project Structure
Copy code
com.freightfox.loadbalancer
├── controller
├── model
├── repository
├── service
├── GlobalExceptionHandler.java
└── LoadbalancerController.java
🧾 Notes
Validation errors return a structured JSON response.

Orders and Vehicles must have unique IDs (orderId, vehicleId).

Orders are assigned based on proximity and available vehicle capacity.

Unassigned orders are stored internally for future planning (can be extended to show in plan).

