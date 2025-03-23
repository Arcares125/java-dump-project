package com.stockmarket.app.dto;

import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Stock Update Request DTO
 * 
 * This class represents the input data structure for updating an existing stock.
 * All fields are optional, allowing partial updates - only the fields that are provided will be updated.
 * 
 * Note the differences from StockCreateRequest:
 * 1. No @NotBlank or @NotNull annotations - all fields are optional for updates
 * 2. No "symbol" field - the symbol is provided in the URL path and can't be changed
 * 3. Still has validation on the fields that are provided (e.g., price must be positive if specified)
 * 
 * This is a common pattern for RESTful APIs:
 * - POST requests (create) require all mandatory fields
 * - PUT/PATCH requests (update) allow partial updates with optional fields
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockUpdateRequest {
    
    /**
     * Company name (optional) - update only if provided
     */
    private String companyName;
    
    /**
     * Current stock price (optional)
     * If provided, must be positive (greater than zero)
     */
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