package Fibonacci;

import java.util.HashMap;
import java.util.Map;

public class FibonacciNumber {

    /**
     * Calculate the nth Fibonacci number using zero-based indexing.
     * F(0) = 0, F(1) = 1, and F(n) = F(n - 1) + F(n - 2) for n >= 2.
     *
     * Examples: fibonacci(2) returns 1; fibonacci(7) returns 13.
     * Accept n from 0 through 92, inclusive, so the result fits in a long.
     * Aim for O(n) time and O(1) extra space.
     *
     * @param n the index in the Fibonacci sequence
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if n is negative or greater than 92
     */

    public static long mapFibonacci(int n) {
        if (n < 0 || n > 92) {
            throw new IllegalArgumentException("n must be between 0 and 92, inclusive.");
        }

        Map<Integer, Long> fib = new HashMap<>();
        fib.put(0, 0L);
        fib.put(1, 1L);

        for (int i = 2; i <= n; i++) {
            fib.put(i, fib.get(i - 1) + fib.get(i - 2));
        }
        return fib.get(n);
    }

    public static long fibonacci(int n) {
        return mapFibonacci(n);
    }
}
