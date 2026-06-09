# Assignment 14 Summary

## Project Overview

**Assignment 14** demonstrates circular references and garbage collection in Python. The program creates scenarios where objects are "dead" (unreachable from code) but still have reference counts higher than zero due to circular references, then forces the garbage collector to clean them up.

## What You'll Learn

1. **Reference Counting**: How Python tracks object references
2. **Circular References**: Why they prevent automatic cleanup
3. **Garbage Collection**: How Python detects and cleans up cycles
4. **sys.getrefcount()**: Inspecting reference counts
5. **gc Module**: Controlling garbage collection
6. **Memory Management**: Best practices for managing memory

## Program Structure

### circular_reference_demo.py

Four comprehensive demonstrations:

#### 1. demonstrate_circular_reference()
**Purpose**: Step-by-step walkthrough of circular reference problem

**Shows**:
- Reference count before and after circular reference
- Objects remaining in memory after deletion
- Garbage collection cleanup

**Output**: 11 steps showing the complete lifecycle

#### 2. demonstrate_with_debug_info()
**Purpose**: Detailed analysis of circular reference lifecycle

**Shows**:
- Object creation with IDs
- Reference count changes
- Unreachable object detection
- Memory state at each phase

**Output**: 6 phases with detailed information

#### 3. demonstrate_reference_cycle_types()
**Purpose**: Different types of circular references

**Shows**:
- Simple cycle (A -> B -> A)
- Self-reference (A -> A)
- Complex cycle (A -> B -> C -> A)

**Output**: Objects collected for each cycle type

#### 4. demonstrate_memory_impact()
**Purpose**: Memory usage with and without garbage collection

**Shows**:
- Memory leak with GC disabled
- Memory freed with gc.collect()
- Comparison of memory usage

**Output**: Before and after memory statistics

## Key Concepts

### Reference Counting

```python
import sys

obj = []
print(sys.getrefcount(obj))  # At least 2
# 1. Local variable 'obj'
# 2. Parameter to getrefcount()
```

**When refcount = 0**: Object is immediately deallocated

### Circular Reference Problem

```python
a = Node("A")
b = Node("B")
a.link = b  # a references b
b.link = a  # b references a (cycle)

# Reference counts:
# a: 2 (variable 'a' + b.link)
# b: 2 (variable 'b' + a.link)

del a
del b

# Reference counts:
# a: 1 (still referenced by b.link)
# b: 1 (still referenced by a.link)

# Objects remain in memory indefinitely!
```

### Garbage Collection Solution

```python
import gc

# Create circular reference
a = Node("A")
b = Node("B")
a.link = b
b.link = a

del a
del b

# Objects are unreachable but still in memory

# Force garbage collection
collected = gc.collect()
print(f"Collected {collected} objects")

# Objects are now deallocated
```

## Reference Counting vs Garbage Collection

| Aspect | Reference Counting | Garbage Collection |
|--------|-------------------|-------------------|
| **Mechanism** | Counts references | Detects cycles |
| **Timing** | Immediate | Periodic |
| **Handles Cycles** | No | Yes |
| **Performance** | Fast | Slower |
| **Memory** | Immediate cleanup | Delayed cleanup |

## The Problem Explained

### Why Reference Counting Fails

Reference counting works by tracking how many references point to an object. When the count reaches zero, the object is deallocated.

**Problem**: Circular references prevent the count from reaching zero.

```
a = Node("A")
b = Node("B")
a.link = b
b.link = a

# a's refcount: 2 (variable 'a' + b.link)
# b's refcount: 2 (variable 'b' + a.link)

del a
del b

# a's refcount: 1 (still referenced by b.link)
# b's refcount: 1 (still referenced by a.link)

# Neither object can be deallocated!
```

### The Solution: Garbage Collection

Python's garbage collector:
1. Identifies objects that are part of reference cycles
2. Determines if the cycle is unreachable from the program
3. Deallocates unreachable cyclic objects

```python
import gc

# After creating circular references and deleting variables
collected = gc.collect()
print(f"Collected {collected} objects")
```

## Key Functions

### sys.getrefcount()

```python
import sys

obj = []
count = sys.getrefcount(obj)
print(f"Reference count: {count}")
```

**Returns**: Number of references to an object
**Note**: Includes the reference from getrefcount() itself

### gc.collect()

```python
import gc

collected = gc.collect()
print(f"Collected {collected} objects")
```

**Returns**: Number of unreachable objects collected
**Effect**: Deallocates unreachable cyclic objects

### gc.disable() / gc.enable()

```python
import gc

gc.disable()   # Disable automatic garbage collection
# ... create circular references ...
gc.collect()   # Manually trigger collection
gc.enable()    # Re-enable automatic collection
```

### gc.get_objects()

```python
import gc

all_objects = gc.get_objects()
print(f"Total objects: {len(all_objects)}")

# Find specific type
nodes = [obj for obj in all_objects if isinstance(obj, Node)]
print(f"Node objects: {len(nodes)}")
```

## Circular Reference Types

### Type 1: Simple Cycle (A -> B -> A)

```python
a = Node("A")
b = Node("B")
a.link = b
b.link = a

del a
del b
gc.collect()  # Collects 2 objects
```

