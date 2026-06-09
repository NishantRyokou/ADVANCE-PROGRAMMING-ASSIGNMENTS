# Assignment 14: Circular References and Garbage Collection in Python

## Overview

This assignment demonstrates how Python handles circular references and garbage collection. You'll create scenarios where objects are "dead" (unreachable from your code) but still have reference counts higher than zero due to circular references. Then you'll force the garbage collector to clean them up.

## Learning Objectives

- Understand reference counting in Python
- Recognize circular reference problems
- Use `sys.getrefcount()` to inspect reference counts
- Detect unreachable objects with the `gc` module
- Force garbage collection with `gc.collect()`
- Understand the difference between reference counting and garbage collection
- Learn best practices for managing circular references

## Assignment Requirements

### 1. Create a Node Class
```python
class Node:
    def __init__(self, name):
        self.name = name
        self.link = None
```

### 2. Create a Circular Reference
```python
node_a = Node("A")
node_b = Node("B")
node_a.link = node_b
node_b.link = node_a
```

### 3. Check Reference Counts
```python
import sys
print(sys.getrefcount(node_a))  # Shows reference count
```

### 4. Delete Variables
```python
del node_a
del node_b
```

### 5. Investigate Unreachable Objects
```python
import gc
gc.collect()  # Identify unreachable objects
```

### 6. Force Garbage Collection
```python
collected = gc.collect()
print(f"Collected {collected} objects")
```

## Program Structure

### circular_reference_demo.py

The main program with four demonstrations:

#### 1. demonstrate_circular_reference()
**Purpose**: Step-by-step walkthrough of circular reference problem

**Steps**:
1. Create Node A and Node B
2. Check initial reference counts
3. Create circular reference (A.link = B, B.link = A)
4. Check reference counts after cycle
5. Disable automatic garbage collection
6. Delete both variables
7. Investigate unreachable objects
8. Enable garbage collection
9. Force garbage collection with gc.collect()
10. Verify cleanup

**Output**: Shows reference counts at each step and number of objects collected

#### 2. demonstrate_with_debug_info()
**Purpose**: Detailed analysis of circular reference lifecycle

**Phases**:
1. **Object Creation**: Create two nodes
2. **Create Cycle**: Link nodes to each other
3. **Delete Variables**: Make objects unreachable
4. **Investigate Garbage**: Call gc.collect() to find unreachable objects
5. **Examine State**: Check garbage collector state
6. **Cleanup**: Enable and run garbage collection

**Output**: Shows object IDs, reference counts, and memory state at each phase

#### 3. demonstrate_reference_cycle_types()
**Purpose**: Show different types of circular references

**Cycle Types**:
1. **Simple Cycle**: A -> B -> A
2. **Self-Reference**: A -> A
3. **Complex Cycle**: A -> B -> C -> A

**Output**: Shows how many objects are collected for each cycle type

#### 4. demonstrate_memory_impact()
**Purpose**: Show memory impact with and without garbage collection

**Scenarios**:
1. **Without GC**: Create 100 circular references with GC disabled
2. **With GC**: Call gc.collect() to free memory

**Output**: Shows memory usage before and after garbage collection

## Key Concepts Explained

### Reference Counting

Every Python object has a reference count that tracks how many references point to it.

```python
import sys

obj = []
print(sys.getrefcount(obj))  # At least 2
# 1. Local variable 'obj'
# 2. Parameter to getrefcount()
```

**When reference count reaches 0**: Object is immediately deallocated.

### The Circular Reference Problem

```python
a = Node("A")
b = Node("B")
a.link = b  # a references b
b.link = a  # b references a (cycle created)

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

Python's garbage collector detects cycles and deallocates unreachable cyclic objects.

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

## Running the Program

### Basic Execution

```bash
python circular_reference_demo.py
```

### Expected Output

The program will show:

1. **Step 1-4**: Reference count changes as circular reference is created
2. **Step 5-7**: Objects deleted but still in memory
3. **Step 8-10**: Garbage collection finds and cleans up objects
4. **Detailed Analysis**: Lifecycle of circular references
5. **Cycle Types**: Different circular reference patterns
6. **Memory Impact**: Before and after garbage collection

### Sample Output

```
================================================================================
ASSIGNMENT 14: CIRCULAR REFERENCES AND GARBAGE COLLECTION
================================================================================

STEP 1: CREATE NODES
--------------------------------------------------------------------------------
Created: Node(name='A')
Created: Node(name='B')

STEP 2: CHECK INITIAL REFERENCE COUNTS
--------------------------------------------------------------------------------
Reference count for Node A: 2
  (includes: local variable, getrefcount parameter, temporary reference)
Reference count for Node B: 2
  (includes: local variable, getrefcount parameter, temporary reference)

