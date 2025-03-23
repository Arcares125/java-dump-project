package com.stockmarket.app.service;

import com.stockmarket.app.dto.StockPriceUpdateDTO;
import com.stockmarket.app.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Service for producing Kafka messages.
 * This service handles all communications with Kafka topics for the application.
 */
@Service
public class KafkaProducerService {
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final boolean isDevProfile;
    
    @Value("${kafka.topics.stock-price-updates:stock-price-updates}")
    private String stockPriceUpdatesTopic;
    
    @Value("${kafka.topics.stock-transactions:stock-transactions}")
    private String stockTransactionsTopic;
    
    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate, Environment environment) {
        this.kafkaTemplate = kafkaTemplate;
        this.isDevProfile = Arrays.asList(environment.getActiveProfiles()).contains("dev");
        
        if (isDevProfile) {
            logger.info("Running in dev profile - using mock Kafka implementation");
        }
    }
    
    /**
     * Send a stock price update to Kafka
     * 
     * @param update the price update data
     */
    public void sendStockPriceUpdate(StockPriceUpdateDTO update) {
        logger.info("Sending stock price update for symbol: {}, price: {}", 
                update.getSymbol(), update.getPrice());
        
        try {
            kafkaTemplate.send(stockPriceUpdatesTopic, update.getSymbol(), update);
            logger.debug("Successfully sent stock price update to Kafka");
        } catch (Exception e) {
            if (!isDevProfile) {
                // Only log as error in non-dev environments
                logger.error("Failed to send stock price update to Kafka: {}", e.getMessage(), e);
            } else {
                logger.info("[DEV MODE] Simulated sending price update for: {}", update.getSymbol());
            }
        }
    }
    
    /**
     * Send a transaction to Kafka
     * 
     * @param transaction the transaction data
     */
    public void sendTransaction(Transaction transaction) {
        logger.info("Sending transaction for symbol: {}", transaction.getStockSymbol());
        
        try {
            kafkaTemplate.send(stockTransactionsTopic, transaction.getStockSymbol(), transaction);
            logger.debug("Successfully sent transaction to Kafka");
        } catch (Exception e) {
            if (!isDevProfile) {
                // Only log as error in non-dev environments
                logger.error("Failed to send transaction to Kafka: {}", e.getMessage(), e);
            } else {
                logger.info("[DEV MODE] Simulated sending transaction for: {}", transaction.getStockSymbol());
            }
        }
    }
} 