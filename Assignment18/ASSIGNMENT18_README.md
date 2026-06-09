# Assignment 18: File Handling and Exception Management

## Overview

This assignment implements a robust file handling system that reads score data from files, performs calculations, and manages exceptions gracefully. The system demonstrates proper resource cleanup, comprehensive error handling, and best practices for file I/O operations.

## Objectives

1. **Exception Handling & Structure**: Implement multi-catch exception handling for file operations
2. **Core Logic & Input Validation**: Read files, parse integers, and perform calculations
3. **Unit Testing**: Create comprehensive test suites for both happy and error paths

## Project Structure

```
Assignment18/
├── Java Implementation
│   ├── ScoreProcessor.java              # Core file processing service
│   ├── ScoreProcessorTest.java          # JUnit 5 test suite
│   └── SimpleScoreTest.java             # Standalone test suite
├── Python Implementation
│   ├── score_processor.py               # Core file processing service
│   └── test_score_processor.py          # Pytest test suite
└── Documentation
    ├── ASSIGNMENT18_README.md           # This file
    ├── QUICK_START.md                   # Quick start guide
    └── IMPLEMENTATION_DETAILS.md        # Architecture details
```

## Key Features

### File Reading
- ✅ Reads integer values from text files
- ✅ Handles whitespace trimming
- ✅ Validates file content

### Score Calculation
- ✅ Multiplies score by 10
- ✅ Handles positive, negative, and zero values
- ✅ Supports large integers

### Exception Handling
- ✅ FileNotFoundException for missing files
- ✅ NumberFormatException/ValueError for invalid data
- ✅ Descriptive error messages
- ✅ Proper exception propagation

### Resource Management
- ✅ Try-with-resources (Java)
- ✅ Context managers (Python)
- ✅ Explicit cleanup in finally blocks
- ✅ Guaranteed cleanup under all conditions

## Java Implementation

### Core Components

#### ScoreProcessor.java
```java
public int processScoreFile(String filePath) throws FileNotFoundException
```

**Features:**
- Try-with-resources for automatic resource cleanup
- Specific exception handling for FileNotFoundException and NumberFormatException
- Finally block for cleanup messages
- Alternative explicit try-catch-finally implementation

**Exception Handling:**
```java
try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
    // Read and process file
} catch (FileNotFoundException e) {
    // Handle missing file
} catch (NumberFormatException e) {
    // Handle invalid format
} finally {
    // Cleanup message
}
```

#### ScoreProcessorTest.java
- JUnit 5 test suite
- 18+ test cases
- @TempDir for temporary test files
- Tests for successful processing and error handling

#### SimpleScoreTest.java
- Standalone test suite
- 15 test cases
- No external dependencies
- Demonstrates all functionality

### Test Coverage

**Successful Processing (5 tests):**
- Valid score (42 → 420)
- Zero score (0 → 0)
- Negative score (-15 → -150)
- Large score (9999 → 99990)
- Score with whitespace

**File Not Found (2 tests):**
- Non-existent file
- Invalid path

**Invalid Format (5 tests):**
- Non-numeric content
- Decimal numbers
- Mixed alphanumeric
- Empty file
- Whitespace-only file

**Edge Cases (3 tests):**
- Maximum integer value
- Minimum integer value
- Leading zeros
- Special characters

## Python Implementation

### Core Components

#### score_processor.py
```python
def process_score_file(self, file_path: str) -> int
```

**Features:**
- Try-except-else-finally structure
- Specific exception handling for FileNotFoundError and ValueError
- Else block for successful processing message
- Finally block for cleanup
- Alternative context manager implementation

**Exception Handling:**
```python
try:
    file_handle = open(file_path, 'r')
    # Read and process file
except FileNotFoundError as e:
    # Handle missing file
except ValueError as e:
    # Handle invalid format
else:
    # Executed if no exception
    print("Data processed successfully")
finally:
    # Cleanup
    if file_handle is not None:
        file_handle.close()
```

#### test_score_processor.py
- Pytest test suite
- 20+ test cases
- Fixtures for setup and teardown
- Tests for successful processing and error handling

### Test Coverage

**Successful Processing (5 tests):**
- Valid score
- Zero score
- Negative score
- Large score
- Score with whitespace

**File Not Found (2 tests):**
- Missing file
- Invalid path

**Invalid Format (5 tests):**
- Non-numeric content
- Decimal numbers
- Mixed alphanumeric
- Empty file
- Whitespace-only file

**Edge Cases (3 tests):**
- Maximum integer value
- Minimum integer value
- Leading zeros
- Special characters

**Context Manager (3 tests):**
- Successful processing with context manager
- Missing file with context manager
- Invalid format with context manager

## Exception Handling Patterns

### Java Pattern

