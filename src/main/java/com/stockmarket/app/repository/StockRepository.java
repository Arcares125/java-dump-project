package com.stockmarket.app.repository;

import com.stockmarket.app.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Stock Repository Interface - Provides database operations for Stock entities.
 * 
 * Key points about Spring Data JPA repositories:
 * 
 * 1. Extends JpaRepository<EntityType, IdType> - This gives us standard CRUD operations for free
 *    (findAll, findById, save, delete, etc.)
 * 
 * 2. Method names are parsed by Spring Data to automatically create queries
 *    (e.g., findBySymbol will create a query that searches the symbol field)
 * 
 * 3. No implementation needed - Spring Data JPA creates implementations at runtime
 * 
 * 4. @Repository - Marks this interface as a Spring Data repository
 *    (technically optional as JpaRepository is already annotated, but good for clarity)
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    
    /**
     * Find a stock by its symbol.
     * 
     * Since symbol should be unique, we return an Optional<Stock>
     * Spring Data JPA will generate a query like:
     * SELECT * FROM stocks WHERE symbol = ?
     * 
     * @param symbol the stock symbol to search for
     * @return Optional containing the stock if found, empty otherwise
     */
    Optional<Stock> findBySymbol(String symbol);
    
    /**
     * Find all stocks in a given sector.
     * 
     * Spring Data JPA will generate a query like:
     * SELECT * FROM stocks WHERE sector = ?
     * 
     * @param sector the sector to search for
     * @return List of stocks in the specified sector
     */
    List<Stock> findBySector(String sector);
    
    /**
     * Find all stocks in a given industry.
     * 
     * Spring Data JPA will generate a query like:
     * SELECT * FROM stocks WHERE industry = ?
     * 
     * @param industry the industry to search for
     * @return List of stocks in the specified industry
     */
    List<Stock> findByIndustry(String industry);
    
    /**
     * Find the top 10 stocks with the highest trading volume.
     * 
     * Spring Data JPA will generate a query like:
     * SELECT * FROM stocks ORDER BY volume DESC LIMIT 10
     * 
     * @return List of the 10 most actively traded stocks
     */
    List<Stock> findTop10ByOrderByVolumeDesc();
    
    /**
     * Find the top 10 stocks with the highest percentage gain.
     * 
     * Spring Data JPA will generate a query like:
     * SELECT * FROM stocks ORDER BY change_percent DESC LIMIT 10
     * 
     * @return List of the 10 stocks with the highest percentage gain
     */
    List<Stock> findTop10ByOrderByChangePercentDesc();
} 