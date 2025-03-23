package com.stockmarket.app.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * >>>>>>>>>>>
 * QUIZ 8: Unit Testing
 * 
 * Your task:
 * 1. Create a unit test for the portfolio service layer that:
 *    - Uses JUnit 5 and Mockito
 *    - Mocks the PortfolioRepository
 *    - Tests the CRUD operations
 *    - Verifies proper exception handling
 *    - Tests Kafka integration (mocking the KafkaProducerService)
 * 
 * 2. Make sure to include:
 *    - Proper test setup with @BeforeEach
 *    - Clear test method names (@DisplayName)
 *    - Comprehensive assertions
 * 
 * HINTS:
 * 1. Create mocks and inject them into the service:
 *    @Mock
 *    private PortfolioRepository portfolioRepository;
 *    
 *    @Mock
 *    private KafkaProducerService kafkaProducerService;
 *    
 *    @InjectMocks
 *    private PortfolioServiceImpl portfolioService;
 * 
 * 2. Set up test data in @BeforeEach:
 *    @BeforeEach
 *    void setUp() {
 *        portfolioDTO = PortfolioDTO.builder()
 *            .name("Test Portfolio")
 *            .description("Test Description")
 *            .username("testuser")
 *            .build();
 *            
 *        portfolio = new Portfolio();
 *        portfolio.setId(1L);
 *        portfolio.setName("Test Portfolio");
 *        portfolio.setDescription("Test Description");
 *        portfolio.setUsername("testuser");
 *    }
 * 
 * 3. Test the createPortfolio method:
 *    @Test
 *    @DisplayName("Should create a new portfolio successfully")
 *    void createPortfolio_Success() {
 *        // Given
 *        when(portfolioRepository.findByName(anyString())).thenReturn(Optional.empty());
 *        when(portfolioRepository.save(any(Portfolio.class))).thenReturn(portfolio);
 *        
 *        // When
 *        PortfolioDTO result = portfolioService.createPortfolio(portfolioDTO);
 *        
 *        // Then
 *        assertNotNull(result);
 *        assertEquals(1L, result.getId());
 *        assertEquals("Test Portfolio", result.getName());
 *        verify(portfolioRepository).save(any(Portfolio.class));
 *        verify(kafkaProducerService).sendPortfolioEvent(any(PortfolioEvent.class));
 *    }
 * 
 * 4. Test exception handling:
 *    @Test
 *    @DisplayName("Should throw exception when getting non-existent portfolio")
 *    void getPortfolioById_NotFound() {
 *        // Given
 *        when(portfolioRepository.findById(anyLong())).thenReturn(Optional.empty());
 *        
 *        // When & Then
 *        assertThrows(EntityNotFoundException.class, () -> {
 *            portfolioService.getPortfolioById(999L);
 *        });
 *    }
 * 
 * 5. Remember to add necessary imports:
 *    import static org.junit.jupiter.api.Assertions.*;
 *    import static org.mockito.ArgumentMatchers.any;
 *    import static org.mockito.ArgumentMatchers.anyLong;
 *    import static org.mockito.ArgumentMatchers.anyString;
 *    import static org.mockito.Mockito.*;
 *    import com.stockmarket.app.dto.PortfolioDTO;
 *    import com.stockmarket.app.dto.PortfolioEvent;
 *    import com.stockmarket.app.model.Portfolio;
 *    import javax.persistence.EntityNotFoundException;
 *    import org.junit.jupiter.api.BeforeEach;
 *    import org.junit.jupiter.api.DisplayName;
 *    import org.junit.jupiter.api.Test;
 *    import org.mockito.InjectMocks;
 *    import org.mockito.Mock;
 *    import java.util.Optional;
 * 
 * >>>>>>>>>>>
 */
@ExtendWith(MockitoExtension.class)
public class PortfolioServiceTest {
    
    // TODO: Implement your solution here
    
} 