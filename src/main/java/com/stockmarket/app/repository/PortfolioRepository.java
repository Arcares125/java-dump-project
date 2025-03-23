package com.stockmarket.app.repository;

import com.stockmarket.app.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * >>>>>>>>>>>
 * QUIZ 3: Repository Layer
 * 
 * Your task:
 * 1. Implement this PortfolioRepository interface that:
 *    - Extends JpaRepository with the right entity type and ID type
 *    - Has a method to find portfolios by name
 *    - Has a method to find portfolios by username
 *    - Has a custom query to find portfolios with a total value greater than a specified amount
 * 
 * >>>>>>>>>>>
 */
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    
    // TODO: Implement your solution here
    
} 