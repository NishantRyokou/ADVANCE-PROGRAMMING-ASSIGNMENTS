# Assignment 16 - Complete Index

## Quick Navigation

### Getting Started
- **New to this assignment?** Start with [QUICK_START.md](QUICK_START.md)
- **Want to compile?** See [COMPILATION_AND_EXECUTION.md](COMPILATION_AND_EXECUTION.md)
- **Need overview?** Read [SUMMARY.md](SUMMARY.md)

### Detailed Learning
- **Complete documentation**: [ASSIGNMENT16_README.md](ASSIGNMENT16_README.md)
- **Synchronization concepts**: [SYNCHRONIZATION_EXPLANATION.md](SYNCHRONIZATION_EXPLANATION.md)

### Source Code
1. [producer_consumer_semaphore.c](producer_consumer_semaphore.c)
2. [producer_consumer_condition_variable.c](producer_consumer_condition_variable.c)
3. [limited_resource_access.c](limited_resource_access.c)
4. [thread_scheduling_simulation.c](thread_scheduling_simulation.c)

---

## File Descriptions

### Documentation Files

#### ASSIGNMENT16_README.md
**Purpose**: Complete assignment documentation
**Contents**:
- Program overview and features
- Synchronization mechanisms explained
- How synchronization prevents inconsistent behavior
- Compilation and execution instructions
- Expected behavior for each program
- Key concepts demonstrated
- Common pitfalls and solutions
- Performance considerations
- References

**Read this for**: Comprehensive understanding of all programs

#### SYNCHRONIZATION_EXPLANATION.md
**Purpose**: Deep dive into synchronization concepts
**Contents**:
- Race conditions and examples
- Semaphores (binary and counting)
- Condition variables
- Mutex operations
- Synchronization patterns
- Deadlock prevention strategies
- Best practices
- Compilation with thread sanitizer

**Read this for**: Understanding synchronization theory and best practices

#### COMPILATION_AND_EXECUTION.md
**Purpose**: Step-by-step compilation and execution guide
**Contents**:
- System requirements
- Installation instructions (Linux, macOS, Windows)
- Single and batch compilation
- Compilation flags explained
- Execution instructions
- Running all programs
- Troubleshooting guide
- Performance testing
- Expected output

**Read this for**: Compiling and running the programs

#### QUICK_START.md
**Purpose**: Fast start guide
**Contents**:
- Quick compilation commands
- Running each program
- What to observe in output
- Key synchronization functions
- Semaphore vs Condition Variable comparison
- Troubleshooting tips
- Performance tips
- File list

**Read this for**: Getting started quickly

#### SUMMARY.md
**Purpose**: High-level overview
**Contents**:
- Programs overview
- Key synchronization concepts
- Synchronization patterns
- Thread safety mechanisms
- POSIX functions used
- Output characteristics
- Compilation and execution
- Learning outcomes
- Real-world applications
- Verification checklist

**Read this for**: Quick overview and verification

#### INDEX.md
**Purpose**: Navigation guide (this file)
**Contents**:
- File descriptions
- Program details
- Synchronization primitives reference
- POSIX functions reference
- Learning path recommendations

**Read this for**: Finding what you need

---

## Program Details

### Program 1: Producer-Consumer (Semaphore)
**File**: `producer_consumer_semaphore.c`

**What it does**:
- 2 producer threads create items
- 2 consumer threads consume items
- Circular buffer holds up to 5 items
- Semaphores control access

**Key synchronization**:
- `empty` semaphore: Tracks empty slots
- `full` semaphore: Tracks filled slots
- Mutex: Protects buffer operations

**Functions used**:
- `sem_init()`, `sem_wait()`, `sem_post()`, `sem_destroy()`
- `pthread_mutex_lock()`, `pthread_mutex_unlock()`

**Expected output**:
- Producers and consumers alternate
- Buffer count varies 0-5
- Final count: 0

**Execution time**: ~1-2 seconds

---

### Program 2: Producer-Consumer (Condition Variable)
**File**: `producer_consumer_condition_variable.c`

**What it does**:
- Same as Program 1 but uses condition variables
- 2 producer threads create items
- 2 consumer threads consume items
- Circular buffer holds up to 5 items

