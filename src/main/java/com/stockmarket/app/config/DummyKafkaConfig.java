package com.stockmarket.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Dummy Kafka configuration that provides a minimal Kafka setup
 * to allow the application to run without a real Kafka server.
 * 
 * This configuration creates a Kafka producer factory that doesn't actually 
 * connect to a Kafka broker but allows the application to function without errors.
 */
@Configuration
@Profile("dev")
public class DummyKafkaConfig {

    private static final Logger logger = LoggerFactory.getLogger(DummyKafkaConfig.class);

    /**
     * Creates a producer factory that doesn't actually connect to Kafka.
     * We configure it with minimal properties to avoid connection errors.
     */
    @Bean
    @Primary
    public ProducerFactory<String, Object> dummyProducerFactory() {
        logger.info("Creating mock Kafka producer factory for development mode");
        Map<String, Object> props = new HashMap<>();
        // Set bootstrap servers to a non-existent server to avoid connection attempts
        props.put("bootstrap.servers", "localhost:9092");
        // Set buffer memory to minimal to avoid allocating unnecessary resources
        props.put("buffer.memory", 1);
        
        return new DefaultKafkaProducerFactory<>(props);
    }

    /**
     * Creates a KafkaTemplate that won't actually connect to Kafka.
     * In dev mode, the KafkaProducerService will handle exceptions gracefully.
     */
    @Bean
    @Primary
    public KafkaTemplate<String, Object> dummyKafkaTemplate() {
        logger.info("Creating mock Kafka template for development mode");
        return new KafkaTemplate<>(dummyProducerFactory());
    }
} 