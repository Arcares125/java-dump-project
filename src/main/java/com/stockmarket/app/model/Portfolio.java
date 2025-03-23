package com.stockmarket.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a user's portfolio in the stock market application.
 * <p>
 * A portfolio contains a collection of stocks that a user owns. Each portfolio
 * has a name, associated user, creation time, and a list of portfolio items
 * that represent individual stock holdings.
 * </p>
 *
 * @author stockmarket-app-team
 * @version 1.0
 */
@Entity
@Table(name = "portfolios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the portfolio as given by the user.
     */
    @NotBlank
    private String name;

    /**
     * The username of the portfolio owner.
     */
    @NotBlank
    private String username;

    /**
     * The timestamp when the portfolio was created.
     */
    @NotNull
    private LocalDateTime createdAt;

    /**
     * The timestamp when the portfolio was last updated.
     */
    private LocalDateTime updatedAt;
    
    /**
     * The list of portfolio items (stock holdings) in this portfolio.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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