package BinarySearch;

import java.util.Arrays;

/** Standalone tests: run main() in IntelliJ or use the commands in README.md. */
public class BinarySearchTest {
    private static int passed;
    private static int failed;

    public static void main(String[] args) {
        runTest("Null array", () -> assertSearch(null, 5, -1));
        runTest("Empty array", () -> assertSearch(new int[0], 5, -1));
        runTest("Single element found", () -> assertSearch(new int[]{7}, 7, 0));
        runTest("Single element absent", () -> {
            assertSearch(new int[]{7}, 6, -1);
            assertSearch(new int[]{7}, 8, -1);
        });
        runTest("Two-element array", () -> {
            int[] numbers = {2, 8};
            assertSearch(numbers, 2, 0);
            assertSearch(numbers, 8, 1);
            assertSearch(numbers, 5, -1);
        });
        runTest("Middle of an odd-length array",
                () -> assertSearch(new int[]{1, 3, 5, 7, 9}, 5, 2));
        runTest("Middle elements of an even-length array", () -> {
            int[] numbers = {1, 3, 5, 7, 9, 11};
            assertSearch(numbers, 5, 2);
            assertSearch(numbers, 7, 3);
        });
        runTest("First element",
                () -> assertSearch(new int[]{2, 4, 6, 8, 10}, 2, 0));
        runTest("Last element",
                () -> assertSearch(new int[]{2, 4, 6, 8, 10}, 10, 4));
        runTest("Target below the minimum",
                () -> assertSearch(new int[]{2, 4, 6, 8, 10}, 1, -1));
        runTest("Target above the maximum",
                () -> assertSearch(new int[]{2, 4, 6, 8, 10}, 11, -1));
        runTest("Target between existing values",
                () -> assertSearch(new int[]{2, 4, 6, 8, 10}, 7, -1));
        runTest("Negative numbers and zero", () -> {
            int[] numbers = {-20, -10, -3, 0, 4, 12};
            assertSearch(numbers, -10, 1);
            assertSearch(numbers, 0, 3);
            assertSearch(numbers, -5, -1);
        });
        runTest("Duplicates accept any matching index", () -> {
            int[] numbers = {1, 1, 2, 4, 4, 4, 9, 9};
            assertSearch(numbers, 1, 0, 1);
            assertSearch(numbers, 4, 3, 4, 5);
            assertSearch(numbers, 9, 6, 7);
            assertSearch(numbers, 3, -1);
        });
        runTest("All elements equal", () -> {
            int[] numbers = {6, 6, 6, 6};
            assertSearch(numbers, 6, 0, 1, 2, 3);
            assertSearch(numbers, 5, -1);
            assertSearch(numbers, 7, -1);
        });
        runTest("Extreme integer values", () -> {
            int[] numbers = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
            assertSearch(numbers, Integer.MIN_VALUE, 0);
            assertSearch(numbers, Integer.MAX_VALUE, 4);
            assertSearch(numbers, Integer.MIN_VALUE + 1, -1);
            assertSearch(numbers, Integer.MAX_VALUE - 1, -1);
        });
        runTest("Large sorted array", () -> {
            int[] numbers = new int[100_001];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = i * 2;
            }
            assertSearch(numbers, 0, 0);
            assertSearch(numbers, 100_000, 50_000);
            assertSearch(numbers, 199_998, 99_999);
            assertSearch(numbers, 200_000, 100_000);
            assertSearch(numbers, 199_999, -1);
        });
        runTest("Repeated calls are independent", () -> {
            int[] numbers = {3, 6, 9, 12};
            assertSearch(numbers, 12, 3);
            assertSearch(new int[]{-5, -2}, -5, 0);
            assertSearch(null, 12, -1);
            assertSearch(numbers, 8, -1);
            assertSearch(numbers, 3, 0);
            assertSearch(numbers, 12, 3);
        });

        System.out.println("\nResults: " + passed + " passed, " + failed + " failed.");
        if (failed > 0) {
            throw new AssertionError("Some tests failed. Check your binary search implementation.");
        }
    }

    private static void assertSearch(int[] numbers, int target, int... allowedIndices) {
        int[] original = numbers == null ? null : numbers.clone();
        int actual = BinarySearch.search(numbers, target);
        if (!Arrays.equals(numbers, original)) {
            throw new AssertionError("The input array was modified.");
        }
        for (int allowed : allowedIndices) {
            if (actual == allowed) {
                return;
            }
        }
        throw new AssertionError("Target " + target + ": expected an index in "
                + Arrays.toString(allowedIndices) + ", got " + actual);
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
