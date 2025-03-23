# Stock Market Application

A Spring Boot application for simulating a stock market platform with real-time price updates.

## 📋 Project Overview

This application provides a RESTful API for managing stocks, portfolios, and tracking market performance. It includes real-time stock price simulations with Kafka integration for event-driven updates.

## 🛠️ Tools & Libraries Used

- **Spring Boot**: Core framework
- **Spring Data JPA**: Database interactions 
- **Spring Kafka**: Messaging and event streaming
- **H2 Database**: In-memory database for development
- **Lombok**: Reduces boilerplate code
- **JUnit 5**: Testing framework
- **Mockito**: Mocking framework for testing

## ⚙️ Installation & Setup

### Prerequisites
- Java 17+
- Maven 3.6+
- Kafka (for running with full functionality)

### Running the Application

```bash
# Clone the repository
git clone https://github.com/yourusername/stockmarket-app.git

# Navigate to the project directory
cd stockmarket-app

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

## 🔄 API Endpoints

### Stocks
- `GET /api/stocks` - Get all stocks
- `GET /api/stocks/{id}` - Get stock by ID
- `GET /api/stocks/symbol/{symbol}` - Get stock by symbol
- `POST /api/stocks` - Create a new stock
- `PUT /api/stocks/symbol/{symbol}` - Update stock details
- `DELETE /api/stocks/{id}` - Delete a stock

### Portfolios
- `GET /api/portfolios` - Get all portfolios
- `GET /api/portfolios/{id}` - Get portfolio by ID
- `POST /api/portfolios` - Create a new portfolio
- `PUT /api/portfolios/{id}` - Update portfolio details
- `DELETE /api/portfolios/{id}` - Delete a portfolio

### Performance
- `GET /api/portfolios/performance/{id}` - Get portfolio performance metrics

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── stockmarket/
│   │           └── app/
│   │               ├── config/       # Configuration classes
│   │               ├── controller/   # REST controllers
│   │               ├── dto/          # Data Transfer Objects
│   │               ├── enums/        # Enumerations
│   │               ├── exception/    # Custom exceptions
│   │               ├── model/        # Entity models
│   │               ├── repository/   # Data repositories
│   │               ├── service/      # Business logic services
│   │               └── util/         # Utility classes
│   └── resources/
│       ├── application.properties    # Application configuration
│       └── data.sql                  # Initial data setup
└── test/
    └── java/
        └── com/
            └── stockmarket/
                └── app/
                    ├── controller/   # Controller tests
                    ├── repository/   # Repository tests
                    └── service/      # Service tests
```

## 🔑 Key Spring Boot Annotations

### Core Spring Annotations
- `@SpringBootApplication`: Marks the main application class, combining @Configuration, @EnableAutoConfiguration, and @ComponentScan
- `@Component`: Marks a class as a Spring component
- `@Configuration`: Indicates a class declares one or more @Bean methods
- `@Bean`: Indicates a method produces a bean to be managed by Spring
- `@Autowired`: Marks a constructor, field, or setter method to be autowired by Spring's dependency injection
- `@Value`: Injects values from properties files
- `@Qualifier`: Specifies which bean should be injected when multiple candidates exist
- `@Profile`: Indicates that a component is eligible for registration when one or more specified profiles are active
- `@Lazy`: Indicates whether a bean is to be lazily initialized
- `@Scope`: Specifies the scope of a bean
- `@Primary`: Indicates a bean should be given preference when multiple candidates qualify for autowiring
- `@EnableScheduling`: Enables Spring's scheduled task execution capability

### Web Layer Annotations
- `@RestController`: Combines @Controller and @ResponseBody, used for RESTful controllers
- `@Controller`: Marks a class as a web controller, capable of handling requests
- `@RequestMapping`: Maps HTTP requests to handler methods
- `@GetMapping`: Shortcut for @RequestMapping(method = RequestMethod.GET)
- `@PostMapping`: Shortcut for @RequestMapping(method = RequestMethod.POST)
- `@PutMapping`: Shortcut for @RequestMapping(method = RequestMethod.PUT)
- `@DeleteMapping`: Shortcut for @RequestMapping(method = RequestMethod.DELETE)
- `@PatchMapping`: Shortcut for @RequestMapping(method = RequestMethod.PATCH)
- `@PathVariable`: Extracts values from the URI path
- `@RequestParam`: Extracts query parameters
- `@RequestBody`: Maps the HttpRequest body to a transfer or domain object
- `@ResponseStatus`: Marks a method or exception class with a status code and reason
- `@ResponseBody`: Indicates a method return value should be bound to the web response body
- `@CrossOrigin`: Enables cross-origin resource sharing (CORS)

### Service Layer Annotations
- `@Service`: Indicates a class is a service layer bean
- `@Transactional`: Defines transaction boundaries and behavior
- `@Scheduled`: Marks a method to be scheduled for recurring execution
- `@Async`: Indicates a method should run asynchronously
- `@Cacheable`: Indicates that the result of a method can be cached
- `@CacheEvict`: Indicates a method that triggers cache eviction
- `@CachePut`: Updates the cache without interfering with the method execution
- `@RequiredArgsConstructor`: Lombok annotation that generates a constructor for all final fields

