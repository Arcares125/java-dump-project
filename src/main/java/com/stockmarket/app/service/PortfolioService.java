package com.stockmarket.app.service;

import com.stockmarket.app.dto.PortfolioDTO;

import java.util.List;
import java.math.BigDecimal;

/**
 * >>>>>>>>>>>
 * QUIZ 4: Service Layer Implementation (Part 1)
 * 
 * Your task:
 * 1. Complete this PortfolioService interface with methods for:
 *    - Create, read, update, delete operations
 *    - Finding by name
 *    - Finding by username
 *    - Finding portfolios with value greater than a specified amount
 * 
 * 2. Think about which exceptions might be thrown and include them in method signatures if needed
 * 
 * HINTS:
 * 1. Define these CRUD operation methods:
 *    // Create a new portfolio
 *    PortfolioDTO createPortfolio(PortfolioDTO portfolioDTO);
 *    
 *    // Get a portfolio by its ID
 *    PortfolioDTO getPortfolioById(Long id);
 *    
 *    // Update an existing portfolio
 *    PortfolioDTO updatePortfolio(Long id, PortfolioDTO portfolioDTO);
 *    
 *    // Delete a portfolio by its ID
 *    void deletePortfolio(Long id);
 *    
 *    // Get all portfolios
 *    List<PortfolioDTO> getAllPortfolios();
 * 
 * 2. Define these finder methods:
 *    // Find a portfolio by its name
 *    PortfolioDTO getPortfolioByName(String name);
 *    
 *    // Find all portfolios for a user
 *    List<PortfolioDTO> getPortfoliosByUsername(String username);
 *    
 *    // Find portfolios with a total value greater than specified amount
 *    List<PortfolioDTO> getPortfoliosByMinimumValue(BigDecimal minValue);
 * 
 * 3. For exceptions, Spring's approach is to use unchecked exceptions, so you typically
 *    don't need to declare them in method signatures. Your implementation will throw:
 *    - EntityNotFoundException - When a requested entity doesn't exist
 *    - IllegalArgumentException - When input validation fails
 * 
 * 4. Add necessary imports:
 *    import java.math.BigDecimal;
 * 
 * >>>>>>>>>>>
 */
public interface PortfolioService {
    
    // TODO: Implement your solution here
    
} 