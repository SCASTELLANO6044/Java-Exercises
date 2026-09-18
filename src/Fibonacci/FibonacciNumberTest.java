package Fibonacci;

/** Standalone tests: run main() in IntelliJ or use the commands in README.md. */
public class FibonacciNumberTest {
    private static int passed;
    private static int failed;

    public static void main(String[] args) {
        runTest("F(0) = 0", () -> assertFibonacci(0, 0L));
        runTest("F(1) = 1", () -> assertFibonacci(1, 1L));
        runTest("F(2) = 1", () -> assertFibonacci(2, 1L));
        runTest("F(3) = 2", () -> assertFibonacci(3, 2L));
        runTest("F(5) = 5", () -> assertFibonacci(5, 5L));
        runTest("F(10) = 55", () -> assertFibonacci(10, 55L));
        runTest("F(20) = 6765", () -> assertFibonacci(20, 6765L));
        runTest("F(46): last result that fits in an int",
                () -> assertFibonacci(46, 1836311903L));
        runTest("F(47): result requires a long",
                () -> assertFibonacci(47, 2971215073L));
        runTest("F(92): largest supported index",
                () -> assertFibonacci(92, 7540113804746346429L));
        runTest("Reject negative index", () -> assertInvalidInput(-1));
        runTest("Reject minimum int", () -> assertInvalidInput(Integer.MIN_VALUE));
        runTest("Reject index 93 (long overflow)", () -> assertInvalidInput(93));
        runTest("Reject maximum int", () -> assertInvalidInput(Integer.MAX_VALUE));
        runTest("Repeated calls are independent", () -> {
            assertFibonacci(10, 55L);
            assertFibonacci(0, 0L);
            assertFibonacci(7, 13L);
            assertFibonacci(1, 1L);
            assertFibonacci(10, 55L);
        });

        System.out.println("\nResults: " + passed + " passed, " + failed + " failed.");
        if (failed > 0) {
            throw new AssertionError("Some tests failed. Check your fibonacci implementation.");
        }
    }

    private static void assertFibonacci(int n, long expected) {
        long actual = FibonacciNumber.fibonacci(n);
        if (actual != expected) {
            throw new AssertionError("F(" + n + "): expected " + expected + ", got " + actual);
        }
    }

    private static void assertInvalidInput(int n) {
        try {
            FibonacciNumber.fibonacci(n);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException for n = " + n);
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
