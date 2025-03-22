package com.stockmarket.app.controller;

import com.stockmarket.app.dto.StockSummaryDTO;
import com.stockmarket.app.repository.StockSummaryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for stock summary operations.
 * 
 * Demonstrates:
 * 1. Using a custom repository directly in a controller (without a service layer)
 * 2. Returning DTOs instead of entities
 * 3. Exception handling
 */
@RestController
@RequestMapping("/api/stock-summaries")
public class StockSummaryController {

    private final StockSummaryRepository stockSummaryRepository;

    @Autowired
    public StockSummaryController(StockSummaryRepository stockSummaryRepository) {
        this.stockSummaryRepository = stockSummaryRepository;
    }

    /**
     * Get summaries for all stocks
     * 
     * @return list of stock summaries with HTTP 200 status
     */
    @GetMapping
    public ResponseEntity<List<StockSummaryDTO>> getAllStockSummaries() {
        List<StockSummaryDTO> summaries = stockSummaryRepository.getStockSummaries();
        return ResponseEntity.ok(summaries);
    }

    /**
     * Get summary for a specific stock
     * 
     * @param symbol the stock symbol
     * @return stock summary with HTTP 200 status, or 404 if not found
     */
    @GetMapping("/{symbol}")
    public ResponseEntity<StockSummaryDTO> getStockSummary(@PathVariable String symbol) {
        try {
            StockSummaryDTO summary = stockSummaryRepository.getStockSummaryBySymbol(symbol);
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            throw new EntityNotFoundException("No summary found for stock symbol: " + symbol);
        }
    }
} 