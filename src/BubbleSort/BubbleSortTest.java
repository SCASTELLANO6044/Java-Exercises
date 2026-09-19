package BubbleSort;

import java.util.Arrays;

/** Standalone tests: run main() in IntelliJ or use the commands in README.md. */
public class BubbleSortTest {
    private static int passed;
    private static int failed;

    public static void main(String[] args) {
        runTest("Null array is accepted", () -> BubbleSort.sort(null));
        runTest("Empty array", () -> assertSort(new int[0]));
        runTest("Single element", () -> assertSort(new int[]{7}, 7));
        runTest("Two elements already sorted", () -> assertSort(new int[]{2, 8}, 2, 8));
        runTest("Two elements reversed", () -> assertSort(new int[]{8, 2}, 2, 8));
        runTest("Two equal elements", () -> assertSort(new int[]{5, 5}, 5, 5));
        runTest("Already sorted array",
                () -> assertSort(new int[]{1, 2, 3, 4, 5}, 1, 2, 3, 4, 5));
        runTest("Reverse-sorted array",
                () -> assertSort(new int[]{6, 5, 4, 3, 2, 1}, 1, 2, 3, 4, 5, 6));
        runTest("Duplicate values are preserved",
                () -> assertSort(new int[]{4, 2, 4, 1, 2, 4}, 1, 2, 2, 4, 4, 4));
        runTest("All elements equal",
                () -> assertSort(new int[]{6, 6, 6, 6}, 6, 6, 6, 6));
        runTest("Negative values",
                () -> assertSort(new int[]{-3, -10, -1, -7}, -10, -7, -3, -1));
        runTest("Mixed signs and zero",
                () -> assertSort(new int[]{3, 0, -2, 8, -5, 0}, -5, -2, 0, 0, 3, 8));
        runTest("Extreme integer values", () -> assertSort(
                new int[]{Integer.MAX_VALUE, 0, Integer.MIN_VALUE, -1, Integer.MAX_VALUE},
                Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE, Integer.MAX_VALUE));
        runTest("Largest value starts at the front",
                () -> assertSort(new int[]{9, 1, 2, 3, 4}, 1, 2, 3, 4, 9));
        runTest("Smallest value starts at the end",
                () -> assertSort(new int[]{2, 3, 4, 5, 1}, 1, 2, 3, 4, 5));
        runTest("Alternating high and low values",
                () -> assertSort(new int[]{8, 1, 7, 2, 6, 3, 5, 4}, 1, 2, 3, 4, 5, 6, 7, 8));
        runTest("Larger array with many duplicates", () -> {
            int[] numbers = new int[512];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = (i * 37) % 101 - 50;
            }
            int[] expected = numbers.clone();
            // The library is used only as a test oracle, not in the exercise method.
            Arrays.sort(expected);
            assertSort(numbers, expected);
        });
        runTest("Repeated calls and sorting twice", () -> {
            int[] first = {4, 1, 3, 2};
            assertSort(first, 1, 2, 3, 4);
            assertSort(first, 1, 2, 3, 4);
            assertSort(new int[]{9, -1, 0}, -1, 0, 9);
            BubbleSort.sort(null);
            assertSort(new int[0]);
            assertSort(first, 1, 2, 3, 4);
            // Reuse the same array with changed contents to check that it is sorted again.
            first[0] = 10;
            assertSort(first, 2, 3, 4, 10);
        });

        System.out.println("\nResults: " + passed + " passed, " + failed + " failed.");
        if (failed > 0) {
            throw new AssertionError("Some tests failed. Check your bubble sort implementation.");
        }
    }

    private static void assertSort(int[] numbers, int... expected) {
        BubbleSort.sort(numbers);
        if (!Arrays.equals(numbers, expected)) {
            throw new AssertionError("Expected " + Arrays.toString(expected)
                    + ", got " + Arrays.toString(numbers));
        }
    }

    private static void runTest(String name, Runnable test) {
        try {
            test.run();
            passed++;
            System.out.println("PASS: " + name);
        } catch (AssertionError | RuntimeException | StackOverflowError error) {
            failed++;
            System.out.println("FAIL: " + name + " - " + error);
        }
    }
}
