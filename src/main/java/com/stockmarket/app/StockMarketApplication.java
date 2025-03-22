package com.stockmarket.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the main application class for the Stock Market application.
 * 
 * @SpringBootApplication is a convenience annotation that adds all of the following:
 * - @Configuration: Tags the class as a source of bean definitions for the application context
 * - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings
 * - @ComponentScan: Tells Spring to look for other components, configurations, and services in the specified package
 */
@SpringBootApplication
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