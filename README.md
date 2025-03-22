# Stock Market Application - Learning Spring Boot

This project helps you learn Spring Boot by building a stock market application. Below is a step-by-step guide to set up the project and understand the key concepts.

## Setting Up the Project

### Using Spring Initializer

1. Go to [Spring Initializer](https://start.spring.io/)
2. Configure your project with:
   - Project: Maven
   - Language: Java
   - Spring Boot: 3.2.0
   - Group: com.stockmarket
   - Artifact: stock-market-app
   - Name: stock-market-app
   - Description: Stock Market Application for Learning Spring Boot
   - Package name: com.stockmarket.app
   - Packaging: Jar
   - Java version: 17

3. Add the following dependencies:
   - Spring Web
   - Spring Data JPA
   - Lombok
   - H2 Database
   - PostgreSQL Driver
   - Validation
   - Spring Boot Actuator
   - Spring Cloud LoadBalancer
   - SpringDoc OpenAPI

4. Click "Generate" and download the project
5. Extract the ZIP file and open it in your favorite IDE

## Project Structure Overview

The generated project will have this basic structure:

```
stock-market-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── stockmarket/
│   │   │           └── app/
│   │   │               └── StockMarketAppApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── stockmarket/
│                   └── app/
│                       └── StockMarketAppApplicationTests.java
├── pom.xml
└── README.md
```

## Understanding Key Spring Boot Annotations

1. **@SpringBootApplication**: The main annotation for a Spring Boot application
   - Combines @Configuration, @EnableAutoConfiguration, and @ComponentScan
   - Marks the main class and tells Spring Boot to start auto-configuration

2. **@RestController**: Marks a class as a REST controller
   - Combines @Controller and @ResponseBody
   - Simplifies creating RESTful web services that return data (not views)

3. **@Service**: Indicates a class is a service component in the business layer
   - Contains business logic and calls repositories
   - Typically where transaction management happens

4. **@Repository**: Marks a class as a data access component
   - Used for database interaction
   - Automatically translates database exceptions

5. **@Entity**: JPA annotation that marks a class for database persistence
   - Represents a table in the database
   - Each instance represents a row in that table

6. **@Configuration**: Indicates a class defines Spring beans
   - Used for application configuration
   - Methods annotated with @Bean provide bean definitions

7. **@Autowired** / **@RequiredArgsConstructor**: For dependency injection
   - Injects beans where needed
   - RequiredArgsConstructor creates constructor for final fields (used with Lombok)

8. **@Transactional**: Defines transaction boundaries
   - Ensures database operations either all succeed or all fail
   - Can be used at class or method level

9. **@Component**: Generic stereotype for Spring-managed components
   - Parent annotation for @Service, @Repository, and @Controller
   - Used when a component doesn't fit other stereotypes

10. **@Profile**: Conditionally enables beans based on environment profiles
    - Allows different beans in different environments (dev, test, prod)

## Project Components to Implement

1. **Models/Entities**: JPA entities representing database tables
   - Stock: Represents stock market data
   - Transaction: Records of buying/selling stocks

2. **Repositories**: Interfaces for database operations
   - Spring Data JPA provides implementation automatically
   - Define custom query methods

3. **DTOs**: Data Transfer Objects for API requests/responses
   - Separate entities from API contracts
   - Validation rules for inputs

4. **Services**: Business logic layer
   - Implement operations like buying/selling stocks
   - Calculate stock performance metrics

5. **Controllers**: RESTful API endpoints
   - CRUD operations for stocks
   - Transaction processing endpoints

6. **Configuration**: Application setup
   - Database configuration
   - Load balancer setup
   - Exception handling

## Practice Exercises

1. **Basic Exercise**: Create a Stock entity and implement CRUD operations
   - Create, Read, Update, Delete operations through REST API
   - Include validation for inputs

2. **Intermediate Exercise**: Implement a Transaction system
   - Record stock purchases and sales
   - Calculate profit/loss for transactions
   - Implement endpoint to get transaction history

3. **Advanced Exercise**: Add a Portfolio feature
   - Create a user entity with a stock portfolio
   - Calculate portfolio value and performance
   - Implement portfolio rebalancing logic

## Learning Resources

- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Lombok Documentation](https://projectlombok.org/features/all)
- [OpenAPI Documentation](https://springdoc.org/)

## Creating a New API Endpoint

Follow these steps in order to create a new API endpoint in the application:

1. **Create the Model/Entity** (e.g., `src/main/java/com/stockmarket/app/model/MyEntity.java`)
   - Define fields, annotations, and validation constraints
   - Use `@Entity`, `@Id`, `@GeneratedValue`, etc.
   - Add Lombok annotations (`@Data`, `@Builder`, etc.) for boilerplate reduction

2. **Create the Repository Interface** (e.g., `src/main/java/com/stockmarket/app/repository/MyEntityRepository.java`)
   - Extend `JpaRepository<MyEntity, IdType>`
   - Define custom query methods if needed
   - Add native SQL queries or JPQL using `@Query` annotation

3. **Create the Service Interface** (e.g., `src/main/java/com/stockmarket/app/service/MyEntityService.java`)
   - Define method signatures for CRUD operations and business logic
   - Document methods with Javadoc comments

4. **Implement the Service** (e.g., `src/main/java/com/stockmarket/app/service/impl/MyEntityServiceImpl.java`)
   - Implement service interface methods
   - Add business logic and validation
   - Use repository methods for database operations
   - Apply `@Transactional` annotations where needed

5. **Create DTOs if needed** (e.g., `src/main/java/com/stockmarket/app/dto/MyEntityDTO.java`)
   - Create Data Transfer Objects for API requests/responses
   - Use for data transformation, validation, or specialized queries

6. **Create the Controller** (e.g., `src/main/java/com/stockmarket/app/controller/MyEntityController.java`)
   - Define RESTful endpoints with appropriate HTTP methods
   - Add input validation
   - Document with OpenAPI annotations for Swagger
   - Map DTOs to entities and vice versa if needed

7. **Add Unit and Integration Tests**
   - Unit test services with mocked repositories
   - Integration test APIs with test containers or in-memory database

8. **Access and Test Your API**
   - Start the application with `mvn spring-boot:run`
   - Access the Swagger UI at `http://localhost:8080/swagger-ui.html`
   - Test endpoints directly from Swagger or with tools like Postman

## Database Access

The application is configured to use H2 in-memory database by default.

- **H2 Console**: Access the database through a web interface at http://localhost:8080/h2-console
- **Connection Details**:
  - JDBC URL: `jdbc:h2:mem:stockdb`
  - Username: `sa`
  - Password: `password`

For production, you can configure PostgreSQL by uncommenting the PostgreSQL section in `application.yml`.

## Making Advanced Queries

The project demonstrates different ways to create database queries:

1. **Method name conventions**: Spring Data JPA automatically implements methods like `findByName()`
2. **JPQL queries**: Use `@Query("SELECT e FROM Entity e WHERE e.property = :value")`
3. **Native SQL queries**: Use `@Query(value = "SELECT * FROM table", nativeQuery = true)`
4. **Custom repositories**: Create repository classes with `EntityManager` for complex operations
5. **Projections**: Create interfaces with specific fields to return partial entities 