**Key synchronization**:
- `not_empty` condition variable: Signals when item available
- `not_full` condition variable: Signals when slot available
- Mutex: Protects buffer operations

**Functions used**:
- `pthread_cond_init()`, `pthread_cond_wait()`, `pthread_cond_signal()`, `pthread_cond_destroy()`
- `pthread_mutex_lock()`, `pthread_mutex_unlock()`

**Expected output**:
- May show "waiting" messages
- Producers and consumers alternate
- Final count: 0

**Execution time**: ~1-2 seconds

---

### Program 3: Limited Resource Access
**File**: `limited_resource_access.c`

**What it does**:
- 8 worker threads compete for 3 resources
- Each thread uses resource for 2 seconds
- Threads queue and wait for availability
- Demonstrates resource pooling

**Key synchronization**:
- Counting semaphore: Tracks available resources
- Mutex: Protects logging

**Functions used**:
- `sem_init()`, `sem_wait()`, `sem_post()`, `sem_destroy()`
- `pthread_mutex_lock()`, `pthread_mutex_unlock()`

**Expected output**:
- Max 3 threads access resources simultaneously
- Other threads wait
- Each thread uses resource for 2 seconds

**Execution time**: ~6 seconds (8 threads ÷ 3 resources × 2 seconds)

---

### Program 4: Thread Scheduling Simulation
**File**: `thread_scheduling_simulation.c`

**What it does**:
- 5 worker threads with random priorities
- 2 scheduler threads manage execution
- Threads scheduled by priority (highest first)
- Demonstrates priority-based scheduling

**Key synchronization**:
- `scheduler_signal` condition variable: Scheduler coordination
- `thread_signal` condition variable: Thread scheduling
- Mutex: Protects scheduler state

**Functions used**:
- `pthread_cond_init()`, `pthread_cond_wait()`, `pthread_cond_signal()`, `pthread_cond_broadcast()`, `pthread_cond_destroy()`
- `pthread_mutex_lock()`, `pthread_mutex_unlock()`

**Expected output**:
- Threads scheduled by priority
- Each thread executes once
- Scheduler threads manage execution

**Execution time**: ~5-7 seconds

---

## Synchronization Primitives Reference

### Semaphores

#### sem_init()
```c
int sem_init(sem_t *sem, int pshared, unsigned int value);
```
- Initialize semaphore
- `pshared`: 0 for threads, 1 for processes
- `value`: Initial counter value

#### sem_wait()
```c
int sem_wait(sem_t *sem);
```
- Decrement counter
- Block if counter is zero
- Atomic operation

#### sem_post()
```c
int sem_post(sem_t *sem);
```
- Increment counter
- Wake one waiting thread
- Atomic operation

#### sem_destroy()
```c
int sem_destroy(sem_t *sem);
```
- Destroy semaphore
- Must not be in use

---

### Condition Variables

#### pthread_cond_init()
```c
int pthread_cond_init(pthread_cond_t *cond, 
                      const pthread_condattr_t *attr);
```
- Initialize condition variable
- `attr`: NULL for default

#### pthread_cond_wait()
```c
int pthread_cond_wait(pthread_cond_t *cond, 
                      pthread_mutex_t *mutex);
```
- Release mutex and wait for signal
- Reacquire mutex when signaled
- Must be in while loop

#### pthread_cond_signal()
```c
int pthread_cond_signal(pthread_cond_t *cond);
```
- Wake one waiting thread
- Doesn't release mutex

#### pthread_cond_broadcast()
```c
int pthread_cond_broadcast(pthread_cond_t *cond);
```
- Wake all waiting threads
- Doesn't release mutex

#### pthread_cond_destroy()
```c
int pthread_cond_destroy(pthread_cond_t *cond);
```
- Destroy condition variable

---

### Mutex

#### pthread_mutex_init()
```c
int pthread_mutex_init(pthread_mutex_t *mutex,
                       const pthread_mutexattr_t *attr);
```
- Initialize mutex
- `attr`: NULL for default

#### pthread_mutex_lock()
```c
int pthread_mutex_lock(pthread_mutex_t *mutex);
```
- Acquire lock
- Block if already locked

#### pthread_mutex_unlock()
```c
int pthread_mutex_unlock(pthread_mutex_t *mutex);
```
- Release lock
- Wake one waiting thread

