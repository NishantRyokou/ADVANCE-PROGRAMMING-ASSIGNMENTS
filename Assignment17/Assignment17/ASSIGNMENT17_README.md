# Assignment 17: User Onboarding Validation Module

## Overview

This assignment implements a comprehensive user registration validation system that enforces strict business constraints on email format and user age. The system provides both Java and Python implementations with custom exception handling and comprehensive test suites.

## Objectives

1. **Custom Exception Design**: Implement proper exception hierarchies with descriptive error messages
2. **Core Service Validation**: Validate email format using regex and enforce age restrictions
3. **Unit Testing**: Create comprehensive test suites using framework-specific assertions

## Project Structure

```
Assignment17/
├── Java Implementation
│   ├── InvalidEmailException.java      # Checked exception for email validation
│   ├── UnderageException.java          # Unchecked exception for age validation
│   ├── RegistrationService.java        # Core registration service
│   └── RegistrationServiceTest.java    # JUnit 5 test suite
├── Python Implementation
│   ├── invalid_email_error.py          # Custom InvalidEmailError exception
│   ├── underage_error.py               # Custom UnderageError exception
│   ├── registration_service.py         # Core registration service
│   └── test_registration_service.py    # Pytest test suite
└── Documentation
    ├── ASSIGNMENT17_README.md          # This file
    ├── QUICK_START.md                  # Quick start guide
    └── IMPLEMENTATION_DETAILS.md       # Detailed implementation notes
```

## Key Features

### Email Validation
- **Null/Empty Check**: Ensures email is not null or empty
- **Format Validation**: Uses regex pattern `^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$`
- **Valid Format**: `identifier@domain.extension`
  - Identifier: alphanumeric, dots, hyphens, underscores
  - Domain: alphanumeric and hyphens
  - Extension: at least 2 letters

### Age Validation
- **Minimum Age**: 18 years old
- **Boundary Testing**: Validates at exact boundary (18) and above
- **Negative/Zero Handling**: Rejects invalid age values

### Exception Handling

#### Java Implementation
- **InvalidEmailException** (Checked Exception)
  - Extends `Exception`
  - Thrown for email validation failures
  - Must be caught or declared in method signature
  
- **UnderageException** (Unchecked Exception)
  - Extends `RuntimeException`
  - Thrown for age validation failures
  - Optional to catch

#### Python Implementation
- **InvalidEmailError** (ValueError-based)
  - Inherits from `ValueError`
  - Represents invalid value constraint
  - Similar to checked exception behavior
  
- **UnderageError** (RuntimeError-based)
  - Inherits from `RuntimeError`
  - Represents runtime constraint violation
  - Similar to unchecked exception behavior

### System Invariants
- Internal `assert` statements verify system initialization state
- Ensures service is properly initialized before processing registrations
- Prevents invalid system context from processing user data

## Java Implementation

### Compilation

```bash
cd Assignment17
javac InvalidEmailException.java
javac UnderageException.java
javac RegistrationService.java
javac -cp .:path/to/junit-jupiter-api-5.x.x.jar:path/to/junit-jupiter-engine-5.x.x.jar RegistrationServiceTest.java
```

### Running Tests

Using Maven:
```bash
mvn test
```

Using JUnit directly:
```bash
java -cp .:path/to/junit-platform-console-standalone.jar org.junit.platform.console.ConsoleLauncher --scan-classpath
```

### Example Usage

```java
RegistrationService service = new RegistrationService();

try {
    // Valid registration
    boolean success = service.registerUser("john.doe@example.com", 25);
    System.out.println("Registration successful: " + success);
} catch (InvalidEmailException e) {
    System.err.println("Email validation failed: " + e.getMessage());
} catch (UnderageException e) {
    System.err.println("Age validation failed: " + e.getMessage());
}
```

## Python Implementation

### Installation

Ensure pytest is installed:
```bash
pip install pytest
```

### Running Tests

```bash
cd Assignment17
pytest test_registration_service.py -v
```

With coverage:
```bash
pip install pytest-cov
pytest test_registration_service.py --cov=registration_service -v
```

### Example Usage

```python
from registration_service import RegistrationService
from invalid_email_error import InvalidEmailError
from underage_error import UnderageError

service = RegistrationService()

try:
    # Valid registration
    success = service.register_user("john.doe@example.com", 25)
    print(f"Registration successful: {success}")
except InvalidEmailError as e:
    print(f"Email validation failed: {e}")
except UnderageError as e:
    print(f"Age validation failed: {e}")
```

## Test Coverage

### Java Test Suite (RegistrationServiceTest)

**Successful Registration Tests (4 tests)**
- Valid email and age
- Minimum age boundary (18)
- Various valid email formats
- Age above minimum

**Email Validation Tests (8 tests)**
- Null email
- Empty email
- Whitespace-only email
- Missing @ symbol
- Missing domain extension
- Multiple @ symbols
- Invalid characters
- Combined validation priority

