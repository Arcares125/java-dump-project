package com.stockmarket.app.dto;

/**
 * >>>>>>>>>>>
 * QUIZ 7: Integration with Kafka (Part 2)
 * 
 * Your task:
 * 1. Create a DTO for portfolio events that will be sent to Kafka:
 *    - Include fields for event type (CREATE, UPDATE, DELETE)
 *    - Include the portfolio ID and relevant data
 *    - Include a timestamp
 * 
 * 2. Make sure it works with the existing KafkaProducerService
 * 
 * HINTS:
 * 1. Add these fields to the PortfolioEvent class:
 *    private Long portfolioId;
 *    private String eventType;  // "CREATE", "UPDATE", "DELETE"
 *    private String portfolioName;  // Optional: include basic portfolio info
 *    private String username;      // Optional: who owns the portfolio
 *    private LocalDateTime timestamp;
 * 
 * 2. Add Lombok annotations for convenience:
 *    @Data
 *    @Builder
 *    @NoArgsConstructor
 *    @AllArgsConstructor
 * 
 * 3. The KafkaProducerService expects objects with a "key" field for routing.
 *    In this case, the portfolio ID can serve as the key, so make sure your 
 *    service implementation sends the event appropriately:
 *    
 *    // In KafkaProducerService
 *    public void sendPortfolioEvent(PortfolioEvent event) {
 *        kafkaTemplate.send("portfolio-events", event.getPortfolioId().toString(), event);
 *    }
 * 
 * 4. Add necessary imports:
 *    import lombok.AllArgsConstructor;
 *    import lombok.Builder;
 *    import lombok.Data;
 *    import lombok.NoArgsConstructor;
 *    import java.time.LocalDateTime;
 * 
 * >>>>>>>>>>>
 */
public class PortfolioEvent {
    
    // TODO: Implement your solution here
    
} 