# Quick Start Guide - Assignment 17

## Java Implementation

### Step 1: Compile the Code

```bash
cd Assignment17
javac InvalidEmailException.java
javac UnderageException.java
javac RegistrationService.java
```

### Step 2: Run Tests with JUnit 5

If you have Maven installed:
```bash
mvn test
```

Or compile and run tests manually:
```bash
javac -cp .:junit-jupiter-api-5.9.0.jar:junit-jupiter-engine-5.9.0.jar RegistrationServiceTest.java
java -cp .:junit-jupiter-api-5.9.0.jar:junit-jupiter-engine-5.9.0.jar:junit-platform-console-standalone-1.9.0.jar org.junit.platform.console.ConsoleLauncher --scan-classpath
```

### Step 3: Test a Simple Registration

Create a simple test file `SimpleTest.java`:

```java
public class SimpleTest {
    public static void main(String[] args) {
        RegistrationService service = new RegistrationService();
        
        try {
            // Valid registration
            boolean result = service.registerUser("john@example.com", 25);
            System.out.println("✓ Valid registration: " + result);
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
        
        try {
            // Invalid email
            service.registerUser("invalid-email", 25);
        } catch (InvalidEmailException e) {
            System.out.println("✓ Caught invalid email: " + e.getMessage());
        }
        
        try {
            // Underage user
            service.registerUser("jane@example.com", 15);
        } catch (UnderageException e) {
            System.out.println("✓ Caught underage user: " + e.getMessage());
        }
    }
}
```

Compile and run:
```bash
javac SimpleTest.java
java SimpleTest
```

Expected output:
```
✓ Valid registration: true
✓ Caught invalid email: Email format is invalid...
✓ Caught underage user: User must be at least 18 years old...
```

---

## Python Implementation

### Step 1: Install Dependencies

```bash
pip install pytest
```

### Step 2: Run Tests

```bash
cd Assignment17
pytest test_registration_service.py -v
```

For more detailed output:
```bash
pytest test_registration_service.py -v --tb=short
```

With coverage report:
```bash
pip install pytest-cov
pytest test_registration_service.py --cov=registration_service -v
```

### Step 3: Test a Simple Registration

Create a simple test file `simple_test.py`:

```python
from registration_service import RegistrationService
from invalid_email_error import InvalidEmailError
from underage_error import UnderageError

service = RegistrationService()

# Valid registration
try:
    result = service.register_user("john@example.com", 25)
    print(f"✓ Valid registration: {result}")
except Exception as e:
    print(f"✗ Error: {e}")

# Invalid email
try:
    service.register_user("invalid-email", 25)
except InvalidEmailError as e:
    print(f"✓ Caught invalid email: {e}")

# Underage user
try:
    service.register_user("jane@example.com", 15)
except UnderageError as e:
    print(f"✓ Caught underage user: {e}")
```

Run it:
```bash
python simple_test.py
```

Expected output:
```
✓ Valid registration: True
✓ Caught invalid email: Email format is invalid...
✓ Caught underage user: User must be at least 18 years old...
```

---

## Test Results Summary

### Java Tests
- **Total Tests**: 18
- **Test Classes**: 1 (RegistrationServiceTest)
- **Test Methods**: 18
- **Coverage Areas**:
  - Successful registrations (4 tests)
  - Email validation (8 tests)
  - Age validation (4 tests)
  - Service state (2 tests)

### Python Tests
- **Total Tests**: 20
- **Test Classes**: 5 (organized by functionality)
- **Test Methods**: 20
- **Coverage Areas**:
  - Successful registrations (4 tests)
  - Email validation (8 tests)
  - Age validation (4 tests)
  - Combined validation (2 tests)
  - Service state (2 tests)

---

## Key Test Cases

### Email Validation
✅ **Valid emails**:
- `user@example.com`
- `john.doe@example.co.uk`
- `user_name@domain.org`

❌ **Invalid emails**:
- `null` or empty string
- `user@domain` (no extension)
- `user@@example.com` (multiple @)
- `user#name@example.com` (invalid character)

### Age Validation
✅ **Valid ages**:
- 18 (minimum)
- 25, 65, etc. (above minimum)

❌ **Invalid ages**:
- 17 (below minimum)
- 0, -5 (invalid values)

---

## Common Issues & Solutions

### Java
**Issue**: `javac: command not found`
- **Solution**: Install JDK and add to PATH

**Issue**: `ClassNotFoundException` for JUnit
- **Solution**: Download JUnit 5 JAR files and add to classpath

### Python
**Issue**: `ModuleNotFoundError: No module named 'pytest'`
- **Solution**: Run `pip install pytest`

**Issue**: `ImportError` for custom modules
- **Solution**: Ensure all Python files are in the same directory

---

## Next Steps

1. Review the test results to understand validation behavior
2. Examine the exception messages for clarity
3. Study the regex pattern for email validation
4. Explore the test cases to understand edge cases
5. Read `IMPLEMENTATION_DETAILS.md` for deeper insights

---

## File Checklist

### Java Files
- [ ] InvalidEmailException.java
- [ ] UnderageException.java
- [ ] RegistrationService.java
- [ ] RegistrationServiceTest.java

### Python Files
- [ ] invalid_email_error.py
- [ ] underage_error.py
- [ ] registration_service.py
- [ ] test_registration_service.py

### Documentation
- [ ] ASSIGNMENT17_README.md
- [ ] QUICK_START.md (this file)
- [ ] IMPLEMENTATION_DETAILS.md
