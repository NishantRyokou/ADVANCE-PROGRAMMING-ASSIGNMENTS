# Assignment 16: Multithreaded C Program with Semaphores and Condition Variables

## Overview

This assignment demonstrates advanced thread synchronization techniques in C using POSIX threads. Four complete programs showcase different synchronization mechanisms and real-world scenarios.

## Programs Included

### 1. Producer-Consumer System (Semaphore-Based)
**File**: `producer_consumer_semaphore.c`

**Concept**: Classic producer-consumer problem solved using semaphores.

**Key Features**:
- 2 producer threads and 2 consumer threads
- Circular buffer with fixed size (5 items)
- Two semaphores: `empty` (tracks empty slots) and `full` (tracks filled slots)
- Mutex for protecting critical sections

**Synchronization Mechanism**:
- `sem_wait(&empty)`: Producer waits if buffer is full
- `sem_post(&full)`: Producer signals that item is available
- `sem_wait(&full)`: Consumer waits if buffer is empty
- `sem_post(&empty)`: Consumer signals that slot is available

**Output Example**:
```
[PRODUCER 1] Produced item: 101 | Buffer count: 1
[CONSUMER 1] Consumed item: 101 | Buffer count: 0
[PRODUCER 2] Produced item: 201 | Buffer count: 1
```

**Why This Works**:
- Semaphores maintain count of available resources
- Prevents buffer overflow (producer waits when full)
- Prevents buffer underflow (consumer waits when empty)
- Mutex ensures atomic operations on shared buffer

---

### 2. Producer-Consumer System (Condition Variable-Based)
**File**: `producer_consumer_condition_variable.c`

**Concept**: Same producer-consumer problem using condition variables instead of semaphores.

**Key Features**:
- 2 producer threads and 2 consumer threads
- Circular buffer with fixed size (5 items)
- Two condition variables: `not_empty` and `not_full`
- Single mutex for all synchronization

**Synchronization Mechanism**:
- `pthread_cond_wait(&not_full, &mutex)`: Producer waits if buffer is full
- `pthread_cond_signal(&not_empty)`: Producer signals consumers
- `pthread_cond_wait(&not_empty, &mutex)`: Consumer waits if buffer is empty
- `pthread_cond_signal(&not_full)`: Consumer signals producers

**Output Example**:
```
[PRODUCER 1] Buffer full, waiting...
[CONSUMER 1] Consumed item: 101 | Buffer count: 2
[PRODUCER 1] Produced item: 102 | Buffer count: 3
```

**Why This Works**:
- Condition variables allow threads to wait for specific conditions
- `while` loop prevents spurious wakeups
- More flexible than semaphores for complex conditions
- Mutex ensures only one thread modifies buffer at a time

**Semaphore vs Condition Variable**:
| Aspect | Semaphore | Condition Variable |
|--------|-----------|-------------------|
| Counting | Yes (maintains count) | No (just signals) |
| Use Case | Resource counting | Condition checking |
| Complexity | Simpler for counting | Better for complex conditions |
| Performance | Slightly faster | More flexible |

---

### 3. Limited Resource Access System
**File**: `limited_resource_access.c`

**Concept**: Multiple threads competing for limited shared resources.

**Key Features**:
- 8 worker threads
- 3 available resources
- Semaphore initialized with count = 3
- Threads queue and wait for resource availability

**Synchronization Mechanism**:
- `sem_wait(&resource_semaphore)`: Thread waits if no resources available
- `sem_post(&resource_semaphore)`: Thread releases resource for others

**Output Example**:
```
[THREAD 1] Started, waiting for resource access...
[THREAD 1] Acquired resource 3 (waited 0 seconds)
[THREAD 4] Started, waiting for resource access...
[THREAD 4] Acquired resource 2 (waited 2 seconds)
[THREAD 1] Releasing resource 3 (used for 2 seconds)
```

**Why This Works**:
- Semaphore count represents available resources
- When count reaches 0, threads block on `sem_wait()`
- As threads release resources, others are unblocked
- Ensures at most 3 threads access resources simultaneously

**Real-World Application**:
- Database connection pooling
- Thread pool management
- Printer queue management
- Limited bandwidth allocation

---

