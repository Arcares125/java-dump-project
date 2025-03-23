package com.stockmarket.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for the Stock Market application.
 */
@SpringBootApplication(exclude = {KafkaAutoConfiguration.class})
@EnableScheduling
public class StockMarketApplication {

    /**
     * The main method serves as the entry point for the application.
     * The SpringApplication.run method launches the application - starting
     * Spring's ApplicationContext and performing auto-configuration, component scanning, etc.
     * 
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(StockMarketApplication.class, args);
    }
} 