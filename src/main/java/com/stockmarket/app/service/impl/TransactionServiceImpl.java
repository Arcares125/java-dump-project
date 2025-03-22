package com.stockmarket.app.service.impl;

import com.stockmarket.app.model.Transaction;
import com.stockmarket.app.enums.TransactionType;
import com.stockmarket.app.repository.TransactionRepository;
import com.stockmarket.app.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the TransactionService interface.
 * 
 * Provides business logic for transaction operations and interacts with the repository layer.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * {@inheritDoc}
     * 
     * Validates the transaction and calculates the total amount before saving.
     */
    @Override
    public Transaction createTransaction(Transaction transaction) {
        // Set creation timestamp if not provided
        if (transaction.getTimestamp() == null) {
            transaction.setTimestamp(LocalDateTime.now());
        }
        
        // Calculate and set total amount
        if (transaction.getTotalAmount() == null || transaction.getTotalAmount().compareTo(BigDecimal.ZERO) == 0) {
            BigDecimal totalAmount = transaction.getPricePerShare().multiply(new BigDecimal(transaction.getQuantity()));
            transaction.setTotalAmount(totalAmount);
        }
        
        return transactionRepository.save(transaction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Transaction> getTransactionsByStockSymbol(String stockSymbol) {
        return transactionRepository.findByStockSymbol(stockSymbol);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Transaction> getTransactionsByType(TransactionType type) {
        return transactionRepository.findByType(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Transaction> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByTimestampBetween(startDate, endDate);
    }

    /**
     * {@inheritDoc}
     * 
     * Updates an existing transaction, recalculating the total amount if necessary.
     */
    @Override
    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction existingTransaction = getTransactionById(id);
        
        // Update fields
        existingTransaction.setType(updatedTransaction.getType());
        existingTransaction.setStockSymbol(updatedTransaction.getStockSymbol());
        existingTransaction.setQuantity(updatedTransaction.getQuantity());
        existingTransaction.setPricePerShare(updatedTransaction.getPricePerShare());
        
        // Recalculate total amount
        BigDecimal totalAmount = existingTransaction.getPricePerShare()
                .multiply(new BigDecimal(existingTransaction.getQuantity()));
        existingTransaction.setTotalAmount(totalAmount);
        
        // If timestamp was updated, use it, otherwise keep the original
        if (updatedTransaction.getTimestamp() != null) {
            existingTransaction.setTimestamp(updatedTransaction.getTimestamp());
        }
        
        return transactionRepository.save(existingTransaction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTransaction(Long id) {
        // Verify transaction exists before attempting to delete
        Transaction transaction = getTransactionById(id);
        transactionRepository.delete(transaction);
    }
} 