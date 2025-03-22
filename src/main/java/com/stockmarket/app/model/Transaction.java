package com.stockmarket.app.model;

import com.stockmarket.app.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * Transaction Entity - represents stock purchase/sale transactions in the database.
 * 
 * This demonstrates additional JPA/Lombok features:
 * 
 * @Enumerated - Specifies how an enum type is stored in the database
 *               EnumType.STRING stores the enum name as a string (more readable in database)
 *               EnumType.ORDINAL stores the enum ordinal value (more efficient but less clear)
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
     * The type of transaction (BUY or SELL)
     */
    @NotNull(message = "Transaction type is required")
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    /**
     * The stock symbol/ticker involved in the transaction
     */
    @NotBlank(message = "Stock symbol is required")
    private String stockSymbol;

    /**
     * Quantity of shares bought or sold
     */
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    /**
     * Price per share at the time of the transaction
     */
    @NotNull(message = "Price per share is required")
    @Positive(message = "Price must be positive")
    private BigDecimal pricePerShare;

    /**
     * Total amount of the transaction (quantity * price per share)
     * This is calculated rather than provided directly, but we store it
     * for easy retrieval and reporting.
     */
    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be positive")
    private BigDecimal totalAmount;

    /**
     * Timestamp when the transaction occurred
     */
    @NotNull(message = "Transaction timestamp is required")
    private LocalDateTime timestamp;
} 