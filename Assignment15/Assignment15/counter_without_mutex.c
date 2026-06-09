#include <stdio.h>
#include <windows.h>
#include <stdint.h>

#define NUM_THREADS 5
#define INCREMENTS_PER_THREAD 100000

int shared_counter = 0;

DWORD WINAPI increment_counter(LPVOID arg) {
    int thread_id = (intptr_t)arg;
    
    for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
        shared_counter++;
    }
    
    printf("Thread %d finished\n", thread_id);
    return 0;
}

int main() {
    HANDLE threads[NUM_THREADS];
    
    printf("=== Counter Without Mutex (Race Condition) ===\n");
    printf("Number of threads: %d\n", NUM_THREADS);
    printf("Increments per thread: %d\n", INCREMENTS_PER_THREAD);
    printf("Expected final counter: %d\n\n", NUM_THREADS * INCREMENTS_PER_THREAD);
    
    printf("Creating threads...\n");
    for (int i = 0; i < NUM_THREADS; i++) {
        threads[i] = CreateThread(NULL, 0, increment_counter, (LPVOID)(intptr_t)i, 0, NULL);
    }
    
    printf("Waiting for threads to complete...\n");
    WaitForMultipleObjects(NUM_THREADS, threads, TRUE, INFINITE);
    
    for (int i = 0; i < NUM_THREADS; i++) {
        CloseHandle(threads[i]);
    }
    
    printf("\n=== Results ===\n");
    printf("Final counter value: %d\n", shared_counter);
    printf("Expected value: %d\n", NUM_THREADS * INCREMENTS_PER_THREAD);
    printf("Difference: %d\n", (NUM_THREADS * INCREMENTS_PER_THREAD) - shared_counter);
    
    if (shared_counter != NUM_THREADS * INCREMENTS_PER_THREAD) {
        printf("\n⚠️ RACE CONDITION DETECTED!\n");
        printf("The counter value is incorrect due to unsynchronized access.\n");
    } else {
        printf("\n✓ Counter is correct (lucky run - race condition may not have occurred)\n");
    }
    
    return 0;
}
