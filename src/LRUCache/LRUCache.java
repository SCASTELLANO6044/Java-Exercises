package LRUCache;

import java.util.LinkedHashMap;
import java.util.Map;

/** A fixed-capacity least recently used cache. Implement the methods below. */
public class LRUCache {

    private LinkedHashMap<Integer, Integer> params;

    /** Initialize an empty cache. You may assume 1 <= capacity <= 3000. */
    public LRUCache(int capacity) {
        this.params = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true){
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            }
        };
    }

    /**
     * Return the stored value, or -1 if key is absent.
     * A successful lookup makes key the most recently used entry.
     * A missing lookup does not change recency.
     * Required average time: O(1).
     */
    public int get(int key) {
        return this.params.getOrDefault(key, -1);
    }

    /**
     * Insert or update key with value, making it the most recently used entry.
     * If inserting exceeds capacity, evict the least recently used key.
     * Updating an existing key must not increase the number of entries.
     * Required average time: O(1).
     */
    public void put(int key, int value) {
        this.params.put(key, value);
    }
}