# Smart-Campus Sensor Management API

A robust, enterprise-grade RESTful API built using Java and JAX-RS. This system manages a network of rooms, sensors, and their corresponding environmental readings, adhering to modern REST principles and HATEOAS.

---

## 🚀 API Design Overview

### Architecture

- **Resource-Oriented Architecture (ROA)** — Every entity (Room, Sensor, Reading) is treated as a unique resource with a stable URI.
  
- **Sub-Resource Locator Pattern** — Navigational paths like `/sensors/{id}/readings` are delegated to specialized resource classes to maintain modularity.
  
- **In-Memory Persistence** — Utilizes a thread-safe `MockDatabase` with `ConcurrentHashMap` to ensure data integrity during concurrent request cycles.

### Key Features

- **Minimal Responses** — Following industry best practices, `POST` requests return a `201 Created` status with a minimal JSON body containing only the resource `id`.

- **HATEOAS Compliance** — Successful creation requests include a `Location` header in the response, providing the direct URL to the newly created resource.

- **Advanced Filtering** — The sensors collection supports dynamic filtering via query parameters (e.g., `?type=CO2`).

- **Comprehensive Error Handling** — Custom `ExceptionMappers` ensure all errors (`404`, `409`, `422`, etc.) return consistent, developer-friendly JSON payloads.

---

## 🛠️ Building and Launching

### Prerequisites

- **Java Development Kit (JDK) 8 or higher**
- **Apache Maven** (or your preferred IDE built-in build tool)
- **Apache Tomcat 9.0+** (or GlassFish)

### Step-by-Step Instructions

**1. Clone the Project**

```bash
git clone https://github.com/Shavin30/SmartCampus.git
cd smart-campus
```

**2. Build the WAR File**

Use Maven to compile the code and package it for deployment:

```bash
mvn clean package
```

This will generate a `.war` file in the `target/` directory.

**3. Deploy to Server**

- Copy the generated `.war` file (e.g., `w2119859_cw.war`) into the `webapps` folder of your Tomcat installation.
- Alternatively, in NetBeans/IntelliJ, right-click the project and select **Run** to automatically deploy to the configured server.

**4. Verify Deployment**

Open your browser and navigate to the base URL:

```
http://localhost:8080/w2119859_cw/api/v1/rooms
```

> ✅ If you see a JSON list of default rooms, the server is running correctly.

---

## 📡 Sample cURL Commands

Demonstrate the API's capabilities using the commands below.

### 1. Create a New Room (Minimal Response)

```bash
curl -i -X POST http://localhost:8080/w2119859_cw/api/v1/rooms -H "Content-Type: application/json" -d "{\"id\": \"LAB-404\", \"name\": \"Deep Learning Lab\", \"capacity\": 30}"
```

> Check the `-v` output for the `201 Created` status and `Location` header.

---

### 2. Register a Sensor to a Room

```bash
curl -i -X POST http://localhost:8080/w2119859_cw/api/v1/rooms -H "Content-Type: application/json" -d "{\"id\": \"LAB-404\", \"name\": \"Deep Learning Lab\", \"capacity\": 30}"
```

---

### 3. Filter Sensors by Type

```bash
curl -X GET "http://localhost:8080/w2119859_cw/api/v1/sensors?type=Temperature"
```

---

### 4. Submit a Reading (Nested Resource)

```bash
curl -i -X POST http://localhost:8080/w2119859_cw/api/v1/sensors/TEMP-99/readings -H "Content-Type: application/json" -d "{\"id\": \"READ-01\", \"timestamp\": 1713600000, \"value\": 22.5}"
```

---

### 5. Safe Room Deletion

```bash
curl -i -X DELETE http://localhost:8080/w2119859_cw/api/v1/rooms/LAB-404
```

> ⚠️ If the room has active sensors, this will return a `409 Conflict` error with a JSON explanation.

---

## 📝 Author

**Shavin De Silva**  
20240198/ w2119859
