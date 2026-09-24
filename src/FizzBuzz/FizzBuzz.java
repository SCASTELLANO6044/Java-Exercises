package FizzBuzz;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/** Print one ordered FizzBuzz sequence using four cooperating threads. */
public class FizzBuzz {
    private final int n;
    private final Semaphore fizz;
    private final Semaphore buzz;
    private final Semaphore fizzbuzz;
    private final Semaphore number;

    public FizzBuzz(int n) {
        this.n = n;
        this.fizz = new Semaphore(0);
        this.buzz = new Semaphore(0);
        this.fizzbuzz = new Semaphore(0);
        this.number = new Semaphore(1);
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 != 0) {
                fizz.acquire();
                printFizz.run();
                number.release();
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 3 != 0 && i % 5 == 0) {
                buzz.acquire();
                printBuzz.run();
                number.release();
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) {
                fizzbuzz.acquire();
                printFizzBuzz.run();
                number.release();
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            // The previous callback must finish before dispatching this token.
            number.acquire();
            if (i % 15 == 0) {
                fizzbuzz.release();
            } else if (i % 3 == 0) {
                fizz.release();
            } else if (i % 5 == 0) {
                buzz.release();
            } else {
                printNumber.accept(i);
                number.release();
            }
        }
    }
}
