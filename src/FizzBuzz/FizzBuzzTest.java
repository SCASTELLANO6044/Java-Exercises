package FizzBuzz;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/** Standalone tests: run main() in IntelliJ or see README.md. */
public class FizzBuzzTest {
    private static int passed;
    private static int failed;
    private static final int[] DEFAULT_ORDER = {0, 1, 2, 3};

    public static void main(String[] args) {
        for (int n : new int[]{1, 2, 3, 5, 14, 15, 16, 30, 50}) {
            runTest("Exact sequence for n = " + n, () -> check(n, DEFAULT_ORDER, -1, -1));
        }
        runTest("Every allowed n from 1 through 50", () -> {
            for (int n = 1; n <= 50; n++) check(n, DEFAULT_ORDER, -1, -1);
        });
        runTest("All 24 worker launch orders", () -> {
            for (int a = 0; a < 4; a++) {
                for (int b = 0; b < 4; b++) {
                    for (int c = 0; c < 4; c++) {
                        for (int d = 0; d < 4; d++) {
                            if (a != b && a != c && a != d && b != c && b != d && c != d) {
                                check(16, new int[]{a, b, c, d}, -1, -1);
                            }
                        }
                    }
                }
            }
        });
        runTest("Each worker can arrive late", () -> {
            for (int role = 0; role < 4; role++) check(30, DEFAULT_ORDER, role, -1);
        });
        runTest("Slow callbacks preserve order and never overlap", () -> {
            for (int role = 0; role < 4; role++) check(50, DEFAULT_ORDER, -1, role);
        });
        runTest("Two instances run independently", () -> {
            CountDownLatch go = new CountDownLatch(1);
            try (Run first = new Run(31, -1, 3); Run second = new Run(50, -1, 0)) {
                first.start(DEFAULT_ORDER, go);
                second.start(new int[]{3, 2, 1, 0}, go);
                go.countDown();
                first.finish();
                second.finish();
            }
        });

        System.out.println("\nResults: " + passed + " passed, " + failed + " failed.");
        if (failed > 0) throw new AssertionError("Check your FizzBuzz implementation.");
    }

    private static void check(int n, int[] order, int delayedRole, int slowRole)
            throws InterruptedException {
        CountDownLatch go = new CountDownLatch(1);
        try (Run run = new Run(n, delayedRole, slowRole)) {
            run.start(order, go);
            go.countDown();
            run.finish();
        }
    }

    private static final class Run implements AutoCloseable {
        private final int n;
        private final int delayedRole;
        private final int slowRole;
        private final FizzBuzz fizzBuzz;
        private final List<Thread> workers = new ArrayList<>();
        private final ConcurrentLinkedQueue<String> output = new ConcurrentLinkedQueue<>();
        private final ConcurrentLinkedQueue<Throwable> errors = new ConcurrentLinkedQueue<>();
        private final AtomicBoolean inCallback = new AtomicBoolean();

        Run(int n, int delayedRole, int slowRole) {
            this.n = n;
            this.delayedRole = delayedRole;
            this.slowRole = slowRole;
            this.fizzBuzz = new FizzBuzz(n);
        }

        void start(int[] order, CountDownLatch go) throws InterruptedException {
            CountDownLatch ready = new CountDownLatch(4);
            for (int role : order) {
                Thread worker = new Thread(() -> {
                    try {
                        ready.countDown();
                        go.await();
                        if (role == delayedRole) Thread.sleep(50);
                        Thread owner = Thread.currentThread();
                        Consumer<String> print = token -> record(token, role, owner);
                        switch (role) {
                            case 0: fizzBuzz.fizz(() -> print.accept("fizz")); break;
                            case 1: fizzBuzz.buzz(() -> print.accept("buzz")); break;
                            case 2: fizzBuzz.fizzbuzz(() -> print.accept("fizzbuzz")); break;
                            case 3: fizzBuzz.number(value -> print.accept(Integer.toString(value))); break;
                            default: throw new AssertionError("Unknown worker role");
                        }
                    } catch (Throwable error) {
                        errors.add(error);
                    }
                }, "fizzbuzz-" + role);
                worker.setDaemon(true);
                workers.add(worker);
                worker.start();
            }
            require(ready.await(5, TimeUnit.SECONDS), "Workers did not become ready");
        }

        private void record(String token, int role, Thread owner) {
            boolean entered = false;
            try {
                require(Thread.currentThread() == owner, "Callback ran on the wrong thread");
                entered = inCallback.compareAndSet(false, true);
                require(entered, "Callbacks overlapped instead of completing in sequence");
                if (role == slowRole) Thread.sleep(2);
                output.add(token);
            } catch (InterruptedException error) {
                Thread.currentThread().interrupt();
                AssertionError failure = new AssertionError("Callback interrupted", error);
                errors.add(failure);
                throw failure;
            } catch (RuntimeException | Error error) {
                errors.add(error);
                throw error;
            } finally {
                if (entered) inCallback.set(false);
            }
        }

        void finish() throws InterruptedException {
            long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(5);
            for (Thread worker : workers) {
                long remaining = deadline - System.nanoTime();
                if (remaining > 0) TimeUnit.NANOSECONDS.timedJoin(worker, remaining);
            }
            if (!errors.isEmpty()) throw new AssertionError("Worker failed", errors.peek());
            for (Thread worker : workers) {
                require(!worker.isAlive(), "n = " + n + ": timed out; possible deadlock or missing wake-up");
            }
            List<String> expected = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                if (i % 15 == 0) expected.add("fizzbuzz");
                else if (i % 3 == 0) expected.add("fizz");
                else if (i % 5 == 0) expected.add("buzz");
                else expected.add(Integer.toString(i));
            }
            List<String> actual = new ArrayList<>(output);
            require(expected.equals(actual), "n = " + n + ": expected " + expected + ", got " + actual);
        }

        @Override
        public void close() {
            for (Thread worker : workers) if (worker.isAlive()) worker.interrupt();
            long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(500);
            for (Thread worker : workers) {
                long remaining = deadline - System.nanoTime();
                if (remaining <= 0) break;
                try {
                    TimeUnit.NANOSECONDS.timedJoin(worker, remaining);
                } catch (InterruptedException error) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void runTest(String name, CheckedTest test) {
        try {
            test.run();
            passed++;
            System.out.println("PASS: " + name);
        } catch (Exception | AssertionError error) {
            failed++;
            System.out.println("FAIL: " + name + " - " + error);
        }
    }

    @FunctionalInterface
    private interface CheckedTest {
        void run() throws Exception;
    }
}
