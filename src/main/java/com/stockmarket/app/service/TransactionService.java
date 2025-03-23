package com.stockmarket.app.service;

import com.stockmarket.app.dto.TransactionCreateRequest;
import com.stockmarket.app.dto.TransactionDTO;
import com.stockmarket.app.dto.TransactionUpdateRequest;
import com.stockmarket.app.enums.TransactionType;

import java.util.List;

/**
 * Service interface for transaction operations.
 */
public interface TransactionService {

    /**
     * Creates a new transaction.
     *
     * @param request the transaction create request
     * @return the created transaction as DTO
     */
    TransactionDTO createTransaction(TransactionCreateRequest request);

    /**
     * Retrieves a transaction by its ID.
     *
     * @param id the transaction ID
     * @return the transaction as DTO
     * @throws javax.persistence.EntityNotFoundException if the transaction is not found
     */
    TransactionDTO getTransactionById(Long id);

    /**
     * Retrieves all transactions.
     *
     * @return a list of all transactions as DTOs
     */
    List<TransactionDTO> getAllTransactions();

    /**
     * Retrieves transactions for a specific stock.
     *
     * @param stockSymbol the stock symbol
     * @return a list of transactions as DTOs
     */
    List<TransactionDTO> getTransactionsByStockSymbol(String stockSymbol);

    /**
     * Retrieves transactions of a specific type.
     *
     * @param type the transaction type (BUY or SELL)
     * @return a list of transactions as DTOs
     */
    List<TransactionDTO> getTransactionsByType(TransactionType type);

    /**
     * Updates an existing transaction.
     *
     * @param id the transaction ID
     * @param request the transaction update request
     * @return the updated transaction as DTO
     * @throws javax.persistence.EntityNotFoundException if the transaction is not found
     */
    TransactionDTO updateTransaction(Long id, TransactionUpdateRequest request);

    /**
     * Deletes a transaction.
     *
     * @param id the transaction ID
     * @throws javax.persistence.EntityNotFoundException if the transaction is not found
     */
    void deleteTransaction(Long id);
} 