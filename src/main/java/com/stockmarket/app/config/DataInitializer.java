package com.stockmarket.app.config;

import com.stockmarket.app.model.Stock;
import com.stockmarket.app.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Initializer Configuration
 * 
 * This class initializes the database with sample data when the application starts.
 * It uses Spring's CommandLineRunner to run code after the application context is loaded.
 * 
 * Key annotations:
 * @Configuration - Marks this class as a source of bean definitions
 * @Profile("!prod") - Only activate this configuration when the "prod" profile is NOT active
 *                     This ensures we don't initialize sample data in production
 * @Slf4j - Adds a logger field from Lombok
 * @RequiredArgsConstructor - Generates constructor for final fields
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    /**
     * Defines a bean that will run after the application starts.
     * The @Bean annotation registers this method as a Spring bean.
     * The @Profile annotation ensures this bean is only created when not in production.
     * 
     * CommandLineRunner is a functional interface with a single run method that
     * Spring Boot will call after the application context is loaded.
     * 
     * @param stockRepository injected by Spring through constructor injection
     * @return CommandLineRunner to execute
     */
    @Bean
    @Profile("!prod")
    public CommandLineRunner initData(StockRepository stockRepository) {
        return args -> {
            // Skip if data already exists
            if (stockRepository.count() > 0) {
                log.info("Database already has data, skipping initialization");
                return;
            }

            log.info("Initializing database with sample stocks...");

            List<Stock> sampleStocks = createSampleStocks();
            stockRepository.saveAll(sampleStocks);

            log.info("Sample data initialization complete. Added {} stocks.", sampleStocks.size());
        };
    }

    /**
     * Create a list of sample stock entities for database initialization.
     * 
     * @return List of Stock entities
     */
    private List<Stock> createSampleStocks() {
        return List.of(
                createStock("AAPL", "Apple Inc.", new BigDecimal("194.50"), new BigDecimal("192.75"), 35000000L, "Technology", "Consumer Electronics"),
                createStock("MSFT", "Microsoft Corporation", new BigDecimal("423.10"), new BigDecimal("421.80"), 22000000L, "Technology", "Software"),
                createStock("GOOGL", "Alphabet Inc.", new BigDecimal("175.85"), new BigDecimal("173.20"), 18500000L, "Technology", "Internet Services"),
                createStock("AMZN", "Amazon.com Inc.", new BigDecimal("182.90"), new BigDecimal("180.50"), 27800000L, "Consumer Discretionary", "E-Commerce"),
                createStock("META", "Meta Platforms Inc.", new BigDecimal("473.28"), new BigDecimal("465.40"), 19600000L, "Communication Services", "Social Media"),
                createStock("TSLA", "Tesla Inc.", new BigDecimal("248.50"), new BigDecimal("252.70"), 32100000L, "Consumer Discretionary", "Automotive"),
                createStock("NVDA", "NVIDIA Corporation", new BigDecimal("124.65"), new BigDecimal("122.30"), 42500000L, "Technology", "Semiconductors"),
                createStock("JPM", "JPMorgan Chase & Co.", new BigDecimal("198.30"), new BigDecimal("197.50"), 8900000L, "Financials", "Banking"),
                createStock("V", "Visa Inc.", new BigDecimal("275.15"), new BigDecimal("273.80"), 7400000L, "Financials", "Payment Processing"),
                createStock("JNJ", "Johnson & Johnson", new BigDecimal("158.90"), new BigDecimal("159.40"), 6200000L, "Healthcare", "Pharmaceuticals")
        );
    }

    /**
     * Helper method to create a Stock entity with all necessary calculations.
     * 
     * @param symbol stock symbol
     * @param companyName company name
     * @param currentPrice current price
     * @param previousClose previous closing price
     * @param volume trading volume
     * @param sector market sector
     * @param industry specific industry
     * @return fully populated Stock entity
     */
    private Stock createStock(String symbol, String companyName, BigDecimal currentPrice, BigDecimal previousClose, 
                             Long volume, String sector, String industry) {
        // Calculate price change
        BigDecimal change = currentPrice.subtract(previousClose);
        
        // Calculate percentage change with 4 decimal places, rounded to nearest
        BigDecimal changePercent = change.divide(previousClose, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        // Use Lombok's builder pattern to create the Stock object
        return Stock.builder()
                .symbol(symbol)
                .companyName(companyName)
                .currentPrice(currentPrice)
                .previousClose(previousClose)
                .change(change)
                .changePercent(changePercent)
                .volume(volume)
                .lastUpdated(LocalDateTime.now())
                .sector(sector)
                .industry(industry)
                .build();
    }
} 