# Assignment 15: Multithreaded C Program with POSIX Threads

A comprehensive demonstration of race conditions and mutex synchronization using POSIX threads (pthread).

## Program Overview

Three C programs demonstrating different approaches to multithreading:

1. **counter_without_mutex.c** - Shows race condition without synchronization
2. **counter_with_mutex.c** - Fixes race condition using mutex
3. **counter_optimized_mutex.c** - Optimized version with reduced lock contention

## What is a Race Condition?

A race condition occurs when multiple threads access and modify shared data simultaneously without synchronization. The final result depends on the order of execution, which is unpredictable.

### Example: Incrementing a Counter

Without synchronization, when two threads increment a counter:

```
Thread 1: Read counter (value = 5)
Thread 2: Read counter (value = 5)
Thread 1: Increment to 6, Write back
Thread 2: Increment to 6, Write back
Final result: 6 (should be 7)
```

The increment operation is NOT atomic:
1. Read current value
2. Add 1
3. Write back

If threads interleave, updates can be lost.

## Program 1: Without Mutex (Race Condition)

### File: counter_without_mutex.c

```c
int shared_counter = 0;

void* increment_counter(void* arg) {
    for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
        shared_counter++;  // NOT ATOMIC - RACE CONDITION!
    }
    pthread_exit(NULL);
}
```

### Expected vs Actual Output

```
Expected: 500,000 (5 threads × 100,000 increments)
Actual: ~498,234 (varies each run)
```

### Why It Fails

The `shared_counter++` operation consists of three machine instructions:
1. Load counter from memory
2. Increment the value
3. Store back to memory

Between these instructions, another thread can execute, causing lost updates.

## Program 2: With Mutex (Synchronized)

### File: counter_with_mutex.c

```c
pthread_mutex_t counter_mutex = PTHREAD_MUTEX_INITIALIZER;

void* increment_counter(void* arg) {
    for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
        pthread_mutex_lock(&counter_mutex);
        shared_counter++;  // PROTECTED - NO RACE CONDITION
        pthread_mutex_unlock(&counter_mutex);
    }
    pthread_exit(NULL);
}
```

### How Mutex Works

1. **pthread_mutex_lock()** - Acquires the lock
   - If lock is free, thread acquires it and continues
   - If lock is held, thread blocks until lock is released

2. **Critical Section** - Only one thread can execute at a time
   - `shared_counter++` is now atomic

3. **pthread_mutex_unlock()** - Releases the lock
   - Allows other waiting threads to acquire it

### Expected Output

```
Final counter value: 500,000
Expected value: 500,000
Difference: 0
✓ Counter is CORRECT!
```

## Program 3: Optimized Mutex

### File: counter_optimized_mutex.c

```c
void* increment_counter(void* arg) {
    int local_count = 0;
    
    for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
        local_count++;  // NO LOCK - FAST
    }
    
    pthread_mutex_lock(&counter_mutex);
    shared_counter += local_count;  // LOCK ONLY ONCE
    pthread_mutex_unlock(&counter_mutex);
    pthread_exit(NULL);
}
```

### Optimization Benefits

- **Reduced Lock Contention** - Lock held only once per thread
- **Better Performance** - Less time waiting for locks
- **Same Correctness** - Final result is still correct

## Compilation

### Linux/Unix

```bash
gcc -pthread counter_without_mutex.c -o counter_without_mutex
gcc -pthread counter_with_mutex.c -o counter_with_mutex
gcc -pthread counter_optimized_mutex.c -o counter_optimized_mutex
```

### macOS

```bash
clang -pthread counter_without_mutex.c -o counter_without_mutex
clang -pthread counter_with_mutex.c -o counter_with_mutex
clang -pthread counter_optimized_mutex.c -o counter_optimized_mutex
```

## Execution

```bash
./counter_without_mutex
./counter_with_mutex
./counter_optimized_mutex
```

## Key POSIX Thread Functions

### pthread_create()
```c
int pthread_create(pthread_t *thread, const pthread_attr_t *attr,
                   void *(*start_routine) (void *), void *arg);
```
- Creates a new thread
- Returns 0 on success, error code on failure
- Thread executes start_routine with arg parameter

