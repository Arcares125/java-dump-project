package com.stockmarket.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a stock in the stock market application.
 * <p>
 * This class defines all the properties of a stock such as its symbol,
 * company name, and current price. It is mapped to a database table named "stocks".
 * </p>
 *
 * @author stockmarket-app-team
 * @version 1.0
 */
@Entity
@Table(name = "stocks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock implements Serializable {

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

    /**
     * A description or additional information about the stock.
     */
    private String description;

    /**
     * Pre-market price for the stock.
     */
    private BigDecimal preMarketPrice;

    /**
     * After-hours trading price for the stock.
     */
    private BigDecimal afterHoursPrice;
} 