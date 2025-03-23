package com.stockmarket.app.dto;

import com.stockmarket.app.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * DTO for updating existing transactions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionUpdateRequest {
    private TransactionType type;
    
    private String stockSymbol;
    
    @Positive
    private Integer quantity;
    
    @Positive
    private BigDecimal pricePerShare;
    
    private String notes;
} 