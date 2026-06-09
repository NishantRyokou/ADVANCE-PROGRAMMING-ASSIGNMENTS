# Assignment 2: Space Complexity Analysis

Write a c program for three different operations as stated in question 1 to analyze the space complexity.

## Implementation Details

The C program implemented in [Assignment2/space_complexity.c](file:///c:/Users/legam/Documents/btech/A.P/Assignment2/space_complexity.c) calculates, allocates, and frees memory to analyze:
1. **Constant Space ($O(1)$)**: Uses a constant amount of stack variables (4 bytes).
2. **Linear Space ($O(N)$)**: Dynamically allocates an array of size $N$ ($4 \times N$ bytes).
3. **Quadratic Space ($O(N^2)$)**: Dynamically allocates a 2D matrix of size $N \times N$ ($4 \times N^2$ bytes).
