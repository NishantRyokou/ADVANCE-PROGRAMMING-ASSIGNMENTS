#ifndef STRING_BUFFER_H
#define STRING_BUFFER_H

#include <stddef.h>

// StringBuffer struct containing data, length, and capacity
typedef struct {
    char *data;
    size_t length;
    size_t capacity;
} StringBuffer;

// Initialize a new StringBuffer with given initial capacity
StringBuffer* sb_init(size_t initial_capacity);

// Append a string to the StringBuffer, growing if necessary
void sb_append(StringBuffer *sb, const char *str);

// Free the StringBuffer and its internal data
void sb_free(StringBuffer *sb);

#endif // STRING_BUFFER_H