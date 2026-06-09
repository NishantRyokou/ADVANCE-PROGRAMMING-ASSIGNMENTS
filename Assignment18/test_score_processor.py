"""
Pytest test suite for ScoreProcessor.
Tests file reading, score calculation, and exception handling.
"""

import pytest
import tempfile
import os
from pathlib import Path
from score_processor import ScoreProcessor


@pytest.fixture
def processor():
    """
    Fixture that provides a fresh ScoreProcessor instance for each test.
    This is the shared configuration setup for all tests.
    """
    return ScoreProcessor()


@pytest.fixture
def temp_score_file():
    """
    Fixture that provides a temporary directory for test files.
    Automatically cleans up after tests.
    """
    with tempfile.TemporaryDirectory() as temp_dir:
        yield temp_dir


class TestSuccessfulProcessing:
    """Test cases for successful score processing."""
    
    def test_successful_score_processing(self, processor, temp_score_file):
        """Should successfully process valid score file."""
        # Arrange: Create a temporary file with a valid score
        score_file = Path(temp_score_file) / "valid_score.txt"
        score_file.write_text("42")
        
        # Act: Process the file
        result = processor.process_score_file(str(score_file))
        
        # Assert: Verify the calculation
        assert result == 420, "Score 42 multiplied by 10 should equal 420"
    
    def test_process_zero_score(self, processor, temp_score_file):
        """Should successfully process score of zero."""
        # Arrange: Create a temporary file with zero
        score_file = Path(temp_score_file) / "zero_score.txt"
        score_file.write_text("0")
        
        # Act: Process the file
        result = processor.process_score_file(str(score_file))
        
        # Assert: Verify the calculation
        assert result == 0, "Score 0 multiplied by 10 should equal 0"
    
    def test_process_negative_score(self, processor, temp_score_file):
        """Should successfully process negative score."""
        # Arrange: Create a temporary file with a negative score
        score_file = Path(temp_score_file) / "negative_score.txt"
        score_file.write_text("-15")
        
        # Act: Process the file
        result = processor.process_score_file(str(score_file))
        
        # Assert: Verify the calculation
        assert result == -150, "Score -15 multiplied by 10 should equal -150"
    
    def test_process_large_score(self, processor, temp_score_file):
        """Should successfully process large score."""
        # Arrange: Create a temporary file with a large score
        score_file = Path(temp_score_file) / "large_score.txt"
        score_file.write_text("9999")
        
        # Act: Process the file
        result = processor.process_score_file(str(score_file))
        
        # Assert: Verify the calculation
        assert result == 99990, "Score 9999 multiplied by 10 should equal 99990"
    
    def test_process_score_with_whitespace(self, processor, temp_score_file):
        """Should handle whitespace around score."""
        # Arrange: Create a temporary file with whitespace
        score_file = Path(temp_score_file) / "whitespace_score.txt"
        score_file.write_text("  50  ")
        
        # Act: Process the file
        result = processor.process_score_file(str(score_file))
        
        # Assert: Verify the calculation
        assert result == 500, "Score 50 with whitespace multiplied by 10 should equal 500"


class TestFileNotFound:
    """Test cases for file not found handling."""
    
    def test_missing_file_raises_exception(self, processor, temp_score_file):
        """Should raise FileNotFoundError when file does not exist."""
        # Arrange: Use a non-existent file path
        non_existent_path = os.path.join(temp_score_file, "nonexistent.txt")
        
        # Act & Assert: Verify FileNotFoundError is raised
        with pytest.raises(FileNotFoundError):
            processor.process_score_file(non_existent_path)
    
    def test_missing_file_error_message(self, processor):
        """Should raise FileNotFoundError with descriptive message."""
        # Arrange: Use a non-existent file path
        non_existent_path = "/nonexistent/path/to/file.txt"
        
        # Act & Assert: Verify exception is raised
        with pytest.raises(FileNotFoundError):
            processor.process_score_file(non_existent_path)


