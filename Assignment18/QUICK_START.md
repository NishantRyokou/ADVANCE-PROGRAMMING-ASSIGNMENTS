# Quick Start - Assignment 18

## Java Implementation

### Step 1: Compile
```bash
cd Assignment18
javac ScoreProcessor.java SimpleScoreTest.java
```

### Step 2: Run Tests
```bash
java -cp . SimpleScoreTest
```

### Step 3: Expected Output
```
======================================================================
ScoreProcessor File Handling Tests
======================================================================

[SUCCESSFUL PROCESSING]
✓ PASS: Valid score (42) processed correctly
✓ PASS: Zero score processed correctly
✓ PASS: Negative score (-15) processed correctly
✓ PASS: Score with whitespace processed correctly

[FILE NOT FOUND HANDLING]
✓ PASS: FileNotFoundException thrown for missing file
✓ PASS: FileNotFoundException thrown for invalid path

[INVALID FORMAT HANDLING]
✓ PASS: NumberFormatException thrown for non-numeric content
✓ PASS: NumberFormatException thrown for decimal number
✓ PASS: NumberFormatException thrown for mixed alphanumeric
✓ PASS: NumberFormatException thrown for empty file
✓ PASS: NumberFormatException thrown for whitespace-only file

[EDGE CASES]
✓ PASS: Large score (9999) processed correctly
✓ PASS: Leading zeros handled correctly
✓ PASS: NumberFormatException thrown for special characters
✓ PASS: Score multiplier is 10

======================================================================
Test Summary
======================================================================
Tests Passed: 15
Tests Failed: 0
Total Tests: 15
======================================================================
```

## Python Implementation

### Step 1: Install Dependencies
```bash
pip install pytest
```

### Step 2: Run Tests
```bash
cd Assignment18
pytest test_score_processor.py -v
```

### Step 3: Expected Output
```
test_score_processor.py::TestSuccessfulProcessing::test_successful_score_processing PASSED
test_score_processor.py::TestSuccessfulProcessing::test_process_zero_score PASSED
test_score_processor.py::TestSuccessfulProcessing::test_process_negative_score PASSED
test_score_processor.py::TestSuccessfulProcessing::test_process_large_score PASSED
test_score_processor.py::TestSuccessfulProcessing::test_process_score_with_whitespace PASSED
test_score_processor.py::TestFileNotFound::test_missing_file_raises_exception PASSED
test_score_processor.py::TestFileNotFound::test_missing_file_error_message PASSED
test_score_processor.py::TestInvalidFormat::test_non_numeric_content_raises_exception PASSED
test_score_processor.py::TestInvalidFormat::test_decimal_number_raises_exception PASSED
test_score_processor.py::TestInvalidFormat::test_mixed_alphanumeric_raises_exception PASSED
test_score_processor.py::TestInvalidFormat::test_empty_file_raises_exception PASSED
test_score_processor.py::TestInvalidFormat::test_whitespace_only_file_raises_exception PASSED
test_score_processor.py::TestEdgeCases::test_maximum_integer_value PASSED
test_score_processor.py::TestEdgeCases::test_minimum_integer_value PASSED
test_score_processor.py::TestEdgeCases::test_number_with_leading_zeros PASSED
test_score_processor.py::TestEdgeCases::test_special_characters_raises_exception PASSED
test_score_processor.py::TestMultiplier::test_score_multiplier PASSED
test_score_processor.py::TestContextManager::test_context_manager_successful_processing PASSED
test_score_processor.py::TestContextManager::test_context_manager_missing_file PASSED
test_score_processor.py::TestContextManager::test_context_manager_invalid_format PASSED

======================== 20 passed in 0.XX s ========================
```

## Test File Examples

### Valid Score File
```
42
```
Result: 420

### Invalid Score File (Non-numeric)
```
abc
```
Error: NumberFormatException/ValueError

### Invalid Score File (Decimal)
```
42.5
```
Error: NumberFormatException/ValueError

### Empty File
```
```
Error: NumberFormatException/ValueError

## Key Concepts

### Exception Handling
- **FileNotFoundException/FileNotFoundError**: File doesn't exist
- **NumberFormatException/ValueError**: Invalid data format
- **Finally Block**: Always executes for cleanup

### Resource Management
- **Try-with-Resources (Java)**: Automatic cleanup
- **Context Manager (Python)**: Automatic cleanup
- **Explicit Finally**: Manual cleanup

### Score Calculation
- Read integer from file
- Multiply by 10
- Return result

## Common Issues

### Java
- **Compilation Error**: Ensure all files are in Assignment18/
- **FileNotFoundException**: Check file path is correct
- **NumberFormatException**: Ensure file contains only a number

### Python
- **ModuleNotFoundError**: Install pytest with `pip install pytest`
- **FileNotFoundError**: Check file path is correct
- **ValueError**: Ensure file contains only a number

## Next Steps

1. Review the test output
2. Examine the source code
3. Read ASSIGNMENT18_README.md for details
4. Study IMPLEMENTATION_DETAILS.md for architecture
5. Modify tests to explore edge cases

## File Locations

All files are in: `Assignment18/`

### Java Files
- ScoreProcessor.java
- ScoreProcessorTest.java
- SimpleScoreTest.java

### Python Files
- score_processor.py
- test_score_processor.py

### Documentation
- ASSIGNMENT18_README.md
- QUICK_START.md (this file)
- IMPLEMENTATION_DETAILS.md
