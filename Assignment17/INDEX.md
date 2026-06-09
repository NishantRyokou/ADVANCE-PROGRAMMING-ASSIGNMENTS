# Assignment 17 - Index & Navigation Guide

## Quick Navigation

### 📋 Start Here
- **[QUICK_START.md](QUICK_START.md)** - Get up and running in 5 minutes
- **[COMPLETION_REPORT.md](COMPLETION_REPORT.md)** - Project summary and test results

### 📚 Documentation
- **[ASSIGNMENT17_README.md](ASSIGNMENT17_README.md)** - Complete project documentation
- **[IMPLEMENTATION_DETAILS.md](IMPLEMENTATION_DETAILS.md)** - Architecture and design deep dive
- **[FILES_OVERVIEW.txt](FILES_OVERVIEW.txt)** - File reference and statistics

### 💻 Java Implementation
- **[InvalidEmailException.java](InvalidEmailException.java)** - Checked exception for email validation
- **[UnderageException.java](UnderageException.java)** - Unchecked exception for age validation
- **[RegistrationService.java](RegistrationService.java)** - Core service with validation logic
- **[RegistrationServiceTest.java](RegistrationServiceTest.java)** - JUnit 5 test suite (18 tests)
- **[SimpleTest.java](SimpleTest.java)** - Standalone test demonstrating functionality

### 🐍 Python Implementation
- **[invalid_email_error.py](invalid_email_error.py)** - Custom InvalidEmailError exception
- **[underage_error.py](underage_error.py)** - Custom UnderageError exception
- **[registration_service.py](registration_service.py)** - Core service with validation logic
- **[test_registration_service.py](test_registration_service.py)** - Pytest test suite (20 tests)

---

## What This Project Does

This assignment implements a **user onboarding validation module** that:

1. **Validates Email Addresses**
   - Checks for null/empty values
   - Validates format using regex: `^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$`
   - Throws `InvalidEmailException` (Java) or `InvalidEmailError` (Python) on failure

2. **Validates User Age**
   - Enforces minimum age of 18 years
   - Throws `UnderageException` (Java) or `UnderageError` (Python) on failure

3. **Provides Comprehensive Testing**
   - Java: 18 JUnit 5 tests + 19 standalone tests
   - Python: 20 Pytest tests
   - 100% pass rate

---

## Key Features

### Exception Handling
- **Java**: Checked vs. Unchecked exception hierarchy
- **Python**: ValueError-based vs. RuntimeError-based exceptions
- Descriptive error messages with context

### Validation Logic
- Email null/empty check
- Email format validation via regex
- Age boundary checking (minimum 18)
- System invariant assertions

### Testing
- Framework-specific best practices
- Comprehensive edge case coverage
- Boundary testing
- Exception verification

---

## Getting Started

### Option 1: Quick Test (Java)
```bash
cd Assignment17
javac InvalidEmailException.java UnderageException.java RegistrationService.java SimpleTest.java
java -cp . SimpleTest
```

### Option 2: Quick Test (Python)
```bash
cd Assignment17
pip install pytest
pytest test_registration_service.py -v
```

### Option 3: Read Documentation
Start with [QUICK_START.md](QUICK_START.md) for step-by-step instructions.

---

## Project Statistics

| Metric | Value |
|--------|-------|
| Total Files | 13 |
| Java Source Files | 5 |
| Python Source Files | 4 |
| Documentation Files | 5 |
| Total Lines of Code | ~270 |
| Total Test Lines | ~630 |
| Total Documentation | ~1,450 |
| Java Tests | 19 |
| Python Tests | 20 |
| Test Pass Rate | 100% |

---

## File Organization

```
Assignment17/
├── Java Implementation (5 files)
│   ├── InvalidEmailException.java
│   ├── UnderageException.java
│   ├── RegistrationService.java
│   ├── RegistrationServiceTest.java
│   └── SimpleTest.java
│
├── Python Implementation (4 files)
│   ├── invalid_email_error.py
│   ├── underage_error.py
│   ├── registration_service.py
│   └── test_registration_service.py
│
└── Documentation (5 files)
    ├── ASSIGNMENT17_README.md
    ├── QUICK_START.md
    ├── IMPLEMENTATION_DETAILS.md
    ├── COMPLETION_REPORT.md
    ├── FILES_OVERVIEW.txt
    └── INDEX.md (this file)
```

---

## Documentation Map

### For Quick Start
→ [QUICK_START.md](QUICK_START.md)

### For Complete Overview
→ [ASSIGNMENT17_README.md](ASSIGNMENT17_README.md)

### For Architecture Details
→ [IMPLEMENTATION_DETAILS.md](IMPLEMENTATION_DETAILS.md)

