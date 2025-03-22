package com.stockmarket.app.service;

import com.stockmarket.app.dto.StockPriceUpdateDTO;
import com.stockmarket.app.model.Stock;
import com.stockmarket.app.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the StockPriceSimulatorService
 */
@ExtendWith(MockitoExtension.class)
class StockPriceSimulatorServiceTest {

    @Mock
    private StockRepository stockRepository;
    
    @Mock
    private KafkaProducerService kafkaProducerService;
    
    @InjectMocks
    private StockPriceSimulatorService simulatorService;
    
    @Captor
    private ArgumentCaptor<Stock> stockCaptor;
    
    @Captor
    private ArgumentCaptor<StockPriceUpdateDTO> updateCaptor;
    
    private List<Stock> testStocks;
    
    @BeforeEach
    void setUp() {
        // Configure simulator properties
        ReflectionTestUtils.setField(simulatorService, "enabled", true);
        ReflectionTestUtils.setField(simulatorService, "minChangePercent", -5.0);
        ReflectionTestUtils.setField(simulatorService, "maxChangePercent", 5.0);
        
        // Create test stocks
        Stock stock1 = Stock.builder()
                .id(1L)
                .symbol("AAPL")
                .companyName("Apple Inc.")
                .currentPrice(new BigDecimal("150.00"))
                .previousClose(new BigDecimal("149.00"))
                .volume(1000000L)
                .lastUpdated(LocalDateTime.now())
                .build();
        
        Stock stock2 = Stock.builder()
                .id(2L)
                .symbol("GOOGL")
                .companyName("Alphabet Inc.")
                .currentPrice(new BigDecimal("2800.00"))
                .previousClose(new BigDecimal("2750.00"))
                .volume(500000L)
                .lastUpdated(LocalDateTime.now())
                .build();
        
        testStocks = Arrays.asList(stock1, stock2);
    }
    
    @Test
    @DisplayName("Should simulate price changes for all stocks")
    void simulateStockPriceChanges_Success() {
        // Given
        when(stockRepository.findAll()).thenReturn(testStocks);
        when(stockRepository.save(any(Stock.class))).thenAnswer(i -> i.getArgument(0));
        
        // When
        simulatorService.simulateStockPriceChanges();
        
        // Then
        verify(stockRepository, times(2)).save(stockCaptor.capture());
        verify(kafkaProducerService, times(2)).sendStockPriceUpdate(updateCaptor.capture());
        
        List<Stock> updatedStocks = stockCaptor.getAllValues();
        List<StockPriceUpdateDTO> updates = updateCaptor.getAllValues();
        
        // Verify first stock update
        assertEquals("AAPL", updatedStocks.get(0).getSymbol());
        assertNotEquals(new BigDecimal("150.00"), updatedStocks.get(0).getCurrentPrice());
        
        // Verify first Kafka update
        assertEquals("AAPL", updates.get(0).getSymbol());
        assertEquals(updatedStocks.get(0).getCurrentPrice(), updates.get(0).getPrice());
        
        // Verify second stock update
        assertEquals("GOOGL", updatedStocks.get(1).getSymbol());
        assertNotEquals(new BigDecimal("2800.00"), updatedStocks.get(1).getCurrentPrice());
        
        // Verify second Kafka update
        assertEquals("GOOGL", updates.get(1).getSymbol());
        assertEquals(updatedStocks.get(1).getCurrentPrice(), updates.get(1).getPrice());
    }
    
    @Test
    @DisplayName("Should skip price simulation when disabled")
    void simulateStockPriceChanges_Disabled() {
        // Given
        ReflectionTestUtils.setField(simulatorService, "enabled", false);
        
        // When
        simulatorService.simulateStockPriceChanges();
        
        // Then
        verify(stockRepository, never()).findAll();
        verify(stockRepository, never()).save(any());
        verify(kafkaProducerService, never()).sendStockPriceUpdate(any());
    }
    
    @Test
    @DisplayName("Should skip price simulation when no stocks exist")
    void simulateStockPriceChanges_NoStocks() {
        // Given
        when(stockRepository.findAll()).thenReturn(List.of());
        
        // When
        simulatorService.simulateStockPriceChanges();
        
        // Then
        verify(stockRepository, times(1)).findAll();
        verify(stockRepository, never()).save(any());
        verify(kafkaProducerService, never()).sendStockPriceUpdate(any());
    }
    
    @Test
    @DisplayName("Should ensure stock price doesn't go below 0.01")
    void simulateStockPriceChanges_MinimumPrice() {
        // Given
        Stock lowPriceStock = Stock.builder()
                .id(3L)
                .symbol("PENNY")
                .companyName("Penny Stock Inc.")
                .currentPrice(new BigDecimal("0.02"))
                .previousClose(new BigDecimal("0.02"))
                .volume(100000L)
                .lastUpdated(LocalDateTime.now())
                .build();
        
        when(stockRepository.findAll()).thenReturn(List.of(lowPriceStock));
        when(stockRepository.save(any(Stock.class))).thenAnswer(i -> i.getArgument(0));
        
        // Set min change to a large negative value to force price reduction
        ReflectionTestUtils.setField(simulatorService, "minChangePercent", -90.0);
        ReflectionTestUtils.setField(simulatorService, "maxChangePercent", -50.0);
        
        // When
        simulatorService.simulateStockPriceChanges();
        
        // Then
        verify(stockRepository).save(stockCaptor.capture());
        
        Stock updatedStock = stockCaptor.getValue();
        // Verify that price was limited to minimum of 0.01
        assertTrue(updatedStock.getCurrentPrice().compareTo(new BigDecimal("0.01")) >= 0);
    }
} 