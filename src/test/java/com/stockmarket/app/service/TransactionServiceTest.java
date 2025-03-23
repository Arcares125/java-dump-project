package com.stockmarket.app.service;

import com.stockmarket.app.dto.TransactionCreateRequest;
import com.stockmarket.app.dto.TransactionDTO;
import com.stockmarket.app.dto.TransactionUpdateRequest;
import com.stockmarket.app.enums.TransactionType;
import com.stockmarket.app.model.Transaction;
import com.stockmarket.app.repository.TransactionRepository;
import com.stockmarket.app.service.impl.TransactionServiceImpl;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for the TransactionService implementation.
 * 
 * Demonstrates:
 * - JUnit 5 testing with Mockito
 * - Mocking repository dependencies
 * - Testing service layer methods
 * - Verifying interactions between components
 */
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    /**
     * @Mock - Creates a mock implementation of TransactionRepository.
     * This allows us to simulate repository behavior without actual database interactions.
     */
    @Mock
    private TransactionRepository transactionRepository;

    /**
     * @InjectMocks - Creates an instance of TransactionServiceImpl and injects the mocked repository into it.
     * This sets up the service to use our mocked repository for testing.
     */
    @InjectMocks
    private TransactionServiceImpl transactionService;

    /**
     * Test data setup
     */
    private Transaction transaction1;
    private Transaction transaction2;
    private TransactionDTO transactionDTO1;
    private TransactionDTO transactionDTO2;
    private List<Transaction> transactions;
    private final LocalDateTime now = LocalDateTime.now();

    /**
     * @BeforeEach - Method runs before each test to set up test data.
     * This ensures we have fresh test data for each test case.
     */
    @BeforeEach
    void setUp() {
        // Create test transactions
        transaction1 = Transaction.builder()
                .id(1L)
                .type(TransactionType.BUY)
                .stockSymbol("AAPL")
                .quantity(10)
                .pricePerShare(new BigDecimal("150.00"))
                .totalValue(new BigDecimal("1500.00"))
                .timestamp(now)
                .build();

        transaction2 = Transaction.builder()
                .id(2L)
                .type(TransactionType.SELL)
                .stockSymbol("MSFT")
                .quantity(5)
                .pricePerShare(new BigDecimal("200.00"))
                .totalValue(new BigDecimal("1000.00"))
                .timestamp(now.plusDays(1))
                .build();

        transactions = Arrays.asList(transaction1, transaction2);
        
        // Create DTOs for test data
        transactionDTO1 = TransactionDTO.builder()
                .id(1L)
                .type(TransactionType.BUY)
                .stockSymbol("AAPL")
                .quantity(10)
                .pricePerShare(new BigDecimal("150.00"))
                .totalValue(new BigDecimal("1500.00"))
                .timestamp(now)
                .build();

        transactionDTO2 = TransactionDTO.builder()
                .id(2L)
                .type(TransactionType.SELL)
                .stockSymbol("MSFT")
                .quantity(5)
                .pricePerShare(new BigDecimal("200.00"))
                .totalValue(new BigDecimal("1000.00"))
                .timestamp(now.plusDays(1))
                .build();
    }

    /**
     * Test for creating a transaction successfully.
     * This verifies that the service correctly handles transaction creation
     * and delegates to the repository.
     */
    @Test
    @DisplayName("Should create a transaction successfully")
    void createTransaction_Success() {
        // Given a transaction create request
        TransactionCreateRequest createRequest = TransactionCreateRequest.builder()
                .type(TransactionType.BUY)
                .stockSymbol("GOOG")
                .quantity(2)
                .pricePerShare(new BigDecimal("1000.00"))
                .build();

        Transaction savedTransaction = Transaction.builder()
                .id(3L)
                .type(TransactionType.BUY)
                .stockSymbol("GOOG")
                .quantity(2)
                .pricePerShare(new BigDecimal("1000.00"))
                .totalValue(new BigDecimal("2000.00"))
                .timestamp(now)
                .build();
                
        TransactionDTO expectedDTO = TransactionDTO.builder()
                .id(3L)
                .type(TransactionType.BUY)
                .stockSymbol("GOOG")
                .quantity(2)
                .pricePerShare(new BigDecimal("1000.00"))
                .totalValue(new BigDecimal("2000.00"))
                .timestamp(now)
                .build();

        // Mock repository behavior
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        // When creating the transaction
        TransactionDTO result = transactionService.createTransaction(createRequest);

        // Then the result should be the saved transaction DTO
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals(TransactionType.BUY, result.getType());
        assertEquals("GOOG", result.getStockSymbol());
        assertEquals(2, result.getQuantity());
        assertEquals(new BigDecimal("1000.00"), result.getPricePerShare());
        assertEquals(new BigDecimal("2000.00"), result.getTotalValue());

        // Verify repository interaction
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    /**
     * Test for getting a transaction by ID when it exists.
     * This verifies the service correctly retrieves an existing transaction.
     */
    @Test
    @DisplayName("Should return transaction by ID when it exists")
    void getTransactionById_Success() {
        // Given a transaction ID
        Long id = 1L;

        // Mock repository behavior
        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction1));

        // When retrieving the transaction
        TransactionDTO result = transactionService.getTransactionById(id);

        // Then the result should be the expected transaction DTO
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("AAPL", result.getStockSymbol());

        // Verify repository interaction
        verify(transactionRepository, times(1)).findById(id);
    }

    /**
     * Test for getting a transaction by ID when it doesn't exist.
     * This verifies the service throws an appropriate exception when the transaction is not found.
     */
    @Test
    @DisplayName("Should throw EntityNotFoundException when transaction doesn't exist")
    void getTransactionById_NotFound() {
        // Given a non-existent transaction ID
        Long id = 999L;

        // Mock repository behavior
        when(transactionRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then retrieving the transaction should throw EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> transactionService.getTransactionById(id));

        // Verify repository interaction
        verify(transactionRepository, times(1)).findById(id);
    }

    /**
     * Test for getting all transactions.
     * This verifies the service correctly retrieves all transactions.
     */
    @Test
    @DisplayName("Should return all transactions")
    void getAllTransactions_Success() {
        // Mock repository behavior
        when(transactionRepository.findAll()).thenReturn(transactions);

        // When retrieving all transactions
        List<TransactionDTO> result = transactionService.getAllTransactions();

        // Then the result should be the list of transaction DTOs
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("AAPL", result.get(0).getStockSymbol());
        assertEquals("MSFT", result.get(1).getStockSymbol());

        // Verify repository interaction
        verify(transactionRepository, times(1)).findAll();
    }

    /**
     * Test for getting transactions by stock symbol.
     * This verifies the service correctly filters transactions by stock symbol.
     */
    @Test
    @DisplayName("Should return transactions for a specific stock symbol")
    void getTransactionsByStockSymbol_Success() {
        // Given a stock symbol
        String stockSymbol = "AAPL";

        // Mock repository behavior
        when(transactionRepository.findByStockSymbol(stockSymbol)).thenReturn(List.of(transaction1));

        // When retrieving transactions by stock symbol
        List<TransactionDTO> result = transactionService.getTransactionsByStockSymbol(stockSymbol);

        // Then the result should be the filtered list of DTOs
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(stockSymbol, result.get(0).getStockSymbol());

        // Verify repository interaction
        verify(transactionRepository, times(1)).findByStockSymbol(stockSymbol);
    }

    /**
     * Test for getting transactions by type.
     * This verifies the service correctly filters transactions by type.
     */
    @Test
    @DisplayName("Should return transactions of a specific type")
    void getTransactionsByType_Success() {
        // Given a transaction type
        TransactionType type = TransactionType.BUY;

        // Mock repository behavior
        when(transactionRepository.findByType(type)).thenReturn(List.of(transaction1));

        // When retrieving transactions by type
        List<TransactionDTO> result = transactionService.getTransactionsByType(type);

        // Then the result should be the filtered list of DTOs
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(type, result.get(0).getType());

        // Verify repository interaction
        verify(transactionRepository, times(1)).findByType(type);
    }

    /**
     * Test for updating a transaction when it exists.
     * This verifies the service correctly updates an existing transaction.
     */
    @Test
    @DisplayName("Should update a transaction when it exists")
    void updateTransaction_Success() {
        // Given a transaction ID and update request
        Long id = 1L;
        
        TransactionUpdateRequest updateRequest = TransactionUpdateRequest.builder()
                .type(TransactionType.SELL)
                .quantity(15)
                .pricePerShare(new BigDecimal("160.00"))
                .build();

        Transaction existingTransaction = Transaction.builder()
                .id(id)
                .type(TransactionType.BUY)
                .stockSymbol("AAPL")
                .quantity(10)
                .pricePerShare(new BigDecimal("150.00"))
                .totalValue(new BigDecimal("1500.00"))
                .timestamp(now)
                .build();

        Transaction updatedTransaction = Transaction.builder()
                .id(id)
                .type(TransactionType.SELL)
                .stockSymbol("AAPL")
                .quantity(15)
                .pricePerShare(new BigDecimal("160.00"))
                .totalValue(new BigDecimal("2400.00"))
                .timestamp(now)
                .build();
                
        TransactionDTO expectedDTO = TransactionDTO.builder()
                .id(id)
                .type(TransactionType.SELL)
                .stockSymbol("AAPL")
                .quantity(15)
                .pricePerShare(new BigDecimal("160.00"))
                .totalValue(new BigDecimal("2400.00"))
                .timestamp(now)
                .build();

        // Mock repository behavior
        when(transactionRepository.findById(id)).thenReturn(Optional.of(existingTransaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(updatedTransaction);

        // When updating the transaction
        TransactionDTO result = transactionService.updateTransaction(id, updateRequest);

        // Then the result should be the updated transaction DTO
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(TransactionType.SELL, result.getType());
        assertEquals(15, result.getQuantity());
        assertEquals(new BigDecimal("160.00"), result.getPricePerShare());
        assertEquals(new BigDecimal("2400.00"), result.getTotalValue());

        // Verify repository interactions
        verify(transactionRepository, times(1)).findById(id);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    /**
     * Test for deleting a transaction when it exists.
     * This verifies the service correctly deletes an existing transaction.
     */
    @Test
    @DisplayName("Should delete a transaction when it exists")
    void deleteTransaction_Success() {
        // Given a transaction ID
        Long id = 1L;

        // Mock repository behavior
        when(transactionRepository.existsById(id)).thenReturn(true);
        doNothing().when(transactionRepository).deleteById(id);

        // When deleting the transaction
        transactionService.deleteTransaction(id);

        // Verify repository interactions
        verify(transactionRepository, times(1)).existsById(id);
        verify(transactionRepository, times(1)).deleteById(id);
    }

    /**
     * Test for deleting a transaction when it doesn't exist.
     * This verifies the service throws an appropriate exception when the transaction is not found.
     */
    @Test
    @DisplayName("Should throw EntityNotFoundException when trying to delete non-existent transaction")
    void deleteTransaction_NotFound() {
        // Given a non-existent transaction ID
        Long id = 999L;

        // Mock repository behavior
        when(transactionRepository.existsById(id)).thenReturn(false);

        // When/Then deleting the transaction should throw EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> transactionService.deleteTransaction(id));

        // Verify repository interaction
        verify(transactionRepository, times(1)).existsById(id);
        verify(transactionRepository, never()).deleteById(anyLong());
    }
} 