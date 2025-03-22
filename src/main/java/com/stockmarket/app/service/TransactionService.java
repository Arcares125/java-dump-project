package com.stockmarket.app.service;

import com.stockmarket.app.model.Transaction;
import com.stockmarket.app.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for managing stock transactions
 */
public interface TransactionService {
    
    /**
     * Create a new transaction
     * 
     * @param transaction the transaction to create
     * @return the created transaction with generated ID
     */
    Transaction createTransaction(Transaction transaction);
    
    /**
     * Get a transaction by its ID
     * 
     * @param id the ID of the transaction
     * @return the transaction if found
     * @throws javax.persistence.EntityNotFoundException if no transaction with the given ID exists
     */
    Transaction getTransactionById(Long id);
    
    /**
     * Get all transactions
     * 
     * @return a list of all transactions
     */
    List<Transaction> getAllTransactions();
    
    /**
     * Get transactions for a specific stock
     * 
     * @param stockSymbol the stock symbol to filter by
     * @return a list of transactions for the given stock
     */
    List<Transaction> getTransactionsByStockSymbol(String stockSymbol);
    
    /**
     * Get transactions of a specific type (BUY or SELL)
     * 
     * @param type the transaction type to filter by
     * @return a list of transactions of the given type
     */
    List<Transaction> getTransactionsByType(TransactionType type);
    
    /**
     * Get transactions within a date range
     * 
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return a list of transactions within the date range
     */
    List<Transaction> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Update an existing transaction
     * 
     * @param id the ID of the transaction to update
     * @param transaction the updated transaction data
     * @return the updated transaction
     * @throws javax.persistence.EntityNotFoundException if no transaction with the given ID exists
     */
    Transaction updateTransaction(Long id, Transaction transaction);
    
    /**
     * Delete a transaction by its ID
     * 
     * @param id the ID of the transaction to delete
     * @throws javax.persistence.EntityNotFoundException if no transaction with the given ID exists
     */
    void deleteTransaction(Long id);
} 