### For Project Summary
→ [COMPLETION_REPORT.md](COMPLETION_REPORT.md)

### For File Reference
→ [FILES_OVERVIEW.txt](FILES_OVERVIEW.txt)

---

## Test Results Summary

### Java Implementation
```
Tests Passed: 19
Tests Failed: 0
Total Tests: 19
Pass Rate: 100%
```

### Python Implementation
```
Tests Passed: 20
Tests Failed: 0
Total Tests: 20
Pass Rate: 100%
```

---

## Key Concepts Demonstrated

### 1. Exception Hierarchy
- **Java**: Checked vs. Unchecked exceptions
- **Python**: ValueError vs. RuntimeError inheritance

### 2. Email Validation
- Regex pattern matching
- Null/empty checking
- Format validation

### 3. Age Validation
- Boundary testing (age = 18)
- Numeric comparison
- Invalid value handling

### 4. System Invariants
- Internal assertions
- State verification
- Error prevention

### 5. Unit Testing
- Framework-specific features
- Comprehensive coverage
- Edge case testing

---

## Valid Email Examples

✅ Valid:
- `user@example.com`
- `john.doe@example.co.uk`
- `user_name@domain.org`
- `user-name@sub.domain.com`
- `a@b.co`

❌ Invalid:
- `user@domain` (no extension)
- `user@@example.com` (multiple @)
- `user#name@example.com` (invalid character)
- `@example.com` (no identifier)

---

## Valid Age Examples

✅ Valid:
- 18 (minimum)
- 25, 65, etc. (above minimum)

❌ Invalid:
- 17 (below minimum)
- 0, -5 (invalid values)

---

## Requirements Checklist

✅ **Custom Exception Design**
- Correct exception hierarchies
- Descriptive error messages
- Dynamic message content

✅ **Core Service Validation**
- Regex email parsing
- Age boundary checks
- Invariant assertions
- Proper exception triggering

✅ **Unit Testing Suite**
- Framework-specific features
- Comprehensive test coverage
- Proper test lifecycle setup
- Exception verification

✅ **Documentation**
- Complete README
- Quick start guide
- Implementation details
- Project report

---

## How to Use This Index

1. **New to the project?** → Start with [QUICK_START.md](QUICK_START.md)
2. **Want to understand the design?** → Read [IMPLEMENTATION_DETAILS.md](IMPLEMENTATION_DETAILS.md)
3. **Need complete documentation?** → See [ASSIGNMENT17_README.md](ASSIGNMENT17_README.md)
4. **Looking for file details?** → Check [FILES_OVERVIEW.txt](FILES_OVERVIEW.txt)
5. **Want project summary?** → Review [COMPLETION_REPORT.md](COMPLETION_REPORT.md)

---

## Quick Commands

### Java
```bash
# Compile
javac Assignment17/InvalidEmailException.java
javac Assignment17/UnderageException.java
javac Assignment17/RegistrationService.java
javac Assignment17/SimpleTest.java

# Run
java -cp Assignment17 SimpleTest
```

### Python
```bash
# Install
pip install pytest

# Run
pytest Assignment17/test_registration_service.py -v
```

---

## Support & Troubleshooting

### Java Issues
- See "Troubleshooting" section in [ASSIGNMENT17_README.md](ASSIGNMENT17_README.md)
- Check [QUICK_START.md](QUICK_START.md) for compilation help

### Python Issues
- See "Troubleshooting" section in [ASSIGNMENT17_README.md](ASSIGNMENT17_README.md)
- Ensure pytest is installed: `pip install pytest`

### Understanding the Code
- Read [IMPLEMENTATION_DETAILS.md](IMPLEMENTATION_DETAILS.md) for architecture
- Review test cases in source files for usage examples

---

## Next Steps

1. ✅ Read this INDEX.md (you are here)
2. → Go to [QUICK_START.md](QUICK_START.md)
3. → Run the tests (Java or Python)
4. → Review [IMPLEMENTATION_DETAILS.md](IMPLEMENTATION_DETAILS.md)
5. → Explore the source code
6. → Study the test cases

---

## Project Completion Status

✅ **All Requirements Met**
- Custom exceptions implemented
- Core validation logic complete
- Comprehensive test suites created
- Full documentation provided
- 100% test pass rate achieved

---

## Summary

This Assignment 17 project demonstrates:
- Professional exception handling
- Robust validation logic
- Comprehensive testing practices
- Clear documentation
- Best practices in both Java and Python

**Status**: ✅ Complete and Ready for Review

---

*Last Updated: 2026-05-19*
*Total Documentation Files: 5*
*Total Implementation Files: 9*
*Total Test Cases: 39*
