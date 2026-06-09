#include "string_buffer.h"
#include <stdio.h>

int main() {
    printf("Dynamic String Buffer Demonstration\n");
    printf("===================================\n\n");

    // Initialize with small capacity
    size_t initial_capacity = 8;
    StringBuffer *sb = sb_init(initial_capacity);

    if (sb == NULL) {
        printf("Failed to initialize StringBuffer\n");
        return 1;
    }

    printf("Initial state:\n");
    printf("  Capacity: %zu\n", sb->capacity);
    printf("  Length: %zu\n", sb->length);
    printf("  Content: \"%s\"\n\n", sb->data);

    // First append - should fit in initial capacity
    sb_append(sb, "Hello");
    printf("After appending \"Hello\":\n");
    printf("  Capacity: %zu\n", sb->capacity);
    printf("  Length: %zu\n", sb->length);
    printf("  Content: \"%s\"\n\n", sb->data);

    // Second append - should still fit
    sb_append(sb, " World");
    printf("After appending \" World\":\n");
    printf("  Capacity: %zu\n", sb->capacity);
    printf("  Length: %zu\n", sb->length);
    printf("  Content: \"%s\"\n\n", sb->data);

    // Third append - should trigger first growth (capacity doubles to 16)
    sb_append(sb, " from a dynamic string buffer");
    printf("After appending \" from a dynamic string buffer\":\n");
    printf("  Capacity: %zu\n", sb->capacity);
    printf("  Length: %zu\n", sb->length);
    printf("  Content: \"%s\"\n\n", sb->data);

    // Fourth append - should trigger second growth (capacity doubles to 32)
    sb_append(sb, " that automatically grows as needed!");
    printf("After appending \" that automatically grows as needed!\":\n");
    printf("  Capacity: %zu\n", sb->capacity);
    printf("  Length: %zu\n", sb->length);
    printf("  Content: \"%s\"\n\n", sb->data);

    // Demonstrate that we can append more without issues
    sb_append(sb, " Final addition.");
    printf("After final append:\n");
    printf("  Capacity: %zu\n", sb->capacity);
    printf("  Length: %zu\n", sb->length);
    printf("  Content: \"%s\"\n\n", sb->data);

    // Free all memory
    sb_free(sb);
    printf("Memory freed successfully!\n");

    return 0;
}