### pthread_join()
```c
int pthread_join(pthread_t thread, void **retval);
```
- Waits for thread to complete
- Blocks until thread exits
- Retrieves thread's return value

### pthread_mutex_lock()
```c
int pthread_mutex_lock(pthread_mutex_t *mutex);
```
- Acquires the mutex lock
- Blocks if lock is held by another thread
- Returns 0 on success

### pthread_mutex_unlock()
```c
int pthread_mutex_unlock(pthread_mutex_t *mutex);
```
- Releases the mutex lock
- Allows other threads to acquire it
- Returns 0 on success

### pthread_mutex_destroy()
```c
int pthread_mutex_destroy(pthread_mutex_t *mutex);
```
- Destroys the mutex
- Should be called when mutex is no longer needed

## Race Condition Explanation

### Without Synchronization

```
Time  Thread 1              Thread 2              Counter
----  --------              --------              -------
1     Read (5)              
2                           Read (5)
3     Increment (6)
4     Write (6)                                   6
5                           Increment (6)
6                           Write (6)             6 (LOST UPDATE!)
```

Thread 2's increment is lost because both threads read the same initial value.

### With Mutex

```
Time  Thread 1              Thread 2              Counter
----  --------              --------              -------
1     Lock acquired
2     Read (5)
3     Increment (6)
4     Write (6)                                   6
5     Unlock
6                           Lock acquired
7                           Read (6)
8                           Increment (7)
9                           Write (7)             7 (CORRECT!)
10                          Unlock
```

Mutex ensures only one thread accesses the counter at a time.

## Performance Comparison

### Without Mutex
- **Speed:** Very fast (no synchronization overhead)
- **Correctness:** ✗ Incorrect (race condition)
- **Use Case:** Not suitable for shared data

### With Mutex (Fine-grained)
- **Speed:** Slow (lock on every operation)
- **Correctness:** ✓ Correct
- **Use Case:** Simple synchronization

### With Mutex (Optimized)
- **Speed:** Fast (lock only once)
- **Correctness:** ✓ Correct
- **Use Case:** Best practice for performance

## Expected Output Examples

### counter_without_mutex.c
```
=== Counter Without Mutex (Race Condition) ===
Number of threads: 5
Increments per thread: 100000
Expected final counter: 500000

Creating threads...
Thread 0 finished
Thread 1 finished
Thread 2 finished
Thread 3 finished
Thread 4 finished

=== Results ===
Final counter value: 498234
Expected value: 500000
Difference: 1766

⚠️ RACE CONDITION DETECTED!
```

### counter_with_mutex.c
```
=== Counter With Mutex (Synchronized) ===
Number of threads: 5
Increments per thread: 100000
Expected final counter: 500000

Creating threads...
Thread 0 finished
Thread 1 finished
Thread 2 finished
Thread 3 finished
Thread 4 finished

=== Results ===
Final counter value: 500000
Expected value: 500000
Difference: 0

✓ Counter is CORRECT!
```

## Key Concepts

### Critical Section
A code segment where shared resources are accessed. Must be protected by synchronization mechanisms.

### Mutual Exclusion
Only one thread can execute the critical section at a time.

### Atomic Operation
An operation that completes without interruption. Cannot be interleaved with other operations.

### Deadlock
When threads wait indefinitely for each other. Prevented by proper lock management.

### Lock Contention
When multiple threads compete for the same lock. Reduces performance.

## Best Practices

1. **Minimize Critical Sections** - Keep locked code as small as possible
2. **Use Local Variables** - Reduce need for synchronization
3. **Avoid Nested Locks** - Prevents deadlock
4. **Always Unlock** - Use try-finally or similar patterns
5. **Test Thoroughly** - Race conditions are hard to reproduce

## Summary

This assignment demonstrates:

✓ **Race Condition** - Incorrect results without synchronization
✓ **Mutex Synchronization** - Correct results with mutex
✓ **Thread Creation** - Using pthread_create()
✓ **Thread Joining** - Using pthread_join()
✓ **Lock/Unlock** - Using pthread_mutex_lock/unlock()
✓ **Performance Optimization** - Reducing lock contention

The programs clearly show why synchronization is essential for multithreaded programs accessing shared data.
