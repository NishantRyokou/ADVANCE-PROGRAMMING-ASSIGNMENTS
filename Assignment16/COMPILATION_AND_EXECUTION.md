# Compilation and Execution Guide - Assignment 16

## System Requirements

- **Operating System**: Linux, macOS, or Windows with WSL/MinGW
- **Compiler**: GCC with POSIX thread support
- **Libraries**: POSIX Threads (pthread), Semaphores (sem)

## Installation

### Linux (Ubuntu/Debian)
```bash
sudo apt-get update
sudo apt-get install build-essential
```

### macOS
```bash
# Install Xcode Command Line Tools
xcode-select --install
```

### Windows (Option 1: WSL - Windows Subsystem for Linux)
```bash
# Install WSL2
wsl --install

# Inside WSL, install build tools
sudo apt-get update
sudo apt-get install build-essential
```

### Windows (Option 2: MinGW)
1. Download MinGW from: https://www.mingw-w64.org/
2. Install to C:\MinGW
3. Add C:\MinGW\bin to PATH environment variable

## Compilation

### Single Program Compilation

**Producer-Consumer (Semaphore)**:
```bash
gcc -pthread -o producer_consumer_semaphore producer_consumer_semaphore.c
```

**Producer-Consumer (Condition Variable)**:
```bash
gcc -pthread -o producer_consumer_condition_variable producer_consumer_condition_variable.c
```

**Limited Resource Access**:
```bash
gcc -pthread -o limited_resource_access limited_resource_access.c
```

**Thread Scheduling Simulation**:
```bash
gcc -pthread -o thread_scheduling_simulation thread_scheduling_simulation.c
```

### Batch Compilation (Linux/macOS)

Create a file named `compile.sh`:
```bash
#!/bin/bash

echo "Compiling Assignment 16 programs..."

gcc -pthread -o producer_consumer_semaphore producer_consumer_semaphore.c
if [ $? -eq 0 ]; then
    echo "✓ producer_consumer_semaphore compiled successfully"
else
    echo "✗ Failed to compile producer_consumer_semaphore"
    exit 1
fi

gcc -pthread -o producer_consumer_condition_variable producer_consumer_condition_variable.c
if [ $? -eq 0 ]; then
    echo "✓ producer_consumer_condition_variable compiled successfully"
else
    echo "✗ Failed to compile producer_consumer_condition_variable"
    exit 1
fi

gcc -pthread -o limited_resource_access limited_resource_access.c
if [ $? -eq 0 ]; then
    echo "✓ limited_resource_access compiled successfully"
else
    echo "✗ Failed to compile limited_resource_access"
    exit 1
fi

gcc -pthread -o thread_scheduling_simulation thread_scheduling_simulation.c
if [ $? -eq 0 ]; then
    echo "✓ thread_scheduling_simulation compiled successfully"
else
    echo "✗ Failed to compile thread_scheduling_simulation"
    exit 1
fi

echo ""
echo "All programs compiled successfully!"
echo ""
echo "Run programs with:"
echo "  ./producer_consumer_semaphore"
echo "  ./producer_consumer_condition_variable"
echo "  ./limited_resource_access"
echo "  ./thread_scheduling_simulation"
```

Make executable and run:
```bash
chmod +x compile.sh
./compile.sh
```

### Batch Compilation (Windows PowerShell)

Create a file named `compile.ps1`:
```powershell
Write-Host "Compiling Assignment 16 programs..."

$programs = @(
    @{name="producer_consumer_semaphore"; file="producer_consumer_semaphore.c"},
    @{name="producer_consumer_condition_variable"; file="producer_consumer_condition_variable.c"},
    @{name="limited_resource_access"; file="limited_resource_access.c"},
    @{name="thread_scheduling_simulation"; file="thread_scheduling_simulation.c"}
)

foreach ($prog in $programs) {
    Write-Host "Compiling $($prog.name)..."
    gcc -pthread -o $prog.name $prog.file
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ $($prog.name) compiled successfully" -ForegroundColor Green
    } else {
        Write-Host "✗ Failed to compile $($prog.name)" -ForegroundColor Red
        exit 1
    }
}

Write-Host ""
Write-Host "All programs compiled successfully!" -ForegroundColor Green
Write-Host ""
Write-Host "Run programs with:"
Write-Host "  .\producer_consumer_semaphore.exe"
Write-Host "  .\producer_consumer_condition_variable.exe"
Write-Host "  .\limited_resource_access.exe"
Write-Host "  .\thread_scheduling_simulation.exe"
```

Run with:
```powershell
powershell -ExecutionPolicy Bypass -File compile.ps1
```

## Compilation Flags Explained

| Flag | Purpose |
|------|---------|
| `-pthread` | Link with POSIX thread library |
| `-o <name>` | Output executable name |
| `-Wall` | Enable all warnings |
| `-Wextra` | Enable extra warnings |
| `-g` | Include debug symbols |
| `-O2` | Optimization level 2 |
| `-fsanitize=thread` | Enable thread sanitizer (race detection) |

### Recommended Compilation with Warnings

```bash
gcc -pthread -Wall -Wextra -g -o producer_consumer_semaphore producer_consumer_semaphore.c
```

### Compilation with Thread Sanitizer (Race Detection)

```bash
gcc -pthread -fsanitize=thread -g -o producer_consumer_semaphore producer_consumer_semaphore.c
```

## Execution

### Linux/macOS

