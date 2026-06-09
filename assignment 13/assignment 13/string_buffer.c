#include "string_buffer.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

// Initialize a new StringBuffer with given initial capacity
StringBuffer* sb_init(size_t initial_capacity) {
    // Allocate memory for the StringBuffer struct
    StringBuffer *sb = (StringBuffer*)malloc(sizeof(StringBuffer));
    if (sb == NULL) {
        return NULL; // Handle malloc failure
    }

    // Allocate memory for the data buffer
    sb->data = (char*)malloc(initial_capacity * sizeof(char));
    if (sb->data == NULL) {
        free(sb); // Free the struct if data allocation failed
        return NULL;
    }

    // Initialize the buffer as empty
    sb->data[0] = '\0';
    sb->length = 0;
    sb->capacity = initial_capacity;

    return sb;
}

// Append a string to the StringBuffer, growing if necessary
void sb_append(StringBuffer *sb, const char *str) {
    if (sb == NULL || str == NULL) {
        return; // Invalid parameters
    }

    size_t str_len = strlen(str);
    size_t new_length = sb->length + str_len;

    // Check if we need to grow the buffer
    if (new_length >= sb->capacity) {
        // Double the capacity until it's large enough
        size_t new_capacity = sb->capacity;
        while (new_capacity <= new_length) {
            new_capacity *= 2;
        }

        // Reallocate the buffer
        char *new_data = (char*)realloc(sb->data, new_capacity * sizeof(char));
        if (new_data == NULL) {
            // realloc failed, keep the original buffer
            return;
        }

        sb->data = new_data;
        sb->capacity = new_capacity;
    }

    // Append the string
    strcpy(sb->data + sb->length, str);
    sb->length = new_length;
}

// Free the StringBuffer and its internal data
void sb_free(StringBuffer *sb) {
    if (sb != NULL) {
        if (sb->data != NULL) {
            free(sb->data);
        }
        free(sb);
    }
}