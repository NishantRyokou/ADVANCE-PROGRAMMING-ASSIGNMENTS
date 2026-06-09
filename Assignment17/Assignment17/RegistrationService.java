import java.util.regex.Pattern;

/**
 * Service class for user registration with email and age validation.
 * Enforces strict business constraints before allowing registration.
 */
public class RegistrationService {
    
    // Email regex pattern: identifier@domain.extension
    // Matches: alphanumeric, dots, hyphens, underscores before @
    // Domain: alphanumeric and hyphens, followed by dot and extension
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    // Minimum age requirement for registration
    private static final int MINIMUM_AGE = 18;
    
    // System state flag for invariant checking
    private boolean systemInitialized;
    
    /**
     * Constructs a RegistrationService with initialized system state.
     */
    public RegistrationService() {
        this.systemInitialized = true;
    }
    
    /**
     * Registers a user with email and age validation.
     * 
     * @param email the user's email address
     * @param age the user's age in years
     * @return true if registration is successful
     * @throws InvalidEmailException if email is null, empty, or invalid format
     * @throws UnderageException if age is below minimum requirement
     */
    public boolean registerUser(String email, int age) throws InvalidEmailException {
        // Internal assert to verify system state invariant
        assert systemInitialized : "System context is invalid: service not properly initialized";
        
        // Validate email is not null or empty
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidEmailException(
                "Email cannot be null or empty. Provided: " + email
            );
        }
        
        // Validate email format against regex pattern
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailException(
                "Email format is invalid. Expected format: identifier@domain.extension. Provided: " + email
            );
        }
        
        // Validate age is at least 18
        if (age < MINIMUM_AGE) {
            throw new UnderageException(
                "User must be at least " + MINIMUM_AGE + " years old to register. Provided age: " + age
            );
        }
        
        // Registration successful
        return true;
    }
    
    /**
     * Gets the minimum age requirement.
     * 
     * @return the minimum age for registration
     */
    public int getMinimumAge() {
        return MINIMUM_AGE;
    }
}
