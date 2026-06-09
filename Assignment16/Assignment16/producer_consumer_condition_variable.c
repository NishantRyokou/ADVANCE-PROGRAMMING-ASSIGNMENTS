#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>

#define BUFFER_SIZE 5
#define NUM_PRODUCERS 2
#define NUM_CONSUMERS 2
#define ITEMS_PER_PRODUCER 5

typedef struct {
    int buffer[BUFFER_SIZE];
    int count;
    int in;
    int out;
} SharedBuffer;

SharedBuffer shared_buffer = {{0}, 0, 0, 0};

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t not_empty = PTHREAD_COND_INITIALIZER;
pthread_cond_t not_full = PTHREAD_COND_INITIALIZER;

void* producer(void* arg) {
    int producer_id = *(int*)arg;
    
    for (int i = 0; i < ITEMS_PER_PRODUCER; i++) {
        int item = (producer_id * 100) + i + 1;
        
        pthread_mutex_lock(&mutex);
        
        while (shared_buffer.count >= BUFFER_SIZE) {
            printf("[PRODUCER %d] Buffer full, waiting...\n", producer_id);
            pthread_cond_wait(&not_full, &mutex);
        }
        
        shared_buffer.buffer[shared_buffer.in] = item;
        shared_buffer.in = (shared_buffer.in + 1) % BUFFER_SIZE;
        shared_buffer.count++;
        
        printf("[PRODUCER %d] Produced item: %d | Buffer count: %d\n", 
               producer_id, item, shared_buffer.count);
        
        pthread_cond_signal(&not_empty);
        pthread_mutex_unlock(&mutex);
        
        usleep(100000 + (rand() % 200000));
    }
    
    printf("[PRODUCER %d] Finished producing\n", producer_id);
    pthread_exit(NULL);
}

void* consumer(void* arg) {
    int consumer_id = *(int*)arg;
    
    for (int i = 0; i < ITEMS_PER_PRODUCER; i++) {
        pthread_mutex_lock(&mutex);
        
        while (shared_buffer.count <= 0) {
            printf("[CONSUMER %d] Buffer empty, waiting...\n", consumer_id);
            pthread_cond_wait(&not_empty, &mutex);
        }
        
        int item = shared_buffer.buffer[shared_buffer.out];
        shared_buffer.out = (shared_buffer.out + 1) % BUFFER_SIZE;
        shared_buffer.count--;
        
        printf("[CONSUMER %d] Consumed item: %d | Buffer count: %d\n", 
               consumer_id, item, shared_buffer.count);
        
        pthread_cond_signal(&not_full);
        pthread_mutex_unlock(&mutex);
        
        usleep(150000 + (rand() % 250000));
    }
    
    printf("[CONSUMER %d] Finished consuming\n", consumer_id);
    pthread_exit(NULL);
}

int main() {
    srand(time(NULL));
    
    pthread_t producers[NUM_PRODUCERS];
    pthread_t consumers[NUM_CONSUMERS];
    int producer_ids[NUM_PRODUCERS];
    int consumer_ids[NUM_CONSUMERS];
    
    printf("=== PRODUCER-CONSUMER SYSTEM (CONDITION VARIABLE-BASED) ===\n");
    printf("Buffer Size: %d\n", BUFFER_SIZE);
    printf("Producers: %d | Consumers: %d\n", NUM_PRODUCERS, NUM_CONSUMERS);
    printf("Items per Producer: %d\n\n", ITEMS_PER_PRODUCER);
    
    for (int i = 0; i < NUM_PRODUCERS; i++) {
        producer_ids[i] = i + 1;
        pthread_create(&producers[i], NULL, producer, &producer_ids[i]);
    }
    
    for (int i = 0; i < NUM_CONSUMERS; i++) {
        consumer_ids[i] = i + 1;
        pthread_create(&consumers[i], NULL, consumer, &consumer_ids[i]);
    }
    
    for (int i = 0; i < NUM_PRODUCERS; i++) {
        pthread_join(producers[i], NULL);
    }
    
    for (int i = 0; i < NUM_CONSUMERS; i++) {
        pthread_join(consumers[i], NULL);
    }
    
    printf("\n=== EXECUTION COMPLETED ===\n");
    printf("Final buffer count: %d (should be 0)\n", shared_buffer.count);
    
    pthread_mutex_destroy(&mutex);
    pthread_cond_destroy(&not_empty);
    pthread_cond_destroy(&not_full);
    
    return 0;
}
