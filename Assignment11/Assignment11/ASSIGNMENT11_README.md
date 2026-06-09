# Assignment 11: Library System in Python

A comprehensive library management system demonstrating abstraction, inheritance, polymorphism, and static counters in Python.

## Files

- **Assignment11LibraryItem.py** - Abstract base class with static counter
- **Assignment11Book.py** - Book subclass with author and pages
- **Assignment11DVD.py** - DVD subclass with duration and genre
- **Assignment11Magazine.py** - Magazine subclass with publisher and issue
- **Assignment11Library.py** - Library management class
- **Assignment11Demo.py** - Comprehensive demonstration and testing

## OOP Concepts Demonstrated

### Abstraction (Abstract Base Class)

LibraryItem is an abstract base class using ABC (Abstract Base Class) module.

```python
from abc import ABC, abstractmethod

class Assignment11LibraryItem(ABC):
    def __init__(self, title, year):
        self.title = title
        self.year = year
    
    @abstractmethod
    def displayInfo(self):
        pass
```

Cannot instantiate LibraryItem directly. Defines common structure for all library items. Forces subclasses to implement displayInfo(). Common fields: title, year. Provides shared functionality.

### Constructor Overloading with Default Arguments

Python uses default arguments to simulate constructor overloading.

```python
class Assignment11Book(Assignment11LibraryItem):
    def __init__(self, title, year, author, pages=None, isbn=None):
        super().__init__(title, year)
        self.author = author
        self.pages = pages if pages is not None else 0
        self.isbn = isbn if isbn is not None else "N/A"
```

Usage:
```python
book1 = Assignment11Book("Python", 2023, "John", 450, "978-1234567890")
book2 = Assignment11Book("Data Science", 2022, "Jane")
```

Optional parameters with default values. Flexible object creation. Reduces code duplication. Maintains backward compatibility.

### Static Counter (Class Variable)

Tracks total items created across all instances.

```python
class Assignment11LibraryItem(ABC):
    item_count = 0
    
    def __init__(self, title, year):
        Assignment11LibraryItem.item_count += 1
        self.item_id = Assignment11LibraryItem.item_count
    
    @classmethod
    def get_total_items(cls):
        return cls.item_count
```

item_count is shared across all instances. Each item gets unique ID. @classmethod for class-level operations. Persists across object creation.

### Method Overriding

Subclasses override abstract displayInfo() method.

```python
class Assignment11Book(Assignment11LibraryItem):
    def displayInfo(self):
        print(f"Title: {self.title}")
        print(f"Author: {self.author}")
        print(f"Pages: {self.pages}")

class Assignment11DVD(Assignment11LibraryItem):
    def displayInfo(self):
        print(f"Title: {self.title}")
        print(f"Director: {self.director}")
        print(f"Duration: {self.duration} minutes")
```

Each subclass provides unique implementation. Same method name, different behavior. Appropriate information displayed for each type.

### Polymorphism

Storing different types in single collection and calling methods.

```python
items = [
    Assignment11Book("1984", 1949, "George Orwell", 328),
    Assignment11DVD("Forrest Gump", 1994, 142, "Drama", "Robert Zemeckis", "PG-13"),
    Assignment11Magazine("The Economist", 2023, "The Economist Group", 1, 100),
]

for item in items:
    item.displayInfo()
```

Single interface for multiple types. Appropriate method called for each type. Easy to add new item types. Flexible and extensible.

## Class Features

### Assignment11Book
- Fields: author, pages, isbn
- Methods: displayInfo(), get_reading_time()
- Calculates estimated reading time

### Assignment11DVD
- Fields: duration, genre, director, rating
- Methods: displayInfo(), get_duration_hours(), is_long_movie()
- Converts duration to hours
- Checks if movie exceeds threshold

### Assignment11Magazine
- Fields: publisher, issue_number, pages
- Methods: displayInfo(), get_issue_label()
- Generates formatted issue label

### Assignment11Library
- Manages collection of items
- Methods: add_item(), remove_item(), display_all_items()
- Search by title and year
- Display statistics

## Usage Examples

### Creating Items

```python
from Assignment11Book import Assignment11Book
from Assignment11DVD import Assignment11DVD
from Assignment11Magazine import Assignment11Magazine

book = Assignment11Book("Clean Code", 2008, "Robert Martin", 464)
dvd = Assignment11DVD("Inception", 2010, 148, "Sci-Fi", "Christopher Nolan", "PG-13")
magazine = Assignment11Magazine("Nature", 2023, "Springer Nature", 1, 150)
```

### Using Library

```python
from Assignment11Library import Assignment11Library

library = Assignment11Library("Central City Library")
library.add_item(book)
library.add_item(dvd)
library.add_item(magazine)

library.display_all_items()
library.get_statistics()
```

### Searching

```python
library.search_by_title("Python")
library.search_by_year(2023)
```

### Item-Specific Methods

```python
reading_hours = book.get_reading_time()
movie_hours = dvd.get_duration_hours()
is_long = dvd.is_long_movie()
issue_label = magazine.get_issue_label()
```

### Static Counter

```python
total = Assignment11LibraryItem.get_total_items()
Assignment11LibraryItem.reset_counter()
```

## Execution

```bash
python Assignment11Demo.py
```

## Key Design Principles

✓ Abstraction - Abstract base class defines common structure  
✓ Inheritance - Subclasses extend LibraryItem  
✓ Polymorphism - Different types in single collection  
✓ Method Overriding - displayInfo() implemented in each subclass  
✓ Constructor Overloading - Default arguments for flexibility  
✓ Static Counter - Tracks total items created  
✓ Type Checking - Validates all inputs  
✓ Error Handling - Raises appropriate exceptions  

## Output

The demo demonstrates:
- Abstract base class preventing direct instantiation
- Constructor overloading with default arguments
- Static counter tracking item creation
- Method overriding with different implementations
- Polymorphic behavior with mixed item types
- Library management functionality
- Search capabilities
- Item-specific methods
- Comprehensive error handling

## Extending the System

To add a new item type:

```python
from Assignment11LibraryItem import Assignment11LibraryItem

class Assignment11AudioBook(Assignment11LibraryItem):
    def __init__(self, title, year, author, duration, narrator=None):
        super().__init__(title, year)
        self.author = author
        self.duration = duration
        self.narrator = narrator if narrator is not None else "Unknown"
    
    def displayInfo(self):
        print(f"Title: {self.title}")
        print(f"Author: {self.author}")
        print(f"Narrator: {self.narrator}")
        print(f"Duration: {self.duration} minutes")
    
    def get_duration_hours(self):
        return round(self.duration / 60, 2)
```

Then use in library:
```python
audiobook = Assignment11AudioBook("Dune", 2021, "Frank Herbert", 720, "Scott Brick")
library.add_item(audiobook)
```

## Summary

This library system effectively demonstrates:
- Abstraction through abstract base class
- Inheritance with multiple subclasses
- Polymorphism with mixed item types
- Method overriding with unique implementations
- Constructor overloading using default arguments
- Static counter for tracking items
- Robust validation and error handling
- Clean, readable code without excessive comments
- Extensible design for adding new item types