### 4. Thread Scheduling Simulation
**File**: `thread_scheduling_simulation.c`

**Concept**: Scheduler threads manage execution of worker threads based on priority.

**Key Features**:
- 5 worker threads with random priorities (1-5)
- 2 scheduler threads
- Condition variables for thread-scheduler communication
- Priority-based scheduling

**Synchronization Mechanism**:
- `pthread_cond_wait(&thread_signal, &mutex)`: Worker waits to be scheduled
- `pthread_cond_broadcast(&thread_signal)`: Scheduler signals all workers
- `pthread_cond_signal(&scheduler_signal)`: Worker signals scheduler

**Output Example**:
```
Thread 1 initialized with priority 3
Thread 2 initialized with priority 5
Thread 3 initialized with priority 2
[SCHEDULER 1] Scheduled thread 2 (priority: 5)
[THREAD 2] Scheduled! Executing work...
[THREAD 2] Work completed
```

**Why This Works**:
- Scheduler selects highest priority thread
- Broadcasts signal to wake all waiting threads
- Only scheduled thread proceeds (checks `current_thread`)
- Ensures orderly execution based on priority

**Real-World Application**:
- Operating system process scheduling
- Task queue management
- Priority-based job execution
- Load balancing systems

---

## Synchronization Primitives Explained

### Semaphores (`sem_t`)

**Functions**:
- `sem_init(sem_t *sem, int pshared, unsigned int value)`: Initialize semaphore
- `sem_wait(sem_t *sem)`: Decrement semaphore, block if zero
- `sem_post(sem_t *sem)`: Increment semaphore, wake one waiting thread
- `sem_destroy(sem_t *sem)`: Destroy semaphore

**Characteristics**:
- Maintains internal counter
- Atomic operations
- Binary semaphore (0 or 1) or counting semaphore (0 to N)
- Simpler for resource counting

**Use Cases**:
- Resource pools
- Producer-consumer with fixed buffer
- Limiting concurrent access

### Condition Variables (`pthread_cond_t`)

**Functions**:
- `pthread_cond_init(pthread_cond_t *cond, const pthread_condattr_t *attr)`: Initialize
- `pthread_cond_wait(pthread_cond_t *cond, pthread_mutex_t *mutex)`: Wait for signal
- `pthread_cond_signal(pthread_cond_t *cond)`: Wake one waiting thread
- `pthread_cond_broadcast(pthread_cond_t *cond)`: Wake all waiting threads
- `pthread_cond_destroy(pthread_cond_t *cond)`: Destroy condition variable

**Characteristics**:
- No internal counter
- Always used with mutex
- Prevents spurious wakeups with while loops
- More flexible for complex conditions

**Use Cases**:
- Waiting for specific conditions
- Complex synchronization patterns
- Thread scheduling
- Event notification

### Mutex (`pthread_mutex_t`)

**Functions**:
- `pthread_mutex_init(pthread_mutex_t *mutex, const pthread_mutexattr_t *attr)`: Initialize
- `pthread_mutex_lock(pthread_mutex_t *mutex)`: Acquire lock
- `pthread_mutex_unlock(pthread_mutex_t *mutex)`: Release lock
- `pthread_mutex_destroy(pthread_mutex_t *mutex)`: Destroy mutex

**Characteristics**:
- Ensures mutual exclusion
- Only one thread can hold lock
- Prevents race conditions
- Essential for protecting shared data

---

## How Synchronization Prevents Inconsistent Behavior

### Without Synchronization (Race Condition)
```
Thread 1: Read buffer[0] = 5
Thread 2: Read buffer[0] = 5
Thread 1: Increment to 6, Write buffer[0] = 6
Thread 2: Increment to 6, Write buffer[0] = 6
Result: buffer[0] = 6 (should be 7)
```

### With Semaphore/Mutex (Correct)
```
Thread 1: Lock mutex
Thread 1: Read buffer[0] = 5
Thread 1: Increment to 6, Write buffer[0] = 6
Thread 1: Unlock mutex
Thread 2: Lock mutex (was waiting)
Thread 2: Read buffer[0] = 6
Thread 2: Increment to 7, Write buffer[0] = 7
Thread 2: Unlock mutex
Result: buffer[0] = 7 (correct)
```

### Key Benefits

