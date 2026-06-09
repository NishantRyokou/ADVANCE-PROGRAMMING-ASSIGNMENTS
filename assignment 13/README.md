# Dynamic String Buffer

This C program implements a dynamic string buffer that automatically grows as needed, preventing buffer overflows and memory leaks.

## Features

- **Automatic Growth**: The buffer doubles in capacity when needed
- **Memory Safety**: Proper handling of malloc/realloc failures
- **Clean Memory Management**: Destructor function prevents memory leaks
- **Simple API**: Easy to use append and initialization functions

## Structure

- `StringBuffer`: Struct containing `char *data`, `size_t length`, and `size_t capacity`
- `sb_init(size_t initial_capacity)`: Creates and initializes a new StringBuffer
- `sb_append(StringBuffer *sb, const char *str)`: Appends a string, growing the buffer if necessary
- `sb_free(StringBuffer *sb)`: Frees all allocated memory

## Building and Running

**Note**: This project requires a C compiler (GCC, Clang, or MSVC) to build and run.

```bash
# Compile the program (using GCC)
gcc -Wall -Wextra -std=c99 -pedantic -o string_buffer_demo main.c string_buffer.c

# Or using Clang
clang -Wall -Wextra -std=c99 -pedantic -o string_buffer_demo main.c string_buffer.c

# Or using MSVC (cl.exe)
cl /W4 /std:c99 main.c string_buffer.c /Fe:string_buffer_demo.exe

# Run the demonstration
./string_buffer_demo

# Clean up (optional)
rm string_buffer_demo
```

## Demonstration

The program demonstrates:
1. Initial buffer creation with small capacity (8 bytes)
2. Multiple appends that fit within initial capacity
3. Buffer growth when capacity is exceeded (doubles to 16, then 32)
4. Proper memory cleanup

## Memory Management

- Uses `malloc` for initial allocation and `realloc` for growth
- Safely handles allocation failures without corrupting existing data
- Provides `sb_free()` to prevent memory leaks