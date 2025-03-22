# 📈 Stock Market Application

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-6DB33F?style=for-the-badge&logo=spring-boot)
![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2_Database-Included-0000FF?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-Central-C71A36?style=for-the-badge&logo=apache-maven)
![Kafka](https://img.shields.io/badge/Apache_Kafka-Latest-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Latest-2496ED?style=for-the-badge&logo=docker&logoColor=white)

A comprehensive Spring Boot application for learning modern Java backend development through a stock market system.

</div>

## 📑 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Setup Instructions](#-setup-instructions)
- [Creating a New API Endpoint](#-creating-a-new-api-endpoint)
- [Database Access](#-database-access)
- [Making Advanced Queries](#-making-advanced-queries)
- [Docker & Containerization](#-docker--containerization)
- [Kafka & Event Streaming](#-kafka--event-streaming)
- [Project Structure](#-project-structure)
- [Key Annotations](#-key-spring-boot-annotations)
- [Practice Exercises](#-practice-exercises)
- [API Documentation](#-api-documentation)
- [Learning Resources](#-learning-resources)

## 🔭 Overview

This project helps you learn Spring Boot by building a fully functional stock market application with real-world features. Perfect for beginners and intermediate developers looking to understand enterprise Java development.

## ✨ Features

- 🔐 **User authentication and authorization**
- 📊 **Stock management with CRUD operations**
- 💰 **Transaction recording system**
- 📂 **Portfolio management**
- 📈 **Performance tracking**
- 🔄 **RESTful API endpoints**
- 📝 **Detailed documentation with Swagger/OpenAPI**
- 🐳 **Docker containerization**
- 🚀 **Event streaming with Kafka**

## 🚀 Setup Instructions

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

### Starting the Application

Run the application using Maven:

```bash
mvn spring-boot:run
```

## 🛠 Creating a New API Endpoint

Follow these steps in order to create a new API endpoint in the application:

### 1. Create the Model/Entity

Create a new Java class in `src/main/java/com/stockmarket/app/model/MyEntity.java`

```java
@Entity
@Table(name = "my_entities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    // Other fields, getters, setters
}
```

### 2. Create the Repository Interface

Create a new interface in `src/main/java/com/stockmarket/app/repository/MyEntityRepository.java`

```java
@Repository
public interface MyEntityRepository extends JpaRepository<MyEntity, Long> {
    List<MyEntity> findByName(String name);
    
    @Query("SELECT e FROM MyEntity e WHERE e.someField = :value")
    List<MyEntity> findBySomeCustomCriteria(@Param("value") String value);
}
```

### 3. Create the Service Interface

Create a new interface in `src/main/java/com/stockmarket/app/service/MyEntityService.java`

```java
public interface MyEntityService {
    MyEntity create(MyEntity entity);
    MyEntity getById(Long id);
    List<MyEntity> getAll();
    MyEntity update(Long id, MyEntity entity);
    void delete(Long id);
}
```

### 4. Implement the Service

Create a new class in `src/main/java/com/stockmarket/app/service/impl/MyEntityServiceImpl.java`

```java
@Service
public class MyEntityServiceImpl implements MyEntityService {
    private final MyEntityRepository repository;
    
    @Autowired
    public MyEntityServiceImpl(MyEntityRepository repository) {
        this.repository = repository;
    }
    
    // Implement all methods from the interface
}
```

### 5. Create DTOs if needed

Create a new class in `src/main/java/com/stockmarket/app/dto/MyEntityDTO.java`

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyEntityDTO {
    private Long id;
    private String name;
    // Other fields
}
```

### 6. Create the Controller

Create a new class in `src/main/java/com/stockmarket/app/controller/MyEntityController.java`

```java
@RestController
@RequestMapping("/api/my-entities")
@Tag(name = "My Entity", description = "My Entity management APIs")
public class MyEntityController {
    private final MyEntityService service;
    
    @Autowired
    public MyEntityController(MyEntityService service) {
        this.service = service;
    }
    
    @PostMapping
    @Operation(summary = "Create a new entity")
    public ResponseEntity<MyEntity> create(@Valid @RequestBody MyEntity entity) {
        // Implementation
    }
    
    // Other endpoints
}
```

### 7. Add Unit and Integration Tests

### 8. Access and Test Your API

- Start the application with `mvn spring-boot:run`
- Access the Swagger UI at `http://localhost:8080/swagger-ui.html`
- Test endpoints directly from Swagger or with tools like Postman

## 💾 Database Access

The application is configured to use H2 in-memory database by default.

<div align="center">

| Setting | Value |
|---------|-------|
| **H2 Console URL** | http://localhost:8080/h2-console |
| **JDBC URL** | jdbc:h2:mem:stockdb |
| **Username** | sa |
| **Password** | password |

</div>

For production, you can configure PostgreSQL by uncommenting the PostgreSQL section in `application.yml`.

## 🔍 Making Advanced Queries

The project demonstrates different ways to create database queries:

1. **Method name conventions**:
   ```java
   List<Transaction> findByStockSymbol(String stockSymbol);
   ```

2. **JPQL queries**:
   ```java
   @Query("SELECT t FROM Transaction t WHERE t.totalAmount > :amount")
   List<Transaction> findTransactionsWithTotalAmountGreaterThan(@Param("amount") BigDecimal amount);
   ```

3. **Native SQL queries**:
   ```java
   @Query(value = "SELECT * FROM transactions WHERE stock_symbol = :symbol", nativeQuery = true)
   List<Transaction> findBySymbolNative(@Param("symbol") String symbol);
   ```

4. **Custom repositories**: Create repository classes with `EntityManager` for complex operations

5. **Projections**: Create interfaces for returning partial entities

## 📂 Project Structure

```
stock-market-app/
├── src/
│   ├── main/
│   │   ├── java/com/stockmarket/app/
│   │   │   ├── config/        # Configuration classes
│   │   │   ├── controller/    # REST controllers
│   │   │   ├── dto/           # Data Transfer Objects
│   │   │   ├── enums/         # Enumeration types
│   │   │   ├── exception/     # Custom exceptions
│   │   │   ├── model/         # JPA entities
│   │   │   ├── repository/    # Data repositories
│   │   │   ├── service/       # Service interfaces and implementations
│   │   │   └── StockMarketAppApplication.java  # Main class
│   │   └── resources/
│   │       ├── application.yml  # Application configuration
│   │       ├── data.sql         # Initial data script (optional)
│   │       └── schema.sql       # Schema creation script (optional)
│   └── test/                  # Test classes
└── pom.xml                    # Maven dependencies
```

## 🔑 Key Spring Boot Annotations

| Annotation | Purpose |
|------------|---------|
| **@SpringBootApplication** | Main application annotation that combines @Configuration, @EnableAutoConfiguration, and @ComponentScan |
| **@RestController** | Marks a class as a REST controller (combines @Controller and @ResponseBody) |
| **@Service** | Indicates a class is a service component in the business layer |
| **@Repository** | Marks a class as a data access component |
| **@Entity** | JPA annotation that marks a class for database persistence |
| **@Configuration** | Indicates a class defines Spring beans |
| **@Autowired** | Injects dependencies automatically |
| **@Transactional** | Defines transaction boundaries |
| **@Component** | Generic stereotype for Spring-managed components |
| **@Profile** | Conditionally enables beans based on environment profiles |
| **@KafkaListener** | Subscribes to Kafka topics for message consumption |
| **@Bean** | Declares a method as producing a bean for Spring container |

## 🏋️ Practice Exercises

### Basic Exercise
Create a Stock entity and implement CRUD operations
- Create, Read, Update, Delete operations through REST API
- Include validation for inputs

### Intermediate Exercise
Implement a Transaction system
- Record stock purchases and sales
- Calculate profit/loss for transactions
- Implement endpoint to get transaction history

### Advanced Exercise
Add a Portfolio feature
- Create a user entity with a stock portfolio
- Calculate portfolio value and performance
- Implement portfolio rebalancing logic

### Advanced Exercise: Kafka Integration
Implement real-time stock price updates with Kafka
- Create a price simulator service that generates updates
- Send updates to Kafka topics
- Create consumers to react to price changes
- Implement streaming analytics on transaction data

## 📖 API Documentation

Swagger UI documentation is available at:
```
http://localhost:8080/swagger-ui.html
```

OpenAPI specification is available at:
```
http://localhost:8080/api-docs
```

## 📚 Learning Resources

- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Lombok Documentation](https://projectlombok.org/features/all)
- [OpenAPI Documentation](https://springdoc.org/)

## 🐳 Docker & Containerization

This project includes Docker support for easy deployment and development.

### Docker Components

- **Dockerfile**: Defines how to build the application container
- **docker-compose.yml**: Orchestrates the multi-container setup
- **Application Profile**: `application-docker.yml` contains Docker-specific configurations

### Running with Docker

1. Build the application:
   ```bash
   mvn clean package
   ```

2. Start all services using Docker Compose:
   ```bash
   docker-compose up -d
   ```

3. Stop all services:
   ```bash
   docker-compose down
   ```

### Docker Architecture

The Docker setup includes the following services:
- **app**: The Spring Boot application
- **postgres**: PostgreSQL database for persistent storage
- **zookeeper**: Required for Kafka coordination
- **kafka**: Message broker for event streaming
- **kafka-ui**: Web UI for monitoring Kafka (available at http://localhost:8090)

## 🚀 Kafka & Event Streaming

The application uses Apache Kafka for real-time event streaming of stock market data.

### Kafka Topics

- **stock-price-updates**: Real-time updates for stock prices
- **stock-transactions**: Record of stock purchase and sale transactions

### Kafka Components

1. **Producer**: `KafkaProducerService` - Publishes messages to Kafka topics
2. **Consumer**: `KafkaConsumerService` - Subscribes to and processes messages from Kafka topics
3. **Configuration**: `KafkaConfig` - Defines Kafka topics and settings

### Using Kafka in Your Code

To publish a stock price update:
```java
// Inject KafkaProducerService
@Autowired
private KafkaProducerService kafkaProducerService;

// Create and send an update
StockPriceUpdateDTO update = StockPriceUpdateDTO.builder()
    .symbol("AAPL")
    .price(new BigDecimal("150.75"))
    .change(new BigDecimal("2.30"))
    .changePercent(new BigDecimal("1.55"))
    .timestamp(LocalDateTime.now())
    .build();
    
kafkaProducerService.sendStockPriceUpdate(update);
```

To consume messages, create a listener:
```java
@KafkaListener(topics = "${kafka.topics.stock-price-updates}", groupId = "${spring.kafka.consumer.group-id}")
public void processStockUpdate(StockPriceUpdateDTO update) {
    // Process the update
    System.out.println("Received update for: " + update.getSymbol());
}
```

---

<div align="center">
  
  ⭐ **Star this repository if you find it helpful!** ⭐
  
  Made with ❤️ for learning Spring Boot
  
</div>