class TestInvalidFormat:
    """Test cases for invalid format handling."""
    
    def test_non_numeric_content_raises_exception(self, processor, temp_score_file):
        """Should raise ValueError for non-numeric content."""
        # Arrange: Create a file with non-numeric content
        score_file = Path(temp_score_file) / "invalid_content.txt"
        score_file.write_text("abc")
        
        # Act & Assert: Verify ValueError is raised
        with pytest.raises(ValueError):
            processor.process_score_file(str(score_file))
    
    def test_decimal_number_raises_exception(self, processor, temp_score_file):
        """Should raise ValueError for decimal number."""
        # Arrange: Create a file with a decimal number
        score_file = Path(temp_score_file) / "decimal_score.txt"
        score_file.write_text("42.5")
        
        # Act & Assert: Verify ValueError is raised
        with pytest.raises(ValueError):
            processor.process_score_file(str(score_file))
    
    def test_mixed_alphanumeric_raises_exception(self, processor, temp_score_file):
        """Should raise ValueError for mixed alphanumeric."""
        # Arrange: Create a file with mixed content
        score_file = Path(temp_score_file) / "mixed_content.txt"
        score_file.write_text("42abc")
        
        # Act & Assert: Verify ValueError is raised
        with pytest.raises(ValueError):
            processor.process_score_file(str(score_file))
    
    def test_empty_file_raises_exception(self, processor, temp_score_file):
        """Should raise ValueError for empty file."""
        # Arrange: Create an empty file
        score_file = Path(temp_score_file) / "empty_file.txt"
        score_file.write_text("")
        
        # Act & Assert: Verify ValueError is raised
        with pytest.raises(ValueError):
            processor.process_score_file(str(score_file))
    
    def test_whitespace_only_file_raises_exception(self, processor, temp_score_file):
        """Should raise ValueError for whitespace-only file."""
        # Arrange: Create a file with only whitespace
        score_file = Path(temp_score_file) / "whitespace_only.txt"
        score_file.write_text("   \n  \t  ")
        
        # Act & Assert: Verify ValueError is raised
        with pytest.raises(ValueError):
            processor.process_score_file(str(score_file))


class TestEdgeCases:
    """Test cases for edge cases."""
    
    def test_maximum_integer_value(self, processor, temp_score_file):
        """Should process maximum integer value."""
        # Arrange: Create a file with maximum integer
        max_val = (2**31 - 1) // 10  # Ensure no overflow
        score_file = Path(temp_score_file) / "max_int.txt"
        score_file.write_text(str(max_val))
        
        # Act: Process the file
        result = processor.process_score_file(str(score_file))
        
        # Assert: Verify the calculation
        assert result == max_val * 10
    
    def test_minimum_integer_value(self, processor, temp_score_file):
        """Should process minimum integer value."""
        # Arrange: Create a file with minimum integer
        min_val = (-2**31) // 10  # Ensure no overflow
        score_file = Path(temp_score_file) / "min_int.txt"
        score_file.write_text(str(min_val))
        
        # Act: Process the file
        result = processor.process_score_file(str(score_file))
        
        # Assert: Verify the calculation
        assert result == min_val * 10
    
    def test_number_with_leading_zeros(self, processor, temp_score_file):
        """Should handle number with leading zeros."""
        # Arrange: Create a file with leading zeros
        score_file = Path(temp_score_file) / "leading_zeros.txt"
        score_file.write_text("00042")
        
        # Act: Process the file
        result = processor.process_score_file(str(score_file))
        
        # Assert: Verify the calculation (leading zeros are ignored)
        assert result == 420, "Leading zeros should be ignored"
    
    def test_special_characters_raises_exception(self, processor, temp_score_file):
        """Should raise ValueError for special characters."""
        # Arrange: Create a file with special characters
        score_file = Path(temp_score_file) / "special_chars.txt"
        score_file.write_text("42@#$")
        
        # Act & Assert: Verify ValueError is raised
        with pytest.raises(ValueError):
            processor.process_score_file(str(score_file))


class TestMultiplier:
    """Test cases for multiplier verification."""
    
    def test_score_multiplier(self, processor):
        """Should verify score multiplier is 10."""
        # Act: Get the multiplier
        multiplier = processor.get_score_multiplier()
        
        # Assert: Verify it equals 10
        assert multiplier == 10, "Score multiplier should be 10"


class TestContextManager:
    """Test cases for context manager implementation."""
    
    def test_context_manager_successful_processing(self, processor, temp_score_file):
        """Should successfully process using context manager."""
        # Arrange: Create a temporary file with a valid score
        score_file = Path(temp_score_file) / "valid_score.txt"
        score_file.write_text("42")
        
        # Act: Process the file using context manager
        result = processor.process_score_file_with_context(str(score_file))
        
        # Assert: Verify the calculation
        assert result == 420, "Score 42 multiplied by 10 should equal 420"
    
    def test_context_manager_missing_file(self, processor, temp_score_file):
        """Should raise FileNotFoundError with context manager."""
        # Arrange: Use a non-existent file path
        non_existent_path = os.path.join(temp_score_file, "nonexistent.txt")
        
        # Act & Assert: Verify FileNotFoundError is raised
        with pytest.raises(FileNotFoundError):
            processor.process_score_file_with_context(non_existent_path)
    
    def test_context_manager_invalid_format(self, processor, temp_score_file):
        """Should raise ValueError with context manager."""
        # Arrange: Create a file with non-numeric content
        score_file = Path(temp_score_file) / "invalid_content.txt"
        score_file.write_text("abc")
        
        # Act & Assert: Verify ValueError is raised
        with pytest.raises(ValueError):
            processor.process_score_file_with_context(str(score_file))
