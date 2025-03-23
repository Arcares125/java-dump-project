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
 * HINTS:
 * 1. The interface already extends JpaRepository<Portfolio, Long> which is correct
 * 
 * 2. Add these method signatures:
 *    // Find a single portfolio by its exact name
 *    Optional<Portfolio> findByName(String name);
 *    
 *    // Find all portfolios belonging to a user
 *    List<Portfolio> findByUsername(String username);
 *    
 *    // Find portfolios with a name containing the search term (case insensitive)
 *    List<Portfolio> findByNameContainingIgnoreCase(String nameFragment);
 * 
 * 3. Add a custom query using @Query annotation:
 *    @Query("SELECT p FROM Portfolio p WHERE p.totalValue > :minValue")
 *    List<Portfolio> findByTotalValueGreaterThan(@Param("minValue") BigDecimal minValue);
 * 
 * 4. Don't forget to add the necessary imports:
 *    import java.math.BigDecimal;
 *    import java.util.List;
 *    import java.util.Optional;
 *    import org.springframework.data.jpa.repository.Query;
 *    import org.springframework.data.repository.query.Param;
 * 
 * >>>>>>>>>>>
 */
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    
    // TODO: Implement your solution here
    
} 