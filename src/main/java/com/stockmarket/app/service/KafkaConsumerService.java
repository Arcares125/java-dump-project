package com.stockmarket.app.service;

import com.stockmarket.app.dto.StockPriceUpdateDTO;
import com.stockmarket.app.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Service for consuming Kafka messages
 */
@Service
public class KafkaConsumerService {
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
    
    /**
     * Consume stock price updates from Kafka
     * 
     * @param update the price update data
     */
    @KafkaListener(topics = "${kafka.topics.stock-price-updates}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeStockPriceUpdate(StockPriceUpdateDTO update) {
        logger.info("Received stock price update for symbol: {}, price: {}", 
            update.getSymbol(), update.getPrice());
        
        // Here you would handle the stock price update
        // For example, update the database, notify users, etc.
    }
    
    /**
     * Consume transactions from Kafka
     * 
     * @param transaction the transaction data
     */
    @KafkaListener(topics = "${kafka.topics.stock-transactions}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeTransaction(Transaction transaction) {
        logger.info("Received transaction for symbol: {}, quantity: {}, type: {}", 
            transaction.getStockSymbol(), transaction.getQuantity(), transaction.getType());
        
        // Here you would handle the transaction
        // For example, update portfolio balances, update statistics, etc.
    }
} 