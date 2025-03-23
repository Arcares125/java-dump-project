package com.stockmarket.app.model;

import com.stockmarket.app.enums.TransactionType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a transaction in the stock market.
 * This can be a BUY or SELL transaction.
 * 
 * Each transaction is associated with a specific stock and records details
 * such as quantity, price, total value, and timestamp.
 * 
 * @author stockmarket-app-team
 * @version 1.0
 */
@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Type of transaction (BUY or SELL)
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    /**
     * Stock symbol for the transaction
     */
    @NotBlank
    private String stockSymbol;

    /**
     * Number of shares traded
     */
    @NotNull
    @Positive
    private Integer quantity;

    /**
     * Price per share at the time of transaction
     */
    @NotNull
    @Positive
    private BigDecimal pricePerShare;

    /**
     * Total value of the transaction (quantity * pricePerShare)
     * Calculated automatically
     */
    @NotNull
    @Positive
    private BigDecimal totalValue;

    /**
     * When the transaction occurred
     */
    @NotNull
    private LocalDateTime timestamp;

    /**
     * Optional user ID for the transaction
     */
    private String userId;

    /**
     * Optional portfolio ID if the transaction is associated with a portfolio
     */
    private Long portfolioId;

    /**
     * Optional notes about the transaction
     */
    private String notes;
} 