#### pthread_mutex_destroy()
```c
int pthread_mutex_destroy(pthread_mutex_t *mutex);
```
- Destroy mutex

---

## POSIX Thread Functions Reference

### Thread Management

#### pthread_create()
```c
int pthread_create(pthread_t *thread, const pthread_attr_t *attr,
                   void *(*start_routine) (void *), void *arg);
```
- Create new thread
- `thread`: Thread identifier
- `start_routine`: Function to execute
- `arg`: Argument to function

#### pthread_join()
```c
int pthread_join(pthread_t thread, void **retval);
```
- Wait for thread completion
- `retval`: Thread return value

#### pthread_exit()
```c
void pthread_exit(void *retval);
```
- Exit thread
- `retval`: Return value

---

## Learning Path

### Beginner
1. Read [QUICK_START.md](QUICK_START.md)
2. Compile and run all programs
3. Observe output and understand flow
4. Read [SUMMARY.md](SUMMARY.md)

### Intermediate
1. Read [ASSIGNMENT16_README.md](ASSIGNMENT16_README.md)
2. Study each program's source code
3. Modify programs (change buffer size, thread count, etc.)
4. Observe how changes affect behavior

### Advanced
1. Read [SYNCHRONIZATION_EXPLANATION.md](SYNCHRONIZATION_EXPLANATION.md)
2. Study synchronization patterns
3. Implement custom synchronization scenarios
4. Use thread sanitizer to detect race conditions
5. Optimize for performance

---

## Common Tasks

### Compile All Programs
```bash
gcc -pthread -o producer_consumer_semaphore producer_consumer_semaphore.c
gcc -pthread -o producer_consumer_condition_variable producer_consumer_condition_variable.c
gcc -pthread -o limited_resource_access limited_resource_access.c
gcc -pthread -o thread_scheduling_simulation thread_scheduling_simulation.c
```

### Run All Programs
```bash
./producer_consumer_semaphore
./producer_consumer_condition_variable
./limited_resource_access
./thread_scheduling_simulation
```

### Compile with Debugging
```bash
gcc -pthread -g -fsanitize=thread -o program program.c
```

### Run with Valgrind
```bash
valgrind --leak-check=full ./program
```

---

## Key Concepts

### Race Condition
Multiple threads access shared data without synchronization, causing unpredictable results.

### Mutual Exclusion
Only one thread accesses critical section at a time, preventing race conditions.

### Semaphore
Synchronization primitive that maintains counter for resource counting.

### Condition Variable
Synchronization primitive that signals when conditions change.

### Deadlock
Threads wait for each other indefinitely, preventing progress.

### Critical Section
Code that accesses shared data and must be protected.

### Atomic Operation
Operation that completes without interruption.

### Spurious Wakeup
Thread wakes without being signaled (rare but possible).

---

## Troubleshooting

### Program Hangs
- Check for deadlock
- Verify all threads are created and joined
- Ensure synchronization primitives are initialized

### Incorrect Output
- Check for race conditions
- Verify synchronization logic
- Use thread sanitizer

### Compilation Errors
- Ensure `-pthread` flag is used
- Check for missing includes
- Verify POSIX thread library is installed

### Segmentation Fault
- Verify synchronization primitives are initialized
- Check array bounds
- Use valgrind for memory checking

---

## References

- POSIX Threads: https://pubs.opengroup.org/onlinepubs/9699919799/
- Semaphores: https://man7.org/linux/man-pages/man7/sem_overview.7.html
- Condition Variables: https://man7.org/linux/man-pages/man3/pthread_cond_wait.3p.html
- Thread Synchronization: https://man7.org/linux/man-pages/man7/pthreads.7.html

---

## Summary

**Assignment 16** provides comprehensive coverage of multithreaded C programming with POSIX threads. Four complete programs demonstrate:

- ✅ Semaphore-based synchronization
- ✅ Condition variable-based synchronization
- ✅ Resource pooling and management
- ✅ Priority-based thread scheduling
- ✅ Safe shared-memory access
- ✅ Thread communication and coordination
- ✅ Race condition prevention
- ✅ Deadlock avoidance

All programs are production-ready and fully documented.

---

**Last Updated**: 2026-05-16
**Status**: Complete and Verified
**Quality**: Production-Ready
