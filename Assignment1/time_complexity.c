#include <stdio.h>
#include <time.h>
#include <stdlib.h>

void constant_time(int n) {
    volatile int val = 0;
    val = 42;
}

void linear_time(int n) {
    volatile int val = 0;
    for (int i = 0; i < n; i++) {
        val++;
    }
}

void quadratic_time(int n) {
    volatile int val = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            val++;
        }
    }
}

int main() {
    int sizes_o1_on[] = {100000, 1000000, 10000000, 100000000};
    int num_sizes_o1_on = sizeof(sizes_o1_on) / sizeof(sizes_o1_on[0]);

    printf("--- Constant Time O(1) & Linear Time O(N) Analysis ---\n");
    printf("%-15s | %-15s | %-15s\n", "Input Size (N)", "O(1) Time (s)", "O(N) Time (s)");
    printf("---------------------------------------------------\n");

    for (int i = 0; i < num_sizes_o1_on; i++) {
        int n = sizes_o1_on[i];

        clock_t start = clock();
        constant_time(n);
        clock_t end = clock();
        double time_o1 = (double)(end - start) / CLOCKS_PER_SEC;

        start = clock();
        linear_time(n);
        end = clock();
        double time_on = (double)(end - start) / CLOCKS_PER_SEC;

        printf("%-15d | %-15.6f | %-15.6f\n", n, time_o1, time_on);
    }

    int sizes_on2[] = {1000, 5000, 10000, 20000, 30000};
    int num_sizes_on2 = sizeof(sizes_on2) / sizeof(sizes_on2[0]);

    printf("\n--- Quadratic Time O(N^2) Analysis ---\n");
    printf("%-15s | %-15s\n", "Input Size (N)", "O(N^2) Time (s)");
    printf("---------------------------------------\n");

    for (int i = 0; i < num_sizes_on2; i++) {
        int n = sizes_on2[i];

        clock_t start = clock();
        quadratic_time(n);
        clock_t end = clock();
        double time_on2 = (double)(end - start) / CLOCKS_PER_SEC;

        printf("%-15d | %-15.6f\n", n, time_on2);
    }

    return 0;
}
