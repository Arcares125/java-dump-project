package com.stockmarket.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Stock Price Updates.
 * This class is used to send stock price updates to Kafka.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockPriceUpdateDTO {
    private String symbol;
    private BigDecimal price;
    private BigDecimal change;
    private BigDecimal changePercent;
    private LocalDateTime timestamp;
} 