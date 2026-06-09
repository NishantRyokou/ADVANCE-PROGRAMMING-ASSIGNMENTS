# Synchronization Mechanisms: Detailed Explanation

## Table of Contents
1. [Race Conditions](#race-conditions)
2. [Semaphores](#semaphores)
3. [Condition Variables](#condition-variables)
4. [Mutex](#mutex)
5. [Synchronization Patterns](#synchronization-patterns)
6. [Deadlock Prevention](#deadlock-prevention)

---

## Race Conditions

### What is a Race Condition?

A race condition occurs when multiple threads access shared data concurrently, and at least one thread modifies the data, without proper synchronization. The final result depends on the order of execution, which is unpredictable.

### Example: Unsynchronized Counter

```c
int counter = 0;

void* increment(void* arg) {
    for (int i = 0; i < 1000000; i++) {
        counter++;  // NOT ATOMIC!
    }
}

// Main: Create 2 threads
// Expected: counter = 2000000
// Actual: counter = 1234567 (varies each run)
```

### Why This Happens

The operation `counter++` is NOT atomic. It consists of three steps:
1. Load counter value from memory
2. Increment the value
3. Store back to memory

**Timeline of Race Condition**:
```
Time | Thread 1           | Thread 2           | Memory
-----|-------------------|-------------------|--------
 1   | Load counter (0)   |                    | counter = 0
 2   |                    | Load counter (0)   | counter = 0
 3   | Increment to 1     |                    | counter = 0
 4   |                    | Increment to 1     | counter = 0
 5   | Store counter (1)  |                    | counter = 1
 6   |                    | Store counter (1)  | counter = 1
```

Result: counter = 1 (should be 2)

### Consequences

- Incorrect calculations
- Data corruption
- Unpredictable behavior
- Difficult to debug (happens randomly)

---

## Semaphores

### What is a Semaphore?

A semaphore is a synchronization primitive that maintains an internal counter. It provides two atomic operations:
- **Wait (P operation)**: Decrement counter, block if zero
- **Post (V operation)**: Increment counter, wake one waiting thread

### Types of Semaphores

#### Binary Semaphore
- Counter: 0 or 1
- Acts like a lock
- Used for mutual exclusion

```c
sem_t binary_sem;
sem_init(&binary_sem, 0, 1);  // Initialize to 1

sem_wait(&binary_sem);   // Acquire (counter becomes 0)
// Critical section
sem_post(&binary_sem);   // Release (counter becomes 1)
```

#### Counting Semaphore
- Counter: 0 to N
- Tracks available resources
- Used for resource pooling

```c
sem_t resource_sem;
sem_init(&resource_sem, 0, 3);  // 3 resources available

sem_wait(&resource_sem);   // Acquire resource (counter: 3→2)
// Use resource
sem_post(&resource_sem);   // Release resource (counter: 2→3)
```

### Semaphore Operations

#### sem_init()
```c
int sem_init(sem_t *sem, int pshared, unsigned int value);
```
- `sem`: Pointer to semaphore
- `pshared`: 0 for threads, 1 for processes
- `value`: Initial counter value
- Returns: 0 on success, -1 on error

#### sem_wait()
```c
int sem_wait(sem_t *sem);
```
- Atomically decrements counter
- If counter > 0: Decrements and returns immediately
- If counter = 0: Blocks until another thread calls sem_post()
- Returns: 0 on success, -1 on error

#### sem_post()
```c
int sem_post(sem_t *sem);
```
- Atomically increments counter
- Wakes one waiting thread (if any)
- Returns: 0 on success, -1 on error

#### sem_destroy()
```c
int sem_destroy(sem_t *sem);
```
- Destroys semaphore
- Must not be in use by any thread

### Semaphore Example: Producer-Consumer

```c
sem_t empty;   // Tracks empty slots
sem_t full;    // Tracks filled slots

sem_init(&empty, 0, BUFFER_SIZE);  // Buffer starts empty
sem_init(&full, 0, 0);             // No items initially

void* producer() {
    int item = produce_item();
    
    sem_wait(&empty);              // Wait for empty slot
    // Add item to buffer
    sem_post(&full);               // Signal item available
}

void* consumer() {
    sem_wait(&full);               // Wait for item
    int item = get_from_buffer();
    sem_post(&empty);              // Signal slot available
}
```

### Advantages of Semaphores
- Simple and efficient
- Good for resource counting
- Low overhead
- Well-understood semantics

### Disadvantages of Semaphores
- No built-in condition checking
- Can be misused (easy to deadlock)
- Less flexible than condition variables

---

## Condition Variables

### What is a Condition Variable?

A condition variable is a synchronization primitive that allows threads to wait for a specific condition to become true. Unlike semaphores, it doesn't maintain a counter—it just signals when a condition changes.

### Key Characteristics

- No internal counter
- Always used with a mutex
- Prevents spurious wakeups with while loops
- More flexible for complex conditions

### Condition Variable Operations

#### pthread_cond_init()
```c
int pthread_cond_init(pthread_cond_t *cond, 
                      const pthread_condattr_t *attr);
```
- Initializes condition variable
- `attr`: NULL for default attributes

#### pthread_cond_wait()
```c
int pthread_cond_wait(pthread_cond_t *cond, 
                      pthread_mutex_t *mutex);
```
- Atomically releases mutex and waits for signal
- When signaled, reacquires mutex and returns
- Must be called with mutex held
- Must be in while loop to check condition

#### pthread_cond_signal()
```c
int pthread_cond_signal(pthread_cond_t *cond);
```
- Wakes one waiting thread
- If multiple threads waiting, only one is woken
- Doesn't release mutex

#### pthread_cond_broadcast()
```c
int pthread_cond_broadcast(pthread_cond_t *cond);
```
- Wakes all waiting threads
- All threads reacquire mutex and check condition

#### pthread_cond_destroy()
```c
int pthread_cond_destroy(pthread_cond_t *cond);
```
- Destroys condition variable

### Condition Variable Pattern

```c
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cond = PTHREAD_COND_INITIALIZER;
int condition_flag = 0;

// Waiting thread
pthread_mutex_lock(&mutex);
while (!condition_flag) {  // MUST use while, not if
    pthread_cond_wait(&cond, &mutex);
}
// Condition is true, mutex is held
pthread_mutex_unlock(&mutex);

// Signaling thread
pthread_mutex_lock(&mutex);
condition_flag = 1;
pthread_cond_signal(&cond);  // or broadcast()
pthread_mutex_unlock(&mutex);
```

### Why Use While Loop?

**Spurious Wakeup**: Thread wakes without being signaled (rare but possible)

```c
// WRONG: Uses if
if (!condition_flag) {
    pthread_cond_wait(&cond, &mutex);
}
// Might proceed even if condition is false!

// CORRECT: Uses while
while (!condition_flag) {
    pthread_cond_wait(&cond, &mutex);
}
// Rechecks condition after waking
```

### Condition Variable Example: Producer-Consumer

```c
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t not_empty = PTHREAD_COND_INITIALIZER;
pthread_cond_t not_full = PTHREAD_COND_INITIALIZER;
int buffer_count = 0;

void* producer() {
    int item = produce_item();
    
    pthread_mutex_lock(&mutex);
    while (buffer_count >= BUFFER_SIZE) {
        pthread_cond_wait(&not_full, &mutex);
    }
    add_to_buffer(item);
    buffer_count++;
    pthread_cond_signal(&not_empty);
    pthread_mutex_unlock(&mutex);
}

void* consumer() {
    pthread_mutex_lock(&mutex);
    while (buffer_count <= 0) {
        pthread_cond_wait(&not_empty, &mutex);
    }
    int item = get_from_buffer();
    buffer_count--;
    pthread_cond_signal(&not_full);
    pthread_mutex_unlock(&mutex);
}
```

### Advantages of Condition Variables
- Flexible condition checking
- Prevents spurious wakeups with while loops
- Better for complex synchronization
- More intuitive for event-based systems

### Disadvantages of Condition Variables
- Requires mutex (more overhead)
- More complex to use correctly
- Easier to make mistakes (forgetting while loop)

---

## Mutex

### What is a Mutex?

A mutex (mutual exclusion) is a lock that ensures only one thread can access a critical section at a time.

### Mutex Operations

#### pthread_mutex_init()
```c
int pthread_mutex_init(pthread_mutex_t *mutex,
                       const pthread_mutexattr_t *attr);
```

#### pthread_mutex_lock()
```c
int pthread_mutex_lock(pthread_mutex_t *mutex);
```
- Acquires lock
- Blocks if another thread holds lock
- Returns when lock is acquired

#### pthread_mutex_unlock()
```c
int pthread_mutex_unlock(pthread_mutex_t *mutex);
```
- Releases lock
- Wakes one waiting thread

#### pthread_mutex_destroy()
```c
int pthread_mutex_destroy(pthread_mutex_t *mutex);
```
- Destroys mutex

### Mutex Example

```c
pthread_mutex_t counter_mutex = PTHREAD_MUTEX_INITIALIZER;
int counter = 0;

void* increment(void* arg) {
    for (int i = 0; i < 1000000; i++) {
        pthread_mutex_lock(&counter_mutex);
        counter++;  // Now atomic
        pthread_mutex_unlock(&counter_mutex);
    }
}

// Main: Create 2 threads
// Result: counter = 2000000 (always correct)
```

### Mutex vs Semaphore

| Aspect | Mutex | Semaphore |
|--------|-------|-----------|
| Purpose | Mutual exclusion | Resource counting |
| Counter | No (binary) | Yes (0 to N) |
| Ownership | Thread that locked it | Any thread |
| Use Case | Protecting data | Limiting access |
| Complexity | Simple | Medium |

---

## Synchronization Patterns

### Pattern 1: Mutual Exclusion

**Goal**: Only one thread accesses resource at a time

```c
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

void* worker(void* arg) {
    pthread_mutex_lock(&mutex);
    // Critical section
    shared_resource++;
    pthread_mutex_unlock(&mutex);
}
```

### Pattern 2: Producer-Consumer

**Goal**: Producers add items, consumers remove items, buffer bounded

```c
sem_t empty, full;
pthread_mutex_t mutex;

void* producer() {
    sem_wait(&empty);
    pthread_mutex_lock(&mutex);
    add_item();
    pthread_mutex_unlock(&mutex);
    sem_post(&full);
}

void* consumer() {
    sem_wait(&full);
    pthread_mutex_lock(&mutex);
    remove_item();
    pthread_mutex_unlock(&mutex);
    sem_post(&empty);
}
```

### Pattern 3: Reader-Writer

**Goal**: Multiple readers OR one writer

```c
pthread_rwlock_t rwlock = PTHREAD_RWLOCK_INITIALIZER;

void* reader(void* arg) {
    pthread_rwlock_rdlock(&rwlock);
    read_data();
    pthread_rwlock_unlock(&rwlock);
}

void* writer(void* arg) {
    pthread_rwlock_wrlock(&rwlock);
    write_data();
    pthread_rwlock_unlock(&rwlock);
}
```

### Pattern 4: Barrier

**Goal**: All threads wait until all reach barrier

```c
pthread_barrier_t barrier;
pthread_barrier_init(&barrier, NULL, NUM_THREADS);

void* worker(void* arg) {
    do_phase1();
    pthread_barrier_wait(&barrier);  // Wait for all
    do_phase2();
}
```

---

## Deadlock Prevention

### What is Deadlock?

Deadlock occurs when threads wait for each other indefinitely, preventing progress.

### Deadlock Example

```c
pthread_mutex_t mutex1, mutex2;

void* thread1() {
    pthread_mutex_lock(&mutex1);
    sleep(1);  // Let thread2 acquire mutex2
    pthread_mutex_lock(&mutex2);  // DEADLOCK: waiting for mutex2
}

void* thread2() {
    pthread_mutex_lock(&mutex2);
    sleep(1);  // Let thread1 acquire mutex1
    pthread_mutex_lock(&mutex1);  // DEADLOCK: waiting for mutex1
}
```

### Deadlock Prevention Strategies

#### 1. Lock Ordering
Always acquire locks in same order

```c
// CORRECT: Always acquire mutex1 before mutex2
void* thread1() {
    pthread_mutex_lock(&mutex1);
    pthread_mutex_lock(&mutex2);
    // Use resources
    pthread_mutex_unlock(&mutex2);
    pthread_mutex_unlock(&mutex1);
}

void* thread2() {
    pthread_mutex_lock(&mutex1);
    pthread_mutex_lock(&mutex2);
    // Use resources
    pthread_mutex_unlock(&mutex2);
    pthread_mutex_unlock(&mutex1);
}
```

#### 2. Timeout
Use timed lock to detect deadlock

```c
struct timespec timeout;
clock_gettime(CLOCK_REALTIME, &timeout);
timeout.tv_sec += 5;  // 5 second timeout

int result = pthread_mutex_timedlock(&mutex, &timeout);
if (result == ETIMEDOUT) {
    printf("Deadlock detected!\n");
}
```

#### 3. Avoid Nested Locks
Minimize lock nesting

```c
// WRONG: Nested locks
void* worker() {
    pthread_mutex_lock(&mutex1);
    pthread_mutex_lock(&mutex2);
    // ...
    pthread_mutex_unlock(&mutex2);
    pthread_mutex_unlock(&mutex1);
}

// BETTER: Single lock
void* worker() {
    pthread_mutex_lock(&combined_mutex);
    // ...
    pthread_mutex_unlock(&combined_mutex);
}
```

#### 4. Condition Variables
Use condition variables instead of busy waiting

```c
// WRONG: Busy waiting (wastes CPU)
while (!condition) {
    sleep(1);
}

// CORRECT: Condition variable
pthread_mutex_lock(&mutex);
while (!condition) {
    pthread_cond_wait(&cond, &mutex);
}
pthread_mutex_unlock(&mutex);
```

---

## Summary Table

| Primitive | Purpose | Counter | Use Case |
|-----------|---------|---------|----------|
| Mutex | Mutual exclusion | No | Protecting shared data |
| Semaphore | Resource counting | Yes | Limiting concurrent access |
| Condition Variable | Event signaling | No | Waiting for conditions |
| Barrier | Synchronization point | N/A | Coordinating phases |
| RWLock | Reader-writer sync | No | Multiple readers, one writer |

---

## Best Practices

1. **Always use while loops with condition variables**
2. **Maintain consistent lock ordering**
3. **Keep critical sections small**
4. **Release locks in reverse order of acquisition**
5. **Use timeouts to detect deadlocks**
6. **Avoid nested locks when possible**
7. **Test with multiple thread counts**
8. **Use thread sanitizer to detect races**

---

## Compilation with Thread Sanitizer

```bash
gcc -pthread -fsanitize=thread -g -o program program.c
./program
```

This detects race conditions and synchronization errors.
