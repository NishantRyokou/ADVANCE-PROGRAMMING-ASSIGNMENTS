#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>

#define NUM_THREADS 5
#define NUM_SCHEDULERS 2

typedef struct {
    int thread_id;
    int priority;
    int scheduled;
} ThreadInfo;

typedef struct {
    ThreadInfo threads[NUM_THREADS];
    int current_thread;
    int threads_completed;
} Scheduler;

Scheduler scheduler = {{0}, -1, 0};

pthread_mutex_t scheduler_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t scheduler_signal = PTHREAD_COND_INITIALIZER;
pthread_cond_t thread_signal = PTHREAD_COND_INITIALIZER;

void* scheduler_thread(void* arg) {
    int scheduler_id = *(int*)arg;
    
    printf("[SCHEDULER %d] Started\n", scheduler_id);
    
    while (1) {
        pthread_mutex_lock(&scheduler_mutex);
        
        if (scheduler.threads_completed >= NUM_THREADS) {
            pthread_mutex_unlock(&scheduler_mutex);
            break;
        }
        
        int next_thread = -1;
        int highest_priority = -1;
        
        for (int i = 0; i < NUM_THREADS; i++) {
            if (!scheduler.threads[i].scheduled && 
                scheduler.threads[i].priority > highest_priority) {
                highest_priority = scheduler.threads[i].priority;
                next_thread = i;
            }
        }
        
        if (next_thread != -1) {
            scheduler.current_thread = next_thread;
            scheduler.threads[next_thread].scheduled = 1;
            
            printf("[SCHEDULER %d] Scheduled thread %d (priority: %d)\n", 
                   scheduler_id, scheduler.threads[next_thread].thread_id, 
                   scheduler.threads[next_thread].priority);
            
            pthread_cond_broadcast(&thread_signal);
        }
        
        pthread_mutex_unlock(&scheduler_mutex);
        usleep(500000);
    }
    
    printf("[SCHEDULER %d] Finished\n", scheduler_id);
    pthread_exit(NULL);
}

void* worker_thread(void* arg) {
    int thread_id = *(int*)arg;
    
    printf("[THREAD %d] Started, waiting for scheduling...\n", thread_id);
    
    pthread_mutex_lock(&scheduler_mutex);
    
    while (scheduler.current_thread != thread_id) {
        pthread_cond_wait(&thread_signal, &scheduler_mutex);
    }
    
    printf("[THREAD %d] Scheduled! Executing work...\n", thread_id);
    
    pthread_mutex_unlock(&scheduler_mutex);
    
    sleep(1);
    
    printf("[THREAD %d] Work completed\n", thread_id);
    
    pthread_mutex_lock(&scheduler_mutex);
    scheduler.threads_completed++;
    scheduler.current_thread = -1;
    pthread_cond_broadcast(&scheduler_signal);
    pthread_mutex_unlock(&scheduler_mutex);
    
    pthread_exit(NULL);
}

int main() {
    srand(time(NULL));
    
    pthread_t schedulers[NUM_SCHEDULERS];
    pthread_t workers[NUM_THREADS];
    int scheduler_ids[NUM_SCHEDULERS];
    int thread_ids[NUM_THREADS];
    
    printf("=== THREAD SCHEDULING SIMULATION ===\n");
    printf("Worker Threads: %d\n", NUM_THREADS);
    printf("Scheduler Threads: %d\n\n", NUM_SCHEDULERS);
    
    for (int i = 0; i < NUM_THREADS; i++) {
        scheduler.threads[i].thread_id = i + 1;
        scheduler.threads[i].priority = (rand() % 5) + 1;
        scheduler.threads[i].scheduled = 0;
        printf("Thread %d initialized with priority %d\n", 
               scheduler.threads[i].thread_id, scheduler.threads[i].priority);
    }
    
    printf("\n");
    
    for (int i = 0; i < NUM_SCHEDULERS; i++) {
        scheduler_ids[i] = i + 1;
        pthread_create(&schedulers[i], NULL, scheduler_thread, &scheduler_ids[i]);
    }
    
    for (int i = 0; i < NUM_THREADS; i++) {
        thread_ids[i] = i + 1;
        pthread_create(&workers[i], NULL, worker_thread, &thread_ids[i]);
        usleep(100000);
    }
    
    for (int i = 0; i < NUM_THREADS; i++) {
        pthread_join(workers[i], NULL);
    }
    
    for (int i = 0; i < NUM_SCHEDULERS; i++) {
        pthread_join(schedulers[i], NULL);
    }
    
    printf("\n=== EXECUTION COMPLETED ===\n");
    printf("All threads have been scheduled and executed\n");
    
    pthread_mutex_destroy(&scheduler_mutex);
    pthread_cond_destroy(&scheduler_signal);
    pthread_cond_destroy(&thread_signal);
    
    return 0;
}
