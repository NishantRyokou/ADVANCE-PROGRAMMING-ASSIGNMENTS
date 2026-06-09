/**
 * Checked exception for invalid email format.
 * This exception is thrown when an email address fails validation.
 */
public class InvalidEmailException extends Exception {
    
    /**
     * Constructs an InvalidEmailException with a descriptive message.
     * 
     * @param message the detail message explaining why the email is invalid
     */
    public InvalidEmailException(String message) {
        super(message);
    }
    
    /**
     * Constructs an InvalidEmailException with a message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public InvalidEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
