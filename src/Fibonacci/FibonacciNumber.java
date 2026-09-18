package Fibonacci;

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
    public static long fibonacci(int n) {
        // TODO: Implement the calculation and input validation here.
        if (n < 2){
            return n;
        }else {
            return fibonacci(n-1) + fibonacci(n-2);
        }
    }
}
