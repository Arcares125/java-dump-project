package com.stockmarket.app.dto;

/**
 * >>>>>>>>>>>
 * SUPPLEMENTARY FOR QUIZ 11: Error Response DTO
 * 
 * Your task:
 * Create a standardized Error Response DTO that will be used by the GlobalExceptionHandler
 * to return consistent error responses to the client.
 * 
 * HINTS:
 * 1. Define the class with all required fields for a useful error response:
 *    - int status - the HTTP status code (e.g., 400, 404, 500)
 *    - String error - a short description of the error type (e.g., "Validation Error")
 *    - String message - a detailed error message
 *    - LocalDateTime timestamp - when the error occurred
 * 
 * 2. Add Lombok annotations for convenience:
 *    @Data
 *    @NoArgsConstructor
 *    @AllArgsConstructor
 *    @Builder
 * 
 * 3. Example implementation:
 * 
 *    @Data
 *    @NoArgsConstructor
 *    @AllArgsConstructor
 *    @Builder
 *    public class ErrorResponse {
 *        private int status;
 *        private String error;
 *        private String message;
 *        private LocalDateTime timestamp;
 *        
 *        // Optional: Add a field to hold validation errors
 *        private List<String> details;
 *    }
 * 
 * 4. Add necessary imports:
 *    import lombok.AllArgsConstructor;
 *    import lombok.Builder;
 *    import lombok.Data;
 *    import lombok.NoArgsConstructor;
 *    import java.time.LocalDateTime;
 *    import java.util.List;
 * 
 * >>>>>>>>>>>
 */
public class ErrorResponse {
    
    // TODO: Implement your solution here
    
} 