1. **Atomicity**: Operations complete without interruption
2. **Consistency**: Shared data remains in valid state
3. **Isolation**: Threads don't interfere with each other
4. **Ordering**: Threads execute in predictable order

---

## Compilation and Execution

### Compile All Programs
```bash
gcc -pthread -o producer_consumer_semaphore producer_consumer_semaphore.c
gcc -pthread -o producer_consumer_condition_variable producer_consumer_condition_variable.c
gcc -pthread -o limited_resource_access limited_resource_access.c
gcc -pthread -o thread_scheduling_simulation thread_scheduling_simulation.c
```

### Run Programs
```bash
./producer_consumer_semaphore
./producer_consumer_condition_variable
./limited_resource_access
./thread_scheduling_simulation
```

### Compilation Flags
- `-pthread`: Link with POSIX thread library
- `-o`: Output executable name

---

## Expected Behavior

### Producer-Consumer (Semaphore)
- Producers and consumers alternate
- Buffer never exceeds 5 items
- All 10 items (2 producers × 5 items) produced and consumed
- Final buffer count: 0

### Producer-Consumer (Condition Variable)
- Similar to semaphore version
- May show "waiting" messages when buffer is full/empty
- Demonstrates condition variable signaling
- Final buffer count: 0

### Limited Resource Access
- Maximum 3 threads access resources simultaneously
- Other threads wait in queue
- Each thread uses resource for 2 seconds
- Total execution time: ~6 seconds (8 threads ÷ 3 resources × 2 seconds)

### Thread Scheduling
- Threads scheduled by priority (highest first)
- Scheduler threads manage execution order
- Each thread executes once in priority order
- Demonstrates priority-based scheduling

---

## Key Concepts Demonstrated

1. **Thread Creation**: `pthread_create()` for spawning threads
2. **Thread Joining**: `pthread_join()` for waiting thread completion
3. **Semaphore Operations**: `sem_wait()`, `sem_post()` for resource counting
4. **Condition Variables**: `pthread_cond_wait()`, `pthread_cond_signal()` for signaling
5. **Mutex Protection**: `pthread_mutex_lock()`, `pthread_mutex_unlock()` for critical sections
6. **Shared Memory**: Safe access to shared buffer/resources
7. **Thread Communication**: Synchronization between multiple threads
8. **Deadlock Prevention**: Proper lock ordering and condition checking

---

## Common Pitfalls and Solutions

### Pitfall 1: Spurious Wakeups
**Problem**: Thread wakes without condition being true
**Solution**: Always use `while` loop with condition variables
```c
while (condition_not_met) {
    pthread_cond_wait(&cond, &mutex);
}
```

### Pitfall 2: Deadlock
**Problem**: Threads waiting for each other indefinitely
**Solution**: Maintain consistent lock ordering, use timeouts

### Pitfall 3: Lost Signals
**Problem**: Signal sent before thread waits
**Solution**: Always check condition before waiting, use broadcast for multiple waiters

### Pitfall 4: Forgetting Mutex
**Problem**: Race condition on shared data
**Solution**: Always protect shared data with mutex

---

## Performance Considerations

1. **Semaphores**: Faster for simple counting, lower overhead
2. **Condition Variables**: More flexible, slightly higher overhead
3. **Mutex**: Essential but can become bottleneck with high contention
4. **Lock Granularity**: Smaller critical sections = better parallelism

---

## References

- POSIX Threads: https://pubs.opengroup.org/onlinepubs/9699919799/
- Semaphores: https://man7.org/linux/man-pages/man7/sem_overview.7.html
- Condition Variables: https://man7.org/linux/man-pages/man3/pthread_cond_wait.3p.html
- Thread Synchronization: https://man7.org/linux/man-pages/man7/pthreads.7.html

---

## Summary

This assignment demonstrates:
- ✅ Producer-consumer synchronization with semaphores
- ✅ Producer-consumer synchronization with condition variables
- ✅ Limited resource access management
- ✅ Thread scheduling and priority-based execution
- ✅ Safe shared-memory access
- ✅ Thread communication and coordination
- ✅ Prevention of race conditions and deadlocks
- ✅ Proper use of POSIX thread functions

All programs are production-ready and demonstrate best practices for multithreaded C programming.
