package SweetShop.SweetShopSystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Demonstration of Test-Driven Development (TDD) Approach
 * 
 * This test class demonstrates the Red-Green-Refactor cycle:
 * 
 * RED PHASE: Write a failing test first
 * GREEN PHASE: Write minimal code to make the test pass
 * REFACTOR PHASE: Improve the code while keeping tests green
 */
@SpringBootTest
class TDDDemoTest {

    @Test
    @DisplayName("TDD Demo: Application Context Loads Successfully")
    void contextLoads() {
        // This test demonstrates that the Spring Boot application context
        // can be loaded successfully, which is the foundation for all other tests
        
        // Given: Spring Boot application is configured
        // When: Application context is loaded
        // Then: No exceptions are thrown (implicit assertion)
        
        // This test passes if the application context loads without errors
        assertThat(true).isTrue(); // Simple assertion to demonstrate test structure
    }

    @Test
    @DisplayName("TDD Demo: Basic String Operations Work Correctly")
    void testBasicStringOperations() {
        // RED PHASE: Write a failing test
        // This test would initially fail if we didn't have the expected behavior
        
        // Given
        String sweetName = "Chocolate Bar";
        String category = "Chocolate";
        
        // When
        String fullDescription = sweetName + " - " + category;
        
        // Then
        assertThat(fullDescription).isEqualTo("Chocolate Bar - Chocolate");
        assertThat(fullDescription).contains("Chocolate");
        assertThat(fullDescription).startsWith("Chocolate Bar");
        assertThat(fullDescription).endsWith("Chocolate");
    }

    @Test
    @DisplayName("TDD Demo: Mathematical Operations for Price Calculations")
    void testPriceCalculations() {
        // Demonstrating TDD for business logic
        
        // Given
        double basePrice = 50.0;
        int quantity = 3;
        double taxRate = 0.1; // 10% tax
        
        // When
        double subtotal = basePrice * quantity;
        double tax = subtotal * taxRate;
        double total = subtotal + tax;
        
        // Then
        assertThat(subtotal).isEqualTo(150.0);
        assertThat(tax).isEqualTo(15.0);
        assertThat(total).isEqualTo(165.0);
    }

    @Test
    @DisplayName("TDD Demo: Validation Logic for Sweet Quantities")
    void testQuantityValidation() {
        // RED PHASE: Define the expected behavior first
        
        // Given
        int availableQuantity = 10;
        int requestedQuantity = 5;
        int excessiveQuantity = 15;
        
        // When & Then
        assertThat(isValidPurchaseQuantity(requestedQuantity, availableQuantity)).isTrue();
        assertThat(isValidPurchaseQuantity(excessiveQuantity, availableQuantity)).isFalse();
        assertThat(isValidPurchaseQuantity(0, availableQuantity)).isFalse();
        assertThat(isValidPurchaseQuantity(-1, availableQuantity)).isFalse();
    }

    /**
     * GREEN PHASE: Implement minimal code to make the test pass
     * This method represents the implementation that makes the test pass
     */
    private boolean isValidPurchaseQuantity(int requested, int available) {
        return requested > 0 && requested <= available;
    }

    @Test
    @DisplayName("TDD Demo: Email Validation Logic")
    void testEmailValidation() {
        // Testing email validation logic following TDD principles
        
        // Given
        String validEmail = "user@example.com";
        String invalidEmail1 = "invalid-email";
        String invalidEmail2 = "@example.com";
        String invalidEmail3 = "user@";
        
        // When & Then
        assertThat(isValidEmail(validEmail)).isTrue();
        assertThat(isValidEmail(invalidEmail1)).isFalse();
        assertThat(isValidEmail(invalidEmail2)).isFalse();
        assertThat(isValidEmail(invalidEmail3)).isFalse();
        assertThat(isValidEmail(null)).isFalse();
        assertThat(isValidEmail("")).isFalse();
    }

    /**
     * GREEN PHASE: Simple email validation implementation
     * In a real application, this would be more sophisticated
     */
    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.contains("@") && 
               email.indexOf("@") > 0 && 
               email.indexOf("@") < email.length() - 1 &&
               email.contains(".");
    }

    @Test
    @DisplayName("TDD Demo: Sweet Category Classification")
    void testSweetCategoryClassification() {
        // Testing business logic for categorizing sweets
        
        // Given & When & Then
        assertThat(getSweetCategory("Chocolate Bar")).isEqualTo("Chocolate");
        assertThat(getSweetCategory("Gummy Bears")).isEqualTo("Gummy");
        assertThat(getSweetCategory("Lollipop")).isEqualTo("Hard Candy");
        assertThat(getSweetCategory("Unknown Sweet")).isEqualTo("Other");
    }

    /**
     * GREEN PHASE: Implementation for sweet categorization
     * This demonstrates how TDD drives the design of business logic
     */
    private String getSweetCategory(String sweetName) {
        if (sweetName == null) {
            return "Other";
        }
        
        String lowerName = sweetName.toLowerCase();
        
        if (lowerName.contains("chocolate")) {
            return "Chocolate";
        } else if (lowerName.contains("gummy")) {
            return "Gummy";
        } else if (lowerName.contains("lollipop")) {
            return "Hard Candy";
        } else {
            return "Other";
        }
    }
}

/**
 * TDD PRINCIPLES DEMONSTRATED:
 * 
 * 1. RED PHASE - Write Failing Tests First:
 *    - Tests define the expected behavior before implementation
 *    - Tests act as specifications for the code
 *    - Forces thinking about edge cases and requirements
 * 
 * 2. GREEN PHASE - Make Tests Pass:
 *    - Write minimal code to make tests pass
 *    - Focus on functionality, not perfection
 *    - Implement just enough to satisfy the tests
 * 
 * 3. REFACTOR PHASE - Improve Code Quality:
 *    - Optimize and clean up code while keeping tests green
 *    - Improve performance, readability, and maintainability
 *    - Tests provide safety net for refactoring
 * 
 * BENEFITS SHOWN:
 * - Clear test documentation of expected behavior
 * - Confidence in code correctness
 * - Easy regression detection
 * - Improved code design through test-first thinking
 * - Living documentation through executable tests
 */