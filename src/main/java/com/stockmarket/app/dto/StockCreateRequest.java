package com.stockmarket.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Stock Creation Request DTO
 * 
 * This class represents the input data structure for creating a new stock.
 * It includes validation annotations to ensure data integrity before processing.
 * 
 * Key validation annotations:
 * - @NotBlank: Ensures a string is not null and contains at least one non-whitespace character
 * - @NotNull: Ensures a value is not null
 * - @Positive: Ensures a numeric value is greater than zero
 * 
 * Note: We separate request DTOs from response DTOs because:
 * 1. They often have different fields (e.g., we don't need ID for creation)
 * 2. Different validation rules apply to inputs vs outputs
 * 3. It provides better API documentation and contract
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockCreateRequest {
    
    /**
     * Stock ticker symbol (e.g., AAPL for Apple Inc.)
     * Cannot be blank (null, empty, or whitespace only)
     */
    @NotBlank(message = "Stock symbol is required")
    private String symbol;
    
    /**
     * Full company name
     * Cannot be blank (null, empty, or whitespace only)
     */
    @NotBlank(message = "Company name is required")
    private String companyName;
    
    /**
     * Current stock price
     * Cannot be null and must be positive (greater than zero)
     */
    @NotNull(message = "Current price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal currentPrice;
    
    /**
     * Previous day's closing price (optional)
     */
    private BigDecimal previousClose;
    
    /**
     * Trading volume (optional)
     */
    private Long volume;
    
    /**
     * Market sector (optional)
     * Examples: Technology, Healthcare, Financials
     */
    private String sector;
    
    /**
     * Industry within sector (optional)
     * Examples: Software, Pharmaceuticals, Banking
     */
    private String industry;
} 