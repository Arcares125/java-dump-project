package com.stockmarket.app.controller;

import com.stockmarket.app.dto.StockCreateRequest;
import com.stockmarket.app.dto.StockDTO;
import com.stockmarket.app.dto.StockUpdateRequest;
import com.stockmarket.app.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Stock API operations.
 * 
 * Key Spring Web annotations:
 * 
 * @RestController - Combines @Controller and @ResponseBody, making the class handle REST requests
 *                   and automatically serialize responses to JSON/XML
 * 
 * @RequestMapping - Maps HTTP requests to handler methods; here it sets the base URL path
 * 
 * @PostMapping, @GetMapping, @PutMapping, @DeleteMapping - Shortcuts for @RequestMapping
 *                                                           with specific HTTP methods
 * 
 * @PathVariable - Extracts values from the URL path 
 * 
 * @RequestBody - Binds the HTTP request body to an object
 * 
 * @Valid - Triggers validation of the annotated object
 * 
 * OpenAPI/Swagger annotations:
 * @Tag - Groups operations together in the API documentation
 * @Operation - Describes an API operation
 */
@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Stock API", description = "APIs for stock management")
public class StockController {

    // Dependency injected through constructor (RequiredArgsConstructor)
    private final StockService stockService;

    /**
     * Create a new stock.
     * 
     * HTTP POST /api/stocks
     * 
     * @Valid ensures that the request body is validated according to the constraints
     * defined in the StockCreateRequest class
     * 
     * ResponseEntity allows us to control the HTTP response status code and headers
     */
    @PostMapping
    @Operation(summary = "Create a new stock")
    public ResponseEntity<StockDTO> createStock(@Valid @RequestBody StockCreateRequest request) {
        log.info("REST request to create stock: {}", request.getSymbol());
        StockDTO createdStock = stockService.createStock(request);
        // Return 201 Created status with the created resource
        return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
    }

    /**
     * Get a stock by ID.
     * 
     * HTTP GET /api/stocks/{id}
     * 
     * @PathVariable extracts the ID from the URL path
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get stock by ID")
    public ResponseEntity<StockDTO> getStockById(@PathVariable Long id) {
        log.info("REST request to get stock by ID: {}", id);
        StockDTO stock = stockService.getStockById(id);
        return ResponseEntity.ok(stock);
    }

    /**
     * Get a stock by symbol.
     * 
     * HTTP GET /api/stocks/symbol/{symbol}
     */
    @GetMapping("/symbol/{symbol}")
    @Operation(summary = "Get stock by symbol")
    public ResponseEntity<StockDTO> getStockBySymbol(@PathVariable String symbol) {
        log.info("REST request to get stock by symbol: {}", symbol);
        StockDTO stock = stockService.getStockBySymbol(symbol);
        return ResponseEntity.ok(stock);
    }

    /**
     * Get all stocks.
     * 
     * HTTP GET /api/stocks
     */
    @GetMapping
    @Operation(summary = "Get all stocks")
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        log.info("REST request to get all stocks");
        List<StockDTO> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    /**
     * Get stocks by sector.
     * 
     * HTTP GET /api/stocks/sector/{sector}
     */
    @GetMapping("/sector/{sector}")
    @Operation(summary = "Get stocks by sector")
    public ResponseEntity<List<StockDTO>> getStocksBySector(@PathVariable String sector) {
        log.info("REST request to get stocks by sector: {}", sector);
        List<StockDTO> stocks = stockService.getStocksBySector(sector);
        return ResponseEntity.ok(stocks);
    }

    /**
     * Get stocks by industry.
     * 
     * HTTP GET /api/stocks/industry/{industry}
     */
    @GetMapping("/industry/{industry}")
    @Operation(summary = "Get stocks by industry")
    public ResponseEntity<List<StockDTO>> getStocksByIndustry(@PathVariable String industry) {
        log.info("REST request to get stocks by industry: {}", industry);
        List<StockDTO> stocks = stockService.getStocksByIndustry(industry);
        return ResponseEntity.ok(stocks);
    }

    /**
     * Get most active stocks.
     * 
     * HTTP GET /api/stocks/most-active
     */
    @GetMapping("/most-active")
    @Operation(summary = "Get most active stocks by volume")
    public ResponseEntity<List<StockDTO>> getMostActiveStocks() {
        log.info("REST request to get most active stocks");
        List<StockDTO> stocks = stockService.getMostActiveStocks();
        return ResponseEntity.ok(stocks);
    }

    /**
     * Get top gaining stocks.
     * 
     * HTTP GET /api/stocks/top-gainers
     */
    @GetMapping("/top-gainers")
    @Operation(summary = "Get top gaining stocks")
    public ResponseEntity<List<StockDTO>> getTopGainers() {
        log.info("REST request to get top gaining stocks");
        List<StockDTO> stocks = stockService.getTopGainers();
        return ResponseEntity.ok(stocks);
    }

    /**
     * Update a stock.
     * 
     * HTTP PUT /api/stocks/symbol/{symbol}
     * 
     * With PUT, we typically replace the entire resource.
     * Here we're doing a partial update for demonstration purposes, but in a strict RESTful API,
     * you might want to use PATCH for partial updates.
     */
    @PutMapping("/symbol/{symbol}")
    @Operation(summary = "Update stock by symbol")
    public ResponseEntity<StockDTO> updateStock(
            @PathVariable String symbol,
            @Valid @RequestBody StockUpdateRequest request
    ) {
        log.info("REST request to update stock: {}", symbol);
        StockDTO updatedStock = stockService.updateStock(symbol, request);
        return ResponseEntity.ok(updatedStock);
    }

    /**
     * Delete a stock.
     * 
     * HTTP DELETE /api/stocks/symbol/{symbol}
     * 
     * Returns 204 No Content on success, indicating that the request was successful
     * but there is no content to return.
     */
    @DeleteMapping("/symbol/{symbol}")
    @Operation(summary = "Delete stock by symbol")
    public ResponseEntity<Void> deleteStock(@PathVariable String symbol) {
        log.info("REST request to delete stock: {}", symbol);
        stockService.deleteStock(symbol);
        return ResponseEntity.noContent().build();
    }
} 