```java
try (Resource resource = new Resource()) {
    // Use resource
} catch (SpecificException1 e) {
    // Handle specific exception 1
} catch (SpecificException2 e) {
    // Handle specific exception 2
} finally {
    // Cleanup (resource auto-closed)
}
```

### Python Pattern

```python
try:
    resource = open(file_path)
    # Use resource
except SpecificException1 as e:
    # Handle specific exception 1
except SpecificException2 as e:
    # Handle specific exception 2
else:
    # Executed if no exception
finally:
    # Cleanup
    if resource is not None:
        resource.close()
```

## Error Messages

### FileNotFoundException/FileNotFoundError
```
ERROR: File not found at path: [path]
Details: [system message]
```

### NumberFormatException/ValueError
```
ERROR: Invalid number format in file
Details: [specific error]
Expected: A single integer value
```

### I/O Errors
```
ERROR: I/O error while reading file
Details: [system message]
```

## Valid Input Examples

✅ Valid scores:
- `42` → 420
- `0` → 0
- `-15` → -150
- `  50  ` → 500 (with whitespace)
- `00042` → 420 (with leading zeros)

❌ Invalid scores:
- `abc` (non-numeric)
- `42.5` (decimal)
- `42abc` (mixed)
- `` (empty)
- `   ` (whitespace only)
- `42@#$` (special characters)

## Compilation & Execution

### Java

**Compile:**
```bash
javac Assignment18/ScoreProcessor.java
javac Assignment18/SimpleScoreTest.java
```

**Run Tests:**
```bash
java -cp Assignment18 SimpleScoreTest
```

**Expected Output:**
```
======================================================================
ScoreProcessor File Handling Tests
======================================================================

[SUCCESSFUL PROCESSING]
✓ PASS: Valid score (42) processed correctly
✓ PASS: Zero score processed correctly
...

Tests Passed: 15
Tests Failed: 0
```

### Python

**Install Dependencies:**
```bash
pip install pytest
```

**Run Tests:**
```bash
pytest Assignment18/test_score_processor.py -v
```

**Expected Output:**
```
test_score_processor.py::TestSuccessfulProcessing::test_successful_score_processing PASSED
test_score_processor.py::TestFileNotFound::test_missing_file_raises_exception PASSED
...
```

## Resource Management

### Try-with-Resources (Java)
```java
try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
    // Resource automatically closed
}
```

**Benefits:**
- Automatic resource closing
- Exception-safe cleanup
- Cleaner code

### Context Manager (Python)
```python
with open(file_path, 'r') as file_handle:
    # Resource automatically closed
```

**Benefits:**
- Automatic resource closing
- Exception-safe cleanup
- Pythonic approach

### Explicit Cleanup (Java)
```java
BufferedReader reader = null;
try {
    reader = new BufferedReader(new FileReader(filePath));
    // Use reader
} finally {
    if (reader != null) {
        reader.close();
    }
}
```

**Benefits:**
- Explicit control
- Educational value
- Backward compatibility

## Best Practices Demonstrated

1. **Specific Exception Handling**
   - Catch specific exceptions, not generic Exception
   - Handle each exception type appropriately
   - Provide context in error messages

2. **Resource Management**
   - Use try-with-resources (Java) or context managers (Python)
   - Ensure cleanup in finally blocks
   - Handle cleanup exceptions

3. **Error Messages**
   - Descriptive and actionable
   - Include context (file path, expected format)
   - Use appropriate output streams (stdout vs stderr)

4. **Input Validation**
   - Check for null/empty values
   - Trim whitespace
   - Validate data format

5. **Testing**
   - Test happy path (successful processing)
   - Test error paths (missing file, invalid format)
   - Test edge cases (empty file, special characters)
   - Use framework-specific assertions

## Troubleshooting

### Java Issues

**Compilation Error: "unreported exception"**
- Solution: Add `throws` clause to method signature

**FileNotFoundException not caught**
- Solution: Ensure FileNotFoundException is caught before IOException

**Resource not closing**
- Solution: Use try-with-resources or explicit finally block

### Python Issues

**FileNotFoundError not caught**
- Solution: Ensure FileNotFoundError is caught before IOError

**ValueError not caught**
- Solution: Ensure ValueError is caught for int() conversion errors

**File not closing**
- Solution: Use context manager or explicit finally block

## Summary

This assignment demonstrates:
- ✅ Proper exception handling with specific catch blocks
- ✅ Resource management with cleanup guarantees
- ✅ File I/O operations with error handling
- ✅ Comprehensive test coverage
- ✅ Both Java and Python implementations
- ✅ Best practices for file handling

## References

- [Java Exception Handling](https://docs.oracle.com/javase/tutorial/essential/exceptions/)
- [Java Try-with-Resources](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html)
- [Python Exception Handling](https://docs.python.org/3/tutorial/errors.html)
- [Python Context Managers](https://docs.python.org/3/reference/compound_stmts.html#with)
- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Pytest Documentation](https://docs.pytest.org/)
