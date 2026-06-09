/**
 * Simple standalone test for RegistrationService.
 * Demonstrates the validation functionality without requiring JUnit.
 */
public class SimpleTest {
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("RegistrationService Validation Tests");
        System.out.println("=".repeat(60));
        
        testSuccessfulRegistrations();
        testEmailValidation();
        testAgeValidation();
        testCombinedValidation();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Test Summary");
        System.out.println("=".repeat(60));
        System.out.println("Tests Passed: " + testsPassed);
        System.out.println("Tests Failed: " + testsFailed);
        System.out.println("Total Tests: " + (testsPassed + testsFailed));
        System.out.println("=".repeat(60));
    }
    
    private static void testSuccessfulRegistrations() {
        System.out.println("\n[SUCCESSFUL REGISTRATIONS]");
        RegistrationService service = new RegistrationService();
        
        // Test 1: Valid email and age
        try {
            boolean result = service.registerUser("john.doe@example.com", 25);
            if (result) {
                pass("Valid email and age (25)");
            } else {
                fail("Valid email and age should return true");
            }
        } catch (Exception e) {
            fail("Valid registration threw exception: " + e.getMessage());
        }
        
        // Test 2: Minimum age boundary
        try {
            boolean result = service.registerUser("jane.smith@domain.org", 18);
            if (result) {
                pass("Minimum age boundary (18)");
            } else {
                fail("Minimum age should be accepted");
            }
        } catch (Exception e) {
            fail("Minimum age threw exception: " + e.getMessage());
        }
        
        // Test 3: Various valid email formats
        String[] validEmails = {
            "user@example.com",
            "john.doe@example.co.uk",
            "user_name@domain.org",
            "user-name@sub.domain.com",
            "a@b.co"
        };
        
        for (String email : validEmails) {
            try {
                boolean result = service.registerUser(email, 21);
                if (result) {
                    pass("Valid email format: " + email);
                } else {
                    fail("Valid email should return true: " + email);
                }
            } catch (Exception e) {
                fail("Valid email threw exception: " + email + " - " + e.getMessage());
            }
        }
    }
    
    private static void testEmailValidation() {
        System.out.println("\n[EMAIL VALIDATION]");
        RegistrationService service = new RegistrationService();
        
        // Test 1: Null email
        try {
            service.registerUser(null, 25);
            fail("Null email should throw InvalidEmailException");
        } catch (InvalidEmailException e) {
            if (e.getMessage().contains("null or empty")) {
                pass("Null email throws InvalidEmailException");
            } else {
                fail("Exception message should mention null or empty");
            }
        } catch (Exception e) {
            fail("Wrong exception type for null email: " + e.getClass().getName());
        }
        
        // Test 2: Empty email
        try {
            service.registerUser("", 25);
            fail("Empty email should throw InvalidEmailException");
        } catch (InvalidEmailException e) {
            if (e.getMessage().contains("null or empty")) {
                pass("Empty email throws InvalidEmailException");
            } else {
                fail("Exception message should mention null or empty");
            }
        } catch (Exception e) {
            fail("Wrong exception type for empty email");
        }
        
        // Test 3: Email without @ symbol
        try {
            service.registerUser("invalidemail.com", 25);
            fail("Email without @ should throw InvalidEmailException");
        } catch (InvalidEmailException e) {
            if (e.getMessage().contains("invalid")) {
                pass("Email without @ throws InvalidEmailException");
            } else {
                fail("Exception message should mention invalid format");
            }
        } catch (Exception e) {
            fail("Wrong exception type for email without @");
        }
        
        // Test 4: Email without extension
        try {
            service.registerUser("user@domain", 25);
            fail("Email without extension should throw InvalidEmailException");
        } catch (InvalidEmailException e) {
            if (e.getMessage().contains("invalid")) {
                pass("Email without extension throws InvalidEmailException");
            } else {
                fail("Exception message should mention invalid format");
            }
        } catch (Exception e) {
            fail("Wrong exception type for email without extension");
        }
        
        // Test 5: Email with multiple @ symbols
        try {
            service.registerUser("user@@example.com", 25);
            fail("Email with multiple @ should throw InvalidEmailException");
        } catch (InvalidEmailException e) {
            if (e.getMessage().contains("invalid")) {
                pass("Email with multiple @ throws InvalidEmailException");
            } else {
                fail("Exception message should mention invalid format");
            }
        } catch (Exception e) {
            fail("Wrong exception type for multiple @");
        }
        
        // Test 6: Email with invalid characters
        try {
            service.registerUser("user#name@example.com", 25);
            fail("Email with invalid characters should throw InvalidEmailException");
        } catch (InvalidEmailException e) {
            if (e.getMessage().contains("invalid")) {
                pass("Email with invalid characters throws InvalidEmailException");
            } else {
                fail("Exception message should mention invalid format");
            }
        } catch (Exception e) {
            fail("Wrong exception type for invalid characters");
        }
    }
    
    private static void testAgeValidation() {
        System.out.println("\n[AGE VALIDATION]");
        RegistrationService service = new RegistrationService();
        
        // Test 1: Age below minimum (17)
        try {
            service.registerUser("user@example.com", 17);
            fail("Age 17 should throw UnderageException");
        } catch (UnderageException e) {
            if (e.getMessage().contains("18")) {
                pass("Age 17 throws UnderageException");
            } else {
                fail("Exception message should mention minimum age 18");
            }
        } catch (Exception e) {
            fail("Wrong exception type for age 17: " + e.getClass().getName());
        }
        
        // Test 2: Age significantly below minimum (5)
        try {
            service.registerUser("user@example.com", 5);
            fail("Age 5 should throw UnderageException");
        } catch (UnderageException e) {
            if (e.getMessage().contains("18")) {
                pass("Age 5 throws UnderageException");
            } else {
                fail("Exception message should mention minimum age");
            }
        } catch (Exception e) {
            fail("Wrong exception type for age 5");
        }
        
        // Test 3: Zero age
        try {
            service.registerUser("user@example.com", 0);
            fail("Age 0 should throw UnderageException");
        } catch (UnderageException e) {
            if (e.getMessage().contains("18")) {
                pass("Age 0 throws UnderageException");
            } else {
                fail("Exception message should mention minimum age");
            }
        } catch (Exception e) {
            fail("Wrong exception type for age 0");
        }
        
        // Test 4: Negative age
        try {
            service.registerUser("user@example.com", -5);
            fail("Negative age should throw UnderageException");
        } catch (UnderageException e) {
            if (e.getMessage().contains("18")) {
                pass("Negative age throws UnderageException");
            } else {
                fail("Exception message should mention minimum age");
            }
        } catch (Exception e) {
            fail("Wrong exception type for negative age");
        }
    }
    
    private static void testCombinedValidation() {
        System.out.println("\n[COMBINED VALIDATION]");
        RegistrationService service = new RegistrationService();
        
        // Test 1: Email validation priority (invalid email with underage)
        try {
            service.registerUser("invalid-email", 10);
            fail("Invalid email should throw InvalidEmailException");
        } catch (InvalidEmailException e) {
            pass("Email validation prioritized over age validation");
        } catch (Exception e) {
            fail("Wrong exception type: " + e.getClass().getName());
        }
        
        // Test 2: Valid email with invalid age
        try {
            service.registerUser("valid@example.com", 10);
            fail("Underage should throw UnderageException");
        } catch (UnderageException e) {
            if (e.getMessage().contains("18")) {
                pass("Age validation checked after email validation");
            } else {
                fail("Exception message should mention minimum age");
            }
        } catch (Exception e) {
            fail("Wrong exception type: " + e.getClass().getName());
        }
    }
    
    private static void pass(String testName) {
        System.out.println("✓ PASS: " + testName);
        testsPassed++;
    }
    
    private static void fail(String testName) {
        System.out.println("✗ FAIL: " + testName);
        testsFailed++;
    }
}
