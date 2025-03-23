package com.stockmarket.app.service;

import com.stockmarket.app.dto.StockCreateRequest;
import com.stockmarket.app.dto.StockDTO;
import com.stockmarket.app.dto.StockUpdateRequest;
import com.stockmarket.app.model.Stock;
import com.stockmarket.app.repository.StockRepository;
import com.stockmarket.app.service.impl.StockServiceImpl;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Tests for StockService
 * 
 * This class demonstrates JUnit 5 unit testing with Mockito.
 * 
 * Key annotations:
 * @ExtendWith(MockitoExtension.class) - Integrates JUnit 5 with Mockito
 * @Mock - Creates a mock implementation of the specified class
 * @InjectMocks - Injects the mocks into the class being tested
 * @BeforeEach - Methods run before each test
 * @Test - Marks a method as a test case
 * @DisplayName - Provides a custom name for the test
 */
@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    /**
     * Mock the repository - we don't want to use the real database for unit tests.
     * This creates a mock implementation of StockRepository that we can program
     * to return whatever we need for our tests.
     */
    @Mock
    private StockRepository stockRepository;

    /**
     * Inject mocks into the service.
     * This creates an instance of StockServiceImpl and injects the mocked
     * StockRepository into it.
     */
    @InjectMocks
    private StockServiceImpl stockService;

    // Test data
    private Stock testStock;
    private StockCreateRequest createRequest;
    private StockUpdateRequest updateRequest;

    /**
     * Set up test data before each test.
     */
    @BeforeEach
    void setUp() {
        // Create test data
        testStock = Stock.builder()
                .id(1L)
                .symbol("AAPL")
                .companyName("Apple Inc.")
                .currentPrice(new BigDecimal("150.00"))
                .previousClose(new BigDecimal("148.50"))
                .change(new BigDecimal("1.50"))
                .changePercent(new BigDecimal("1.01"))
                .volume(1000000L)
                .lastUpdated(LocalDateTime.now())
                .sector("Technology")
                .industry("Consumer Electronics")
                .build();

        // Setup create request
        createRequest = new StockCreateRequest();
        createRequest.setSymbol("AAPL");
        createRequest.setCompanyName("Apple Inc.");
        createRequest.setCurrentPrice(new BigDecimal("150.00"));
        createRequest.setPreviousClose(new BigDecimal("148.50"));
        createRequest.setVolume(1000000L);
        createRequest.setSector("Technology");
        createRequest.setIndustry("Consumer Electronics");

        // Setup update request
        updateRequest = new StockUpdateRequest();
        updateRequest.setCompanyName("Apple Inc. Updated");
        updateRequest.setCurrentPrice(new BigDecimal("152.00"));
    }

    /**
     * Test creating a stock successfully.
     * 
     * This test:
     * 1. Mocks the repository to return empty when checking if stock exists
     * 2. Mocks the save method to return our test stock
     * 3. Verifies the returned DTO has expected values
     * 4. Verifies the save method was called exactly once
     */
    @Test
    @DisplayName("Should create stock successfully when it doesn't exist")
    void createStock_Success() {
        // Setup mock behavior
        when(stockRepository.findBySymbol("AAPL")).thenReturn(Optional.empty());
        when(stockRepository.save(any(Stock.class))).thenReturn(testStock);

        // Execute service method
        StockDTO result = stockService.createStock(createRequest);

        // Verify results
        assertNotNull(result);
        assertEquals("AAPL", result.getSymbol());
        assertEquals("Apple Inc.", result.getCompanyName());
        assertEquals(0, new BigDecimal("150.00").compareTo(result.getCurrentPrice()));
        
        // Verify the repository was called correctly
        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    /**
     * Test failure when trying to create a stock that already exists.
     * 
     * This test:
     * 1. Mocks the repository to return a stock when checking if it exists
     * 2. Verifies that an IllegalArgumentException is thrown
     * 3. Verifies the save method was never called
     */
    @Test
    @DisplayName("Should throw exception when creating stock that already exists")
    void createStock_AlreadyExists() {
        // Setup mock behavior
        when(stockRepository.findBySymbol("AAPL")).thenReturn(Optional.of(testStock));

        // Execute and verify exception
        assertThrows(IllegalArgumentException.class, () -> {
            stockService.createStock(createRequest);
        });

        // Verify the repository save method was never called
        verify(stockRepository, never()).save(any(Stock.class));
    }

    /**
     * Test getting a stock by ID successfully.
     */
    @Test
    @DisplayName("Should get stock by ID when it exists")
    void getStockById_Success() {
        // Setup mock behavior
        when(stockRepository.findById(1L)).thenReturn(Optional.of(testStock));

        // Execute service method
        StockDTO result = stockService.getStockById(1L);

        // Verify results
        assertNotNull(result);
        assertEquals("AAPL", result.getSymbol());
        assertEquals(1L, result.getId());
    }

    /**
     * Test failure when getting a stock by ID that doesn't exist.
     */
    @Test
    @DisplayName("Should throw exception when getting stock by ID that doesn't exist")
    void getStockById_NotFound() {
        // Setup mock behavior
        when(stockRepository.findById(1L)).thenReturn(Optional.empty());

        // Execute and verify exception
        assertThrows(EntityNotFoundException.class, () -> {
            stockService.getStockById(1L);
        });
    }

    /**
     * Test getting all stocks successfully.
     */
    @Test
    @DisplayName("Should get all stocks")
    void getAllStocks_Success() {
        // Create a second stock
        Stock stock2 = Stock.builder()
                .id(2L)
                .symbol("MSFT")
                .companyName("Microsoft Corporation")
                .currentPrice(new BigDecimal("350.00"))
                .build();

        // Setup mock behavior
        when(stockRepository.findAll()).thenReturn(Arrays.asList(testStock, stock2));

        // Execute service method
        List<StockDTO> results = stockService.getAllStocks();

        // Verify results
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("AAPL", results.get(0).getSymbol());
        assertEquals("MSFT", results.get(1).getSymbol());
    }

    /**
     * Test updating a stock successfully.
     */
    @Test
    @DisplayName("Should update stock successfully when it exists")
    void updateStock_Success() {
        // Setup mock behavior
        when(stockRepository.findBySymbol("AAPL")).thenReturn(Optional.of(testStock));
        
        // Create updated stock to return from save
        Stock updatedStock = Stock.builder()
                .id(1L)
                .symbol("AAPL")
                .companyName("Apple Inc. Updated")
                .currentPrice(new BigDecimal("152.00"))
                .previousClose(new BigDecimal("148.50"))
                .change(new BigDecimal("3.50"))
                .changePercent(new BigDecimal("2.36"))
                .volume(1000000L)
                .lastUpdated(LocalDateTime.now())
                .sector("Technology")
                .industry("Consumer Electronics")
                .build();
        
        when(stockRepository.save(any(Stock.class))).thenReturn(updatedStock);

        // Execute service method
        StockDTO result = stockService.updateStock("AAPL", updateRequest);

        // Verify results
        assertNotNull(result);
        assertEquals("Apple Inc. Updated", result.getCompanyName());
        assertEquals(0, new BigDecimal("152.00").compareTo(result.getCurrentPrice()));
        
        // Verify the repository was called correctly
        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    /**
     * Test failure when updating a stock that doesn't exist.
     */
    @Test
    @DisplayName("Should throw exception when updating stock that doesn't exist")
    void updateStock_NotFound() {
        // Setup mock behavior
        when(stockRepository.findBySymbol("AAPL")).thenReturn(Optional.empty());

        // Execute and verify exception
        assertThrows(EntityNotFoundException.class, () -> {
            stockService.updateStock("AAPL", updateRequest);
        });

        // Verify the repository save method was never called
        verify(stockRepository, never()).save(any(Stock.class));
    }

    /**
     * Test deleting a stock successfully.
     */
    @Test
    @DisplayName("Should delete stock successfully when it exists")
    void deleteStock_Success() {
        // Setup mock behavior
        when(stockRepository.findBySymbol("AAPL")).thenReturn(Optional.of(testStock));
        doNothing().when(stockRepository).delete(any(Stock.class));

        // Execute service method
        stockService.deleteStock("AAPL");

        // Verify the repository was called correctly
        verify(stockRepository, times(1)).delete(any(Stock.class));
    }

    /**
     * Test failure when deleting a stock that doesn't exist.
     */
    @Test
    @DisplayName("Should throw exception when deleting stock that doesn't exist")
    void deleteStock_NotFound() {
        // Setup mock behavior
        when(stockRepository.findBySymbol("AAPL")).thenReturn(Optional.empty());

        // Execute and verify exception
        assertThrows(EntityNotFoundException.class, () -> {
            stockService.deleteStock("AAPL");
        });

        // Verify the repository delete method was never called
        verify(stockRepository, never()).delete(any(Stock.class));
    }
} 