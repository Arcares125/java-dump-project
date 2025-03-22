package com.stockmarket.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Stock Entity - represents stock data in the database.
 * 
 * Here's what each annotation does:
 * 
 * @Entity - Marks this class as a JPA entity, which will be mapped to a database table
 * @Table - Specifies the table name in the database (in this case, "stocks")
 * @Data - Lombok annotation that generates getters, setters, toString, equals, and hashCode methods
 * @Builder - Lombok annotation that implements the Builder pattern for this class
 * @NoArgsConstructor - Lombok annotation that generates a constructor with no parameters
 * @AllArgsConstructor - Lombok annotation that generates a constructor with all parameters
 */
@Entity
@Table(name = "stocks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    /**
     * @Id - Marks this field as the primary key
     * @GeneratedValue - Specifies the strategy for ID generation (IDENTITY uses auto-increment)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @NotBlank - Validation annotation that checks the value is not null, not empty, and not just whitespace
     */
    @NotBlank(message = "Stock symbol is required")
    private String symbol;

    @NotBlank(message = "Company name is required")
    private String companyName;

    /**
     * @NotNull - Validation annotation that checks the value is not null
     * @Positive - Validation annotation that checks the value is greater than zero
     * 
     * BigDecimal is used for monetary values to avoid floating-point precision errors
     */
    @NotNull(message = "Current price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal currentPrice;

    // Previous day's closing price
    private BigDecimal previousClose;
    
    // Price change from previous close
    private BigDecimal change;
    
    // Percentage change from previous close
    private BigDecimal changePercent;
    
    // Trading volume
    private Long volume;
    
    // Last time the stock data was updated
    private LocalDateTime lastUpdated;
    
    // Market sector (e.g., Technology, Healthcare, etc.)
    private String sector;
    
    // Industry within the sector (e.g., Software, Pharmaceuticals, etc.)
    private String industry;
} 