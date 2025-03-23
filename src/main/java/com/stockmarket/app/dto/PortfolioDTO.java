package com.stockmarket.app.dto;

/**
 * >>>>>>>>>>>
 * QUIZ 2: Working with Entities and DTOs (Part 2)
 * 
 * Your task:
 * 1. Implement this PortfolioDTO class with:
 *    - Fields that correspond to the Portfolio entity (id, name, description, etc.)
 *    - Lombok annotations for easier development
 *    - Validation annotations (@NotBlank, @Size, etc.) 
 *    - Add a list of stock symbols or PortfolioItemDTOs as needed
 * 
 * 2. Consider which fields should be exposed in the API vs. kept internal
 * 
 * HINTS:
 * 1. Start by looking at the Portfolio entity structure in Portfolio.java
 * 2. Include these fields:
 *    - Long id
 *    - String name
 *    - String description
 *    - String username (who owns the portfolio)
 *    - BigDecimal totalValue
 *    - List<String> stockSymbols or List<PortfolioItemDTO> items
 * 
 * 3. Add these Lombok annotations at the class level:
 *    @Data                   // Generates getters, setters, equals, hashCode, toString
 *    @Builder                // Implements the builder pattern
 *    @NoArgsConstructor      // Generates a no-args constructor
 *    @AllArgsConstructor     // Generates a constructor with all fields
 * 
 * 4. You might create a simplified PortfolioItemDTO class with:
 *    - String symbol
 *    - Integer quantity
 *    - BigDecimal purchasePrice
 * 
 * >>>>>>>>>>>
 * 
 * >>>>>>>>>>>
 * QUIZ 6: Adding Validation (Part 1)
 * 
 * Your task:
 * 1. Add validation to your DTO:
 *    - Name should not be empty and be between 3-50 characters
 *    - Description should be optional but max 200 characters
 * 
 * HINTS:
 * 1. Add these validation annotations to the name field:
 *    @NotBlank(message = "Portfolio name is required")
 *    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
 * 
 * 2. Add this validation annotation to the description field:
 *    @Size(max = 200, message = "Description cannot exceed 200 characters")
 * 
 * 3. For numeric fields, consider adding:
 *    @PositiveOrZero(message = "Value cannot be negative")
 * 
 * 4. Don't forget to add the proper import statements:
 *    import javax.validation.constraints.NotBlank;
 *    import javax.validation.constraints.Size;
 *    import javax.validation.constraints.PositiveOrZero;
 * 
 * >>>>>>>>>>>
 */
public class PortfolioDTO {
    
    // TODO: Implement your solution here
    
} 