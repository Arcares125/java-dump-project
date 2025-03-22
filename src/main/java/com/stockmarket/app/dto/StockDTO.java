package com.stockmarket.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Stock Data Transfer Object (DTO) Class
 * 
 * A DTO is an object that carries data between processes, in this case between
 * our service layer and the API controllers. DTOs help us to:
 * 
 * 1. Decouple our API from our domain model (entities)
 * 2. Control what data is exposed through the API
 * 3. Optimize data transfer by including only necessary fields
 * 4. Version our API independently from our database schema
 * 
 * Lombok annotations:
 * @Data - Generates getters, setters, equals, hashCode, and toString methods
 * @Builder - Implements the Builder pattern for object creation
 * @NoArgsConstructor - Generates a constructor with no parameters
 * @AllArgsConstructor - Generates a constructor with parameters for all fields
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
    // Unique identifier
    private Long id;
    
    // Stock ticker symbol (e.g., AAPL, MSFT)
    private String symbol;
    
    // Full company name
    private String companyName;
    
    // Current stock price
    private BigDecimal currentPrice;
    
    // Previous day's closing price
    private BigDecimal previousClose;
    
    // Price change from previous close
    private BigDecimal change;
    
    // Percentage change from previous close
    private BigDecimal changePercent;
    
    // Trading volume
    private Long volume;
    
    // Timestamp of when the data was last updated
    private LocalDateTime lastUpdated;
    
    // Market sector (e.g., Technology, Healthcare)
    private String sector;
    
    // Industry within sector (e.g., Software, Pharmaceuticals)
    private String industry;
} 