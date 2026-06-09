"""
ScoreProcessor module for reading and processing score data from files.
Handles file I/O with proper exception handling and resource cleanup.
"""


class ScoreProcessor:
    """
    Processes score data from files with comprehensive error handling.
    
    Reads an integer score from a file, multiplies it by 10, and returns the result.
    Handles file not found and invalid format errors gracefully.
    Ensures proper resource cleanup in all scenarios.
    """
    
    # Multiplication factor for score calculation
    SCORE_MULTIPLIER = 10
    
    def process_score_file(self, file_path: str) -> int:
        """
        Process a score from a file by reading an integer value,
        multiplying it by 10, and returning the result.
        
        Handles file not found and invalid number format errors gracefully.
        Ensures proper resource cleanup in all scenarios using try-except-else-finally.
        
        Args:
            file_path: The path to the file containing the score
            
        Returns:
            The score multiplied by 10
            
        Raises:
            FileNotFoundError: If the file does not exist
            ValueError: If the file content is not a valid integer
        """
        file_handle = None
        
        try:
            # Open the file
            file_handle = open(file_path, 'r')
            
            # Read the first line
            line = file_handle.readline()
            
            # Check if file is empty
            if not line or not line.strip():
                raise ValueError("File is empty or contains no data")
            
            # Parse the line as an integer
            score = int(line.strip())
            
            # Calculate result
            result = score * self.SCORE_MULTIPLIER
            
            print(f"Successfully read score: {score}")
            print(f"Calculated result: {result}")
            
            return result
            
        except FileNotFoundError as e:
            # Handle missing file
            print(f"ERROR: File not found at path: {file_path}")
            print(f"Details: {e}")
            raise
            
        except ValueError as e:
            # Handle invalid number format
            print("ERROR: Invalid number format in file")
            print(f"Details: {e}")
            print("Expected: A single integer value")
            raise
            
        except IOError as e:
            # Handle other I/O errors
            print("ERROR: I/O error while reading file")
            print(f"Details: {e}")
            raise RuntimeError(f"Failed to read file: {e}") from e
            
        else:
            # Executed if no exception occurred
            print("Data processed successfully")
            
        finally:
            # Cleanup - always executed
            if file_handle is not None:
                try:
                    file_handle.close()
                    print("File cleanup completed")
                except IOError as e:
                    print(f"ERROR: Failed to close file: {e}")
    
    def process_score_file_with_context(self, file_path: str) -> int:
        """
        Alternative implementation using context manager (with statement).
        This is the Pythonic way to handle file I/O.
        
        Args:
            file_path: The path to the file containing the score
            
        Returns:
            The score multiplied by 10
            
        Raises:
            FileNotFoundError: If the file does not exist
            ValueError: If the file content is not a valid integer
        """
        try:
            # Context manager automatically closes the file
            with open(file_path, 'r') as file_handle:
                # Read the first line
                line = file_handle.readline()
                
                # Check if file is empty
                if not line or not line.strip():
                    raise ValueError("File is empty or contains no data")
                
                # Parse the line as an integer
                score = int(line.strip())
                
                # Calculate result
                result = score * self.SCORE_MULTIPLIER
                
                print(f"Successfully read score: {score}")
                print(f"Calculated result: {result}")
                
                return result
                
        except FileNotFoundError as e:
            # Handle missing file
            print(f"ERROR: File not found at path: {file_path}")
            print(f"Details: {e}")
            raise
            
        except ValueError as e:
            # Handle invalid number format
            print("ERROR: Invalid number format in file")
            print(f"Details: {e}")
            print("Expected: A single integer value")
            raise
            
        except IOError as e:
            # Handle other I/O errors
            print("ERROR: I/O error while reading file")
            print(f"Details: {e}")
            raise RuntimeError(f"Failed to read file: {e}") from e
            
        else:
            # Executed if no exception occurred
            print("Data processed successfully")
            
        finally:
            # Cleanup message - always executed
            print("File cleanup completed")
    
    def get_score_multiplier(self) -> int:
        """
        Get the score multiplier constant.
        
        Returns:
            The multiplier value (10)
        """
        return self.SCORE_MULTIPLIER
