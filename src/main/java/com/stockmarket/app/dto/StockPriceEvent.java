package com.stockmarket.app.dto;

/**
 * >>>>>>>>>>>
 * SUPPLEMENTARY FOR QUIZ 10: Stock Price Event DTO
 * 
 * Your task:
 * Create a Stock Price Event DTO that will be used by the Kafka consumer to process stock price updates
 * 
 * HINTS:
 * 1. Define the class with all required fields:
 *    - String symbol - the stock symbol
 *    - BigDecimal newPrice - the updated stock price
 *    - LocalDateTime timestamp - when the price change occurred
 * 
 * 2. Add Lombok annotations for convenience:
 *    @Data
 *    @NoArgsConstructor
 *    @AllArgsConstructor
 *    @Builder
 * 
 * 3. Make sure the class is serializable for Kafka:
 *    - Implement Serializable or ensure Jackson can serialize/deserialize it
 *    - Add a default constructor for deserialization
 * 
 * 4. Example implementation:
 * 
 *    @Data
 *    @NoArgsConstructor
 *    @AllArgsConstructor
 *    @Builder
 *    public class StockPriceEvent implements Serializable {
 *        private String symbol;
 *        private BigDecimal newPrice;
 *        private LocalDateTime timestamp;
 *    }
 * 
 * 5. Add necessary imports:
 *    import lombok.AllArgsConstructor;
 *    import lombok.Builder;
 *    import lombok.Data;
 *    import lombok.NoArgsConstructor;
 *    import java.io.Serializable;
 *    import java.math.BigDecimal;
 *    import java.time.LocalDateTime;
 * 
 * >>>>>>>>>>>
 */
public class StockPriceEvent {
    
    // TODO: Implement your solution here
    
} 