**Age Validation Tests (4 tests)**
- Below minimum age (17)
- Significantly below minimum (5)
- Zero age
- Negative age

**Service State Tests (2 tests)**
- Consistency across multiple registrations
- Service functionality after multiple operations

**Total: 18 test cases**

### Python Test Suite (test_registration_service.py)

**Successful Registration Tests (4 tests)**
- Valid email and age
- Minimum age boundary (18)
- Various valid email formats
- Age above minimum

**Email Validation Tests (8 tests)**
- None email
- Empty email
- Whitespace-only email
- Missing @ symbol
- Missing domain extension
- Multiple @ symbols
- Invalid characters
- Email with spaces

**Age Validation Tests (4 tests)**
- Below minimum age (17)
- Significantly below minimum (5)
- Zero age
- Negative age

**Combined Validation Tests (2 tests)**
- Email validation priority
- Age check when email is valid

**Service State Tests (2 tests)**
- Consistency across multiple registrations
- Independent state across instances

**Total: 20 test cases**

## Validation Rules

### Email Validation Order
1. Check if email is null or empty
2. Validate email format against regex pattern
3. If both checks pass, proceed to age validation

### Age Validation
1. Check if age is at least 18 years old
2. Throw UnderageException if age < 18

### Exception Priority
- Email validation exceptions are thrown before age validation
- This ensures consistent error reporting order

## Valid Email Examples

✅ Valid emails:
- `user@example.com`
- `john.doe@example.co.uk`
- `user_name@domain.org`
- `user-name@sub.domain.com`
- `a@b.co`
- `test.email+tag@company.co.uk`

❌ Invalid emails:
- `user@domain` (no extension)
- `user@@example.com` (multiple @)
- `user@.com` (no domain)
- `@example.com` (no identifier)
- `user name@example.com` (space in identifier)
- `user#name@example.com` (invalid character)

## System Invariants

The service includes internal assertions to verify:
- System is properly initialized before processing registrations
- Service state remains valid across multiple operations
- No invalid system context processes user data

Enable assertions in Java:
```bash
java -ea RegistrationServiceTest
```

## Error Messages

### InvalidEmailException Messages
- "Email cannot be null or empty. Provided: [value]"
- "Email format is invalid. Expected format: identifier@domain.extension. Provided: [value]"

### UnderageException Messages
- "User must be at least 18 years old to register. Provided age: [value]"

## Implementation Highlights

### Regex Pattern Explanation
```
^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$
```
- `^` - Start of string
- `[a-zA-Z0-9._-]+` - One or more valid identifier characters
- `@` - Literal @ symbol
- `[a-zA-Z0-9.-]+` - One or more valid domain characters
- `\.` - Literal dot (escaped)
- `[a-zA-Z]{2,}` - Two or more letters for extension
- `$` - End of string

### Exception Hierarchy

**Java:**
```
Exception (checked)
└── InvalidEmailException

RuntimeException (unchecked)
└── UnderageException
```

**Python:**
```
ValueError (checked-like)
└── InvalidEmailError

RuntimeError (unchecked-like)
└── UnderageError
```

## Best Practices Demonstrated

1. **Separation of Concerns**: Exceptions and service logic are separate
2. **Descriptive Error Messages**: All exceptions include context about the failure
3. **Comprehensive Testing**: Multiple test cases cover edge cases and boundaries
4. **Proper Exception Handling**: Checked vs. unchecked exceptions used appropriately
5. **System Invariants**: Internal assertions verify system state
6. **Regex Validation**: Standard email format validation using regex
7. **Boundary Testing**: Tests include minimum age boundary (18)

## Troubleshooting

### Java Compilation Issues
- Ensure JDK 11+ is installed
- Check classpath includes all required JAR files
- Verify file names match class names exactly

### Python Import Errors
- Ensure all Python files are in the same directory
- Check Python version is 3.7+
- Verify pytest is installed: `pip install pytest`

### Test Failures
- Check email regex pattern matches expected format
- Verify minimum age is set to 18
- Ensure exception types are correct (checked vs. unchecked)

## References

- [Java Exception Handling](https://docs.oracle.com/javase/tutorial/essential/exceptions/)
- [Python Exception Handling](https://docs.python.org/3/tutorial/errors.html)
- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Pytest Documentation](https://docs.pytest.org/)
- [Email Validation Regex](https://www.regular-expressions.info/email.html)

## Summary

This assignment demonstrates:
- ✅ Custom exception design with proper hierarchies
- ✅ Email validation using regex patterns
- ✅ Age boundary validation
- ✅ System invariant assertions
- ✅ Comprehensive unit testing with framework-specific features
- ✅ Proper exception handling and error messaging
- ✅ Both Java and Python implementations with equivalent functionality
