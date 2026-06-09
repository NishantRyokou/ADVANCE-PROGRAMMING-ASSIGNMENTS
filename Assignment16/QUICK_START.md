# Quick Start Guide - Assignment 16

## Compilation

Compile all four programs:

```bash
gcc -pthread -o producer_consumer_semaphore producer_consumer_semaphore.c
gcc -pthread -o producer_consumer_condition_variable producer_consumer_condition_variable.c
gcc -pthread -o limited_resource_access limited_resource_access.c
gcc -pthread -o thread_scheduling_simulation thread_scheduling_simulation.c
```

Or compile all at once:

```bash
gcc -pthread -o producer_consumer_semaphore producer_consumer_semaphore.c && \
gcc -pthread -o producer_consumer_condition_variable producer_consumer_condition_variable.c && \
gcc -pthread -o limited_resource_access limited_resource_access.c && \
gcc -pthread -o thread_scheduling_simulation thread_scheduling_simulation.c
```

## Running Programs

### 1. Producer-Consumer (Semaphore)
```bash
./producer_consumer_semaphore
```

**What to observe**:
- 2 producers create 5 items each (10 total)
- 2 consumers consume all 10 items
- Buffer never exceeds 5 items
- Final buffer count: 0

**Expected output**:
```
=== PRODUCER-CONSUMER SYSTEM (SEMAPHORE-BASED) ===
Buffer Size: 5
Producers: 2 | Consumers: 2
Items per Producer: 5

[PRODUCER 1] Produced item: 101 | Buffer count: 1
[CONSUMER 1] Consumed item: 101 | Buffer count: 0
[PRODUCER 2] Produced item: 201 | Buffer count: 1
...
=== EXECUTION COMPLETED ===
Final buffer count: 0 (should be 0)
```

### 2. Producer-Consumer (Condition Variable)
```bash
./producer_consumer_condition_variable
```

**What to observe**:
- Same behavior as semaphore version
- May show "waiting" messages when buffer is full/empty
- Demonstrates condition variable signaling
- Final buffer count: 0

**Expected output**:
```
=== PRODUCER-CONSUMER SYSTEM (CONDITION VARIABLE-BASED) ===
Buffer Size: 5
Producers: 2 | Consumers: 2
Items per Producer: 5

[PRODUCER 1] Produced item: 101 | Buffer count: 1
[PRODUCER 1] Buffer full, waiting...
[CONSUMER 1] Consumed item: 101 | Buffer count: 0
[PRODUCER 1] Produced item: 102 | Buffer count: 1
...
=== EXECUTION COMPLETED ===
Final buffer count: 0 (should be 0)
```

### 3. Limited Resource Access
```bash
./limited_resource_access
```

**What to observe**:
- 8 threads competing for 3 resources
- Maximum 3 threads access resources simultaneously
- Other threads wait in queue
- Each thread uses resource for 2 seconds
- Total execution time: ~6 seconds

**Expected output**:
```
=== LIMITED RESOURCE ACCESS SYSTEM ===
Total Threads: 8
Available Resources: 3
Work Duration per Thread: 2 seconds

[THREAD 1] Started, waiting for resource access...
[THREAD 1] Acquired resource 3 (waited 0 seconds)
[THREAD 2] Started, waiting for resource access...
[THREAD 3] Started, waiting for resource access...
[THREAD 2] Acquired resource 2 (waited 0 seconds)
[THREAD 3] Acquired resource 1 (waited 0 seconds)
[THREAD 4] Started, waiting for resource access...
[THREAD 4] Acquired resource 3 (waited 2 seconds)
...
=== EXECUTION COMPLETED ===
All threads have finished accessing resources
```

### 4. Thread Scheduling Simulation
```bash
./thread_scheduling_simulation
```

**What to observe**:
- 5 worker threads with random priorities
- 2 scheduler threads manage execution
- Threads scheduled by priority (highest first)
- Each thread executes once
- Demonstrates priority-based scheduling

