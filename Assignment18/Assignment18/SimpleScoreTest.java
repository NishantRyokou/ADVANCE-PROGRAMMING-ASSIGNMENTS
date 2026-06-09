import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Simple standalone test for ScoreProcessor.
 * Demonstrates file handling and exception management without JUnit.
 */
public class SimpleScoreTest {
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    private static String testDir = "test_scores";
    
    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("ScoreProcessor File Handling Tests");
        System.out.println("=".repeat(70));
        
        // Create test directory
        createTestDirectory();
        
        try {
            testSuccessfulProcessing();
            testFileNotFound();
            testInvalidFormat();
            testEdgeCases();
        } finally {
            // Cleanup test directory
            cleanupTestDirectory();
        }
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("Test Summary");
        System.out.println("=".repeat(70));
        System.out.println("Tests Passed: " + testsPassed);
        System.out.println("Tests Failed: " + testsFailed);
        System.out.println("Total Tests: " + (testsPassed + testsFailed));
        System.out.println("=".repeat(70));
    }
    
    private static void createTestDirectory() {
        File dir = new File(testDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
    
    private static void cleanupTestDirectory() {
        try {
            Files.walk(Paths.get(testDir))
                .sorted((a, b) -> b.compareTo(a))
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        System.err.println("Failed to delete: " + path);
                    }
                });
        } catch (IOException e) {
            System.err.println("Failed to cleanup test directory: " + e.getMessage());
        }
    }
    
    private static void testSuccessfulProcessing() {
        System.out.println("\n[SUCCESSFUL PROCESSING]");
        ScoreProcessor processor = new ScoreProcessor();
        
        // Test 1: Valid score
        try {
            String filePath = testDir + "/valid_score.txt";
            createScoreFile(filePath, "42");
            
            int result = processor.processScoreFile(filePath);
            if (result == 420) {
                pass("Valid score (42) processed correctly");
            } else {
                fail("Expected 420, got " + result);
            }
        } catch (Exception e) {
            fail("Valid score processing threw exception: " + e.getMessage());
        }
        
        // Test 2: Zero score
        try {
            String filePath = testDir + "/zero_score.txt";
            createScoreFile(filePath, "0");
            
            int result = processor.processScoreFile(filePath);
            if (result == 0) {
                pass("Zero score processed correctly");
            } else {
                fail("Expected 0, got " + result);
            }
        } catch (Exception e) {
            fail("Zero score processing threw exception: " + e.getMessage());
        }
        
        // Test 3: Negative score
        try {
            String filePath = testDir + "/negative_score.txt";
            createScoreFile(filePath, "-15");
            
            int result = processor.processScoreFile(filePath);
            if (result == -150) {
                pass("Negative score (-15) processed correctly");
            } else {
                fail("Expected -150, got " + result);
            }
        } catch (Exception e) {
            fail("Negative score processing threw exception: " + e.getMessage());
        }
        
        // Test 4: Score with whitespace
        try {
            String filePath = testDir + "/whitespace_score.txt";
            createScoreFile(filePath, "  50  ");
            
            int result = processor.processScoreFile(filePath);
            if (result == 500) {
                pass("Score with whitespace processed correctly");
            } else {
                fail("Expected 500, got " + result);
            }
        } catch (Exception e) {
            fail("Whitespace score processing threw exception: " + e.getMessage());
        }
    }
    
    private static void testFileNotFound() {
        System.out.println("\n[FILE NOT FOUND HANDLING]");
        ScoreProcessor processor = new ScoreProcessor();
        
        // Test 1: Non-existent file
        try {
            String nonExistentPath = testDir + "/nonexistent_file.txt";
            processor.processScoreFile(nonExistentPath);
            fail("Should have thrown FileNotFoundException");
        } catch (java.io.FileNotFoundException e) {
            pass("FileNotFoundException thrown for missing file");
        } catch (Exception e) {
            fail("Wrong exception type: " + e.getClass().getName());
        }
        
        // Test 2: Invalid path
        try {
            String invalidPath = "/invalid/path/to/file.txt";
            processor.processScoreFile(invalidPath);
            fail("Should have thrown FileNotFoundException");
        } catch (java.io.FileNotFoundException e) {
            pass("FileNotFoundException thrown for invalid path");
        } catch (Exception e) {
            fail("Wrong exception type: " + e.getClass().getName());
        }
    }
    
    private static void testInvalidFormat() {
        System.out.println("\n[INVALID FORMAT HANDLING]");
        ScoreProcessor processor = new ScoreProcessor();
        
        // Test 1: Non-numeric content
        try {
            String filePath = testDir + "/invalid_content.txt";
            createScoreFile(filePath, "abc");
            
            processor.processScoreFile(filePath);
            fail("Should have thrown NumberFormatException");
        } catch (NumberFormatException e) {
            pass("NumberFormatException thrown for non-numeric content");
        } catch (Exception e) {
            fail("Wrong exception type: " + e.getClass().getName());
        }
        
        // Test 2: Decimal number
        try {
            String filePath = testDir + "/decimal_score.txt";
            createScoreFile(filePath, "42.5");
            
            processor.processScoreFile(filePath);
            fail("Should have thrown NumberFormatException");
        } catch (NumberFormatException e) {
            pass("NumberFormatException thrown for decimal number");
        } catch (Exception e) {
            fail("Wrong exception type: " + e.getClass().getName());
        }
        
        // Test 3: Mixed alphanumeric
        try {
            String filePath = testDir + "/mixed_content.txt";
            createScoreFile(filePath, "42abc");
            
            processor.processScoreFile(filePath);
            fail("Should have thrown NumberFormatException");
        } catch (NumberFormatException e) {
            pass("NumberFormatException thrown for mixed alphanumeric");
        } catch (Exception e) {
            fail("Wrong exception type: " + e.getClass().getName());
        }
        
        // Test 4: Empty file
        try {
            String filePath = testDir + "/empty_file.txt";
            createScoreFile(filePath, "");
            
            processor.processScoreFile(filePath);
            fail("Should have thrown NumberFormatException");
        } catch (NumberFormatException e) {
            pass("NumberFormatException thrown for empty file");
        } catch (Exception e) {
            fail("Wrong exception type: " + e.getClass().getName());
        }
        
        // Test 5: Whitespace only
        try {
            String filePath = testDir + "/whitespace_only.txt";
            createScoreFile(filePath, "   ");
            
            processor.processScoreFile(filePath);
            fail("Should have thrown NumberFormatException");
        } catch (NumberFormatException e) {
            pass("NumberFormatException thrown for whitespace-only file");
        } catch (Exception e) {
            fail("Wrong exception type: " + e.getClass().getName());
        }
    }
    
    private static void testEdgeCases() {
        System.out.println("\n[EDGE CASES]");
        ScoreProcessor processor = new ScoreProcessor();
        
        // Test 1: Large score
        try {
            String filePath = testDir + "/large_score.txt";
            createScoreFile(filePath, "9999");
            
            int result = processor.processScoreFile(filePath);
            if (result == 99990) {
                pass("Large score (9999) processed correctly");
            } else {
                fail("Expected 99990, got " + result);
            }
        } catch (Exception e) {
            fail("Large score processing threw exception: " + e.getMessage());
        }
        
        // Test 2: Leading zeros
        try {
            String filePath = testDir + "/leading_zeros.txt";
            createScoreFile(filePath, "00042");
            
            int result = processor.processScoreFile(filePath);
            if (result == 420) {
                pass("Leading zeros handled correctly");
            } else {
                fail("Expected 420, got " + result);
            }
        } catch (Exception e) {
            fail("Leading zeros processing threw exception: " + e.getMessage());
        }
        
        // Test 3: Special characters
        try {
            String filePath = testDir + "/special_chars.txt";
            createScoreFile(filePath, "42@#$");
            
            processor.processScoreFile(filePath);
            fail("Should have thrown NumberFormatException");
        } catch (NumberFormatException e) {
            pass("NumberFormatException thrown for special characters");
        } catch (Exception e) {
            fail("Wrong exception type: " + e.getClass().getName());
        }
        
        // Test 4: Multiplier verification
        int multiplier = processor.getScoreMultiplier();
        if (multiplier == 10) {
            pass("Score multiplier is 10");
        } else {
            fail("Expected multiplier 10, got " + multiplier);
        }
    }
    
    private static void createScoreFile(String filePath, String content) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
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
