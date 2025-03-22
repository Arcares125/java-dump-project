package com.stockmarket.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for stock summary information.
 * 
 * DTOs are used to transfer data between processes, reducing the number of method calls
 * and carrying only the data needed for a specific operation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockSummaryDTO {
    
    private String symbol;
    private Long totalTransactions;
    private BigDecimal averagePrice;
    private BigDecimal totalVolume;
    
    /**
     * Constructor for creating a DTO from native query results
     */
    public StockSummaryDTO(String symbol, Long totalTransactions, Double averagePrice, Long totalVolume) {
        this.symbol = symbol;
        this.totalTransactions = totalTransactions;
        this.averagePrice = averagePrice != null ? new BigDecimal(averagePrice) : BigDecimal.ZERO;
        this.totalVolume = totalVolume != null ? new BigDecimal(totalVolume) : BigDecimal.ZERO;
    }
} 