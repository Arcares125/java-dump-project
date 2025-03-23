package com.stockmarket.app.service;

import com.stockmarket.app.dto.StockPriceUpdateDTO;
import com.stockmarket.app.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * >>>>>>>>>>>
 * QUIZ 10: Kafka Consumer
 * 
 * Your task:
 * Create a Kafka consumer service that:
 * 1. Listens to the "stock-prices" topic
 * 2. Updates the corresponding stock prices in the database when messages are received
 * 3. Logs the update operation
 * 
 * HINTS:
 * 1. Annotate the class with proper Kafka annotations:
 *    @Service
 *    @KafkaListener(topics = "stock-prices", groupId = "stock-app")
 * 
 * 2. Inject the necessary dependencies:
 *    private final StockRepository stockRepository;
 *    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
 *    
 *    public KafkaConsumerService(StockRepository stockRepository) {
 *        this.stockRepository = stockRepository;
 *    }
 * 
 * 3. Create a listener method:
 *    @KafkaListener(topics = "stock-prices")
 *    public void consume(ConsumerRecord<String, StockPriceEvent> record) {
 *        StockPriceEvent event = record.value();
 *        logger.info("Received stock price update: {}", event);
 *        
 *        // Find the stock by symbol
 *        Stock stock = stockRepository.findBySymbol(event.getSymbol())
 *            .orElse(null);
 *            
 *        if (stock != null) {
 *            // Update the stock price
 *            stock.setCurrentPrice(event.getNewPrice());
 *            stock.setLastUpdated(LocalDateTime.now());
 *            
 *            // Save the updated stock
 *            stockRepository.save(stock);
 *            logger.info("Updated price for stock {}: ${}", 
 *                stock.getSymbol(), stock.getCurrentPrice());
 *        } else {
 *            logger.warn("Stock not found with symbol: {}", event.getSymbol());
 *        }
 *    }
 * 
 * 4. Create a StockPriceEvent class (or use an existing one) with fields:
 *    - String symbol
 *    - BigDecimal newPrice
 *    - LocalDateTime timestamp
 * 
 * 5. Configure Kafka consumer properties in application.properties:
 *    spring.kafka.consumer.bootstrap-servers=localhost:9092
 *    spring.kafka.consumer.group-id=stock-app
 *    spring.kafka.consumer.auto-offset-reset=earliest
 *    spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
 *    spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
 *    spring.kafka.consumer.properties.spring.json.trusted.packages=com.stockmarket.app.dto
 * 
 * 6. Add necessary imports:
 *    import org.apache.kafka.clients.consumer.ConsumerRecord;
 *    import org.slf4j.Logger;
 *    import org.slf4j.LoggerFactory;
 *    import org.springframework.kafka.annotation.KafkaListener;
 *    import com.stockmarket.app.model.Stock;
 *    import com.stockmarket.app.repository.StockRepository;
 *    import com.stockmarket.app.dto.StockPriceEvent;
 *    import java.time.LocalDateTime;
 * 
 * >>>>>>>>>>>
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