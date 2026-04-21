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

# Report: Questions and Answers

---

## Question 1
**Explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.**

The JAX-RS specification defines that, by default, a new instance of a resource class is instantiated for every incoming request. The runtime does not treat it as a singleton unless explicitly configured otherwise.

- **Impact on In-Memory Data:** Because a new object is created for each request, class-level instance variables are not persistent across different calls. To prevent data loss, developers must use external storage or static thread-safe data structures (like `ConcurrentHashMap`).
- **Synchronization:** Since the server container (like Tomcat) manages concurrent requests using multiple threads, shared in-memory structures must be synchronized to prevent race conditions, where multiple threads attempt to modify the same data simultaneously.

---

## Question 2
**Why is the provision of "Hypermedia" (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?**

Hypermedia as the Engine of Application State (HATEOAS) is considered a hallmark of advanced REST design because it allows responses to contain links that enable clients to discover available actions and transition between states dynamically.

- **Benefit over Static Documentation:** This approach makes the API self-descriptive. Instead of relying on hard-coded URLs found in static documentation, client developers can follow links provided by the server, making the client more resilient to changes in the API's URL structure.

---

## Question 3
**When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client-side processing.**

- **Network Bandwidth:** Returning only IDs significantly reduces the size of the response payload, leading to reduced network traffic and faster transmission times.
- **Client-Side Processing:** If only IDs are returned, the client must perform additional requests to fetch details for each room, increasing total latency. However, returning full objects allows the client to process all data at once but increases initial load times and bandwidth consumption.

---

## Question 4
**Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.**

In the design, the DELETE operation should be idempotent, meaning that making the same request multiple times must produce the same result on the server.

- **Justification:** If a client sends the same DELETE request multiple times, the first request will successfully delete the resource. Subsequent requests will find that the resource no longer exists; while the HTTP status code might change (e.g., from `204 No Content` to `404 Not Found`), the state of the server remains the same: the resource is gone.

---

## Question 5
**We explicitly use the `@Consumes(MediaType.APPLICATION_JSON)` annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as `text/plain` or `application/xml`. How does JAX-RS handle this mismatch?**

The `@Consumes` annotation specifies exactly which MIME types a resource method can accept.

- **Technical Consequences:** If a client sends data in an unmapped format (like `text/plain`), JAX-RS will detect a mismatch between the request's `Content-Type` header and the method's `@Consumes` requirement.
- **Handling:** JAX-RS handles this by automatically rejecting the request and returning an `HTTP 415 Unsupported Media Type` response to the client, preventing the method logic from executing with incompatible data.

---

## Question 6
**You implemented this filtering using `@QueryParam`. Contrast this with an alternative design where the type is part of the URL path (e.g., `/api/v1/sensors/type/CO2`). Why is the query parameter approach generally considered superior for filtering and searching collections?**

Using `@QueryParam` is superior for filtering because path parameters are intended to identify a specific resource (e.g., `/customers/123`), whereas query parameters are intended to provide additional information for filtering or sorting a collection.

- **Superiority:** Path parameters define the hierarchical identity of the resource. Using query parameters for filtering (e.g., `?type=CO2`) keeps the base URL clean and allows for optional, combinable parameters without creating overly complex, rigid URL paths.

---

## Question 7
**Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., `sensors/{id}/readings/{rid}`) in one massive controller class?**

The Sub-Resource Locator pattern allows a resource class to delegate handling for a specific path segment to another separate class.

- **Architectural Benefits:** This promotes modularity and maintainability. Instead of one "massive" controller handling every possible nested operation, logic is broken into smaller, focused classes. This separation of concerns makes the code easier to navigate, test, and evolve independently.

---

## Question 8
**Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?**

- **HTTP 404:** Indicates that the endpoint/URL itself does not exist on the server.
- **HTTP 422 (Unprocessable Entity):** Is more accurate when the URL is correct and the JSON payload is syntactically valid, but the data is logically erroneous (e.g., a missing reference to a required ID). It signals that the server understands the request but cannot process the specific instructions contained within.

---

## Question 9
**From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?**

- **Information Gathering:** An attacker can gather specific details about the server's internal architecture, including package names, class names, library versions, and potentially even database schema details.
- **Risk:** This information helps attackers identify specific vulnerabilities in the software stack (such as known bugs in a specific version of a library) to launch a more targeted and effective attack.

---

## Question 10
**Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting `Logger.info()` statements inside every single resource method?**

Using filters for cross-cutting concerns like logging is advantageous due to the separation of concerns principle.

- **Efficiency:** Instead of manually inserting code into every method, a single filter can intercept all incoming and outgoing traffic automatically.
- **Maintainability:** Centralizing logging logic in a filter makes the API cleaner and ensures that logging remains consistent across the entire application without cluttering the business logic of individual resource methods.

## 📝 Author

**Shavin De Silva**  
20240198/ w2119859
