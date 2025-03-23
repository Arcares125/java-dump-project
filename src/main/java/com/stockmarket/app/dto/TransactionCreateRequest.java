package com.stockmarket.app.dto;

import com.stockmarket.app.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * DTO for creating new transactions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreateRequest {
    @NotNull
    private TransactionType type;
    
    @NotBlank
    private String stockSymbol;
    
    @NotNull
    @Positive
    private Integer quantity;
    
    @NotNull
    @Positive
    private BigDecimal pricePerShare;
    
    private String userId;
    
    private Long portfolioId;
    
    private String notes;
} 