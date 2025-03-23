package com.stockmarket.app.service.impl;

import com.stockmarket.app.dto.StockCreateRequest;
import com.stockmarket.app.dto.StockDTO;
import com.stockmarket.app.dto.StockPriceUpdateDTO;
import com.stockmarket.app.dto.StockUpdateRequest;
import com.stockmarket.app.model.Stock;
import com.stockmarket.app.repository.StockRepository;
import com.stockmarket.app.service.KafkaProducerService;
import com.stockmarket.app.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the StockService interface.
 * 
 * Key Spring annotations:
 * 
 * @Service - Marks this as a Spring service component, eligible for dependency injection
 * 
 * @Transactional - Defines transaction boundaries; ensures database operations within
 * a method are atomic (either all succeed or all fail)
 * 
 * @RequiredArgsConstructor - Lombok annotation that generates a constructor for all final fields,
 * which is used by Spring for dependency injection
 * 
 * @Slf4j - Lombok annotation that adds a logger field for logging
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StockServiceImpl implements StockService {

    /**
     * The repository is marked as final because:
     * 1. It's required by @RequiredArgsConstructor to generate a constructor
     * 2. It emphasizes that this dependency won't change after initialization
     * 3. It's a good practice for required dependencies
     */
    private final StockRepository stockRepository;
    private final KafkaProducerService kafkaProducerService;

    /**
     * {@inheritDoc}
     * 
     * @Transactional ensures that the entire method runs in a transaction,
     * so if any exception occurs, the database changes will be rolled back.
     */
    @Override
    @Transactional
    public StockDTO createStock(StockCreateRequest request) {
        log.info("Creating new stock with symbol: {}", request.getSymbol());
        
        // Check if stock already exists
        if (stockRepository.findBySymbol(request.getSymbol()).isPresent()) {
            log.warn("Stock with symbol {} already exists", request.getSymbol());
            throw new IllegalArgumentException("Stock with symbol " + request.getSymbol() + " already exists");
        }

        // Use the builder pattern (from Lombok) to create a new Stock entity
        Stock stock = Stock.builder()
                .symbol(request.getSymbol())
                .companyName(request.getCompanyName())
                .currentPrice(request.getCurrentPrice())
                .previousClose(request.getPreviousClose())
                .volume(request.getVolume())
                .sector(request.getSector())
                .industry(request.getIndustry())
                .lastUpdated(LocalDateTime.now())
                .build();

        // Calculate change and change percent if previousClose is available
        if (request.getPreviousClose() != null) {
            stock.setChange(request.getCurrentPrice().subtract(request.getPreviousClose()));
            stock.setChangePercent(
                    stock.getChange().divide(request.getPreviousClose(), 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100))
            );
        }

        // Save the stock to the database and map to DTO
        Stock savedStock = stockRepository.save(stock);
        log.info("Stock created successfully with ID: {}", savedStock.getId());
        return mapToDTO(savedStock);
    }

    /**
     * {@inheritDoc}
     * 
     * @Transactional(readOnly = true) is an optimization that tells Spring this method
     * only reads data and doesn't modify it. This can improve performance in some cases.
     */
    @Override
    @Transactional(readOnly = true)
    public StockDTO getStockById(Long id) {
        log.debug("Getting stock by ID: {}", id);
        return stockRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> {
                    log.warn("Stock not found with ID: {}", id);
                    return new EntityNotFoundException("Stock not found with id " + id);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public StockDTO getStockBySymbol(String symbol) {
        log.debug("Getting stock by symbol: {}", symbol);
        return stockRepository.findBySymbol(symbol)
                .map(this::mapToDTO)
                .orElseThrow(() -> {
                    log.warn("Stock not found with symbol: {}", symbol);
                    return new EntityNotFoundException("Stock not found with symbol " + symbol);
                });
    }

    /**
     * {@inheritDoc}
     * 
     * This method demonstrates Java 8 Stream API for transforming collections.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockDTO> getAllStocks() {
        log.debug("Getting all stocks");
        return stockRepository.findAll().stream()  // Convert list to stream
                .map(this::mapToDTO)               // Transform each Stock to StockDTO
                .collect(Collectors.toList());     // Collect results back to a list
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockDTO> getStocksBySector(String sector) {
        log.debug("Getting stocks by sector: {}", sector);
        return stockRepository.findBySector(sector).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockDTO> getStocksByIndustry(String industry) {
        log.debug("Getting stocks by industry: {}", industry);
        return stockRepository.findByIndustry(industry).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockDTO> getMostActiveStocks() {
        log.debug("Getting most active stocks");
        return stockRepository.findTop10ByOrderByVolumeDesc().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockDTO> getTopGainers() {
        log.debug("Getting top gaining stocks");
        return stockRepository.findTop10ByOrderByChangePercentDesc().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     * 
     * This method demonstrates partial updates - only update fields that are provided.
     */
    @Override
    @Transactional
    public StockDTO updateStock(String symbol, StockUpdateRequest request) {
        log.info("Updating stock with symbol: {}", symbol);
        
        // Find the stock or throw exception if not found
        Stock stock = stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> {
                    log.warn("Stock not found with symbol: {}", symbol);
                    return new EntityNotFoundException("Stock not found with symbol " + symbol);
                });

        // Conditionally update fields if they are provided in the request
        if (request.getCompanyName() != null) {
            stock.setCompanyName(request.getCompanyName());
        }

        if (request.getCurrentPrice() != null) {
            stock.setCurrentPrice(request.getCurrentPrice());
            
            // Recalculate change and change percent if previousClose is available
            if (stock.getPreviousClose() != null) {
                updateChangeValues(stock);
            }
        }

        if (request.getPreviousClose() != null) {
            stock.setPreviousClose(request.getPreviousClose());
            
            // Recalculate change and change percent if we have both prices
            if (stock.getCurrentPrice() != null) {
                updateChangeValues(stock);
            }
        }

        if (request.getVolume() != null) {
            stock.setVolume(request.getVolume());
        }

        if (request.getSector() != null) {
            stock.setSector(request.getSector());
        }

        if (request.getIndustry() != null) {
            stock.setIndustry(request.getIndustry());
        }

        // Always update the lastUpdated timestamp
        stock.setLastUpdated(LocalDateTime.now());
        
        // Save updates and return the updated stock
        Stock updatedStock = stockRepository.save(stock);
        log.info("Stock updated successfully: {}", symbol);
        return mapToDTO(updatedStock);
    }

    /**
     * Helper method to update change and change percent values.
     * Also sends a price update notification via Kafka.
     */
    private void updateChangeValues(Stock stock) {
        // Calculate change (difference between current price and previous close)
        stock.setChange(stock.getCurrentPrice().subtract(stock.getPreviousClose()));
        
        // Calculate change percent
        stock.setChangePercent(
                stock.getChange().divide(stock.getPreviousClose(), 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
        );
        
        // Send price update to Kafka
        StockPriceUpdateDTO update = StockPriceUpdateDTO.builder()
                .symbol(stock.getSymbol())
                .price(stock.getCurrentPrice())
                .change(stock.getChange())
                .changePercent(stock.getChangePercent())
                .timestamp(LocalDateTime.now())
                .build();
        
        kafkaProducerService.sendStockPriceUpdate(update);
        log.info("Sent price update to Kafka for {}: {}", stock.getSymbol(), stock.getCurrentPrice());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteStock(String symbol) {
        log.info("Deleting stock with symbol: {}", symbol);
        
        Stock stock = stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> {
                    log.warn("Stock not found with symbol: {}", symbol);
                    return new EntityNotFoundException("Stock not found with symbol " + symbol);
                });
                
        stockRepository.delete(stock);
        log.info("Stock deleted successfully: {}", symbol);
    }

    /**
     * Maps a Stock entity to a StockDTO.
     * 
     * This private helper method centralizes the mapping logic for reuse across the service.
     * In larger applications, consider using mapping libraries like MapStruct.
     */
    private StockDTO mapToDTO(Stock stock) {
        return StockDTO.builder()
                .id(stock.getId())
                .symbol(stock.getSymbol())
                .companyName(stock.getCompanyName())
                .currentPrice(stock.getCurrentPrice())
                .previousClose(stock.getPreviousClose())
                .change(stock.getChange())
                .changePercent(stock.getChangePercent())
                .volume(stock.getVolume())
                .lastUpdated(stock.getLastUpdated())
                .sector(stock.getSector())
                .industry(stock.getIndustry())
                .build();
    }
} 