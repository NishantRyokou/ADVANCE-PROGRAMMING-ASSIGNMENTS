import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for ScoreProcessor.
 * Tests file reading, score calculation, and exception handling.
 */
@DisplayName("ScoreProcessor Test Suite")
public class ScoreProcessorTest {
    
    private ScoreProcessor processor;
    
    @TempDir
    Path tempDir;
    
    /**
     * Setup method executed before each test.
     * Initializes a fresh ScoreProcessor instance.
     */
    @BeforeEach
    public void setUp() {
        processor = new ScoreProcessor();
    }
    
    // ==================== Successful Processing Tests ====================
    
    @Test
    @DisplayName("Should successfully process valid score file")
    public void testSuccessfulScoreProcessing() throws IOException {
        // Arrange: Create a temporary file with a valid score
        File scoreFile = tempDir.resolve("valid_score.txt").toFile();
        try (FileWriter writer = new FileWriter(scoreFile)) {
            writer.write("42");
        }
        
        // Act: Process the file
        int result = processor.processScoreFile(scoreFile.getAbsolutePath());
        
        // Assert: Verify the calculation
        assertEquals(420, result, "Score 42 multiplied by 10 should equal 420");
    }
    
    @Test
    @DisplayName("Should successfully process score of zero")
    public void testProcessZeroScore() throws IOException {
        // Arrange: Create a temporary file with zero
        File scoreFile = tempDir.resolve("zero_score.txt").toFile();
        try (FileWriter writer = new FileWriter(scoreFile)) {
            writer.write("0");
        }
        
        // Act: Process the file
        int result = processor.processScoreFile(scoreFile.getAbsolutePath());
        
        // Assert: Verify the calculation
        assertEquals(0, result, "Score 0 multiplied by 10 should equal 0");
    }
    
    @Test
    @DisplayName("Should successfully process negative score")
    public void testProcessNegativeScore() throws IOException {
        // Arrange: Create a temporary file with a negative score
        File scoreFile = tempDir.resolve("negative_score.txt").toFile();
        try (FileWriter writer = new FileWriter(scoreFile)) {
            writer.write("-15");
        }
        
        // Act: Process the file
        int result = processor.processScoreFile(scoreFile.getAbsolutePath());
        
        // Assert: Verify the calculation
        assertEquals(-150, result, "Score -15 multiplied by 10 should equal -150");
    }
    
    @Test
    @DisplayName("Should successfully process large score")
    public void testProcessLargeScore() throws IOException {
        // Arrange: Create a temporary file with a large score
        File scoreFile = tempDir.resolve("large_score.txt").toFile();
        try (FileWriter writer = new FileWriter(scoreFile)) {
            writer.write("9999");
        }
        
        // Act: Process the file
        int result = processor.processScoreFile(scoreFile.getAbsolutePath());
        
        // Assert: Verify the calculation
        assertEquals(99990, result, "Score 9999 multiplied by 10 should equal 99990");
    }
    
    @Test
    @DisplayName("Should handle whitespace around score")
    public void testProcessScoreWithWhitespace() throws IOException {
        // Arrange: Create a temporary file with whitespace
        File scoreFile = tempDir.resolve("whitespace_score.txt").toFile();
        try (FileWriter writer = new FileWriter(scoreFile)) {
            writer.write("  50  ");
        }
        
        // Act: Process the file
        int result = processor.processScoreFile(scoreFile.getAbsolutePath());
        
        // Assert: Verify the calculation
        assertEquals(500, result, "Score 50 with whitespace multiplied by 10 should equal 500");
    }
    
    // ==================== File Not Found Tests ====================
    
    @Test
    @DisplayName("Should throw FileNotFoundException when file does not exist")
    public void testMissingFileThrowsException() {
        // Arrange: Use a non-existent file path
        String nonExistentPath = tempDir.resolve("nonexistent.txt").toAbsolutePath().toString();
        
        // Act & Assert: Verify FileNotFoundException is thrown
        FileNotFoundException exception = assertThrows(
            FileNotFoundException.class,
            () -> processor.processScoreFile(nonExistentPath),
            "Should throw FileNotFoundException for missing file"
        );
        
        // Verify exception message
        assertNotNull(exception.getMessage(), "Exception should have a message");
    }
    
    @Test
    @DisplayName("Should throw FileNotFoundException with descriptive message")
    public void testMissingFileErrorMessage() {
        // Arrange: Use a non-existent file path
        String nonExistentPath = "/nonexistent/path/to/file.txt";
        
        // Act & Assert: Verify exception is thrown and message is descriptive
        FileNotFoundException exception = assertThrows(
            FileNotFoundException.class,
            () -> processor.processScoreFile(nonExistentPath)
        );
        
        assertNotNull(exception.getMessage(), "Exception message should not be null");
    }
    
    // ==================== Invalid Format Tests ====================
    
    @Test
    @DisplayName("Should throw NumberFormatException for non-numeric content")
    public void testNonNumericContentThrowsException() throws IOException {
        // Arrange: Create a file with non-numeric content
        File scoreFile = tempDir.resolve("invalid_content.txt").toFile();
        try (FileWriter writer = new FileWriter(scoreFile)) {
            writer.write("abc");
        }
        
        // Act & Assert: Verify NumberFormatException is thrown
        NumberFormatException exception = assertThrows(
            NumberFormatException.class,
            () -> processor.processScoreFile(scoreFile.getAbsolutePath()),
            "Should throw NumberFormatException for non-numeric content"
        );
        
        assertNotNull(exception.getMessage(), "Exception should have a message");
    }
    
