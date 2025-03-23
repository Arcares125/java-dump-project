package com.stockmarket.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing an individual stock holding in a portfolio.
 */
@Entity
@Table(name = "portfolio_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The stock symbol (ticker) for this portfolio item.
     */
    @NotBlank
    private String stockSymbol;

    /**
     * Number of shares owned.
     */
    @NotNull
    @Positive
    private Integer quantity;

    /**
     * Average purchase price per share.
     */
    @NotNull
    @Positive
    private BigDecimal averagePurchasePrice;

    /**
     * Current value of the holding (quantity * current stock price).
     */
    @NotNull
    @Positive
    private BigDecimal currentValue;

    /**
     * The portfolio this item belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
} 