package LRUCache;

/** Standalone tests: run main() in IntelliJ or use the commands in README.md. */
public class LRUCacheTest {
    private static int passed;
    private static int failed;

    public static void main(String[] args) {
//        runTest("Empty cache returns -1", () -> {
//            LRUCache cache = new LRUCache(2);
//            assertGet(cache, 1, -1);
//            assertGet(cache, 0, -1);
//        });
//        runTest("Insert and retrieve a value", () -> {
//            LRUCache cache = new LRUCache(2);
//            cache.put(1, 42);
//            assertGet(cache, 1, 42);
//            assertGet(cache, 2, -1);
//        });
        runTest("Provided LeetCode example", () -> {
            LRUCache cache = new LRUCache(2);
            cache.put(1, 1);
            cache.put(2, 2);
            assertGet(cache, 1, 1);
            cache.put(3, 3);
            assertGet(cache, 2, -1);
            cache.put(4, 4);
            assertGet(cache, 1, -1);
            assertGet(cache, 3, 3);
            assertGet(cache, 4, 4);
        });
        runTest("Capacity one evicts the previous key", () -> {
            LRUCache cache = new LRUCache(1);
            cache.put(1, 10);
            cache.put(2, 20);
            assertGet(cache, 1, -1);
            assertGet(cache, 2, 20);
            cache.put(3, 30);
            assertGet(cache, 2, -1);
            assertGet(cache, 3, 30);
        });
        runTest("Capacity one updates an existing key", () -> {
            LRUCache cache = new LRUCache(1);
            cache.put(1, 10);
            cache.put(1, 99);
            assertGet(cache, 1, 99);
            cache.put(2, 20);
            assertGet(cache, 1, -1);
            assertGet(cache, 2, 20);
        });
        runTest("Successful get refreshes recency", () -> {
            LRUCache cache = new LRUCache(2);
            cache.put(1, 10);
            cache.put(2, 20);
            assertGet(cache, 1, 10);
            cache.put(3, 30);
            assertGet(cache, 2, -1);
            assertGet(cache, 1, 10);
            assertGet(cache, 3, 30);
        });
        runTest("Updating an existing key refreshes recency", () -> {
            LRUCache cache = new LRUCache(2);
            cache.put(1, 10);
            cache.put(2, 20);
            cache.put(1, 11);
            cache.put(3, 30);
            assertGet(cache, 2, -1);
            assertGet(cache, 1, 11);
            assertGet(cache, 3, 30);
        });
        runTest("Putting the same value still refreshes recency", () -> {
            LRUCache cache = new LRUCache(2);
            cache.put(1, 10);
            cache.put(2, 20);
            cache.put(1, 10);
            cache.put(3, 30);
            assertGet(cache, 2, -1);
            assertGet(cache, 1, 10);
        });
        runTest("Missing lookups do not change eviction order", () -> {
            LRUCache cache = new LRUCache(2);
            cache.put(1, 10);
            cache.put(2, 20);
            assertGet(cache, 99, -1);
            assertGet(cache, 99, -1);
            cache.put(3, 30);
            assertGet(cache, 1, -1);
            assertGet(cache, 2, 20);
            assertGet(cache, 3, 30);
        });
        runTest("No eviction when exactly at capacity", () -> {
            LRUCache cache = new LRUCache(3);
            cache.put(1, 10);
            cache.put(2, 20);
            cache.put(3, 30);
            assertGet(cache, 1, 10);
            assertGet(cache, 2, 20);
            assertGet(cache, 3, 30);
        });
        runTest("Updates do not consume extra capacity", () -> {
            LRUCache cache = new LRUCache(2);
            cache.put(1, 10);
            cache.put(1, 11);
            cache.put(1, 12);
            cache.put(2, 20);
            assertGet(cache, 1, 12);
            assertGet(cache, 2, 20);
            cache.put(3, 30);
            assertGet(cache, 1, -1);
            assertGet(cache, 2, 20);
        });
        runTest("Repeated reads do not create duplicate entries", () -> {
            LRUCache cache = new LRUCache(3);
            cache.put(1, 10);
            cache.put(2, 20);
            cache.put(3, 30);
            for (int i = 0; i < 10; i++) {
                assertGet(cache, 1, 10);
            }
            cache.put(4, 40);
            assertGet(cache, 2, -1);
            cache.put(5, 50);
            assertGet(cache, 3, -1);
            assertGet(cache, 1, 10);
            assertGet(cache, 4, 40);
            assertGet(cache, 5, 50);
        });
        runTest("Evicted keys can be inserted again", () -> {
            LRUCache cache = new LRUCache(2);
            cache.put(1, 10);
            cache.put(2, 20);
            cache.put(3, 30);
            cache.put(1, 100);
            assertGet(cache, 2, -1);
            assertGet(cache, 3, 30);
            assertGet(cache, 1, 100);
            cache.put(4, 40);
            assertGet(cache, 3, -1);
            assertGet(cache, 1, 100);
        });
        runTest("Zero is a valid key and value", () -> {
            LRUCache cache = new LRUCache(2);
            cache.put(0, 0);
            cache.put(1, 0);
            assertGet(cache, 0, 0);
            cache.put(2, 2);
            assertGet(cache, 1, -1);
            assertGet(cache, 0, 0);
        });
        runTest("Maximum allowed key and value", () -> {
            LRUCache cache = new LRUCache(1);
            cache.put(10_000, 100_000);
            assertGet(cache, 10_000, 100_000);
            cache.put(10_000, 0);
            assertGet(cache, 10_000, 0);
            cache.put(0, 100_000);
            assertGet(cache, 10_000, -1);
            assertGet(cache, 0, 100_000);
        });
        runTest("Equal values belong to distinct keys", () -> {
            LRUCache cache = new LRUCache(2);
            cache.put(1, 7);
            cache.put(2, 7);
            cache.put(3, 7);
            assertGet(cache, 1, -1);
            assertGet(cache, 2, 7);
            assertGet(cache, 3, 7);
        });
        runTest("Cache instances are independent", () -> {
            LRUCache first = new LRUCache(1);
            first.put(1, 10);
            LRUCache second = new LRUCache(2);
            second.put(1, 100);
            second.put(2, 200);
            assertGet(first, 1, 10);
            first.put(2, 20);
            assertGet(first, 1, -1);
            assertGet(first, 2, 20);
            assertGet(second, 1, 100);
            assertGet(second, 2, 200);
        });
        runTest("Mixed reads and updates maintain recency", () -> {
            LRUCache cache = new LRUCache(3);
            cache.put(1, 10);
            cache.put(2, 20);
            cache.put(3, 30);
            assertGet(cache, 1, 10);
            cache.put(2, 22);
            cache.put(4, 40);
            assertGet(cache, 3, -1);
            assertGet(cache, 1, 10);
            cache.put(5, 50);
            assertGet(cache, 2, -1);
            assertGet(cache, 4, 40);
            assertGet(cache, 1, 10);
            assertGet(cache, 5, 50);
        });
        runTest("Maximum capacity", () -> {
            LRUCache cache = new LRUCache(3000);
            for (int key = 0; key < 3000; key++) {
                cache.put(key, key + 1);
            }
            for (int key = 0; key < 3000; key++) {
                assertGet(cache, key, key + 1);
            }
            cache.put(3000, 3001);
            assertGet(cache, 0, -1);
            assertGet(cache, 1, 2);
            assertGet(cache, 2999, 3000);
            assertGet(cache, 3000, 3001);
        });
        runTest("Long sequence of evictions and reinsertions", () -> {
            LRUCache cache = new LRUCache(3);
            for (int i = 0; i < 10_000; i++) {
                cache.put(i % 7, i);
                assertGet(cache, i % 7, i);
                if (i >= 3) {
                    assertGet(cache, (i - 3) % 7, -1);
                }
            }
            for (int i = 9999; i >= 9997; i--) {
                assertGet(cache, i % 7, i);
            }
        });

        System.out.println("\nResults: " + passed + " passed, " + failed + " failed.");
        if (failed > 0) {
            throw new AssertionError("Some tests failed. Check your LRUCache implementation.");
        }
    }

    private static void assertGet(LRUCache cache, int key, int expected) {
        int actual = cache.get(key);
        if (actual != expected) {
            throw new AssertionError("get(" + key + "): expected " + expected + ", got " + actual);
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