### Data Access Annotations
- `@Repository`: Indicates a class is a repository (DAO)
- `@Entity`: Marks a class as a JPA entity
- `@Table`: Specifies the primary table for the annotated entity
- `@Id`: Specifies the primary key of an entity
- `@GeneratedValue`: Specifies the generation strategy for primary keys
- `@Column`: Specifies column mapping
- `@OneToMany`: Defines a one-to-many relationship
- `@ManyToOne`: Defines a many-to-one relationship
- `@ManyToMany`: Defines a many-to-many relationship
- `@OneToOne`: Defines a one-to-one relationship
- `@JoinColumn`: Specifies a foreign key column
- `@JoinTable`: Specifies a join table between two entities
- `@Query`: Defines a custom JPQL query
- `@Modifying`: Indicates a query is modifying and not just selecting
- `@Transient`: Marks a field as not persistable
- `@Embeddable`: Marks a class whose instances are stored as intrinsic part of an owning entity
- `@Embedded`: Specifies a persistent field of an entity whose value is an instance of an embeddable class
- `@CreatedDate`: JPA auditing - marks field to hold creation date
- `@LastModifiedDate`: JPA auditing - marks field to hold last modification date
- `@Builder.Default`: Lombok annotation specifying a default value for a field in a builder

### Validation Annotations
- `@Valid`: Triggers validation of an annotated bean
- `@NotNull`: Validates that the annotated property value is not null
- `@NotBlank`: Validates that the annotated string is not null and contains at least one non-whitespace character
- `@NotEmpty`: Validates that the annotated element is not null nor empty
- `@Size`: Validates that the annotated property value has a size between the attributes min and max
- `@Min`: Validates that the annotated property has a value not less than the value attribute
- `@Max`: Validates that the annotated property has a value not greater than the value attribute
- `@Pattern`: Validates that the annotated string matches the specified regular expression
- `@Email`: Validates that the annotated string is a valid email address
- `@Positive`: Validates that the annotated numeric value is positive
- `@PositiveOrZero`: Validates that the annotated numeric value is positive or zero
- `@Negative`: Validates that the annotated numeric value is negative
- `@NegativeOrZero`: Validates that the annotated numeric value is negative or zero
- `@Past`: Validates that the annotated date is in the past
- `@Future`: Validates that the annotated date is in the future

### Testing Annotations
- `@SpringBootTest`: Indicates a test that needs the Spring context
- `@WebMvcTest`: Test slice for testing controllers with MockMvc
- `@DataJpaTest`: Test slice for testing JPA repositories
- `@MockBean`: Adds mock objects to the Spring context
- `@SpyBean`: Adds spy objects to the Spring context
- `@Mock`: Creates a mock implementation of a class or interface
- `@InjectMocks`: Injects mock fields into the tested object
- `@ExtendWith`: Registers extensions for JUnit Jupiter
- `@BeforeEach`: Method to run before each test
- `@AfterEach`: Method to run after each test
- `@BeforeAll`: Method to run once before all tests
- `@AfterAll`: Method to run once after all tests
- `@Test`: Marks a method as a test
- `@DisplayName`: Declares a custom name for a test
- `@ParameterizedTest`: Denotes a parameterized test method
- `@RepeatedTest`: Repeats a test a specified number of times
- `@TestPropertySource`: Configures the property sources for a test
- `@Captor`: Creates an ArgumentCaptor for use in verification

### Lombok Annotations
- `@Data`: Generates getters, setters, equals, hashCode, and toString methods
- `@Builder`: Implements the builder pattern
- `@NoArgsConstructor`: Generates a no-args constructor
- `@AllArgsConstructor`: Generates a constructor with one parameter for each field
- `@Getter`: Generates getters for all fields
- `@Setter`: Generates setters for all fields
- `@EqualsAndHashCode`: Generates equals and hashCode methods
- `@ToString`: Generates a toString method
- `@Slf4j`: Creates a logger field

### Kafka Annotations
- `@EnableKafka`: Enables detection of Kafka listener annotations
- `@KafkaListener`: Marks a method as a listener for Kafka topics
- `@SendTo`: Indicates the result of a KafkaListener method should be sent to the specified topic

### Exception Handling Annotations
- `@ControllerAdvice`: Allows handling exceptions across the application
- `@ExceptionHandler`: Handles specific exceptions in controller methods
- `@RestControllerAdvice`: Combines @ControllerAdvice and @ResponseBody

## 🔄 Kafka Integration

The application integrates with Apache Kafka for real-time messaging:

- Stock price updates are published to the `stock-price-updates` topic
- Portfolio changes are published to dedicated topics
- Events follow a standardized format for easy processing

## 🔒 Security

- Basic authentication for API endpoints
- Input validation for all user inputs
- Exception handling for robust error management

## 📊 Testing

```bash
# Run tests
mvn test

# Run with coverage
mvn test jacoco:report
```

## 🚀 Future Enhancements

- OAuth2 integration for improved security
- Docker containerization
- UI dashboard for visualization
- User account management
- Historical price tracking and charts
- Integration with external market data APIs

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.
