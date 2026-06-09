# Garbage Collection and Circular References in Python

## Overview

This assignment demonstrates how Python's garbage collector handles circular references—situations where objects reference each other, creating cycles that prevent automatic cleanup through reference counting alone.

## Key Concepts

### 1. Reference Counting

Python uses reference counting as its primary memory management mechanism. Each object has a reference count that tracks how many references point to it.

```python
import sys

obj = []
print(sys.getrefcount(obj))  # At least 2 (local variable + getrefcount parameter)
```

**When reference count reaches 0**: Object is immediately deallocated.

**Problem**: Reference counting alone cannot handle circular references.

### 2. Circular References

A circular reference occurs when objects reference each other, directly or indirectly, creating a cycle.

**Simple Cycle (A -> B -> A)**:
```python
class Node:
    def __init__(self, name):
        self.name = name
        self.link = None

a = Node("A")
b = Node("B")
a.link = b  # A references B
b.link = a  # B references A (cycle created)
```

**Reference Count Problem**:
- Node A: refcount = 2 (variable `a` + `b.link`)
- Node B: refcount = 2 (variable `b` + `a.link`)

When we delete the variables:
```python
del a
del b
```

- Node A: refcount = 1 (still referenced by `b.link`)
- Node B: refcount = 1 (still referenced by `a.link`)

**Result**: Both objects remain in memory indefinitely, even though they're unreachable from the program.

### 3. The Garbage Collector

Python's garbage collector (gc module) detects and cleans up circular references that reference counting cannot handle.

**How it works**:
1. Identifies objects that are part of reference cycles
2. Determines if the cycle is unreachable from the program
3. Deallocates unreachable cyclic objects

## Reference Counting vs Garbage Collection

| Aspect | Reference Counting | Garbage Collection |
|--------|-------------------|-------------------|
| **Mechanism** | Counts references to each object | Detects unreachable cycles |
| **Timing** | Immediate (when refcount = 0) | Periodic or on-demand |
| **Handles Cycles** | No | Yes |
| **Performance** | Fast, constant overhead | Slower, periodic pauses |
| **Memory** | Immediate cleanup | Delayed cleanup |

## The Problem: Circular References

### Example 1: Simple Cycle

```python
class Node:
    def __init__(self, name):
        self.name = name
        self.link = None

a = Node("A")
b = Node("B")
a.link = b
b.link = a

# Reference counts:
# a: 2 (variable 'a' + b.link)
# b: 2 (variable 'b' + a.link)

del a
del b

# Reference counts:
# a: 1 (still referenced by b.link)
# b: 1 (still referenced by a.link)

# Objects remain in memory!
```

### Example 2: Self-Reference

```python
node = Node("Self")
node.link = node  # Self-reference

# Reference count: 2 (variable + self.link)

del node

# Reference count: 1 (still referenced by itself)
# Object remains in memory!
```

### Example 3: Complex Cycle

```python
a = Node("A")
b = Node("B")
c = Node("C")

a.link = b
b.link = c
c.link = a  # Creates cycle: A -> B -> C -> A

del a
del b
del c

# All three objects remain in memory due to cycle
```

## The Solution: Garbage Collection

### Using gc.collect()

```python
import gc

# Create circular reference
a = Node("A")
b = Node("B")
a.link = b
b.link = a

del a
del b

# Objects are now unreachable but still in memory

# Force garbage collection
collected = gc.collect()
print(f"Collected {collected} objects")

# Objects are now deallocated
```

### How gc.collect() Works

1. **Mark Phase**: Identifies all reachable objects starting from root references
2. **Sweep Phase**: Deallocates objects not marked as reachable
3. **Returns**: Number of unreachable objects collected

### Automatic vs Manual Collection

**Automatic Collection** (default):
```python
import gc

# Automatic collection enabled by default
# Runs periodically based on thresholds
```

**Manual Collection**:
```python
import gc

gc.disable()  # Disable automatic collection
# ... create circular references ...
gc.collect()  # Manually trigger collection
gc.enable()   # Re-enable automatic collection
```

## Detecting Circular References

### Using sys.getrefcount()

```python
import sys

a = Node("A")
b = Node("B")

print(sys.getrefcount(a))  # 2 (variable + parameter)

a.link = b
b.link = a

print(sys.getrefcount(a))  # 3 (variable + parameter + b.link)
print(sys.getrefcount(b))  # 3 (variable + parameter + a.link)
```

### Using gc.get_objects()

