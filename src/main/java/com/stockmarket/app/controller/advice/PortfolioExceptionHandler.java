package com.stockmarket.app.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * >>>>>>>>>>>
 * QUIZ 11: Global Exception Handler
 * 
 * Your task:
 * Create a global exception handler that will handle all exceptions thrown by the application and return appropriate responses.
 * 
 * HINTS:
 * 1. Implement a global exception handler with @ControllerAdvice:
 *    @ControllerAdvice
 *    public class GlobalExceptionHandler {
 *        // Exception handlers go here
 *    }
 * 
 * 2. Add a logger to the class:
 *    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
 * 
 * 3. Create handlers for different types of exceptions:
 *    // For general application exceptions
 *    @ExceptionHandler(Exception.class)
 *    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
 *        logger.error("Unexpected error occurred", ex);
 *        ErrorResponse errorResponse = new ErrorResponse(
 *            HttpStatus.INTERNAL_SERVER_ERROR.value(),
 *            "An unexpected error occurred",
 *            ex.getMessage(),
 *            LocalDateTime.now()
 *        );
 *        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
 *    }
 * 
 *    // For validation exceptions
 *    @ExceptionHandler(MethodArgumentNotValidException.class)
 *    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
 *        List<String> errors = ex.getBindingResult()
 *            .getFieldErrors()
 *            .stream()
 *            .map(error -> error.getField() + ": " + error.getDefaultMessage())
 *            .collect(Collectors.toList());
 *            
 *        ErrorResponse errorResponse = new ErrorResponse(
 *            HttpStatus.BAD_REQUEST.value(),
 *            "Validation error",
 *            errors.toString(),
 *            LocalDateTime.now()
 *        );
 *        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
 *    }
 * 
 *    // For entity not found exceptions
 *    @ExceptionHandler(EntityNotFoundException.class)
 *    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
 *        ErrorResponse errorResponse = new ErrorResponse(
 *            HttpStatus.NOT_FOUND.value(),
 *            "Resource not found",
 *            ex.getMessage(),
 *            LocalDateTime.now()
 *        );
 *        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
 *    }
 * 
 * 4. Create an ErrorResponse class (if not already existing):
 *    @Data
 *    @AllArgsConstructor
 *    @NoArgsConstructor
 *    public class ErrorResponse {
 *        private int status;
 *        private String error;
 *        private String message;
 *        private LocalDateTime timestamp;
 *    }
 * 
 * 5. Add necessary imports:
 *    import org.slf4j.Logger;
 *    import org.slf4j.LoggerFactory;
 *    import org.springframework.http.HttpStatus;
 *    import org.springframework.http.ResponseEntity;
 *    import org.springframework.web.bind.MethodArgumentNotValidException;
 *    import org.springframework.web.bind.annotation.ExceptionHandler;
 *    import org.springframework.web.context.request.WebRequest;
 *    import javax.persistence.EntityNotFoundException;
 *    import java.time.LocalDateTime;
 *    import java.util.List;
 *    import java.util.stream.Collectors;
 *    import com.stockmarket.app.dto.ErrorResponse;
 * 
 * >>>>>>>>>>>
 */
@ControllerAdvice
public class PortfolioExceptionHandler {
    
    // TODO: Implement your solution here
    
} 