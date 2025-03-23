package com.stockmarket.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * >>>>>>>>>>>
 * QUIZ 1: Spring Boot Basics
 * 
 * Your task:
 * 1. Implement this REST controller that:
 *    - Maps to the "/api/portfolios" endpoint (already done)
 *    - Has a GET method that returns a simple "Portfolio service is running" message
 * 
 * HINTS:
 * 1. Add a method with @GetMapping annotation (no path needed for root endpoint)
 * 2. The method should return a String message
 * 3. Example:
 *    @GetMapping
 *    public String portfolioServiceStatus() {
 *        return "Portfolio service is running";
 *    }
 * 4. You can test your endpoint by accessing http://localhost:8080/api/portfolios
 * 
 * >>>>>>>>>>>
 * 
 * >>>>>>>>>>>
 * QUIZ 5: Controller Implementation (Do this after completing Quiz 1-4)
 * 
 * Your task:
 * 1. Update this controller to:
 *    - Inject the PortfolioService
 *    - Implement endpoints for CRUD operations
 *    - Handle validation and error responses
 *    - Return appropriate HTTP status codes
 * 
 * HINTS:
 * 1. Inject the PortfolioService using constructor injection:
 *    private final PortfolioService portfolioService;
 *    
 *    public PortfolioController(PortfolioService portfolioService) {
 *        this.portfolioService = portfolioService;
 *    }
 * 
 * 2. Implement these endpoints:
 *    - GET /api/portfolios - Get all portfolios
 *      @GetMapping
 *      public List<PortfolioDTO> getAllPortfolios() {...}
 *      
 *    - GET /api/portfolios/{id} - Get portfolio by ID
 *      @GetMapping("/{id}")
 *      public PortfolioDTO getPortfolioById(@PathVariable Long id) {...}
 *      
 *    - POST /api/portfolios - Create new portfolio
 *      @PostMapping
 *      @ResponseStatus(HttpStatus.CREATED)
 *      public PortfolioDTO createPortfolio(@Valid @RequestBody PortfolioDTO portfolio) {...}
 *      
 *    - PUT /api/portfolios/{id} - Update portfolio
 *      @PutMapping("/{id}")
 *      public PortfolioDTO updatePortfolio(@PathVariable Long id, @Valid @RequestBody PortfolioDTO portfolio) {...}
 *      
 *    - DELETE /api/portfolios/{id} - Delete portfolio
 *      @DeleteMapping("/{id}")
 *      @ResponseStatus(HttpStatus.NO_CONTENT)
 *      public void deletePortfolio(@PathVariable Long id) {...}
 *      
 * 3. For error handling, you can either:
 *    - Let exceptions bubble up to a global exception handler (PortfolioExceptionHandler)
 *    - Handle specific exceptions in the controller using try-catch blocks
 *    
 * 4. Use @Valid with @RequestBody to trigger validation of DTOs
 * 
 * >>>>>>>>>>>
 */
@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {
    // @Operation(summary = "Get stocks by sector")
    // public ResponseEntity<List<StockDTO>> getStocksBySector(@PathVariable String sector) {
    //     log.info("REST request to get stocks by sector: {}", sector);
    //     List<StockDTO> stocks = stockService.getStocksBySector(sector);
    //     return ResponseEntity.ok(stocks);
    // }
    // TODO: Implement your solution here
    
} 