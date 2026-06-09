"""
User registration service with email and age validation.
Enforces strict business constraints before allowing registration.
"""

import re
from typing import Optional
from invalid_email_error import InvalidEmailError
from underage_error import UnderageError


class RegistrationService:
    """
    Service class for user registration with email and age validation.
    
    Enforces strict business constraints:
    - Email must not be null or empty
    - Email must match standard format: identifier@domain.extension
    - User must be at least 18 years old
    """
    
    # Email regex pattern: identifier@domain.extension
    # Matches: alphanumeric, dots, hyphens, underscores before @
    # Domain: alphanumeric and hyphens, followed by dot and extension
    EMAIL_REGEX = r"^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"
    
    # Minimum age requirement for registration
    MINIMUM_AGE = 18
    
    def __init__(self):
        """Initialize the RegistrationService with system state."""
        self._system_initialized = True
    
    def register_user(self, email: str, age: int) -> bool:
        """
        Register a user with email and age validation.
        
        Args:
            email: The user's email address
            age: The user's age in years
            
        Returns:
            True if registration is successful
            
        Raises:
            InvalidEmailError: If email is null, empty, or invalid format
            UnderageError: If age is below minimum requirement
            
        Raises:
            AssertionError: If system context is invalid
        """
        # Internal assert to verify system state invariant
        assert self._system_initialized, "System context is invalid: service not properly initialized"
        
        # Validate email is not None or empty
        if email is None or not email or email.strip() == "":
            raise InvalidEmailError(
                f"Email cannot be null or empty. Provided: {email}"
            )
        
        # Validate email format against regex pattern
        if not re.match(self.EMAIL_REGEX, email):
            raise InvalidEmailError(
                f"Email format is invalid. Expected format: identifier@domain.extension. Provided: {email}"
            )
        
        # Validate age is at least 18
        if age < self.MINIMUM_AGE:
            raise UnderageError(
                f"User must be at least {self.MINIMUM_AGE} years old to register. Provided age: {age}"
            )
        
        # Registration successful
        return True
    
    def get_minimum_age(self) -> int:
        """
        Get the minimum age requirement.
        
        Returns:
            The minimum age for registration
        """
        return self.MINIMUM_AGE
