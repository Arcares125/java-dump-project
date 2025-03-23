package com.stockmarket.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Portfolio Entity - represents a user's stock portfolio
 * 
 * This demonstrates JPA relationships:
 * - @OneToMany relationship with PortfolioItem
 * 
 * >>>>>>>>>>>
 * QUIZ 2: Working with Entities and DTOs (Part 1)
 * 
 * Your task:
 * 1. Review this Portfolio entity class and understand:
 *    - How JPA annotations are used (@Entity, @Id, etc.)
 *    - How Lombok annotations are used (@Data, @Builder, etc.)
 *    - The relationship between Portfolio and PortfolioItem
 * 
 * Then create a corresponding PortfolioDTO class in the dto package
 * >>>>>>>>>>>
 */
@Entity
@Table(name = "portfolios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the portfolio
     */
    @NotBlank(message = "Portfolio name is required")
    private String name;

    /**
     * The user who owns this portfolio
     */
    @NotBlank(message = "Username is required")
    private String username;

    /**
     * Description of the portfolio
     */
    private String description;

    /**
     * Total value of all portfolio items
     */
    @NotNull(message = "Total value is required")
    private BigDecimal totalValue;

    /**
     * List of portfolio items (stocks in this portfolio)
     * 
     * CascadeType.ALL - When portfolio is saved/updated/deleted, the same operation applies to its items
     * orphanRemoval=true - If an item is removed from the list, it's also deleted from the database
     * 
     * This demonstrates a one-to-many relationship
     */
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PortfolioItem> items = new ArrayList<>();
    
    /**
     * Helper method to add a portfolio item and maintain the relationship
     */
    public void addItem(PortfolioItem item) {
        items.add(item);
        item.setPortfolio(this);
    }
    
    /**
     * Helper method to remove a portfolio item and maintain the relationship
     */
    public void removeItem(PortfolioItem item) {
        items.remove(item);
        item.setPortfolio(null);
    }
} 