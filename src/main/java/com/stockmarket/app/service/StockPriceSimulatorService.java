package com.stockmarket.app.service;

import com.stockmarket.app.dto.StockPriceUpdateDTO;
import com.stockmarket.app.model.Stock;
import com.stockmarket.app.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Service that simulates stock price changes and publishes updates to Kafka.
 */
@Service
public class StockPriceSimulatorService {
    
    private static final Logger logger = LoggerFactory.getLogger(StockPriceSimulatorService.class);
    private static final Random random = new Random();
    
    private final StockRepository stockRepository;
    private final KafkaProducerService kafkaProducerService;
    
    @Value("${stocksimulator.enabled:true}")
    private boolean enabled;
    
    @Value("${stocksimulator.price-change.min-percent:-5.0}")
    private double minChangePercent;
    
    @Value("${stocksimulator.price-change.max-percent:5.0}")
    private double maxChangePercent;
    
    @Autowired
    public StockPriceSimulatorService(
            StockRepository stockRepository,
            KafkaProducerService kafkaProducerService) {
        this.stockRepository = stockRepository;
        this.kafkaProducerService = kafkaProducerService;
    }
    
    /**
     * Scheduled task that runs every 30 seconds to simulate stock price changes
     * and publish updates to Kafka.
     */
    @Scheduled(fixedRateString = "${stocksimulator.interval:30000}")
    public void simulateStockPriceChanges() {
        if (!enabled) {
            logger.debug("Stock price simulation is disabled");
            return;
        }
        
        List<Stock> stocks = stockRepository.findAll();
        
        if (stocks.isEmpty()) {
            logger.info("No stocks found in database. Skipping price simulation.");
            return;
        }
        
        logger.info("Simulating price changes for {} stocks", stocks.size());
        
        for (Stock stock : stocks) {
            // Calculate a random price change between configured min and max
            double range = maxChangePercent - minChangePercent;
            BigDecimal changePercent = new BigDecimal(random.nextDouble() * range + minChangePercent)
                    .setScale(2, RoundingMode.HALF_UP);
            
            // Calculate the actual change amount
            BigDecimal change = stock.getCurrentPrice()
                    .multiply(changePercent)
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            
            // Calculate new price
            BigDecimal newPrice = stock.getCurrentPrice().add(change)
                    .setScale(2, RoundingMode.HALF_UP);
            
            // Ensure price doesn't go below 0.01
            if (newPrice.compareTo(new BigDecimal("0.01")) < 0) {
                newPrice = new BigDecimal("0.01");
                change = newPrice.subtract(stock.getCurrentPrice());
                changePercent = change.divide(stock.getCurrentPrice(), 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100"))
                        .setScale(2, RoundingMode.HALF_UP);
            }
            
            // Update stock price in database
            stock.setCurrentPrice(newPrice);
            stockRepository.save(stock);
            
            // Create and send price update
            StockPriceUpdateDTO update = StockPriceUpdateDTO.builder()
                    .symbol(stock.getSymbol())
                    .price(newPrice)
                    .change(change)
                    .changePercent(changePercent)
                    .timestamp(LocalDateTime.now())
                    .build();
            
            kafkaProducerService.sendStockPriceUpdate(update);
            
            logger.info("Updated price for {}: {} ({}{} / {}%)", 
                    stock.getSymbol(), 
                    newPrice, 
                    change.compareTo(BigDecimal.ZERO) >= 0 ? "+" : "", 
                    change, 
                    changePercent);
        }
    }
} 