# Compilation and Execution Guide

## System Requirements

- **Linux/Unix/macOS** with GCC or Clang compiler
- **POSIX Threads (pthread)** library support
- **C99 or later** standard

## Compilation Instructions

### On Linux/Unix/macOS

```bash
# Compile without mutex (race condition demo)
gcc -pthread counter_without_mutex.c -o counter_without_mutex

# Compile with mutex (synchronized)
gcc -pthread counter_with_mutex.c -o counter_with_mutex

# Compile optimized mutex version
gcc -pthread counter_optimized_mutex.c -o counter_optimized_mutex
```

### Alternative with Clang (macOS)

```bash
clang -pthread counter_without_mutex.c -o counter_without_mutex
clang -pthread counter_with_mutex.c -o counter_with_mutex
clang -pthread counter_optimized_mutex.c -o counter_optimized_mutex
```

### Compiler Flags Explained

- `-pthread` - Links with pthread library and enables thread support
- `-o filename` - Specifies output executable name

## Execution Instructions

### Run Without Mutex (Race Condition)

```bash
./counter_without_mutex
```

**Expected Output (varies each run):**
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
The counter value is incorrect due to unsynchronized access.
```

**Note:** The final counter value will be different each run due to race condition.

### Run With Mutex (Synchronized)

```bash
./counter_with_mutex
```

**Expected Output (consistent):**
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
Mutex successfully prevented race condition.
```

**Note:** The final counter value will always be 500000.

### Run Optimized Mutex Version

```bash
./counter_optimized_mutex
```

**Expected Output (consistent and faster):**
```
=== Counter With Optimized Mutex ===
Number of threads: 5
Increments per thread: 100000
Expected final counter: 500000

Creating threads...
Thread 0 finished (local count: 100000)
Thread 1 finished (local count: 100000)
Thread 2 finished (local count: 100000)
Thread 3 finished (local count: 100000)
Thread 4 finished (local count: 100000)

=== Results ===
Final counter value: 500000
Expected value: 500000
Difference: 0

✓ Counter is CORRECT!
Optimized mutex approach is faster and still correct.
```

## Running Multiple Times

To observe the race condition more clearly, run the first program multiple times:

```bash
for i in {1..5}; do
    echo "=== Run $i ==="
    ./counter_without_mutex
    echo ""
done
```

You'll see different results each time due to the race condition.

## Performance Comparison

To compare performance, use the `time` command:

```bash
time ./counter_without_mutex
time ./counter_with_mutex
time ./counter_optimized_mutex
```

**Expected Performance:**
1. Without mutex: Fastest (but incorrect)
2. With mutex: Slowest (lock on every operation)
3. Optimized mutex: Fast (lock only once)

## Debugging

### Enable Debug Output

Modify the programs to add more debug information:

```c
printf("Thread %d: Before lock\n", thread_id);
pthread_mutex_lock(&counter_mutex);
printf("Thread %d: Acquired lock\n", thread_id);
shared_counter++;
printf("Thread %d: Counter = %d\n", thread_id, shared_counter);
pthread_mutex_unlock(&counter_mutex);
printf("Thread %d: Released lock\n", thread_id);
```

### Using GDB Debugger

```bash
gdb ./counter_with_mutex
(gdb) break increment_counter
(gdb) run
(gdb) next
(gdb) print shared_counter
(gdb) continue
```

### Using Valgrind (Thread Checker)

```bash
valgrind --tool=helgrind ./counter_without_mutex
valgrind --tool=helgrind ./counter_with_mutex
```

Helgrind will detect race conditions and synchronization issues.

## Troubleshooting

### Error: "undefined reference to `pthread_create'"

**Solution:** Make sure to use `-pthread` flag during compilation:
```bash
gcc -pthread program.c -o program
```

### Error: "pthread.h: No such file or directory"

**Solution:** Install pthread development libraries:
```bash
# Ubuntu/Debian
sudo apt-get install libpthread-stubs0-dev

# macOS (usually pre-installed)
# If missing, install Xcode Command Line Tools
xcode-select --install
```

### Program Hangs

**Possible Causes:**
- Deadlock (threads waiting for each other)
- Infinite loop in critical section
- Mutex not initialized

**Solution:**
- Check mutex initialization
- Verify lock/unlock pairs
- Use timeout with pthread_mutex_timedlock()

## Advanced Variations

### Increase Thread Count

Modify NUM_THREADS in the source:
```c
#define NUM_THREADS 10  // Instead of 5
```

### Increase Increments

Modify INCREMENTS_PER_THREAD:
```c
#define INCREMENTS_PER_THREAD 1000000  // Instead of 100000
```

### Add Thread IDs to Output

```c
printf("Thread %ld: Counter = %d\n", pthread_self(), shared_counter);
```

## Expected Observations

### Without Mutex
- Different results each run
- Results always less than expected
- More threads = more lost updates
- More increments = more lost updates

### With Mutex
- Same result every run (500000)
- Correct synchronization
- Slower due to lock overhead

### Optimized Mutex
- Same result every run (500000)
- Faster than fine-grained locking
- Better performance/correctness balance

## Summary

These programs demonstrate:

✓ **Race Condition** - Incorrect results without synchronization
✓ **Mutex Synchronization** - Correct results with mutex
✓ **Thread Creation** - Using pthread_create()
✓ **Thread Joining** - Using pthread_join()
✓ **Lock/Unlock** - Using pthread_mutex_lock/unlock()
✓ **Performance Trade-offs** - Speed vs correctness

The key takeaway: **Always synchronize access to shared data in multithreaded programs!**
