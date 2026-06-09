#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <time.h>

#define NUM_THREADS 8
#define NUM_RESOURCES 3
#define WORK_DURATION 2

typedef struct {
    int thread_id;
    int resource_id;
    int start_time;
} ResourceAccess;

sem_t resource_semaphore;
pthread_mutex_t log_mutex = PTHREAD_MUTEX_INITIALIZER;

int get_elapsed_time(int start_time) {
    return (int)(time(NULL)) - start_time;
}

void* thread_work(void* arg) {
    int thread_id = *(int*)arg;
    int start_time = (int)time(NULL);
    
    printf("[THREAD %d] Started, waiting for resource access...\n", thread_id);
    
    sem_wait(&resource_semaphore);
    
    int resource_id = NUM_RESOURCES - (thread_id % NUM_RESOURCES);
    int access_time = get_elapsed_time(start_time);
    
    pthread_mutex_lock(&log_mutex);
    printf("[THREAD %d] Acquired resource %d (waited %d seconds)\n", 
           thread_id, resource_id, access_time);
    pthread_mutex_unlock(&log_mutex);
    
    sleep(WORK_DURATION);
    
    pthread_mutex_lock(&log_mutex);
    printf("[THREAD %d] Releasing resource %d (used for %d seconds)\n", 
           thread_id, resource_id, WORK_DURATION);
    pthread_mutex_unlock(&log_mutex);
    
    sem_post(&resource_semaphore);
    
    printf("[THREAD %d] Finished\n", thread_id);
    pthread_exit(NULL);
}

int main() {
    srand(time(NULL));
    
    pthread_t threads[NUM_THREADS];
    int thread_ids[NUM_THREADS];
    
    sem_init(&resource_semaphore, 0, NUM_RESOURCES);
    
    printf("=== LIMITED RESOURCE ACCESS SYSTEM ===\n");
    printf("Total Threads: %d\n", NUM_THREADS);
    printf("Available Resources: %d\n", NUM_RESOURCES);
    printf("Work Duration per Thread: %d seconds\n\n", WORK_DURATION);
    
    for (int i = 0; i < NUM_THREADS; i++) {
        thread_ids[i] = i + 1;
        pthread_create(&threads[i], NULL, thread_work, &thread_ids[i]);
        usleep(100000);
    }
    
    for (int i = 0; i < NUM_THREADS; i++) {
        pthread_join(threads[i], NULL);
    }
    
    printf("\n=== EXECUTION COMPLETED ===\n");
    printf("All threads have finished accessing resources\n");
    
    sem_destroy(&resource_semaphore);
    pthread_mutex_destroy(&log_mutex);
    
    return 0;
}
