#include <stdio.h>
#include <stdlib.h>

void constant_space(int n) {
    volatile int val = 0;
    val = 42;
    printf("%-15lu | ", (unsigned long)sizeof(val));
}

void linear_space(int n) {
    int *arr = (int *)malloc(n * sizeof(int));
    if (arr == NULL) {
        printf("%-15s | ", "Allocation Fail");
        return;
    }
    printf("%-15lu | ", (unsigned long)(n * sizeof(int)));
    free(arr);
}

void quadratic_space(int n) {
    int **matrix = (int **)malloc(n * sizeof(int *));
    if (matrix == NULL) {
        printf("%-15s\n", "Allocation Fail");
        return;
    }
    for (int i = 0; i < n; i++) {
        matrix[i] = (int *)malloc(n * sizeof(int));
        if (matrix[i] == NULL) {
            printf("%-15s\n", "Allocation Fail");
            return;
        }
    }
    printf("%-15lu\n", (unsigned long)(n * n * sizeof(int)));
    for (int i = 0; i < n; i++) {
        free(matrix[i]);
    }
    free(matrix);
}

int main() {
    int sizes[] = {1000, 2000, 4000, 8000};
    int num_sizes = sizeof(sizes) / sizeof(sizes[0]);

    printf("--- Space Complexity Analysis (Bytes Allocated) ---\n");
    printf("%-15s | %-15s | %-15s | %-15s\n", "Input Size (N)", "O(1) Space (B)", "O(N) Space (B)", "O(N^2) Space (B)");
    printf("-------------------------------------------------------------------------\n");

    for (int i = 0; i < num_sizes; i++) {
        int n = sizes[i];
        printf("%-15d | ", n);
        constant_space(n);
        linear_space(n);
        quadratic_space(n);
    }

    return 0;
}
