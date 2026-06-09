"""
Custom exception for age validation failures.
Inherits from RuntimeError as it represents a runtime constraint violation.
"""


class UnderageError(RuntimeError):
    """
    Raised when a user is below the minimum age requirement.
    
    This is an unchecked exception (inherits from RuntimeError) that indicates
    the user does not meet the age constraint for registration.
    """
    
    def __init__(self, message: str):
        """
        Initialize the UnderageError with a descriptive message.
        
        Args:
            message: A descriptive error message explaining the age restriction
        """
        self.message = message
        super().__init__(self.message)
    
    def __str__(self) -> str:
        """Return the error message."""
        return self.message
