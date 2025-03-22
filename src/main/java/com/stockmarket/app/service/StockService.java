package com.stockmarket.app.service;

import com.stockmarket.app.dto.StockCreateRequest;
import com.stockmarket.app.dto.StockDTO;
import com.stockmarket.app.dto.StockUpdateRequest;

import java.util.List;

/**
 * Stock Service Interface
 * 
 * This interface defines the business operations for managing stocks.
 * It acts as a contract between controllers and implementation classes.
 * 
 * Benefits of using service interfaces:
 * 1. Separation of concerns - isolates business logic from controllers
 * 2. Abstraction - hides implementation details
 * 3. Testability - easier to mock for testing controllers
 * 4. Flexibility - allows multiple implementations (e.g., real vs. mock for testing)
 * 
 * The service layer is where business logic, validation, and transaction management happens.
 */
public interface StockService {
    
    /**
     * Create a new stock in the system.
     * 
     * @param request DTO containing stock details
     * @return StockDTO representing the created stock
     * @throws IllegalArgumentException if a stock with the same symbol already exists
     */
    StockDTO createStock(StockCreateRequest request);
    
    /**
     * Retrieve a stock by its unique ID.
     * 
     * @param id unique identifier of the stock
     * @return StockDTO representing the found stock
     * @throws jakarta.persistence.EntityNotFoundException if no stock with the ID exists
     */
    StockDTO getStockById(Long id);
    
    /**
     * Retrieve a stock by its symbol.
     * 
     * @param symbol stock ticker symbol
     * @return StockDTO representing the found stock
     * @throws jakarta.persistence.EntityNotFoundException if no stock with the symbol exists
     */
    StockDTO getStockBySymbol(String symbol);
    
    /**
     * Get all stocks in the system.
     * 
     * @return List of all stocks
     */
    List<StockDTO> getAllStocks();
    
    /**
     * Get all stocks in a specific sector.
     * 
     * @param sector the market sector
     * @return List of stocks in the specified sector
     */
    List<StockDTO> getStocksBySector(String sector);
    
    /**
     * Get all stocks in a specific industry.
     * 
     * @param industry the industry
     * @return List of stocks in the specified industry
     */
    List<StockDTO> getStocksByIndustry(String industry);
    
    /**
     * Get the most actively traded stocks by volume.
     * 
     * @return List of the top 10 most active stocks
     */
    List<StockDTO> getMostActiveStocks();
    
    /**
     * Get the stocks with the highest percentage gains.
     * 
     * @return List of the top 10 gainers
     */
    List<StockDTO> getTopGainers();
    
    /**
     * Update an existing stock.
     * 
     * @param symbol the symbol of the stock to update
     * @param request DTO containing the fields to update
     * @return StockDTO representing the updated stock
     * @throws jakarta.persistence.EntityNotFoundException if no stock with the symbol exists
     */
    StockDTO updateStock(String symbol, StockUpdateRequest request);
    
    /**
     * Delete a stock from the system.
     * 
     * @param symbol the symbol of the stock to delete
     * @throws jakarta.persistence.EntityNotFoundException if no stock with the symbol exists
     */
    void deleteStock(String symbol);
} 