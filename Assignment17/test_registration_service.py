"""
Pytest test suite for RegistrationService.
Tests email validation, age validation, and exception handling.
"""

import pytest
from registration_service import RegistrationService
from invalid_email_error import InvalidEmailError
from underage_error import UnderageError


@pytest.fixture
def registration_service():
    """
    Fixture that provides a fresh RegistrationService instance for each test.
    This is the shared configuration setup for all tests.
    """
    return RegistrationService()


class TestRegistrationServiceSuccessful:
    """Test cases for successful user registration."""
    
    def test_successful_registration(self, registration_service):
        """Should successfully register user with valid email and age."""
        result = registration_service.register_user("john.doe@example.com", 25)
        assert result is True, "Registration should return True for valid inputs"
    
    def test_successful_registration_at_minimum_age(self, registration_service):
        """Should successfully register user at minimum age boundary."""
        result = registration_service.register_user("jane.smith@domain.org", 18)
        assert result is True, "Registration should succeed at age 18"
    
    def test_successful_registration_with_various_email_formats(self, registration_service):
        """Should successfully register user with various valid email formats."""
        valid_emails = [
            "user@example.com",
            "john.doe@example.co.uk",
            "user_name@domain.org",
            "user-name@sub.domain.com",
            "a@b.co"
        ]
        
        for email in valid_emails:
            result = registration_service.register_user(email, 21)
            assert result is True, f"Registration should succeed for email: {email}"
    
    def test_successful_registration_with_age_above_minimum(self, registration_service):
        """Should successfully register user with age above minimum."""
        result = registration_service.register_user("user@example.com", 65)
        assert result is True, "Registration should succeed for age 65"


class TestEmailValidation:
    """Test cases for email validation."""
    
    def test_null_email_raises_exception(self, registration_service):
        """Should raise InvalidEmailError when email is None."""
        with pytest.raises(InvalidEmailError) as exc_info:
            registration_service.register_user(None, 25)
        assert "null or empty" in str(exc_info.value), \
            "Exception message should mention null or empty email"
    
    def test_empty_email_raises_exception(self, registration_service):
        """Should raise InvalidEmailError when email is empty string."""
        with pytest.raises(InvalidEmailError) as exc_info:
            registration_service.register_user("", 25)
        assert "null or empty" in str(exc_info.value), \
            "Exception message should mention null or empty email"
    
    def test_whitespace_email_raises_exception(self, registration_service):
        """Should raise InvalidEmailError when email is whitespace only."""
        with pytest.raises(InvalidEmailError) as exc_info:
            registration_service.register_user("   ", 25)
        assert "null or empty" in str(exc_info.value), \
            "Exception message should mention null or empty email"
    
    def test_email_without_at_symbol_raises_exception(self, registration_service):
        """Should raise InvalidEmailError when email lacks @ symbol."""
        with pytest.raises(InvalidEmailError) as exc_info:
            registration_service.register_user("invalidemail.com", 25)
        assert "invalid" in str(exc_info.value), \
            "Exception message should mention invalid format"
    
    def test_email_without_extension_raises_exception(self, registration_service):
        """Should raise InvalidEmailError when email lacks domain extension."""
        with pytest.raises(InvalidEmailError) as exc_info:
            registration_service.register_user("user@domain", 25)
        assert "invalid" in str(exc_info.value), \
            "Exception message should mention invalid format"
    
    def test_email_with_multiple_at_symbols_raises_exception(self, registration_service):
        """Should raise InvalidEmailError when email has multiple @ symbols."""
        with pytest.raises(InvalidEmailError) as exc_info:
            registration_service.register_user("user@@example.com", 25)
        assert "invalid" in str(exc_info.value), \
            "Exception message should mention invalid format"
    
    def test_email_with_invalid_characters_raises_exception(self, registration_service):
        """Should raise InvalidEmailError when email has invalid characters."""
        with pytest.raises(InvalidEmailError) as exc_info:
            registration_service.register_user("user#name@example.com", 25)
        assert "invalid" in str(exc_info.value), \
            "Exception message should mention invalid format"
    
    def test_email_with_space_raises_exception(self, registration_service):
        """Should raise InvalidEmailError when email contains spaces."""
        with pytest.raises(InvalidEmailError) as exc_info:
            registration_service.register_user("user name@example.com", 25)
        assert "invalid" in str(exc_info.value), \
            "Exception message should mention invalid format"


class TestAgeValidation:
    """Test cases for age validation."""
    
    def test_underage_raises_exception(self, registration_service):
        """Should raise UnderageError when age is below minimum."""
        with pytest.raises(UnderageError) as exc_info:
            registration_service.register_user("user@example.com", 17)
        assert "18" in str(exc_info.value), \
            "Exception message should mention minimum age of 18"
    
    def test_young_age_raises_exception(self, registration_service):
        """Should raise UnderageError when age is significantly below minimum."""
        with pytest.raises(UnderageError) as exc_info:
            registration_service.register_user("user@example.com", 5)
        assert "18" in str(exc_info.value), \
            "Exception message should mention minimum age requirement"
    
    def test_zero_age_raises_exception(self, registration_service):
        """Should raise UnderageError when age is zero."""
        with pytest.raises(UnderageError) as exc_info:
            registration_service.register_user("user@example.com", 0)
        assert "18" in str(exc_info.value), \
            "Exception message should mention minimum age requirement"
    
    def test_negative_age_raises_exception(self, registration_service):
        """Should raise UnderageError when age is negative."""
        with pytest.raises(UnderageError) as exc_info:
            registration_service.register_user("user@example.com", -5)
        assert "18" in str(exc_info.value), \
            "Exception message should mention minimum age requirement"


class TestCombinedValidation:
    """Test cases for combined validation scenarios."""
    
    def test_email_validation_priority(self, registration_service):
        """Should prioritize email validation over age validation."""
        # Invalid email with underage - should throw InvalidEmailError first
        with pytest.raises(InvalidEmailError):
            registration_service.register_user("invalid-email", 10)
    
    def test_both_invalid_with_age_check_second(self, registration_service):
        """Should throw UnderageError when both email and age are invalid."""
        # Valid email but invalid age
        with pytest.raises(UnderageError) as exc_info:
            registration_service.register_user("valid@example.com", 10)
        assert "18" in str(exc_info.value), \
            "Exception message should mention minimum age requirement"


class TestServiceState:
    """Test cases for service state consistency."""
    
    def test_service_state_consistency(self, registration_service):
        """Should maintain service state across multiple registrations."""
        # First registration
        result1 = registration_service.register_user("user1@example.com", 25)
        assert result1 is True, "First registration should succeed"
        
        # Second registration
        result2 = registration_service.register_user("user2@example.com", 30)
        assert result2 is True, "Second registration should succeed"
        
        # Service should still be functional
        with pytest.raises(UnderageError):
            registration_service.register_user("user3@example.com", 15)
    
    def test_multiple_service_instances_independent(self):
        """Should maintain independent state across multiple service instances."""
        service1 = RegistrationService()
        service2 = RegistrationService()
        
        # Both should work independently
        result1 = service1.register_user("user1@example.com", 25)
        result2 = service2.register_user("user2@example.com", 30)
        
        assert result1 is True, "Service 1 registration should succeed"
        assert result2 is True, "Service 2 registration should succeed"
