# Quick Start Guide - Assignment 14

## Running the Program

```bash
python circular_reference_demo.py
```

## What the Program Does

The program demonstrates circular references and garbage collection through 4 demonstrations:

### 1. Basic Circular Reference Demo
Shows step-by-step how circular references prevent cleanup:
- Creates two nodes that reference each other
- Shows reference count changes
- Demonstrates that objects remain in memory after deletion
- Forces garbage collection to clean them up

### 2. Detailed Lifecycle Analysis
Traces the complete lifecycle of circular references:
- Object creation
- Cycle creation
- Variable deletion
- Garbage investigation
- Cleanup

### 3. Different Cycle Types
Shows how different circular reference patterns are handled:
- Simple cycle (A -> B -> A)
- Self-reference (A -> A)
- Complex cycle (A -> B -> C -> A)

### 4. Memory Impact
Demonstrates memory usage with and without garbage collection:
- Creates 100 circular references with GC disabled
- Shows memory leak
- Calls gc.collect() to free memory
- Shows memory freed

## Key Concepts

### Reference Counting
Every object has a count of how many references point to it.

```python
import sys
obj = []
print(sys.getrefcount(obj))  # Shows reference count
```

### Circular Reference Problem
When objects reference each other, they can't be cleaned up by reference counting alone.

```python
a = Node("A")
b = Node("B")
a.link = b
b.link = a  # Circular reference created

del a
del b  # Objects still in memory!
```

### Garbage Collection Solution
Python's garbage collector detects and cleans up unreachable cycles.

```python
import gc
gc.collect()  # Force garbage collection
```

## Understanding the Output

### Reference Count Changes

```
Initial: refcount = 2
After cycle: refcount = 3
After deletion: refcount = 1 (still referenced by other object)
```

### Garbage Collection Results

```
Unreachable objects collected: 2
(Both Node A and Node B are freed)
```

## Key Functions

### sys.getrefcount()
Shows how many references point to an object.

```python
import sys
count = sys.getrefcount(obj)
```

### gc.collect()
Forces garbage collection and returns number of objects collected.

```python
import gc
collected = gc.collect()
print(f"Collected {collected} objects")
```

### gc.disable() / gc.enable()
Control automatic garbage collection.

```python
import gc
gc.disable()   # Turn off automatic GC
gc.collect()   # Manually collect
gc.enable()    # Turn on automatic GC
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
a = Node("A")
b = Node("B")
a.link = b
b.link = a

# Break cycle before deletion
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
del b  # Objects immediately freed
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
# Cycles automatically broken
```

## Troubleshooting

### Issue: __del__ Not Called
**Solution**: Call gc.collect() to trigger garbage collection

```python
del a
del b
gc.collect()  # Now __del__ is called
```

### Issue: Memory Leak
**Solution**: Ensure garbage collection is enabled

```python
import gc
gc.enable()
gc.collect()
```

### Issue: Unexpected Reference Count
**Solution**: Use gc.get_referrers() to find references

```python
import gc
referrers = gc.get_referrers(obj)
print(f"Found {len(referrers)} referrers")
```

## Expected Output

The program will show:

1. **Step 1-4**: Reference count changes
2. **Step 5-7**: Objects deleted but still in memory
3. **Step 8-10**: Garbage collection cleans up
4. **Detailed Analysis**: Lifecycle of circular references
5. **Cycle Types**: Different patterns
6. **Memory Impact**: Before and after GC

## Key Takeaways

- ✅ Reference counting alone can't handle circular references
- ✅ Garbage collector detects and cleans up unreachable cycles
- ✅ sys.getrefcount() shows reference count
- ✅ gc.collect() forces garbage collection
- ✅ Break cycles explicitly for better control
- ✅ Use weak references for optional back-references

## Next Steps

1. Run the program: `python circular_reference_demo.py`
2. Read the output carefully
3. Study ASSIGNMENT14_README.md for details
4. Read GARBAGE_COLLECTION_EXPLANATION.md for concepts
5. Experiment with modifying the code

## Files

- `circular_reference_demo.py` - Main program
- `ASSIGNMENT14_README.md` - Complete documentation
- `GARBAGE_COLLECTION_EXPLANATION.md` - Detailed concepts
- `QUICK_START.md` - This file

---

**Status**: ✅ Complete
**Language**: Python 3
**Quality**: Production-Ready