**Expected output**:
```
=== THREAD SCHEDULING SIMULATION ===
Worker Threads: 5
Scheduler Threads: 2

Thread 1 initialized with priority 3
Thread 2 initialized with priority 5
Thread 3 initialized with priority 2
Thread 4 initialized with priority 4
Thread 5 initialized with priority 1

[SCHEDULER 1] Scheduled thread 2 (priority: 5)
[THREAD 2] Scheduled! Executing work...
[SCHEDULER 2] Scheduled thread 4 (priority: 4)
[THREAD 4] Scheduled! Executing work...
[THREAD 2] Work completed
[SCHEDULER 1] Scheduled thread 1 (priority: 3)
...
=== EXECUTION COMPLETED ===
All threads have been scheduled and executed
```

## Key Synchronization Functions

### Semaphores
```c
sem_init(&sem, 0, initial_value);    // Initialize
sem_wait(&sem);                       // Decrement, block if zero
sem_post(&sem);                       // Increment, wake one thread
sem_destroy(&sem);                    // Cleanup
```

### Condition Variables
```c
pthread_cond_init(&cond, NULL);       // Initialize
pthread_cond_wait(&cond, &mutex);     // Wait for signal
pthread_cond_signal(&cond);           // Wake one thread
pthread_cond_broadcast(&cond);        // Wake all threads
pthread_cond_destroy(&cond);          // Cleanup
```

### Mutex
```c
pthread_mutex_init(&mutex, NULL);     // Initialize
pthread_mutex_lock(&mutex);           // Acquire lock
pthread_mutex_unlock(&mutex);         // Release lock
pthread_mutex_destroy(&mutex);        // Cleanup
```

## Comparison: Semaphore vs Condition Variable

| Feature | Semaphore | Condition Variable |
|---------|-----------|-------------------|
| **Counting** | Yes (0 to N) | No |
| **Mutex Required** | No | Yes |
| **Use Case** | Resource counting | Event signaling |
| **Complexity** | Simpler | More flexible |
| **Performance** | Slightly faster | Slightly slower |

**When to use Semaphore**:
- Limiting concurrent access to N resources
- Producer-consumer with fixed buffer
- Simple counting problems

**When to use Condition Variable**:
- Waiting for complex conditions
- Event-based synchronization
- Thread scheduling
- Multiple conditions on same data

## Troubleshooting

### Program hangs
- Check for deadlock (threads waiting for each other)
- Verify all threads are created and joined
- Ensure semaphores/condition variables are properly initialized

### Incorrect output
- Check for race conditions (missing mutex)
- Verify synchronization logic
- Use thread sanitizer: `gcc -fsanitize=thread`

### Compilation errors
- Ensure `-pthread` flag is used
- Check for missing includes: `<pthread.h>`, `<semaphore.h>`
- Verify POSIX thread library is installed

## Performance Tips

1. **Minimize critical sections**: Keep locks held for shortest time
2. **Use appropriate synchronization**: Semaphore for counting, condition variable for events
3. **Avoid nested locks**: Reduces deadlock risk
4. **Use broadcast sparingly**: `signal()` is faster than `broadcast()`
5. **Profile with thread tools**: Identify bottlenecks

## Further Reading

- `man pthread_create` - Thread creation
- `man sem_overview` - Semaphore overview
- `man pthread_cond_wait` - Condition variables
- `man pthread_mutex_lock` - Mutex operations

## Files Included

1. `producer_consumer_semaphore.c` - Semaphore-based producer-consumer
2. `producer_consumer_condition_variable.c` - Condition variable-based producer-consumer
3. `limited_resource_access.c` - Resource pool management
4. `thread_scheduling_simulation.c` - Priority-based scheduling
5. `ASSIGNMENT16_README.md` - Complete documentation
6. `SYNCHRONIZATION_EXPLANATION.md` - Detailed synchronization concepts
7. `QUICK_START.md` - This file

## Summary

All four programs demonstrate proper thread synchronization:
- ✅ Safe shared-memory access
- ✅ Thread communication and coordination
- ✅ Prevention of race conditions
- ✅ Proper use of POSIX thread functions
- ✅ Production-ready code

Run each program to see synchronization in action!