### Type 2: Self-Reference (A -> A)

```python
a = Node("A")
a.link = a

del a
gc.collect()  # Collects 1 object
```

### Type 3: Complex Cycle (A -> B -> C -> A)

```python
a = Node("A")
b = Node("B")
c = Node("C")

a.link = b
b.link = c
c.link = a

del a
del b
del c
gc.collect()  # Collects 3 objects
```

## Best Practices

### 1. Break Cycles Explicitly

```python
a = Node("A")
b = Node("B")
a.link = b
b.link = a

# Break the cycle before deletion
a.link = None
b.link = None

del a
del b
```

### 2. Use Weak References

```python
import weakref

a = Node("A")
b = Node("B")
a.link = b
b.link = weakref.ref(a)  # Weak reference

del a
del b
# Objects are immediately deallocated
```

### 3. Use Context Managers

```python
class Node:
    def __enter__(self):
        return self
    
    def __exit__(self, exc_type, exc_val, exc_tb):
        self.link = None  # Break cycle
        return False

with Node("A") as a:
    with Node("B") as b:
        a.link = b
        b.link = a
# Cycles are automatically broken
```

### 4. Monitor Garbage Collection

```python
import gc

# Get collection statistics
stats = gc.get_stats()
for stat in stats:
    print(f"Collections: {stat['collections']}")

# Get garbage collection counts
counts = gc.get_count()
print(f"Gen 0: {counts[0]}, Gen 1: {counts[1]}, Gen 2: {counts[2]}")
```

## Running the Program

```bash
python circular_reference_demo.py
```

## Expected Output

The program will display:

1. **Demonstration 1**: Step-by-step circular reference walkthrough
2. **Demonstration 2**: Detailed lifecycle analysis
3. **Demonstration 3**: Different cycle types
4. **Demonstration 4**: Memory impact analysis

Each demonstration shows:
- Reference counts at each step
- Objects created and deleted
- Garbage collection results
- Memory statistics

## Common Pitfalls

### Pitfall 1: Assuming Immediate Cleanup

```python
# WRONG: Assuming __del__ is called immediately
a = Node("A")
b = Node("B")
a.link = b
b.link = a

del a  # __del__ NOT called yet!
print("A is destroyed")  # This may not be true
```

### Pitfall 2: Ignoring Circular References

```python
# WRONG: Creating circular references without cleanup
class LinkedList:
    def __init__(self):
        self.head = None
    
    def add(self, value):
        node = Node(value)
        node.link = self.head
        self.head = node
        # If head points back to node, cycle created!
```

### Pitfall 3: Disabling Garbage Collection

```python
# WRONG: Disabling GC without re-enabling
gc.disable()

# Create many circular references
for i in range(1000000):
    a = Node(f"A{i}")
    b = Node(f"B{i}")
    a.link = b
    b.link = a
    del a
    del b

# Memory leak! GC is disabled
```

## Troubleshooting

### Issue: __del__ Not Called

**Problem**: Destructor not called immediately after deletion

**Reason**: Object still referenced by circular reference

**Solution**: Call gc.collect() to trigger garbage collection

```python
del a
del b
gc.collect()  # Now __del__ is called
```

### Issue: Memory Leak

**Problem**: Memory usage keeps increasing

**Reason**: Circular references not being collected

**Solution**: Ensure garbage collection is enabled

```python
import gc
gc.enable()
gc.collect()
```

### Issue: Unexpected Reference Count

**Problem**: Reference count higher than expected

**Reason**: Multiple references to object

**Solution**: Use gc.get_referrers() to find references

```python
import gc
referrers = gc.get_referrers(obj)
print(f"Found {len(referrers)} referrers")
```

## Performance Considerations

### Automatic vs Manual Collection

**Automatic** (default):
- Runs periodically
- Minimal control
- Suitable for most applications

**Manual** (gc.collect()):
- Explicit control
- Better for performance-critical code
- Requires careful management

### Generational Collection

Python uses 3 generations:
- **Gen 0**: Newly created objects (collected frequently)
- **Gen 1**: Objects that survived one collection
- **Gen 2**: Long-lived objects (collected rarely)

## Key Takeaways

- ✅ Reference counting alone can't handle circular references
- ✅ Garbage collector detects and cleans up unreachable cycles
- ✅ sys.getrefcount() shows reference count for an object
- ✅ gc.collect() forces garbage collection
- ✅ Break cycles explicitly for better control
- ✅ Use weak references for optional back-references
- ✅ Monitor garbage collection for performance-critical code

## Files Included

1. `circular_reference_demo.py` - Main program with 4 demonstrations
2. `ASSIGNMENT14_README.md` - Complete documentation
3. `GARBAGE_COLLECTION_EXPLANATION.md` - Detailed explanation
4. `QUICK_START.md` - Quick start guide
5. `SUMMARY.md` - This file

## References

- Python gc module: https://docs.python.org/3/library/gc.html
- Python sys module: https://docs.python.org/3/library/sys.html
- Weak references: https://docs.python.org/3/library/weakref.html
- Memory management: https://docs.python.org/3/c-api/memory.html

---

**Status**: ✅ Complete and Verified
**Language**: Python 3
**Quality**: Production-Ready
