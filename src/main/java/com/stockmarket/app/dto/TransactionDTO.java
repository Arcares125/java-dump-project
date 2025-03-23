package com.stockmarket.app.dto;

import com.stockmarket.app.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Transaction entity.
 * Used for transferring transaction data between layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    private TransactionType type;
    private String stockSymbol;
    private Integer quantity;
    private BigDecimal pricePerShare;
    private BigDecimal totalValue;
    private LocalDateTime timestamp;
    private String userId;
    private Long portfolioId;
    private String notes;
} 