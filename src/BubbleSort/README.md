# Bubble sort exercise

`sort(int[] numbers)` in `BubbleSort.java` contains a completed iterative solution.
Each pass moves the largest remaining value to the end of the unsorted section,
then shortens that section for the next pass. The `swap` helper exchanges neighbors.

Bubble sort repeatedly compares adjacent elements and swaps those that are
out of order. Your method must sort the original array in ascending order.

```java
int[] numbers = {5, 1, 4, 2, 8};
BubbleSort.sort(numbers);
// numbers should now contain {1, 2, 4, 5, 8}.
```

## Requirements

- Modify the supplied array in place. The method returns `void`.
- Keep every original value, including duplicates, in nondecreasing order.
- Accept null, empty, and single-element arrays without throwing an exception.
- Handle negative values, zero, and the full Java `int` range.
- Keep calls independent. Sorting an already sorted array should leave it unchanged.
- Implement bubble sort yourself without library sorting methods.
- Target O(n^2) worst-case time and O(1) extra space.
- Optional improvement: stop early if a complete pass makes no swaps, giving
  O(n) time for an already sorted array.

## Run the tests

In IntelliJ, open `BubbleSortTest.java` and run `main`.
No JUnit installation or assertion flags are required.

Alternatively, with a JDK on your PATH, run from the project root:

```powershell
javac -d out src/BubbleSort/BubbleSort.java src/BubbleSort/BubbleSortTest.java
java -cp out BubbleSort.BubbleSortTest
```

The runner prints `PASS` or `FAIL` for each of 18 cases and a final count.
Success is `18 passed, 0 failed`; failures produce a nonzero exit code.
Tests check the contents of the original array, including preservation of
duplicates, boundary values, arrays needing multiple passes, and repeated calls.
The larger case uses 512 elements to keep a correct quadratic algorithm quick.

The tests verify results; they do not measure complexity or enforce the use of
bubble sort. The test code uses `Arrays.sort` to obtain expected values for the
larger case, but your exercise method should implement the algorithm itself.
If your loop does not terminate, stop the run in IntelliJ or press Ctrl+C in the
terminal.
