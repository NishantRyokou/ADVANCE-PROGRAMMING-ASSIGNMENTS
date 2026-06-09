"""
Custom exception for invalid email format validation.
Inherits from ValueError as it represents an invalid value.
"""


class InvalidEmailError(ValueError):
    """
    Raised when an email address fails validation.
    
    This is a checked-like exception (inherits from ValueError) that indicates
    the email provided does not meet the required format or content constraints.
    """
    
    def __init__(self, message: str):
        """
        Initialize the InvalidEmailError with a descriptive message.
        
        Args:
            message: A descriptive error message explaining why the email is invalid
        """
        self.message = message
        super().__init__(self.message)
    
    def __str__(self) -> str:
        """Return the error message."""
        return self.message
