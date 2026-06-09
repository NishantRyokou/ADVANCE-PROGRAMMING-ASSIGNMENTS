# Makefile for Dynamic String Buffer

CC = gcc
CFLAGS = -Wall -Wextra -std=c99 -pedantic
TARGET = string_buffer_demo
SOURCES = main.c string_buffer.c
HEADERS = string_buffer.h

$(TARGET): $(SOURCES) $(HEADERS)
	$(CC) $(CFLAGS) -o $(TARGET) $(SOURCES)

clean:
	rm -f $(TARGET)

.PHONY: clean