package H2O;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/** Standalone concurrency tests. Run main() in IntelliJ or see README.md. */
public class H2OTest {
    private static int passed;
    private static int failed;
    private static final long TIMEOUT_SECONDS = 5;

    public static void main(String[] args) {
        runTest("Example: HOH", () -> check(new H2O(), "HOH"));
        runTest("Example: OOHHHH", () -> check(new H2O(), "OOHHHH"));
        runTest("Lone hydrogen waits", () -> checkWaiting("H", "OH"));
        runTest("Lone oxygen waits", () -> checkWaiting("O", "HH"));
        runTest("Two hydrogens wait for oxygen", () -> checkWaiting("HH", "O"));
        runTest("Hydrogen and oxygen wait for another hydrogen",
                () -> checkWaiting("HO", "H"));
        runTest("Maximum input, hydrogens launched first",
                () -> check(new H2O(), repeat("H", 40) + repeat("O", 20)));
        runTest("Maximum input, oxygens launched first",
                () -> check(new H2O(), repeat("O", 20) + repeat("H", 40)));
        runTest("Maximum input, alternating molecules",
                () -> check(new H2O(), repeat("HOH", 20)));
        runTest("Twenty shuffled launch schedules", () -> {
            Random random = new Random(42);
            for (int round = 0; round < 20; round++) {
                List<Character> atoms = new ArrayList<>();
                for (char atom : repeat("HHO", 1 + round).toCharArray()) {
                    atoms.add(atom);
                }
                Collections.shuffle(atoms, random);
                StringBuilder water = new StringBuilder();
                for (char atom : atoms) water.append(atom);
                check(new H2O(), water.toString());
            }
        });
        runTest("Same instance supports successive batches", () -> {
            H2O water = new H2O();
            for (int i = 0; i < 5; i++) check(water, "OHHHOH");
        });
        runTest("Separate instances cannot share atoms", () -> {
            try (Batch first = new Batch(new H2O()); Batch second = new Batch(new H2O())) {
                first.start("HH");
                second.start("O");
                first.assertWaiting();
                second.assertWaiting();
                first.start("O");
                second.start("HH");
                first.finish();
                second.finish();
            }
        });
        runTest("Next molecule waits for all previous callbacks to finish",
                H2OTest::checkSlowCallback);

        System.out.println("\nResults: " + passed + " passed, " + failed + " failed.");
        if (failed > 0) throw new AssertionError("Check your H2O synchronization.");
    }

    private static void check(H2O water, String atoms) throws InterruptedException {
        try (Batch batch = new Batch(water)) {
            batch.start(atoms);
            batch.finish();
        }
    }

    private static void checkWaiting(String prefix, String remainder) throws InterruptedException {
        try (Batch batch = new Batch(new H2O())) {
            batch.start(prefix);
            batch.assertWaiting();
            batch.start(remainder);
            batch.finish();
        }
    }

    private static void checkSlowCallback() throws InterruptedException {
        CountDownLatch firstEntered = new CountDownLatch(1);
        CountDownLatch allowFirstReturn = new CountDownLatch(1);
        AtomicInteger started = new AtomicInteger();
        AtomicInteger completed = new AtomicInteger();
        Object observationLock = new Object();
        try (Batch batch = new Batch(new H2O(), () -> {
            int index;
            synchronized (observationLock) {
                index = started.getAndIncrement();
                require(completed.get() >= (index / 3) * 3,
                        "A new molecule started while a previous callback was unfinished");
            }
            if (index == 0) {
                firstEntered.countDown();
                try {
                    await(allowFirstReturn, "Timed out waiting to unblock the first callback");
                } catch (InterruptedException error) {
                    Thread.currentThread().interrupt();
                    throw new AssertionError("Callback interrupted during cleanup", error);
                }
            }
        }, () -> {
            synchronized (observationLock) {
                completed.incrementAndGet();
            }
        })) {
            try {
                batch.start("HHO");
                await(firstEntered, "No callback started for the first complete molecule");
                batch.start("OOHHHH");
                Thread.sleep(150);
                batch.assertNoWorkerErrors();
            } finally {
                allowFirstReturn.countDown();
            }
            batch.finish();
        }
    }

