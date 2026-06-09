# Quick Start Guide - Assignment 15

## What This Assignment Demonstrates

A multithreaded C program showing:
- **Race Condition** - Incorrect results without synchronization
- **Mutex Synchronization** - Correct results with mutex
- **POSIX Threads** - Thread creation, joining, and synchronization

## Three Programs

### 1. Without Mutex (Race Condition)
```c
void* increment_counter(void* arg) {
    for (int i = 0; i < 100000; i++) {
        shared_counter++;  // RACE CONDITION!
    }
}
```
**Result:** Incorrect (varies each run)

### 2. With Mutex (Synchronized)
```c
void* increment_counter(void* arg) {
    for (int i = 0; i < 100000; i++) {
        pthread_mutex_lock(&counter_mutex);
        shared_counter++;  // PROTECTED
        pthread_mutex_unlock(&counter_mutex);
    }
}
```
**Result:** Correct (consistent)

### 3. Optimized Mutex (Best Practice)
```c
void* increment_counter(void* arg) {
    int local_count = 0;
    for (int i = 0; i < 100000; i++) {
        local_count++;  // NO LOCK
    }
    pthread_mutex_lock(&counter_mutex);
    shared_counter += local_count;  // LOCK ONCE
    pthread_mutex_unlock(&counter_mutex);
}
```
**Result:** Correct and fast

## Compilation

```bash
gcc -pthread counter_without_mutex.c -o counter_without_mutex
gcc -pthread counter_with_mutex.c -o counter_with_mutex
gcc -pthread counter_optimized_mutex.c -o counter_optimized_mutex
```

## Execution

```bash
./counter_without_mutex      # Shows race condition
./counter_with_mutex         # Shows correct synchronization
./counter_optimized_mutex    # Shows optimized approach
```

## Expected Output

### Without Mutex
```
Final counter value: 498234
Expected value: 500000
Difference: 1766
⚠️ RACE CONDITION DETECTED!
```

### With Mutex
```
Final counter value: 500000
Expected value: 500000
Difference: 0
✓ Counter is CORRECT!
```

### Optimized Mutex
```
Final counter value: 500000
Expected value: 500000
Difference: 0
✓ Counter is CORRECT!
```

## Key POSIX Functions

### Create Thread
```c
pthread_create(&thread, NULL, function, arg);
```

### Wait for Thread
```c
pthread_join(thread, NULL);
```

### Lock Mutex
```c
pthread_mutex_lock(&mutex);
```

### Unlock Mutex
```c
pthread_mutex_unlock(&mutex);
```

## Why Race Condition Occurs

The operation `counter++` is NOT atomic:
```
1. Load counter from memory
2. Add 1
3. Store back to memory
```

Between these steps, another thread can execute, causing lost updates.

## How Mutex Fixes It

Mutex ensures only one thread executes the critical section at a time:
```
Thread 1: [Lock] counter++ [Unlock]
Thread 2:                   [Lock] counter++ [Unlock]
```

## Performance

| Program | Speed | Correctness |
|---------|-------|-------------|
| Without Mutex | Very Fast | ✗ Wrong |
| With Mutex | Slow | ✓ Correct |
| Optimized | Fast | ✓ Correct |

## Key Takeaway

**Always synchronize access to shared data in multithreaded programs!**

## Files

- `counter_without_mutex.c` - Race condition demo
- `counter_with_mutex.c` - Mutex synchronization
- `counter_optimized_mutex.c` - Optimized approach
- `ASSIGNMENT15_README.md` - Full documentation
- `COMPILATION_AND_EXECUTION.md` - Detailed compilation guide
- `RACE_CONDITION_EXPLANATION.md` - Technical explanation
- `SUMMARY.md` - Complete summary

## Next Steps

1. Read ASSIGNMENT15_README.md for complete details
2. Compile the programs
3. Run each program multiple times
4. Observe the differences
5. Read RACE_CONDITION_EXPLANATION.md for technical details

## Common Issues

### Compilation Error: "undefined reference to pthread_create"
**Solution:** Use `-pthread` flag:
```bash
gcc -pthread program.c -o program
```

### Different Results Each Run
**Expected:** This is the race condition! Run without_mutex multiple times to see.

### Same Result Every Run
**Expected:** This is correct! With mutex, results should be consistent.

## Testing

Run without_mutex multiple times to see race condition:
```bash
for i in {1..5}; do ./counter_without_mutex; done
```

You'll see different results each time!

## Summary

This assignment teaches:
✓ What race conditions are
✓ Why they occur
✓ How to fix them with mutex
✓ POSIX thread functions
✓ Synchronization techniques
✓ Performance considerations

**Status:** Ready to compile and run on Linux/Unix/macOS!
