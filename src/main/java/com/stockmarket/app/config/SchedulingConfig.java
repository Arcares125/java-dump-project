package com.stockmarket.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration class that enables scheduling functionality in the application.
 * This allows using @Scheduled annotations on methods like in the StockPriceSimulatorService.
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {
    // No additional configuration needed - @EnableScheduling does the work
} 