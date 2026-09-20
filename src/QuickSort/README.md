# Quicksort exercise

`sort(int[] numbers)` in `QuickSort.java` contains a completed solution.
It keeps the base cases and delegates larger inputs to a recursive range helper.
The helper saves the middle element's value as its pivot, swaps elements within
the original array, and then sorts the two remaining ranges.

Quicksort chooses a pivot, partitions the array around it, and sorts the
remaining sections. You may choose your pivot and partitioning scheme.

```java
int[] numbers = {5, 1, 4, 2, 8};
int[] sorted = QuickSort.sort(numbers);
// sorted and numbers should be the same array, containing {1, 2, 4, 5, 8}.
```

## Requirements

- Modify the supplied array in place and return that same array as an `int[]`.
- Keep every original value, including duplicates, in nondecreasing order.
- Return null for null input. For an empty or single-element array, return
  that same array unchanged.
- Handle negative values, zero, and the full Java `int` range.
- Keep calls independent. Sorting an already sorted array should leave it unchanged.
- Implement quicksort yourself without library sorting methods or separate
  arrays for the partitions. Recursive stack space is allowed.
- Ensure every recursive call works on a smaller range, including when many
  elements equal the pivot.
- Target O(n log n) average time. O(n^2) worst-case time is acceptable for this
  exercise. A basic recursive implementation typically uses O(log n) stack
  space with balanced partitions and can use O(n) with very unbalanced ones.

## Run the tests

In IntelliJ, open `QuickSortTest.java` and run `main`.
No JUnit installation or assertion flags are required.

Alternatively, with a JDK on your PATH, run from the project root:

```powershell
javac -d out src/QuickSort/QuickSort.java src/QuickSort/QuickSortTest.java
java -cp out QuickSort.QuickSortTest
```

The runner prints `PASS` or `FAIL` for each of 20 cases and a final count.
Success is `20 passed, 0 failed`; failures produce a nonzero exit code.
Tests check the returned array reference, the original array's contents, duplicate preservation, extreme
values, repeated pivot values, sorted and reverse-sorted inputs, and repeated
calls. Larger inputs are limited to 512 elements to accommodate basic recursive
implementations.

The tests verify results; they do not measure complexity or enforce quicksort.
The test code uses `Arrays.sort` to obtain expected values for a larger case,
but your exercise method should implement the algorithm itself.
If partitioning loops forever, stop the run in IntelliJ or press Ctrl+C in the
terminal. A `StackOverflowError` can indicate ranges that do not shrink or
excessive recursion depth.
