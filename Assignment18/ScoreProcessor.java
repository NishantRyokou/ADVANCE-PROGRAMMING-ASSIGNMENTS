import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * ScoreProcessor class for reading and processing score data from files.
 * Handles file I/O with proper exception handling and resource cleanup.
 */
public class ScoreProcessor {
    
    // Multiplication factor for score calculation
    private static final int SCORE_MULTIPLIER = 10;
    
    /**
     * Processes a score from a file by reading an integer value,
     * multiplying it by 10, and returning the result.
     * 
     * Handles file not found and invalid number format errors gracefully.
     * Ensures proper resource cleanup in all scenarios.
     * 
     * @param filePath the path to the file containing the score
     * @return the score multiplied by 10
     * @throws FileNotFoundException if the file does not exist
     * @throws NumberFormatException if the file content is not a valid integer
     */
    public int processScoreFile(String filePath) throws FileNotFoundException {
        int score = 0;
        
        try {
            // Try-with-resources automatically closes the BufferedReader
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line = reader.readLine();
                
                // Check if file is empty
                if (line == null || line.trim().isEmpty()) {
                    throw new NumberFormatException("File is empty or contains no data");
                }
                
                // Parse the line as an integer
                score = Integer.parseInt(line.trim());
                
                // Calculate result
                int result = score * SCORE_MULTIPLIER;
                
                System.out.println("Successfully read score: " + score);
                System.out.println("Calculated result: " + result);
                
                return result;
            }
            
        } catch (FileNotFoundException e) {
            // Handle missing file
            System.err.println("ERROR: File not found at path: " + filePath);
            System.err.println("Details: " + e.getMessage());
            throw e;
            
        } catch (NumberFormatException e) {
            // Handle invalid number format
            System.err.println("ERROR: Invalid number format in file");
            System.err.println("Details: " + e.getMessage());
            System.err.println("Expected: A single integer value");
            throw e;
            
        } catch (IOException e) {
            // Handle other I/O errors
            System.err.println("ERROR: I/O error while reading file");
            System.err.println("Details: " + e.getMessage());
            throw new RuntimeException("Failed to read file: " + e.getMessage(), e);
            
        } finally {
            // Cleanup message - always executed
            System.out.println("File cleanup completed");
        }
    }
    
    /**
     * Alternative implementation using explicit try-catch-finally
     * (without try-with-resources) for demonstration purposes.
     * 
     * @param filePath the path to the file containing the score
     * @return the score multiplied by 10
     */
    public int processScoreFileExplicit(String filePath) throws FileNotFoundException {
        BufferedReader reader = null;
        
        try {
            // Open the file
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            
            // Check if file is empty
            if (line == null || line.trim().isEmpty()) {
                throw new NumberFormatException("File is empty or contains no data");
            }
            
            // Parse the line as an integer
            int score = Integer.parseInt(line.trim());
            
            // Calculate result
            int result = score * SCORE_MULTIPLIER;
            
            System.out.println("Successfully read score: " + score);
            System.out.println("Calculated result: " + result);
            
            return result;
            
        } catch (FileNotFoundException e) {
            // Handle missing file
            System.err.println("ERROR: File not found at path: " + filePath);
            System.err.println("Details: " + e.getMessage());
            throw e;
            
        } catch (NumberFormatException e) {
            // Handle invalid number format
            System.err.println("ERROR: Invalid number format in file");
            System.err.println("Details: " + e.getMessage());
            System.err.println("Expected: A single integer value");
            throw e;
            
        } catch (IOException e) {
            // Handle other I/O errors
            System.err.println("ERROR: I/O error while reading file");
            System.err.println("Details: " + e.getMessage());
            throw new RuntimeException("Failed to read file: " + e.getMessage(), e);
            
        } finally {
            // Cleanup - close the reader if it was opened
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("File cleanup completed");
                } catch (IOException e) {
                    System.err.println("ERROR: Failed to close file: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Gets the score multiplier constant.
     * 
     * @return the multiplier value (10)
     */
    public int getScoreMultiplier() {
        return SCORE_MULTIPLIER;
    }
}
