# DAA Assignment 2 — Data Structures

**Student:** Akmaral Zhumabay  
**Group:** SE-2517  
**Course:** Design and Analysis of Algorithms  
**GitHub Repository:** https://github.com/AkmaralZhumabay/DAA_Assignment2_Akmaral_Zhumabay_SE2517

## Overview

This project implements three data structures from scratch:

- `DynamicArray`
- `MyLinkedList`
- `MinHeap`

The project measures their performance using four workloads and records running time, steps, moves, and comparisons.

## Project Structure

```text
src/
├── main/java/daa/
│   ├── ds/
│   │   ├── IntList.java
│   │   ├── DynamicArray.java
│   │   ├── MyLinkedList.java
│   │   └── MinHeap.java
│   ├── metrics/
│   │   ├── Metrics.java
│   │   ├── Result.java
│   │   └── CsvWriter.java
│   ├── bench/
│   │   └── Benchmark.java
│   └── Main.java
└── test/java/daa/ds/
    ├── DynamicArrayTest.java
    ├── MyLinkedListTest.java
    └── MinHeapTest.java

results/
├── results.csv
└── plots/
    ├── w1_random_access.png
    ├── w2_search.png
    ├── w3_insert_remove.png
    └── w4_priority_processing.png

plot_results.py
REPORT.md
README.md
pom.xml
```

## Requirements

- Java 21
- Maven
- JUnit 5
- Python 3 with `pandas` and `matplotlib` for plot generation

## Build

From the project root:

```bash
mvn clean compile
```

## Run Tests

```bash
mvn test
```

The test suite checks:

- normal operations
- empty structures
- one-element structures
- duplicate values
- first and last indices
- invalid indices
- heap property after insertion and extraction
- non-decreasing MinHeap output

## Run Benchmark

Run `daa.Main` from IntelliJ.

The benchmark uses:

```text
n = 100, 1,000, 10,000, 100,000
Random seed = 42
Measured runs = 5
Warm-up runs = 1
```

It generates:

```text
results/results.csv
```

with columns:

```text
workload,variant,structure,n,time_ms,steps,moves,comparisons
```

## Generate Plots

From the project root run:

```bash
python plot_results.py
```

The generated PNG files are saved in:

```text
results/plots/
```

## Workloads

- **W1 — Random Access:** 10,000 random `get(index)` operations.
- **W2 — Search:** 1,000 `contains(x)` queries, half present and half absent.
- **W3 — Insert & Remove:** 1,000 insertions and 1,000 removals at the head and middle.
- **W4 — Priority Processing:** insert n values into MinHeap and extract all values in non-decreasing order.

## Git Workflow

Development uses:

- `main`
- `feature/metrics`
- `feature/array`
- `feature/list`
- `feature/heap`

The final working version is tagged:

```text
v1.0
```

## Report

See `REPORT.md` for complexity analysis, loop invariant proofs, benchmark plots, and performance discussion.