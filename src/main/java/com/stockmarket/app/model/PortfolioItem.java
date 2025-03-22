package com.stockmarket.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * PortfolioItem Entity - represents a stock holding within a portfolio
 * 
 * This demonstrates a ManyToOne relationship with Portfolio
 */
@Entity
@Table(name = "portfolio_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The stock symbol/ticker
     */
    @NotBlank(message = "Stock symbol is required")
    private String stockSymbol;

    /**
     * Number of shares held
     */
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    /**
     * Average purchase price per share
     */
    @NotNull(message = "Purchase price is required")
    @Positive(message = "Purchase price must be positive")
    private BigDecimal purchasePrice;

    /**
     * Current market price per share
     */
    @NotNull(message = "Current price is required")
    @Positive(message = "Current price must be positive")
    private BigDecimal currentPrice;

    /**
     * Total current value (quantity * currentPrice)
     */
    @NotNull(message = "Current value is required")
    @Positive(message = "Current value must be positive")
    private BigDecimal currentValue;

    /**
     * The portfolio this item belongs to
     * 
     * This demonstrates a many-to-one relationship
     * 
     * @ManyToOne - Many portfolio items can belong to one portfolio
     * @JoinColumn - Specifies the foreign key column in the portfolio_items table
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
} 