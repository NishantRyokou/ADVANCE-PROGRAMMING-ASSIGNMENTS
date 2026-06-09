/**
 * Unchecked exception (RuntimeException) for age validation failures.
 * This exception is thrown when a user is below the minimum age requirement.
 */
public class UnderageException extends RuntimeException {
    
    /**
     * Constructs an UnderageException with a descriptive message.
     * 
     * @param message the detail message explaining the age restriction
     */
    public UnderageException(String message) {
        super(message);
    }
    
    /**
     * Constructs an UnderageException with a message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public UnderageException(String message, Throwable cause) {
        super(message, cause);
    }
}
