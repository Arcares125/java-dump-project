package com.stockmarket.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dummy Kafka topic configuration for development mode.
 * 
 * This class provides mock beans that replace the real Kafka topic beans
 * defined in KafkaConfig for the development environment.
 */
@Configuration
@Profile("dev")
public class DummyKafkaTopicConfig {

    private static final Logger logger = LoggerFactory.getLogger(DummyKafkaTopicConfig.class);

    @Value("${kafka.topics.stock-price-updates:stock-price-updates}")
    private String stockPriceUpdatesTopic;

    @Value("${kafka.topics.stock-transactions:stock-transactions}")
    private String stockTransactionsTopic;

    /**
     * Creates a mock topic info bean for development mode.
     * This replaces the real Kafka admin and topic beans.
     * 
     * @return A TopicInfo object containing the topic names
     */
    @Bean
    public TopicInfo mockTopicInfo() {
        logger.info("In development mode - not creating Kafka topics");
        logger.info("Would create topic: {}", stockPriceUpdatesTopic);
        logger.info("Would create topic: {}", stockTransactionsTopic);
        
        return new TopicInfo(stockPriceUpdatesTopic, stockTransactionsTopic);
    }
    
    /**
     * Simple class to hold information about Kafka topics.
     */
    public static class TopicInfo {
        private final String priceUpdateTopic;
        private final String transactionTopic;
        
        public TopicInfo(String priceUpdateTopic, String transactionTopic) {
            this.priceUpdateTopic = priceUpdateTopic;
            this.transactionTopic = transactionTopic;
        }
        
        public String getPriceUpdateTopic() {
            return priceUpdateTopic;
        }
        
        public String getTransactionTopic() {
            return transactionTopic;
        }
    }
} 