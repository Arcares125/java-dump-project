package com.stockmarket.app.service.scheduled;

import org.springframework.stereotype.Component;

/**
 * >>>>>>>>>>>
 * QUIZ 9: Scheduled Job
 * 
 * Your task:
 * 1. Create a scheduled job that runs daily to:
 *    - Log the total count of portfolios
 *    - Calculate and log the average portfolio value
 *    - Use the proper Spring annotation (@Scheduled)
 *    - Set it to run at midnight every day
 * 
 * 2. Inject the necessary repository or service to get the data
 * 
 * HINTS:
 * 1. Inject the repository or service you need:
 *    private final PortfolioRepository portfolioRepository;
 *    
 *    public PortfolioStatsScheduler(PortfolioRepository portfolioRepository) {
 *        this.portfolioRepository = portfolioRepository;
 *    }
 * 
 * 2. Create a scheduled method with the right cron expression for midnight:
 *    @Scheduled(cron = "0 0 0 * * ?")  // Run at midnight every day
 *    public void generateDailyStats() {
 *        // Implementation
 *    }
 * 
 * 3. In the method, calculate and log the statistics:
 *    // Get all portfolios
 *    List<Portfolio> portfolios = portfolioRepository.findAll();
 *    
 *    // Log the count
 *    logger.info("Daily Portfolio Stats - Total portfolios: {}", portfolios.size());
 *    
 *    // Calculate average value
 *    if (!portfolios.isEmpty()) {
 *        BigDecimal totalValue = portfolios.stream()
 *            .map(Portfolio::getTotalValue)
 *            .reduce(BigDecimal.ZERO, BigDecimal::add);
 *            
 *        BigDecimal avgValue = totalValue.divide(
 *            new BigDecimal(portfolios.size()), 
 *            2, 
 *            RoundingMode.HALF_UP
 *        );
 *        
 *        logger.info("Daily Portfolio Stats - Average portfolio value: ${}", avgValue);
 *    }
 * 
 * 4. Add a logger to the class:
 *    private static final Logger logger = LoggerFactory.getLogger(PortfolioStatsScheduler.class);
 * 
 * 5. Add necessary imports:
 *    import org.slf4j.Logger;
 *    import org.slf4j.LoggerFactory;
 *    import org.springframework.scheduling.annotation.Scheduled;
 *    import com.stockmarket.app.model.Portfolio;
 *    import com.stockmarket.app.repository.PortfolioRepository;
 *    import java.math.BigDecimal;
 *    import java.math.RoundingMode;
 *    import java.util.List;
 * 
 * >>>>>>>>>>>
 */
@Component
public class PortfolioStatsScheduler {
    
    // TODO: Implement your solution here
    
}