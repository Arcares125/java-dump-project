package com.stockmarket.app.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for API error responses.
 * Used to provide structured error information to clients.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, List<String>> validationErrors;
    
    /**
     * Adds a validation error for a specific field.
     * 
     * @param field the field name
     * @param message the error message
     */
    public void addValidationError(String field, String message) {
        if (validationErrors == null) {
            validationErrors = new HashMap<>();
        }
        
        if (!validationErrors.containsKey(field)) {
            validationErrors.put(field, new ArrayList<>());
        }
        
        validationErrors.get(field).add(message);
    }
} 