import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for RegistrationService.
 * Tests email validation, age validation, and exception handling.
 */
@DisplayName("RegistrationService Test Suite")
public class RegistrationServiceTest {
    
    private RegistrationService registrationService;
    
    /**
     * Setup method executed before each test.
     * Initializes a fresh RegistrationService instance.
     */
    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationService();
    }
    
    // ==================== Successful Registration Tests ====================
    
    @Test
    @DisplayName("Should successfully register user with valid email and age")
    public void testSuccessfulRegistration() throws InvalidEmailException {
        boolean result = registrationService.registerUser("john.doe@example.com", 25);
        assertTrue(result, "Registration should return true for valid inputs");
    }
    
    @Test
    @DisplayName("Should successfully register user at minimum age boundary")
    public void testSuccessfulRegistrationAtMinimumAge() throws InvalidEmailException {
        boolean result = registrationService.registerUser("jane.smith@domain.org", 18);
        assertTrue(result, "Registration should succeed at age 18");
    }
    
    @Test
    @DisplayName("Should successfully register user with various valid email formats")
    public void testSuccessfulRegistrationWithVariousEmailFormats() throws InvalidEmailException {
        String[] validEmails = {
            "user@example.com",
            "john.doe@example.co.uk",
            "user_name@domain.org",
            "user-name@sub.domain.com",
            "a@b.co"
        };
        
        for (String email : validEmails) {
            boolean result = registrationService.registerUser(email, 21);
            assertTrue(result, "Registration should succeed for email: " + email);
        }
    }
    
    @Test
    @DisplayName("Should successfully register user with age above minimum")
    public void testSuccessfulRegistrationWithAgeAboveMinimum() throws InvalidEmailException {
        boolean result = registrationService.registerUser("user@example.com", 65);
        assertTrue(result, "Registration should succeed for age 65");
    }
    
    // ==================== Email Validation Tests ====================
    
    @Test
    @DisplayName("Should throw InvalidEmailException when email is null")
    public void testNullEmailThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> registrationService.registerUser(null, 25),
            "Should throw InvalidEmailException for null email"
        );
        assertTrue(exception.getMessage().contains("null or empty"),
            "Exception message should mention null or empty email");
    }
    
    @Test
    @DisplayName("Should throw InvalidEmailException when email is empty string")
    public void testEmptyEmailThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> registrationService.registerUser("", 25),
            "Should throw InvalidEmailException for empty email"
        );
        assertTrue(exception.getMessage().contains("null or empty"),
            "Exception message should mention null or empty email");
    }
    
    @Test
    @DisplayName("Should throw InvalidEmailException when email is whitespace only")
    public void testWhitespaceEmailThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> registrationService.registerUser("   ", 25),
            "Should throw InvalidEmailException for whitespace-only email"
        );
        assertTrue(exception.getMessage().contains("null or empty"),
            "Exception message should mention null or empty email");
    }
    
    @Test
    @DisplayName("Should throw InvalidEmailException when email lacks @ symbol")
    public void testEmailWithoutAtSymbolThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> registrationService.registerUser("invalidemail.com", 25),
            "Should throw InvalidEmailException for email without @"
        );
        assertTrue(exception.getMessage().contains("invalid"),
            "Exception message should mention invalid format");
    }
    
    @Test
    @DisplayName("Should throw InvalidEmailException when email lacks domain extension")
    public void testEmailWithoutExtensionThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> registrationService.registerUser("user@domain", 25),
            "Should throw InvalidEmailException for email without extension"
        );
        assertTrue(exception.getMessage().contains("invalid"),
            "Exception message should mention invalid format");
    }
    
    @Test
    @DisplayName("Should throw InvalidEmailException when email has multiple @ symbols")
    public void testEmailWithMultipleAtSymbolsThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> registrationService.registerUser("user@@example.com", 25),
            "Should throw InvalidEmailException for multiple @ symbols"
        );
        assertTrue(exception.getMessage().contains("invalid"),
            "Exception message should mention invalid format");
    }
    
    @Test
    @DisplayName("Should throw InvalidEmailException when email has invalid characters")
    public void testEmailWithInvalidCharactersThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> registrationService.registerUser("user#name@example.com", 25),
            "Should throw InvalidEmailException for invalid characters"
        );
        assertTrue(exception.getMessage().contains("invalid"),
            "Exception message should mention invalid format");
    }
    
    // ==================== Age Validation Tests ====================
    
    @Test
    @DisplayName("Should throw UnderageException when age is below minimum")
    public void testUnderageThrowsException() {
        UnderageException exception = assertThrows(
            UnderageException.class,
            () -> registrationService.registerUser("user@example.com", 17),
            "Should throw UnderageException for age 17"
        );
        assertTrue(exception.getMessage().contains("18"),
            "Exception message should mention minimum age of 18");
    }
    
    @Test
    @DisplayName("Should throw UnderageException when age is significantly below minimum")
    public void testYoungAgeThrowsException() {
        UnderageException exception = assertThrows(
            UnderageException.class,
            () -> registrationService.registerUser("user@example.com", 5),
            "Should throw UnderageException for age 5"
        );
        assertTrue(exception.getMessage().contains("18"),
            "Exception message should mention minimum age requirement");
    }
    
    @Test
    @DisplayName("Should throw UnderageException when age is zero")
    public void testZeroAgeThrowsException() {
        UnderageException exception = assertThrows(
            UnderageException.class,
            () -> registrationService.registerUser("user@example.com", 0),
            "Should throw UnderageException for age 0"
        );
        assertTrue(exception.getMessage().contains("18"),
            "Exception message should mention minimum age requirement");
    }
    
    @Test
    @DisplayName("Should throw UnderageException when age is negative")
    public void testNegativeAgeThrowsException() {
        UnderageException exception = assertThrows(
            UnderageException.class,
            () -> registrationService.registerUser("user@example.com", -5),
            "Should throw UnderageException for negative age"
        );
        assertTrue(exception.getMessage().contains("18"),
            "Exception message should mention minimum age requirement");
    }
    
    // ==================== Combined Validation Tests ====================
    
    @Test
    @DisplayName("Should prioritize email validation over age validation")
    public void testEmailValidationPriority() {
        // Invalid email with underage - should throw InvalidEmailException first
        assertThrows(
            InvalidEmailException.class,
            () -> registrationService.registerUser("invalid-email", 10),
            "Should throw InvalidEmailException before checking age"
        );
    }
    
    @Test
    @DisplayName("Should throw UnderageException when both email and age are invalid")
    public void testBothInvalidWithAgeCheckSecond() {
        // Valid email but invalid age
        UnderageException exception = assertThrows(
            UnderageException.class,
            () -> registrationService.registerUser("valid@example.com", 10),
            "Should throw UnderageException when email is valid but age is invalid"
        );
        assertTrue(exception.getMessage().contains("18"),
            "Exception message should mention minimum age requirement");
    }
    
    // ==================== Service State Tests ====================
    
    @Test
    @DisplayName("Should maintain service state across multiple registrations")
    public void testServiceStateConsistency() throws InvalidEmailException {
        // First registration
        boolean result1 = registrationService.registerUser("user1@example.com", 25);
        assertTrue(result1, "First registration should succeed");
        
        // Second registration
        boolean result2 = registrationService.registerUser("user2@example.com", 30);
        assertTrue(result2, "Second registration should succeed");
        
        // Service should still be functional
        assertThrows(
            UnderageException.class,
            () -> registrationService.registerUser("user3@example.com", 15),
            "Service should still validate age after multiple registrations"
        );
    }
}
