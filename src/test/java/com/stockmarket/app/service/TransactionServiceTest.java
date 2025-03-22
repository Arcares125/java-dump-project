package com.stockmarket.app.service;

import com.stockmarket.app.model.Transaction;
import com.stockmarket.app.enums.TransactionType;
import com.stockmarket.app.repository.TransactionRepository;
import com.stockmarket.app.service.impl.TransactionServiceImpl;
import jakarta.persistence.EntityNotFoundException;
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
                .totalAmount(new BigDecimal("1500.00"))
                .timestamp(now)
                .build();

        transaction2 = Transaction.builder()
                .id(2L)
                .type(TransactionType.SELL)
                .stockSymbol("MSFT")
                .quantity(5)
                .pricePerShare(new BigDecimal("200.00"))
                .totalAmount(new BigDecimal("1000.00"))
                .timestamp(now.plusDays(1))
                .build();

        transactions = Arrays.asList(transaction1, transaction2);
    }

    /**
     * Test for creating a transaction successfully.
     * This verifies that the service correctly handles transaction creation
     * and delegates to the repository.
     */
    @Test
    @DisplayName("Should create a transaction successfully")
    void createTransaction_Success() {
        // Given a transaction to create
        Transaction newTransaction = Transaction.builder()
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
                .totalAmount(new BigDecimal("2000.00"))
                .timestamp(now)
                .build();

        // Mock repository behavior
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        // When creating the transaction
        Transaction result = transactionService.createTransaction(newTransaction);

        // Then the result should be the saved transaction
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals(TransactionType.BUY, result.getType());
        assertEquals("GOOG", result.getStockSymbol());
        assertEquals(2, result.getQuantity());
        assertEquals(new BigDecimal("1000.00"), result.getPricePerShare());
        assertEquals(new BigDecimal("2000.00"), result.getTotalAmount());

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
        Transaction result = transactionService.getTransactionById(id);

        // Then the result should be the expected transaction
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
        List<Transaction> result = transactionService.getAllTransactions();

        // Then the result should be the list of transactions
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
        List<Transaction> result = transactionService.getTransactionsByStockSymbol(stockSymbol);

        // Then the result should be the filtered list
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
        List<Transaction> result = transactionService.getTransactionsByType(type);

        // Then the result should be the filtered list
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(type, result.get(0).getType());

        // Verify repository interaction
        verify(transactionRepository, times(1)).findByType(type);
    }

    /**
     * Test for getting transactions by date range.
     * This verifies the service correctly filters transactions by date range.
     */
    @Test
    @DisplayName("Should return transactions within a date range")
    void getTransactionsByDateRange_Success() {
        // Given a date range
        LocalDateTime startDate = now.minusDays(1);
        LocalDateTime endDate = now.plusDays(2);

        // Mock repository behavior
        when(transactionRepository.findByTimestampBetween(startDate, endDate)).thenReturn(transactions);

        // When retrieving transactions by date range
        List<Transaction> result = transactionService.getTransactionsByDateRange(startDate, endDate);

        // Then the result should be the filtered list
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verify repository interaction
        verify(transactionRepository, times(1)).findByTimestampBetween(startDate, endDate);
    }

    /**
     * Test for updating a transaction when it exists.
     * This verifies the service correctly updates an existing transaction.
     */
    @Test
    @DisplayName("Should update a transaction when it exists")
    void updateTransaction_Success() {
        // Given a transaction ID and updated data
        Long id = 1L;
        Transaction updatedTransaction = Transaction.builder()
                .type(TransactionType.SELL)
                .stockSymbol("AAPL")
                .quantity(20)
                .pricePerShare(new BigDecimal("155.00"))
                .build();

        Transaction afterUpdate = Transaction.builder()
                .id(1L)
                .type(TransactionType.SELL)
                .stockSymbol("AAPL")
                .quantity(20)
                .pricePerShare(new BigDecimal("155.00"))
                .totalAmount(new BigDecimal("3100.00"))
                .timestamp(now)
                .build();

        // Mock repository behavior
        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction1));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(afterUpdate);

        // When updating the transaction
        Transaction result = transactionService.updateTransaction(id, updatedTransaction);

        // Then the result should be the updated transaction
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(TransactionType.SELL, result.getType());
        assertEquals(20, result.getQuantity());
        assertEquals(new BigDecimal("155.00"), result.getPricePerShare());
        assertEquals(new BigDecimal("3100.00"), result.getTotalAmount());

        // Verify repository interactions
        verify(transactionRepository, times(1)).findById(id);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    /**
     * Test for deleting a transaction when it exists.
     * This verifies the service correctly handles transaction deletion.
     */
    @Test
    @DisplayName("Should delete a transaction when it exists")
    void deleteTransaction_Success() {
        // Given a transaction ID
        Long id = 1L;

        // Mock repository behavior
        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction1));
        doNothing().when(transactionRepository).delete(transaction1);

        // When deleting the transaction
        transactionService.deleteTransaction(id);

        // Verify repository interactions
        verify(transactionRepository, times(1)).findById(id);
        verify(transactionRepository, times(1)).delete(transaction1);
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
        when(transactionRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then deleting the transaction should throw EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> transactionService.deleteTransaction(id));

        // Verify repository interactions
        verify(transactionRepository, times(1)).findById(id);
        verify(transactionRepository, never()).delete(any(Transaction.class));
    }
} 