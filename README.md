# Spring Boot 3 & Java - Building a Complete REST API

This repository contains the source code developed during the DevDojo intensive Spring Boot - Direto das Trincheiras course. The project involves creating a REST API for managing animes and producers, covering everything from the initial environment setup to deployment, with a strong focus on best practices, clean architecture, and robust testing.

## 🎯 Project Goal

The main objective is to apply and deepen knowledge within the Spring ecosystem to build a scalable, secure, and maintainable back-end application, following industry standards. This project serves as a practical portfolio of the skills acquired in back-end development with Java.

---

## 🛠️ Technologies and Tools

The project was built using the following technology stack:

* **Language:** Java 17+
* **Framework:** Spring Boot 3
* **Dependency Manager:** Maven
* **Database:**
    * MySQL (for production/development)
    * H2 (for testing)
* **Object-Relational Mapping (ORM):** Spring Data JPA (Hibernate)
* **Object Mapping (DTO):** MapStruct
* **Containers:** Docker and Docker Compose
* **Testing:**
    * JUnit 5
    * Mockito
    * AssertJ
    * Testcontainers
    * REST Assured
    * WireMock
* **API Documentation:** SpringDoc (OpenAPI 3 / Swagger)
* **Security:** Spring Security (role-based authentication and authorization)
* **Code Quality:** Checkstyle
* **Database Migration:** Flyway
* **CI/CD & Build:** Google Jib (for creating Docker images)
* **Observability:** Spring Boot Actuator, Prometheus, Grafana
* **HTTP Client:** RestClient (Spring 6)

---

## 📖 Topics and Concepts Covered

The course and project structure covered the following fundamental topics:

### 1. Spring Boot Fundamentals
* Creating projects with **Spring Initializr**.
* Understanding the structure of a Spring Boot project and the `pom.xml` file.
* Using essential annotations: `@RestController`, `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, `@RequestMapping`.
* Mapping requests with `@RequestParam` and `@PathVariable`.
* Handling HTTP responses with `ResponseEntity`.
* Dependency Injection (DI), Spring Beans (`@Component`, `@Service`, `@Repository`, `@Configuration`).

### 2. Architecture and Best Practices
* Applying the **Model-View-Controller (MVC)** pattern.
* Separating responsibilities into layers (Controller, Service, Repository).
* Decoupling DTOs (Data Transfer Objects) from the domain layer using **MapStruct**.
* Centralized exception handling with `@ExceptionHandler` and `@RestControllerAdvice`.
* Input data validation with **Bean Validation** (`jakarta.validation`).
* Project organization (e.g., `package-by-layer`).

### 3. Data Persistence with Spring Data JPA
* Configuring datasources for different environments using **Spring Profiles**.
* Mapping entities with JPA annotations (`@Entity`, `@Id`, `@GeneratedValue`).
* Entity relationships (`@ManyToOne`, `@ManyToMany`).
* `FetchType` strategies (`LAZY` vs `EAGER`) and solving the N+1 problem with `EntityGraphs`.
* Creating repositories and custom queries with **JPQL** and **Query Methods**.
* Paginating and sorting results with `Pageable`.
* Transactional control with `@Transactional`.
* Database schema migration with **Flyway**.

### 4. Testing
* **Unit Tests:** Focusing on testing each layer (Repository, Service, Controller) in isolation using **JUnit**, **Mockito**, and **AssertJ**.
* **Integration Tests:**
    * `@DataJpaTest` for the persistence layer.
    * `@SpringBootTest` for full application tests.
    * Using **Testcontainers** to instance a real database (MySQL) in the test environment.
    * **REST Assured** for more fluent and expressive API testing.
    * **WireMock** to mock external services and test integration with `RestClient`.
* Parameterized tests to reduce test code duplication.

### 5. Security
* Implementing authentication and authorization with **Spring Security**.
* Configuring in-memory authentication and later switching to a database-driven approach.
* Role-based access control with `@PreAuthorize`.
* Protection against CSRF attacks.
* Integrating security with OpenAPI documentation.

### 6. Documentation and API Clients
* Generating interactive API documentation with **SpringDoc (Swagger UI)**.
* **Contract-First** approach with OpenAPI, generating the API client from a YAML specification file.

### 7. Docker and Deployment
* Introduction to **Docker** and managing containers with **Docker Compose** to orchestrate the application and the database.
* Creating optimized Docker images for Java applications with **Google Jib**, without needing a `Dockerfile`.
* Publishing images to Docker Hub.

### 8. Observability
* Exposing application metrics and health endpoints with **Spring Boot Actuator**.
* Collecting metrics with **Prometheus**.
* Creating dashboards for metric visualization with **Grafana**.

---

## 🚀 How to Run the Project

1.  **Prerequisites:**
    * Java 17 or higher
    * Maven 3.x
    * Docker and Docker Compose

2.  **Clone the repository:**
    ```bash
    git clone [https://github.com/your-username/your-repository.git](https://github.com/your-username/your-repository.git)
    cd your-repository
    ```

3.  **Run with Docker Compose (recommended):**
    The `docker-compose.yml` in the project root will configure and start the MySQL and application containers.

    ```bash
    docker-compose up --build
    ```

4.  **Access the Application:**
    * The API will be available at `http://localhost:8080`.
    * The Swagger UI documentation will be at `http://localhost:8080/swagger-ui.html`.

5.  **Run the tests:**
    To run all unit and integration tests, use the Maven command:
    ```bash
    mvn clean verify
    ```
    *Note: The integration tests will use Testcontainers to provision an isolated test environment.*
