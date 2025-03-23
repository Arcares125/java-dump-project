package com.stockmarket.app.service.impl;

import com.stockmarket.app.repository.PortfolioRepository;
import com.stockmarket.app.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * >>>>>>>>>>>
 * QUIZ 4: Service Layer Implementation (Part 2)
 * 
 * Your task:
 * 1. Implement the PortfolioServiceImpl class that:
 *    - Implements the PortfolioService interface
 *    - Uses the PortfolioRepository for database operations
 *    - Maps between Portfolio entities and PortfolioDTO objects
 *    - Handles appropriate exceptions for not found, validation failures, etc.
 *    - Uses proper transaction management
 * 
 * >>>>>>>>>>>
 * 
 * >>>>>>>>>>>
 * QUIZ 7: Integration with Kafka (Part 1)
 * 
 * Your task:
 * 1. Modify the service to send an event to Kafka when:
 *    - A portfolio is created
 *    - A portfolio is updated
 *    - Use the existing KafkaProducerService
 * 
 * >>>>>>>>>>>
 */
@Service
public class PortfolioServiceImpl implements PortfolioService {
    
    private final PortfolioRepository portfolioRepository;
    
    @Autowired
    public PortfolioServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }
    
    // TODO: Implement your solution here
    
} 