    private static final class Batch implements AutoCloseable {
        private final H2O water;
        private final List<Thread> workers = new ArrayList<>();
        private final ConcurrentLinkedQueue<Throwable> errors = new ConcurrentLinkedQueue<>();
        private final StringBuffer output = new StringBuffer();
        private final CountDownLatch anyRelease = new CountDownLatch(1);
        private final Runnable beforeRelease;
        private final Runnable afterRelease;

        Batch(H2O water) {
            this(water, () -> {}, () -> {});
        }

        Batch(H2O water, Runnable beforeRelease, Runnable afterRelease) {
            this.water = water;
            this.beforeRelease = beforeRelease;
            this.afterRelease = afterRelease;
        }

        void start(String atoms) throws InterruptedException {
            CountDownLatch ready = new CountDownLatch(atoms.length());
            CountDownLatch go = new CountDownLatch(1);
            CountDownLatch entered = new CountDownLatch(atoms.length());
            for (char atom : atoms.toCharArray()) {
                Thread worker = new Thread(() -> {
                    try {
                        ready.countDown();
                        go.await();
                        Thread owner = Thread.currentThread();
                        AtomicInteger calls = new AtomicInteger();
                        Runnable release = () -> {
                            try {
                                require(Thread.currentThread() == owner,
                                        "Callback must run on its calling atom thread");
                                require(calls.incrementAndGet() == 1, "Callback called more than once");
                                anyRelease.countDown();
                                beforeRelease.run();
                                output.append(atom);
                                afterRelease.run();
                            } catch (Throwable error) {
                                errors.add(error);
                                throw error;
                            }
                        };
                        entered.countDown();
                        if (atom == 'H') water.hydrogen(release);
                        else water.oxygen(release);
                        require(calls.get() == 1, "Method returned without calling its callback");
                    } catch (Throwable error) {
                        errors.add(error);
                    }
                }, "atom-" + atom + "-" + workers.size());
                worker.setDaemon(true);
                workers.add(worker);
                worker.start();
            }
            try {
                await(ready, "Workers did not become ready");
            } finally {
                go.countDown();
            }
            await(entered, "Workers did not enter their atom methods");
        }

        void assertWaiting() throws InterruptedException {
            require(!anyRelease.await(150, TimeUnit.MILLISECONDS),
                    "An atom was released before a complete molecule had arrived");
            assertNoWorkerErrors();
        }

        void finish() throws InterruptedException {
            long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(TIMEOUT_SECONDS);
            for (Thread worker : workers) {
                long remaining = deadline - System.nanoTime();
                if (remaining > 0) TimeUnit.NANOSECONDS.timedJoin(worker, remaining);
            }
            assertNoWorkerErrors();
            for (Thread worker : workers) {
                require(!worker.isAlive(), "Timed out: possible deadlock or a missing wake-up");
            }
            String actual = output.toString();
            require(actual.length() == workers.size(),
                    "Expected " + workers.size() + " callbacks, got: " + actual);
            for (int i = 0; i < actual.length(); i += 3) {
                String molecule = actual.substring(i, i + 3);
                int hydrogens = 0;
                for (char atom : molecule.toCharArray()) if (atom == 'H') hydrogens++;
                require(hydrogens == 2, "Invalid molecule " + molecule + " in output " + actual);
            }
        }

        void assertNoWorkerErrors() {
            Throwable error = errors.peek();
            if (error != null) throw new AssertionError("Worker failed: " + error, error);
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

    private static String repeat(String value, int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) result.append(value);
        return result.toString();
    }

    private static void await(CountDownLatch latch, String message) throws InterruptedException {
        require(latch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS), message);
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
