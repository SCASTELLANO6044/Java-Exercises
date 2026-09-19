# Binary search exercise

`search(int[] numbers, int target)` in `BinarySearch.java` contains a completed
solution. It handles null and empty inputs, then calls the iterative
`binarySearch` helper to narrow the search range.

Given an integer array sorted in ascending order, find the index of a target
value using binary search.

```java
int[] numbers = {2, 5, 8, 12, 16};
// search(numbers, 12) should return 3 (indices start at zero).
// search(numbers, 7) should return -1.
```

## Requirements

- Return the target's zero-based index if it is present.
- Return -1 if it is absent, or if the array is null or empty.
- For duplicate values, any index containing the target is accepted.
- The input is sorted in nondecreasing order. You do not need to sort it or
  check whether it is sorted. Unsorted input is outside the exercise's scope.
- Do not modify the array.
- Handle negative values, zero, and the full Java `int` range.
- Keep calls independent.
- Implement the algorithm yourself without library search methods.
- Aim for O(log n) time and O(1) extra space.

## Run the tests

In IntelliJ, open `BinarySearchTest.java` and run `main`.
No JUnit installation or assertion flags are required.

Alternatively, with a JDK on your PATH, run from the project root:

```powershell
javac -d out src/BinarySearch/BinarySearch.java src/BinarySearch/BinarySearchTest.java
java -cp out BinarySearch.BinarySearchTest
```

The runner prints `PASS` or `FAIL` for each of 18 cases and a final count.
Success is `18 passed, 0 failed`; failures produce a nonzero exit code.
Tests cover null and empty arrays, small arrays, boundary indices, missing
targets, duplicates, negative and extreme values, large arrays, and repeated
calls. Every non-null input is also checked for modifications.

Complexity and use of binary search are practice requirements, not
automatically measured by the tests. If your search loop does not terminate,
stop the run in IntelliJ or press Ctrl+C in the terminal.
