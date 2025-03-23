package com.stockmarket.app.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Kafka setup.
 * This class configures Kafka producer and creates required topics.
 * 
 * Only active when not using the 'dev' profile.
 */
@Configuration
@Profile("!dev")
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Value("${kafka.topics.stock-price-updates:stock-price-updates}")
    private String stockPriceUpdatesTopic;

    @Value("${kafka.topics.stock-transactions:stock-transactions}")
    private String stockTransactionsTopic;

    /**
     * Configures Kafka Admin client with bootstrap servers.
     * This is used for creating topics.
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    /**
     * Creates the stock price updates topic.
     */
    @Bean
    public NewTopic stockPriceUpdatesTopic() {
        // Create topic with 3 partitions and replication factor of 1
        return new NewTopic(stockPriceUpdatesTopic, 3, (short) 1);
    }

    /**
     * Creates the stock transactions topic.
     */
    @Bean
    public NewTopic stockTransactionsTopic() {
        // Create topic with 3 partitions and replication factor of 1
        return new NewTopic(stockTransactionsTopic, 3, (short) 1);
    }

    /**
     * Configures the producer factory with serializers and other settings.
     */
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        // Additional producer configurations for reliability
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.RETRIES_CONFIG, 10);
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Creates the Kafka template for sending messages.
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
} 