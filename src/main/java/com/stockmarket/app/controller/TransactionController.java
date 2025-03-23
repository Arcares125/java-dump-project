package com.stockmarket.app.controller;

import com.stockmarket.app.dto.TransactionCreateRequest;
import com.stockmarket.app.dto.TransactionDTO;
import com.stockmarket.app.dto.TransactionUpdateRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param createRequest the transaction data from request body
     * @return the created transaction with HTTP 201 status
     */
    @PostMapping
    @Operation(summary = "Create a new transaction", description = "Creates a new stock transaction with the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully", content = @Content(schema = @Schema(implementation = TransactionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<TransactionDTO> createTransaction(
            @Parameter(description = "Transaction data", required = true) @Valid @RequestBody TransactionCreateRequest createRequest) {
        TransactionDTO createdTransaction = transactionService.createTransaction(createRequest);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    /**
     * Get a transaction by its ID
     * 
     * @param id the transaction ID from path variable
     * @return the transaction with HTTP 200 status, or 404 if not found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a transaction by ID", description = "Retrieves a transaction by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction found", content = @Content(schema = @Schema(implementation = TransactionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    public ResponseEntity<TransactionDTO> getTransactionById(
            @Parameter(description = "Transaction ID", required = true) @PathVariable Long id) {
        TransactionDTO transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    /**
     * Get all transactions
     * 
     * @return list of all transactions with HTTP 200 status
     */
    @GetMapping
    @Operation(summary = "Get all transactions", description = "Retrieves a list of all transactions")
    @ApiResponse(responseCode = "200", description = "List of transactions retrieved successfully", content = @Content(schema = @Schema(implementation = TransactionDTO.class)))
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    /**
     * Get transactions by stock symbol
     * 
     * @param stockSymbol the stock symbol from request parameter
     * @return list of transactions for the given stock symbol
     */
    @GetMapping("/stock")
    @Operation(summary = "Get transactions by stock symbol", description = "Retrieves all transactions for a specific stock")
    @ApiResponse(responseCode = "200", description = "List of transactions retrieved successfully", content = @Content(schema = @Schema(implementation = TransactionDTO.class)))
    public ResponseEntity<List<TransactionDTO>> getTransactionsByStockSymbol(
            @Parameter(description = "Stock symbol (e.g., AAPL, MSFT)", required = true) @RequestParam String stockSymbol) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByStockSymbol(stockSymbol);
        return ResponseEntity.ok(transactions);
    }

    /**
     * Get transactions by type (BUY or SELL)
     * 
     * @param type the transaction type from request parameter
     * @return list of transactions of the given type
     */
    @GetMapping("/type")
    @Operation(summary = "Get transactions by type", description = "Retrieves all transactions of a specific type (BUY or SELL)")
    @ApiResponse(responseCode = "200", description = "List of transactions retrieved successfully", content = @Content(schema = @Schema(implementation = TransactionDTO.class)))
    public ResponseEntity<List<TransactionDTO>> getTransactionsByType(
            @Parameter(description = "Transaction type (BUY or SELL)", required = true) @RequestParam TransactionType type) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByType(type);
        return ResponseEntity.ok(transactions);
    }

    /**
     * Update an existing transaction
     * 
     * @param id            the transaction ID from path variable
     * @param updateRequest the updated transaction data from request body
     * @return the updated transaction with HTTP 200 status, or 404 if not found
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a transaction", description = "Updates an existing transaction with the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction updated successfully", content = @Content(schema = @Schema(implementation = TransactionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    public ResponseEntity<TransactionDTO> updateTransaction(
            @Parameter(description = "Transaction ID", required = true) @PathVariable Long id,
            @Parameter(description = "Updated transaction data", required = true) @Valid @RequestBody TransactionUpdateRequest updateRequest) {
        TransactionDTO updatedTransaction = transactionService.updateTransaction(id, updateRequest);
        return ResponseEntity.ok(updatedTransaction);
    }

    /**
     * Delete a transaction
     * 
     * @param id the transaction ID from path variable
     * @return HTTP 204 status (no content) on successful deletion, or 404 if not
     *         found
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a transaction", description = "Deletes a transaction by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transaction deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    public ResponseEntity<Void> deleteTransaction(
            @Parameter(description = "Transaction ID", required = true) @PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}