```bash
./producer_consumer_semaphore
./producer_consumer_condition_variable
./limited_resource_access
./thread_scheduling_simulation
```

### Windows (MinGW)

```cmd
producer_consumer_semaphore.exe
producer_consumer_condition_variable.exe
limited_resource_access.exe
thread_scheduling_simulation.exe
```

### Windows (WSL)

```bash
./producer_consumer_semaphore
./producer_consumer_condition_variable
./limited_resource_access
./thread_scheduling_simulation
```

## Running All Programs

### Linux/macOS Script

Create `run_all.sh`:
```bash
#!/bin/bash

echo "Running Assignment 16 Programs"
echo "=============================="
echo ""

echo "1. Producer-Consumer (Semaphore)"
echo "================================"
./producer_consumer_semaphore
echo ""

echo "2. Producer-Consumer (Condition Variable)"
echo "=========================================="
./producer_consumer_condition_variable
echo ""

echo "3. Limited Resource Access"
echo "=========================="
./limited_resource_access
echo ""

echo "4. Thread Scheduling Simulation"
echo "==============================="
./thread_scheduling_simulation
echo ""

echo "All programs completed!"
```

Make executable and run:
```bash
chmod +x run_all.sh
./run_all.sh
```

### Windows PowerShell Script

Create `run_all.ps1`:
```powershell
Write-Host "Running Assignment 16 Programs" -ForegroundColor Cyan
Write-Host "==============================" -ForegroundColor Cyan
Write-Host ""

Write-Host "1. Producer-Consumer (Semaphore)" -ForegroundColor Yellow
Write-Host "================================" -ForegroundColor Yellow
.\producer_consumer_semaphore.exe
Write-Host ""

Write-Host "2. Producer-Consumer (Condition Variable)" -ForegroundColor Yellow
Write-Host "==========================================" -ForegroundColor Yellow
.\producer_consumer_condition_variable.exe
Write-Host ""

Write-Host "3. Limited Resource Access" -ForegroundColor Yellow
Write-Host "==========================" -ForegroundColor Yellow
.\limited_resource_access.exe
Write-Host ""

Write-Host "4. Thread Scheduling Simulation" -ForegroundColor Yellow
Write-Host "===============================" -ForegroundColor Yellow
.\thread_scheduling_simulation.exe
Write-Host ""

Write-Host "All programs completed!" -ForegroundColor Green
```

Run with:
```powershell
powershell -ExecutionPolicy Bypass -File run_all.ps1
```

## Troubleshooting

### Error: "gcc: command not found"
**Solution**: Install GCC or add it to PATH
- Linux: `sudo apt-get install build-essential`
- macOS: `xcode-select --install`
- Windows: Install MinGW or use WSL

### Error: "undefined reference to `pthread_create'"
**Solution**: Add `-pthread` flag to compilation
```bash
gcc -pthread -o program program.c
```

### Error: "undefined reference to `sem_init'"
**Solution**: Link with semaphore library (usually included with pthread)
```bash
gcc -pthread -o program program.c
```

### Program hangs
**Possible causes**:
- Deadlock in synchronization
- Infinite loop in thread
- Missing signal/broadcast

**Solution**:
- Add timeout: `Ctrl+C` to interrupt
- Check synchronization logic
- Use thread sanitizer to detect issues

### Segmentation fault
**Possible causes**:
- Uninitialized semaphore/condition variable
- Accessing freed memory
- Buffer overflow

**Solution**:
- Verify all synchronization primitives are initialized
- Check array bounds
- Use valgrind: `valgrind ./program`

## Performance Testing

### Measure Execution Time

**Linux/macOS**:
```bash
time ./producer_consumer_semaphore
```

**Windows PowerShell**:
```powershell
Measure-Command { .\producer_consumer_semaphore.exe }
```

### Thread Sanitizer (Race Detection)

```bash
gcc -pthread -fsanitize=thread -g -o program program.c
./program
```

This will detect:
- Data races
- Synchronization errors
- Deadlocks (sometimes)

### Valgrind (Memory Checking)

```bash
valgrind --leak-check=full ./program
```

This will detect:
- Memory leaks
- Invalid memory access
- Use-after-free errors

## Expected Output

### Producer-Consumer (Semaphore)
```
=== PRODUCER-CONSUMER SYSTEM (SEMAPHORE-BASED) ===
Buffer Size: 5
Producers: 2 | Consumers: 2
Items per Producer: 5

[PRODUCER 1] Produced item: 101 | Buffer count: 1
[CONSUMER 1] Consumed item: 101 | Buffer count: 0
...
=== EXECUTION COMPLETED ===
Final buffer count: 0 (should be 0)
```

### Limited Resource Access
```
=== LIMITED RESOURCE ACCESS SYSTEM ===
Total Threads: 8
Available Resources: 3
Work Duration per Thread: 2 seconds

[THREAD 1] Started, waiting for resource access...
[THREAD 1] Acquired resource 3 (waited 0 seconds)
...
=== EXECUTION COMPLETED ===
All threads have finished accessing resources
```

## Summary

**Compilation**:
```bash
gcc -pthread -o <executable> <source.c>
```

**Execution**:
```bash
./<executable>
```

**With debugging**:
```bash
gcc -pthread -g -fsanitize=thread -o <executable> <source.c>
./<executable>
```

All programs should compile without errors and run successfully on any POSIX-compliant system with pthread support.
