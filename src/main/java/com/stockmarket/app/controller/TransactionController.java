package com.stockmarket.app.controller;

import com.stockmarket.app.model.Transaction;
import com.stockmarket.app.enums.TransactionType;
import com.stockmarket.app.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller for Transaction operations.
 * 
 * Demonstrates Spring MVC REST annotations and validation.
 */
@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transaction", description = "Transaction management APIs")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Create a new transaction
     * 
     * @param transaction the transaction data from request body
     * @return the created transaction with HTTP 201 status
     */
    @PostMapping
    @Operation(summary = "Create a new transaction", 
               description = "Creates a new stock transaction with the provided data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transaction created successfully",
                    content = @Content(schema = @Schema(implementation = Transaction.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Transaction> createTransaction(
            @Parameter(description = "Transaction data", required = true)
            @Valid @RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    /**
     * Get a transaction by its ID
     * 
     * @param id the transaction ID from path variable
     * @return the transaction with HTTP 200 status, or 404 if not found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a transaction by ID", 
               description = "Retrieves a transaction by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transaction found",
                    content = @Content(schema = @Schema(implementation = Transaction.class))),
        @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    public ResponseEntity<Transaction> getTransactionById(
            @Parameter(description = "Transaction ID", required = true)
            @PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    /**
     * Get all transactions
     * 
     * @return list of all transactions with HTTP 200 status
     */
    @GetMapping
    @Operation(summary = "Get all transactions", 
               description = "Retrieves a list of all transactions")
    @ApiResponse(responseCode = "200", description = "List of transactions retrieved successfully",
                content = @Content(schema = @Schema(implementation = Transaction.class)))
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    /**
     * Get transactions by stock symbol
     * 
     * @param stockSymbol the stock symbol from request parameter
     * @return list of transactions for the given stock symbol
     */
    @GetMapping("/stock")
    @Operation(summary = "Get transactions by stock symbol", 
               description = "Retrieves all transactions for a specific stock")
    @ApiResponse(responseCode = "200", description = "List of transactions retrieved successfully",
                content = @Content(schema = @Schema(implementation = Transaction.class)))
    public ResponseEntity<List<Transaction>> getTransactionsByStockSymbol(
            @Parameter(description = "Stock symbol (e.g., AAPL, MSFT)", required = true)
            @RequestParam String stockSymbol) {
        List<Transaction> transactions = transactionService.getTransactionsByStockSymbol(stockSymbol);
        return ResponseEntity.ok(transactions);
    }

    /**
     * Get transactions by type (BUY or SELL)
     * 
     * @param type the transaction type from request parameter
     * @return list of transactions of the given type
     */
    @GetMapping("/type")
    @Operation(summary = "Get transactions by type", 
               description = "Retrieves all transactions of a specific type (BUY or SELL)")
    @ApiResponse(responseCode = "200", description = "List of transactions retrieved successfully",
                content = @Content(schema = @Schema(implementation = Transaction.class)))
    public ResponseEntity<List<Transaction>> getTransactionsByType(
            @Parameter(description = "Transaction type (BUY or SELL)", required = true)
            @RequestParam TransactionType type) {
        List<Transaction> transactions = transactionService.getTransactionsByType(type);
        return ResponseEntity.ok(transactions);
    }

    /**
     * Get transactions by date range
     * 
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return list of transactions within the date range
     */
    @GetMapping("/date-range")
    @Operation(summary = "Get transactions by date range", 
               description = "Retrieves all transactions that occurred within the specified date range")
    @ApiResponse(responseCode = "200", description = "List of transactions retrieved successfully",
                content = @Content(schema = @Schema(implementation = Transaction.class)))
    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(
            @Parameter(description = "Start date (ISO format: yyyy-MM-ddTHH:mm:ss)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date (ISO format: yyyy-MM-ddTHH:mm:ss)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Transaction> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    /**
     * Update an existing transaction
     * 
     * @param id the transaction ID from path variable
     * @param transaction the updated transaction data from request body
     * @return the updated transaction with HTTP 200 status, or 404 if not found
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a transaction", 
               description = "Updates an existing transaction with the provided data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transaction updated successfully",
                    content = @Content(schema = @Schema(implementation = Transaction.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    public ResponseEntity<Transaction> updateTransaction(
            @Parameter(description = "Transaction ID", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Updated transaction data", required = true)
            @Valid @RequestBody Transaction transaction) {
        Transaction updatedTransaction = transactionService.updateTransaction(id, transaction);
        return ResponseEntity.ok(updatedTransaction);
    }

    /**
     * Delete a transaction
     * 
     * @param id the transaction ID from path variable
     * @return HTTP 204 status (no content) on successful deletion, or 404 if not found
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a transaction", 
               description = "Deletes a transaction by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Transaction deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    public ResponseEntity<Void> deleteTransaction(
            @Parameter(description = "Transaction ID", required = true)
            @PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
} 