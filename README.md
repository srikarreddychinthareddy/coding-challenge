
# Java Solution for Coding Challenge

This repository contains a Java implementation of the two coding assignments:

1. **Assignment 1 – Producer–Consumer Pattern**
2. **Assignment 2 – CSV Data Analysis with Streams**

Both assignments are implemented using core Java (no external frameworks) and include unit tests written with **JUnit 5**.

---

## Project Structure

```text
.
├── assignment1/
│   ├── Buffer.java                 # Thread-safe bounded buffer (wait/notifyAll)
│   ├── Producer.java               # Producer runnable
│   ├── Consumer.java               # Consumer runnable
│   ├── ProducerConsumerDemo.java   # Demo with main()
│   └── ProducerConsumerTest.java   # JUnit 5 tests
├── assignment2/
│   ├── Employee.java               # POJO for employee record
│   ├── DataAnalyzer.java           # Stream-based analysis methods
│   ├── DataAnalysisDemo.java       # Demo with main()
│   ├── DataAnalyzerTest.java       # JUnit 5 tests
│   └── employees.csv               # Sample dataset (~1000 rows)
└── README.md
````

> Note: IDE files (`.idea`, `*.iml`) and build outputs (`out/…`) are not required to run the code.

---

## Assignment 1 – Producer–Consumer

**Goal:** Demonstrate the classic producer–consumer pattern using low-level synchronization.

* `Buffer<T>` is a bounded FIFO queue that uses `synchronized`, `wait()` and `notifyAll()` to coordinate access between threads.
* `Producer` reads integers from a source list and inserts them into the buffer.
* After producing all items, the producer places a **sentinel** value (`null`) into the buffer to signal completion.
* `Consumer` removes items from the buffer and stores them into a destination list until it encounters the sentinel.

### Run the demo (CLI)

From the project root:

```bash
mkdir -p bin
javac -d bin $(find assignment1 -name "*.java" -not -name "*Test.java")
java -cp bin assignment1.ProducerConsumerDemo
```

**Expected output (example):**

```text
Source: [1, 2, 3, 4, 5]
Destination: [1, 2, 3, 4, 5]
```

This shows that all produced items were consumed in order.

---

## Assignment 2 – CSV Data Analysis

**Goal:** Perform simple analytics on a CSV file using Java Streams and functional-style operations.

* `employees.csv` contains fictitious employee data.
* `Employee` represents one row from the dataset.
* `DataAnalyzer` provides static methods to:

    * count employees by team,
    * compute average salary by gender,
    * compute average salary by team,
    * find the **top N** employees by salary,
    * compute average bonus percentage by team.
* `DataAnalysisDemo` reads `employees.csv` and prints a formatted summary using these methods.

### Run the demo (CLI)

From the project root:

```bash
mkdir -p bin
javac -d bin $(find assignment2 -name "*.java" -not -name "*Test.java")
java -cp bin assignment2.DataAnalysisDemo
```

**Example of console output (shape, not exact numbers):**

```text
Count of employees by team:
  Client Services: 106
  Finance: 102
  ...

Average salary by gender:
  Female: $90,0xx.xx
  Male:   $91,1xx.xx
  ...

Average salary by team:
  Engineering: $94,2xx.xx
  Sales:       $92,xxx.xx
  ...

Top 5 employees by salary:
  Katherine  : $149,908.00
  Rose       : $149,903.00
  ...

Average bonus percentage by team:
  Business Development: 10.57%
  Client Services      : 10.49%
  ...
```

Exact values depend on the dataset.

---

## Running Unit Tests (JUnit 5)

The tests use **JUnit 5 (Jupiter)**.

### Option 1 – From the command line

1. Download the JUnit 5 standalone console JAR, e.g.:

   ```text
   junit-platform-console-standalone-1.10.1.jar
   ```

2. Compile **all** source files (including tests):

   ```bash
   mkdir -p bin
   javac -cp junit-platform-console-standalone-1.10.1.jar \
         -d bin $(find assignment1 assignment2 -name "*.java")
   ```

3. Run the tests:

   ```bash
   java -jar junit-platform-console-standalone-1.10.1.jar \
        --class-path bin \
        --scan-class-path
   ```

This will discover and execute:

* `assignment1.ProducerConsumerTest`
* `assignment2.DataAnalyzerTest`

---

## Running in IntelliJ IDEA (Recommended)

1. **Open the project**

    * `File → Open…` and select the project folder (the one containing `assignment1` and `assignment2`).

2. **Add JUnit 5**

    * `File → Project Structure… → Libraries → +`
    * Choose **From Maven…** and enter:

      ```text
      org.junit.jupiter:junit-jupiter:5.10.0
      ```
    * Apply changes.

3. **Run demos**

    * Right-click `ProducerConsumerDemo.java` → **Run 'ProducerConsumerDemo'**.
    * Right-click `DataAnalysisDemo.java` → **Run 'DataAnalysisDemo'**.

4. **Run tests**

    * Right-click `ProducerConsumerTest.java` → **Run 'ProducerConsumerTest'**.
    * Right-click `DataAnalyzerTest.java` → **Run 'DataAnalyzerTest'**.

---

## Notes

* The dataset `employees.csv` can be swapped with any CSV file that has the same column structure; `DataAnalyzer` will still work.
* The producer–consumer implementation is designed for a **single producer** and **single consumer** using a sentinel value (`null`) for termination. Extending it to multiple producers/consumers would require a slightly different termination protocol.
* The code targets **Java 8+** and uses only standard library features plus JUnit 5 for testing.