```python
import gc

# Get all objects tracked by garbage collector
all_objects = gc.get_objects()

# Find Node objects
nodes = [obj for obj in all_objects if isinstance(obj, Node)]
print(f"Found {len(nodes)} Node objects")
```

### Using gc.garbage

```python
import gc

gc.set_debug(gc.DEBUG_SAVEALL)  # Save unreachable objects

# Create circular reference
a = Node("A")
b = Node("B")
a.link = b
b.link = a

del a
del b

gc.collect()

# Examine unreachable objects
print(f"Unreachable objects: {len(gc.garbage)}")
for obj in gc.garbage:
    if isinstance(obj, Node):
        print(f"  Node: {obj.name}")
```

## Memory Impact

### Without Garbage Collection

```python
import gc

gc.disable()

# Create 100 circular references
for i in range(100):
    a = Node(f"A{i}")
    b = Node(f"B{i}")
    a.link = b
    b.link = a
    del a
    del b

# 200 objects remain in memory!
```

### With Garbage Collection

```python
import gc

gc.disable()

# Create 100 circular references
for i in range(100):
    a = Node(f"A{i}")
    b = Node(f"B{i}")
    a.link = b
    b.link = a
    del a
    del b

# Manually collect
collected = gc.collect()
print(f"Collected {collected} objects")

# Memory is freed
```

## Destructor Behavior

### __del__ Method

```python
class Node:
    def __init__(self, name):
        self.name = name
        self.link = None
    
    def __del__(self):
        print(f"Node {self.name} is being destroyed")
```

**Important**: With circular references, `__del__` may not be called immediately:

```python
a = Node("A")
b = Node("B")
a.link = b
b.link = a

del a  # __del__ NOT called (still referenced by b.link)
del b  # __del__ NOT called (still referenced by a.link)

gc.collect()  # NOW __del__ is called for both
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

class Node:
    def __init__(self, name):
        self.name = name
        self.link = None

a = Node("A")
b = Node("B")
a.link = b
b.link = weakref.ref(a)  # Weak reference doesn't prevent cleanup

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
    print(f"Generation {stat['collections']}: {stat['collected']} collected")

# Get garbage collection counts
counts = gc.get_count()
print(f"Objects in generation 0: {counts[0]}")
print(f"Objects in generation 1: {counts[1]}")
print(f"Objects in generation 2: {counts[2]}")
```

## Generational Garbage Collection

Python uses generational garbage collection with 3 generations:

**Generation 0**: Newly created objects
- Collected frequently
- Most objects die young

**Generation 1**: Objects that survived one collection
- Collected less frequently
- Intermediate lifespan

**Generation 2**: Long-lived objects
- Collected rarely
- Persistent objects

```python
import gc

# Get collection thresholds
thresholds = gc.get_threshold()
print(f"Gen 0 threshold: {thresholds[0]}")  # Default: 700
print(f"Gen 1 threshold: {thresholds[1]}")  # Default: 10
print(f"Gen 2 threshold: {thresholds[2]}")  # Default: 10

# Set custom thresholds
gc.set_threshold(1000, 15, 15)
```

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

### Pitfall 4: Relying on gc.garbage

```python
# WRONG: Assuming gc.garbage contains all unreachable objects
gc.set_debug(gc.DEBUG_SAVEALL)

# gc.garbage only contains objects if DEBUG_SAVEALL is set
# This is for debugging only, not for production
```

## Summary

### Key Points

1. **Reference Counting**: Python's primary memory management, but can't handle cycles
2. **Circular References**: Objects referencing each other, preventing cleanup
3. **Garbage Collector**: Detects and cleans up unreachable cycles
4. **gc.collect()**: Manually triggers garbage collection
5. **sys.getrefcount()**: Shows reference count for an object
6. **Weak References**: Alternative to break cycles
7. **Best Practice**: Break cycles explicitly or use weak references

### When to Use Garbage Collection

- **Automatic (default)**: Most applications
- **Manual (gc.collect())**: Performance-critical code
- **Disabled (gc.disable())**: Real-time systems, then manually collect

### When to Break Cycles

- **Explicit cleanup**: Recommended for critical resources
- **Weak references**: For optional back-references
- **Context managers**: For resource management

## References

- Python gc module: https://docs.python.org/3/library/gc.html
- Python sys module: https://docs.python.org/3/library/sys.html
- Weak references: https://docs.python.org/3/library/weakref.html
- Memory management: https://docs.python.org/3/c-api/memory.html
