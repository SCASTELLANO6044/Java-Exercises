# LRU cache exercise

Implement the constructor, `get`, and `put` in `LRUCache.java`, following the
[LeetCode LRU Cache problem](https://leetcode.com/problems/lru-cache/).
The methods are deliberately unfinished; tests will fail until you implement
them. Add fields, helper methods, or supporting classes as needed.

## Required behavior

- `LRUCache(int capacity)` creates an empty cache with the given maximum number
  of entries.
- `get(int key)` returns its stored value or -1 when absent. A successful read
  makes that key most recently used. A missing read does not change recency.
- `put(int key, int value)` inserts or updates a key and makes it most recently
  used, even when the value is unchanged.
- Updating a key does not consume another slot.
- If an insertion exceeds capacity, evict the least recently used key.
- Each cache instance must maintain its own entries and recency.
- Both `get` and `put` must run in O(1) average time. Target O(capacity) storage.

Assume all inputs satisfy the supplied constraints; extra argument validation
is not required:

- 1 <= capacity <= 3000
- 0 <= key <= 10,000
- 0 <= value <= 100,000
- At most 200,000 calls to `get` and `put` per instance

## Example

```java
LRUCache cache = new LRUCache(2);
cache.put(1, 1);
cache.put(2, 2);
cache.get(1);    // 1; key 1 becomes most recently used
cache.put(3, 3); // evicts key 2
cache.get(2);    // -1
cache.put(4, 4); // evicts key 1
cache.get(1);    // -1
cache.get(3);    // 3
cache.get(4);    // 4
```

## Run the tests

In IntelliJ, open `LRUCacheTest.java` and run `main`.
No JUnit installation or assertion flags are required.

Alternatively, with a JDK on your PATH, run from the project root:

```powershell
javac -d out src/LRUCache/*.java
java -cp out LRUCache.LRUCacheTest
```

The runner prints `PASS` or `FAIL` for each of 20 cases and a final count.
Success is `20 passed, 0 failed`; failures produce a nonzero exit code.
Tests cover the supplied example, capacity one, recency changes, repeated
updates and reads, missing keys, reinsertion, independent instances, boundary
values, maximum capacity, and a long operation sequence.

Assertions use `get`, so successful assertions also refresh recency. Expected
evictions account for that. The tests verify behavior but do not prove O(1)
complexity; review the operations used by your implementation separately.

For submission to LeetCode, omit the `package LRUCache;` declaration and submit
the implementation without the test runner. Keep any helper classes within
the submitted code.
