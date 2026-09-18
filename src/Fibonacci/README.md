# Fibonacci number exercise

Implement `fibonacci(int n)` in `FibonacciNumber.java`. The method is deliberately
unfinished; the tests will fail until you implement it. Only this method needs
editing.

Return the number at index `n`, using zero-based indexing:

| n | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| F(n) | 0 | 1 | 1 | 2 | 3 | 5 | 8 | 13 |

For n >= 2, each number is the sum of the two preceding numbers.

Your method should:

- Return a `long` containing F(n), not the whole sequence.
- Accept indices from 0 through 92, inclusive.
- Throw `IllegalArgumentException` for any other index. F(93) exceeds `long`.
- Produce the same result regardless of previous calls.
- Aim for O(n) time and O(1) extra space.

## Run the tests

In IntelliJ, open `FibonacciNumberTest.java` and click the run icon beside `main`.
No JUnit installation or assertion flags are required.

Alternatively, with a JDK available on your PATH, run from the project root:

```powershell
javac -d out src/Fibonacci/FibonacciNumber.java src/Fibonacci/FibonacciNumberTest.java
java -cp out Fibonacci.FibonacciNumberTest
```

The runner prints `PASS` or `FAIL` for each of 15 cases and a final count. Any
failure also causes a nonzero exit code. Success is `15 passed, 0 failed`.

Tests cover base cases, intermediate values, results beyond the `int` range,
the largest supported index, invalid inputs, and repeated calls. Complexity is
a practice target, not automatically measured. A recursive solution that
recalculates the same values can take a very long time on the larger cases;
stop the run in IntelliJ or press Ctrl+C in the terminal if needed.
