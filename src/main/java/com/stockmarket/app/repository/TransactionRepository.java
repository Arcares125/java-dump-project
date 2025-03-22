package com.stockmarket.app.repository;

import com.stockmarket.app.model.Transaction;
import com.stockmarket.app.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Transaction entity.
 * Extends JpaRepository to leverage built-in methods for CRUD operations.
 * 
 * Also demonstrates custom query methods based on method naming conventions.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    /**
     * Find all transactions for a specific stock symbol
     * 
     * @param stockSymbol the symbol of the stock
     * @return a list of transactions for the given stock
     */
    List<Transaction> findByStockSymbol(String stockSymbol);
    
    /**
     * Find all transactions of a specific type (BUY or SELL)
     * 
     * @param type the type of transaction
     * @return a list of transactions of the given type
     */
    List<Transaction> findByType(TransactionType type);
    
    /**
     * Find all transactions that occurred between two timestamps
     * 
     * @param startDate the start date/time
     * @param endDate the end date/time
     * @return a list of transactions that occurred in the given time period
     */
    List<Transaction> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Example of a custom JPQL query (JPA Query Language)
     * Finds transactions with a total amount greater than the specified value
     * 
     * @param amount the minimum total amount
     * @return list of transactions with total amount greater than the specified value
     */
    @Query("SELECT t FROM Transaction t WHERE t.totalAmount > :amount")
    List<Transaction> findTransactionsWithTotalAmountGreaterThan(@Param("amount") BigDecimal amount);
    
    /**
     * Example of a native SQL query
     * Finds the total number of transactions per stock symbol
     * 
     * @return list of objects containing stock symbol and count
     */
    @Query(value = "SELECT stock_symbol, COUNT(*) as transaction_count " +
                  "FROM transactions " +
                  "GROUP BY stock_symbol " +
                  "ORDER BY transaction_count DESC", 
           nativeQuery = true)
    List<Object[]> findTransactionCountByStockSymbol();
    
    /**
     * Another native SQL query example
     * Finds transactions with specific criteria using SQL syntax
     * 
     * @param symbol the stock symbol to search for
     * @param minAmount the minimum total amount
     * @return list of matching transactions
     */
    @Query(value = "SELECT * FROM transactions " +
                  "WHERE stock_symbol = :symbol " +
                  "AND total_amount > :minAmount " +
                  "ORDER BY timestamp DESC", 
           nativeQuery = true)
    List<Transaction> findTransactionsBySymbolAndMinAmount(
            @Param("symbol") String symbol, 
            @Param("minAmount") BigDecimal minAmount);
} 