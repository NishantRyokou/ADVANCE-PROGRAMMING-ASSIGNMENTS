# Assignment 10: Student System in Python

A comprehensive student management system demonstrating core Object-Oriented Programming concepts in Python.

## Files

- **Assignment10Address.py** - Address class for composition
- **Assignment10Student.py** - Base Student class with property validation
- **Assignment10ScholarshipStudent.py** - ScholarshipStudent extending Student
- **Assignment10Demo.py** - Comprehensive demonstration and testing

## OOP Concepts Demonstrated

### Composition (HAS-A Relationship)

Student contains an Address object as a field. Address is a separate class with its own responsibility. Type checking ensures only valid Address objects are used.

```python
class Assignment10Student:
    def __init__(self, name, age, address, courses=None):
        if not isinstance(address, Assignment10Address):
            raise TypeError("address must be an Assignment10Address object")
        self.address = address
```

### Property Validation (@property decorator)

Age is stored as protected attribute (_age) and controlled via @property decorator with validation.

```python
@property
def age(self):
    return self._age

@age.setter
def age(self, value):
    if not isinstance(value, int):
        raise TypeError("Age must be an integer")
    if value < 5 or value > 100:
        raise ValueError("Age must be between 5 and 100")
    self._age = value
```

Validation Rules:
- Must be an integer
- Must be between 5 and 100 years old

### Inheritance and Method Overriding

ScholarshipStudent extends Student and overrides display() using super().

```python
class Assignment10ScholarshipStudent(Assignment10Student):
    def __init__(self, name, age, address, scholarship_amount, courses=None):
        super().__init__(name, age, address, courses)
        self.scholarship_amount = scholarship_amount
    
    def display(self):
        super().display()
        print(f"Scholarship Amount: ${self.scholarship_amount:,.2f}")
```

### Mutable Behavior (Course List Persistence)

Course list updates persist across all references to the same object.

```python
student = Assignment10Student("Alice", 20, address)
student.add_course("Math")
student.add_course("Physics")

same_student = student
same_student.add_course("Chemistry")

print(student.courses)
```

All changes made through any reference affect the original object.

### Data Validation

Type checking and value validation throughout:
- Address must be Assignment10Address object
- Age must be integer between 5-100
- Course must be non-empty string
- Scholarship amount must be non-negative number

### Polymorphism

Storing different types in single list and calling appropriate methods.

```python
students = [
    Assignment10Student("Grace", 20, address1, ["Math"]),
    Assignment10ScholarshipStudent("Henry", 21, address2, 12000, ["Engineering"]),
    Assignment10Student("Iris", 19, address3, ["Art"]),
    Assignment10ScholarshipStudent("Jack", 22, address1, 18000, ["Business"])
]

for student in students:
    student.display()
```

## Class Structure

```
Assignment10Address
├── street
├── city
└── zip_code

Assignment10Student
├── name
├── _age (protected, accessed via @property)
├── address (Assignment10Address object - composition)
├── courses (list)
├── add_course()
├── remove_course()
└── display()

Assignment10ScholarshipStudent (extends Assignment10Student)
├── scholarship_amount
├── display() (overrides)
└── update_scholarship()
```

## Usage Examples

### Creating Objects

```python
from Assignment10Address import Assignment10Address
from Assignment10Student import Assignment10Student
from Assignment10ScholarshipStudent import Assignment10ScholarshipStudent

address = Assignment10Address("123 Main St", "New York", "10001")

student = Assignment10Student("Alice Johnson", 20, address)

scholarship_student = Assignment10ScholarshipStudent(
    "Bob Smith", 
    21, 
    address, 
    scholarship_amount=15000
)
```

### Working with Courses

```python
student.add_course("Mathematics")
student.add_course("Physics")
student.add_course("Chemistry")

student.display()

student.remove_course("Physics")
```

### Property Validation

```python
student.age = 22
print(student.age)

try:
    student.age = 3
except ValueError as e:
    print(f"Error: {e}")
```

### Scholarship Operations

```python
scholarship_student.update_scholarship(20000)
scholarship_student.display()
```

## Execution

```bash
python Assignment10Demo.py
```

## Key Design Principles

✓ Composition - Student HAS-A Address  
✓ Property Validation - Age controlled via @property  
✓ Inheritance - ScholarshipStudent extends Student  
✓ Method Overriding - display() overridden with super()  
✓ Mutable Behavior - Course list changes persist  
✓ Type Checking - Validates all inputs  
✓ Error Handling - Raises appropriate exceptions  
✓ Polymorphism - Different types in single list  

## Output

The demo demonstrates:
- Composition with Address objects
- Property validation with age constraints
- Course list management with persistence
- Inheritance and method overriding
- Polymorphic behavior with mixed student types
- Mutable object behavior across references
- Comprehensive error handling

## Extending the System

To add a new student type:

```python
class InternshipStudent(Assignment10Student):
    def __init__(self, name, age, address, company, courses=None):
        super().__init__(name, age, address, courses)
        self.company = company
    
    def display(self):
        super().display()
        print(f"Internship Company: {self.company}")
```

Then add to the polymorphic list:
```python
students.append(InternshipStudent("Charlie", 20, address, "Tech Corp"))
```

## Summary

This student system effectively demonstrates:
- Composition through Address objects
- Property validation with @property decorator
- Inheritance with ScholarshipStudent
- Method overriding using super()
- Mutable behavior with persistent course lists
- Polymorphism with mixed student types
- Robust validation and error handling
- Clean, readable code without excessive comments
