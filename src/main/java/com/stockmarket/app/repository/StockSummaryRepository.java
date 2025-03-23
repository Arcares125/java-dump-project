package com.stockmarket.app.repository;

import com.stockmarket.app.dto.StockSummaryDTO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom repository for complex stock summary queries.
 * 
 * This demonstrates:
 * 1. Creating a custom repository (not extending JpaRepository)
 * 2. Using EntityManager directly for complete control over queries
 * 3. Mapping SQL query results to DTOs
 */
@Repository
public class StockSummaryRepository {

    /**
     * EntityManager provides an API for managing a persistence context
     * and interacting with entities within that context.
     */
    @PersistenceContext
    private EntityManager entityManager;
    
    /**
     * Get summary statistics for all stocks.
     * 
     * This demonstrates:
     * 1. Creating a native SQL query with complex joins and aggregations
     * 2. Mapping results to DTOs for easier consumption
     * 
     * @return List of summary data for each stock
     */
    public List<StockSummaryDTO> getStockSummaries() {
        // SQL query with multiple joins and aggregations
        String sql = """
            SELECT 
                t.stock_symbol as symbol,
                COUNT(t.id) as totalTransactions,
                AVG(t.price_per_share) as averagePrice,
                SUM(t.quantity) as totalVolume
            FROM 
                transactions t
            GROUP BY 
                t.stock_symbol
            ORDER BY 
                totalTransactions DESC
        """;
        
        // Create native query
        Query query = entityManager.createNativeQuery(sql);
        
        // Execute query and get results
        List<Object[]> results = query.getResultList();
        List<StockSummaryDTO> summaries = new ArrayList<>();
        
        // Map each result row to a DTO
        for (Object[] result : results) {
            String symbol = (String) result[0];
            Long totalTransactions = ((Number) result[1]).longValue();
            Double averagePrice = result[2] != null ? ((Number) result[2]).doubleValue() : null;
            Long totalVolume = result[3] != null ? ((Number) result[3]).longValue() : null;
            
            summaries.add(new StockSummaryDTO(symbol, totalTransactions, averagePrice, totalVolume));
        }
        
        return summaries;
    }
    
    /**
     * Get detailed summary for a specific stock symbol.
     * 
     * @param symbol The stock symbol to get summary for
     * @return Summary data for the specified stock
     */
    public StockSummaryDTO getStockSummaryBySymbol(String symbol) {
        // SQL query with parameter and complex calculations
        String sql = """
            SELECT 
                t.stock_symbol as symbol,
                COUNT(t.id) as totalTransactions,
                AVG(t.price_per_share) as averagePrice,
                SUM(t.quantity) as totalVolume
            FROM 
                transactions t
            WHERE 
                t.stock_symbol = :symbol
            GROUP BY 
                t.stock_symbol
        """;
        
        // Create native query with parameter
        Query query = entityManager.createNativeQuery(sql)
                                  .setParameter("symbol", symbol);
        
        // Execute query and get results
        Object[] result = (Object[]) query.getSingleResult();
        
        // Map the result to a DTO
        String stockSymbol = (String) result[0];
        Long totalTransactions = ((Number) result[1]).longValue();
        Double averagePrice = result[2] != null ? ((Number) result[2]).doubleValue() : null;
        Long totalVolume = result[3] != null ? ((Number) result[3]).longValue() : null;
        
        return new StockSummaryDTO(stockSymbol, totalTransactions, averagePrice, totalVolume);
    }
} 