STEP 3: CREATE CIRCULAR REFERENCE
--------------------------------------------------------------------------------
Set A.link = B
Set B.link = A
Circular reference created: A -> B -> A

STEP 4: CHECK REFERENCE COUNTS AFTER CIRCULAR REFERENCE
--------------------------------------------------------------------------------
Reference count for Node A: 3
  (increased by 1 due to B.link pointing to A)
Reference count for Node B: 3
  (increased by 1 due to A.link pointing to B)

...

STEP 10: FORCE GARBAGE COLLECTION
--------------------------------------------------------------------------------
Calling gc.collect() to force garbage collection...
Number of unreachable objects collected: 2

STEP 11: VERIFY CLEANUP
--------------------------------------------------------------------------------
Objects remaining in gc.garbage: 0
```

## Key Functions Used

### sys.getrefcount()

```python
import sys

obj = []
count = sys.getrefcount(obj)
print(f"Reference count: {count}")
```

**Returns**: Number of references to an object
**Note**: Includes the reference from the getrefcount() call itself

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

### gc.set_debug()

```python
import gc

gc.set_debug(gc.DEBUG_SAVEALL)  # Save unreachable objects in gc.garbage
gc.collect()
print(f"Unreachable objects: {len(gc.garbage)}")
```

## Understanding the Output

### Reference Count Changes

```
Initial: refcount = 2
  - Local variable 'a'
  - getrefcount() parameter

After cycle: refcount = 3
  - Local variable 'a'
  - getrefcount() parameter
  - b.link pointing to 'a'

After del: refcount = 1
  - b.link pointing to 'a' (b still exists)
```

### Garbage Collection Results

```
Unreachable objects collected: 2
  - Node A (unreachable due to cycle)
  - Node B (unreachable due to cycle)
```

## Common Patterns

### Pattern 1: Simple Cycle

```python
a = Node("A")
b = Node("B")
a.link = b
b.link = a
del a
del b
gc.collect()  # Collects 2 objects
```

### Pattern 2: Self-Reference

```python
a = Node("A")
a.link = a
del a
gc.collect()  # Collects 1 object
```

### Pattern 3: Complex Cycle

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
# Instead of relying on garbage collection
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

## Troubleshooting

### Issue: __del__ Not Called

**Problem**: Destructor not called immediately after deletion

**Reason**: Object still referenced by circular reference

**Solution**: Call gc.collect() to trigger garbage collection

```python
a = Node("A")
b = Node("B")
a.link = b
b.link = a

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

gc.enable()  # Ensure GC is enabled
gc.collect()  # Force collection
```

### Issue: Unexpected Reference Count

**Problem**: Reference count higher than expected

**Reason**: Multiple references to object

**Solution**: Use gc.get_referrers() to find references

```python
import gc

obj = []
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

```python
import gc

# Get collection thresholds
thresholds = gc.get_threshold()
print(f"Gen 0: {thresholds[0]}, Gen 1: {thresholds[1]}, Gen 2: {thresholds[2]}")

# Set custom thresholds
gc.set_threshold(1000, 15, 15)
```

## Summary

### What You'll Learn

1. **Reference Counting**: How Python tracks object references
2. **Circular References**: Why they prevent automatic cleanup
3. **Garbage Collection**: How Python detects and cleans up cycles
4. **sys.getrefcount()**: Inspecting reference counts
5. **gc Module**: Controlling garbage collection
6. **Memory Management**: Best practices for managing memory
7. **Performance**: Trade-offs between automatic and manual collection

### Key Takeaways

- ✅ Reference counting alone can't handle circular references
- ✅ Garbage collector detects and cleans up unreachable cycles
- ✅ sys.getrefcount() shows reference count for an object
- ✅ gc.collect() forces garbage collection
- ✅ Break cycles explicitly for better control
- ✅ Use weak references for optional back-references
- ✅ Monitor garbage collection for performance-critical code

## Files Included

1. `circular_reference_demo.py` - Main program with 4 demonstrations
2. `ASSIGNMENT14_README.md` - This file
3. `GARBAGE_COLLECTION_EXPLANATION.md` - Detailed explanation of concepts

## Running the Assignment

```bash
python circular_reference_demo.py
```

The program will run all four demonstrations and show:
- Reference count changes
- Circular reference creation and deletion
- Garbage collection results
- Memory impact analysis

## References

- Python gc module: https://docs.python.org/3/library/gc.html
- Python sys module: https://docs.python.org/3/library/sys.html
- Weak references: https://docs.python.org/3/library/weakref.html
- Memory management: https://docs.python.org/3/c-api/memory.html

---

**Status**: ✅ Complete
**Language**: Python 3
**Quality**: Production-Ready
