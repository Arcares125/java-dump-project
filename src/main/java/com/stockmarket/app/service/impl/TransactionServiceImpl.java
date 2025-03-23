package com.stockmarket.app.service.impl;

import com.stockmarket.app.dto.TransactionCreateRequest;
import com.stockmarket.app.dto.TransactionDTO;
import com.stockmarket.app.dto.TransactionUpdateRequest;
import com.stockmarket.app.enums.TransactionType;
import com.stockmarket.app.model.Transaction;
import com.stockmarket.app.repository.TransactionRepository;
import com.stockmarket.app.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the TransactionService interface.
 * This service handles all business logic related to stock transactions.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionDTO createTransaction(TransactionCreateRequest request) {
        log.info("Creating transaction for stock {}", request.getStockSymbol());
        
        BigDecimal totalValue = request.getPricePerShare().multiply(BigDecimal.valueOf(request.getQuantity()));
        
        Transaction transaction = Transaction.builder()
                .type(request.getType())
                .stockSymbol(request.getStockSymbol())
                .quantity(request.getQuantity())
                .pricePerShare(request.getPricePerShare())
                .totalValue(totalValue)
                .timestamp(LocalDateTime.now())
                .userId(request.getUserId())
                .portfolioId(request.getPortfolioId())
                .notes(request.getNotes())
                .build();
        
        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transaction created with ID: {}", savedTransaction.getId());
        
        return convertToDTO(savedTransaction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionDTO getTransactionById(Long id) {
        log.info("Retrieving transaction with ID: {}", id);
        
        return transactionRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> {
                    log.error("Transaction not found with ID: {}", id);
                    return new EntityNotFoundException("Transaction not found with ID: " + id);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TransactionDTO> getAllTransactions() {
        log.info("Retrieving all transactions");
        
        return transactionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TransactionDTO> getTransactionsByStockSymbol(String stockSymbol) {
        log.info("Retrieving transactions for stock: {}", stockSymbol);
        
        return transactionRepository.findByStockSymbol(stockSymbol).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TransactionDTO> getTransactionsByType(TransactionType type) {
        log.info("Retrieving transactions of type: {}", type);
        
        return transactionRepository.findByType(type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionDTO updateTransaction(Long id, TransactionUpdateRequest request) {
        log.info("Updating transaction with ID: {}", id);
        
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Transaction not found with ID: {}", id);
                    return new EntityNotFoundException("Transaction not found with ID: " + id);
                });
        
        // Update fields if provided in the request
        if (request.getType() != null) {
            transaction.setType(request.getType());
        }
        if (request.getStockSymbol() != null) {
            transaction.setStockSymbol(request.getStockSymbol());
        }
        if (request.getQuantity() != null) {
            transaction.setQuantity(request.getQuantity());
        }
        if (request.getPricePerShare() != null) {
            transaction.setPricePerShare(request.getPricePerShare());
        }
        
        // Recalculate total value if quantity or price changed
        if (request.getQuantity() != null || request.getPricePerShare() != null) {
            BigDecimal totalValue = transaction.getPricePerShare().multiply(BigDecimal.valueOf(transaction.getQuantity()));
            transaction.setTotalValue(totalValue);
        }
        
        if (request.getNotes() != null) {
            transaction.setNotes(request.getNotes());
        }
        
        Transaction updatedTransaction = transactionRepository.save(transaction);
        log.info("Transaction updated successfully");
        
        return convertToDTO(updatedTransaction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTransaction(Long id) {
        log.info("Deleting transaction with ID: {}", id);
        
        if (!transactionRepository.existsById(id)) {
            log.error("Transaction not found with ID: {}", id);
            throw new EntityNotFoundException("Transaction not found with ID: " + id);
        }
        
        transactionRepository.deleteById(id);
        log.info("Transaction deleted successfully");
    }

    /**
     * Converts a Transaction entity to a TransactionDTO.
     *
     * @param transaction the transaction entity
     * @return a DTO representation of the transaction
     */
    private TransactionDTO convertToDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .stockSymbol(transaction.getStockSymbol())
                .quantity(transaction.getQuantity())
                .pricePerShare(transaction.getPricePerShare())
                .totalValue(transaction.getTotalValue())
                .timestamp(transaction.getTimestamp())
                .userId(transaction.getUserId())
                .portfolioId(transaction.getPortfolioId())
                .notes(transaction.getNotes())
                .build();
    }
} 