    @Test
    @DisplayName("Should throw NumberFormatException for decimal number")
    public void testDecimalNumberThrowsException() throws IOException {
        // Arrange: Create a file with a decimal number
        File scoreFile = tempDir.resolve("decimal_score.txt").toFile();
        try (FileWriter writer = new FileWriter(scoreFile)) {
            writer.write("42.5");
        }
        
        // Act & Assert: Verify NumberFormatException is thrown
        NumberFormatException exception = assertThrows(
            NumberFormatException.class,
            () -> processor.processScoreFile(scoreFile.getAbsolutePath()),
            "Should throw NumberFormatException for decimal number"
        );
        
        assertNotNull(exception.getMessage(), "Exception should have a message");
    }
    
    @Test
    @DisplayName("Should throw NumberFormatException for mixed alphanumeric")
    public void testMixedAlphanumericThrowsException() throws IOException {
        // Arrange: Create a file with mixed content
        File scoreFile = tempDir.resolve("mixed_content.txt").toFile();
        try (FileWriter writer = new FileWriter(scoreFile)) {
            writer.write("42abc");
        }
        
        // Act & Assert: Verify NumberFormatException is thrown
        NumberFormatException exception = assertThrows(
            NumberFormatException.class,
            () -> processor.processScoreFile(scoreFile.getAbsolutePath()),
            "Should throw NumberFormatException for mixed alphanumeric"
        );
        
        assertNotNull(exception.getMessage(), "Exception should have a message");
    }
    
    @Test
    @DisplayName("Should throw NumberFormatException for empty file")
    public void testEmptyFileThrowsException() throws IOException {
        // Arrange: Create an empty file
        File scoreFile = tempDir.resolve("empty_file.txt").toFile();
        scoreFile.createNewFile();
        
        // Act & Assert: Verify NumberFormatException is thrown
        NumberFormatException exception = assertThrows(
            NumberFormatException.class,
            () -> processor.processScoreFile(scoreFile.getAbsolutePath()),
            "Should throw NumberFormatException for empty file"
        );
        
        assertNotNull(exception.getMessage(), "Exception should have a message");
    }
    
    @Test
    @DisplayName("Should throw NumberFormatException for whitespace-only file")
    public void testWhitespaceOnlyFileThrowsException() throws IOException {
        // Arrange: Create a file with only whitespace
        File scoreFile = tempDir.resolve("whitespace_only.txt").toFile();
        try (FileWriter writer = new FileWriter(scoreFile)) {
            writer.write("   \n  \t  ");
        }
        
        // Act & Assert: Verify NumberFormatException is thrown
        NumberFormatException exception = assertThrows(
            NumberFormatException.class,
            () -> processor.processScoreFile(scoreFile.getAbsolutePath()),
            "Should throw NumberFormatException for whitespace-only file"
        );
        
        assertNotNull(exception.getMessage(), "Exception should have a message");
    }
    
    // ==================== Multiplier Tests ====================
    
    @Test
    @DisplayName("Should verify score multiplier is 10")
    public void testScoreMultiplier() {
        // Act: Get the multiplier
        int multiplier = processor.getScoreMultiplier();
        
        // Assert: Verify it equals 10
        assertEquals(10, multiplier, "Score multiplier should be 10");
    }
    
    // ==================== Edge Case Tests ====================
    
    @Test
    @DisplayName("Should process maximum integer value")
    public void testMaximumIntegerValue() throws IOException {
        // Arrange: Create a file with maximum integer
        File scoreFile = tempDir.resolve("max_int.txt").toFile();
        try (FileWriter writer = new FileWriter(scoreFile)) {
            writer.write(String.valueOf(Integer.MAX_VALUE / 10));
        }
        
        // Act: Process the file
        int result = processor.processScoreFile(scoreFile.getAbsolutePath());
        
        // Assert: Verify the calculation
        assertEquals((Integer.MAX_VALUE / 10) * 10, result);
    }
    
    @Test
    @DisplayName("Should process minimum integer value")
    public void testMinimumIntegerValue() throws IOException {
        // Arrange: Create a file with minimum integer
        File scoreFile = tempDir.resolve("min_int.txt").toFile();
        try (FileWriter writer = new FileWriter(scoreFile)) {
            writer.write(String.valueOf(Integer.MIN_VALUE / 10));
        }
        
        // Act: Process the file
        int result = processor.processScoreFile(scoreFile.getAbsolutePath());
        
        // Assert: Verify the calculation
        assertEquals((Integer.MIN_VALUE / 10) * 10, result);
    }
    
    @Test
    @DisplayName("Should throw exception for number with leading zeros")
    public void testNumberWithLeadingZeros() throws IOException {
        // Arrange: Create a file with leading zeros (should still parse)
        File scoreFile = tempDir.resolve("leading_zeros.txt").toFile();
        try (FileWriter writer = new FileWriter(scoreFile)) {
            writer.write("00042");
        }
        
        // Act: Process the file
        int result = processor.processScoreFile(scoreFile.getAbsolutePath());
        
        // Assert: Verify the calculation (leading zeros are ignored)
        assertEquals(420, result, "Leading zeros should be ignored");
    }
    
    @Test
    @DisplayName("Should throw exception for special characters")
    public void testSpecialCharactersThrowsException() throws IOException {
        // Arrange: Create a file with special characters
        File scoreFile = tempDir.resolve("special_chars.txt").toFile();
        try (FileWriter writer = new FileWriter(scoreFile)) {
            writer.write("42@#$");
        }
        
        // Act & Assert: Verify NumberFormatException is thrown
        NumberFormatException exception = assertThrows(
            NumberFormatException.class,
            () -> processor.processScoreFile(scoreFile.getAbsolutePath()),
            "Should throw NumberFormatException for special characters"
        );
        
        assertNotNull(exception.getMessage(), "Exception should have a message");
    }
}
