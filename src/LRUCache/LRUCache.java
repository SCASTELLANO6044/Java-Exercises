package LRUCache;

import java.util.HashMap;
import java.util.Map;

class Node {
    int key;
    int value;
    Node prev;
    Node next;

    public Node(int key, int value){
        this.key=key;
        this.value=value;
        this.prev=null;
        this.next=null;

    }
}

/** A fixed-capacity least recently used cache. Implement the methods below. */
public class LRUCache {

    private Map<Integer, Node> cache;
    private final int capacity;
    private Node oldest;
    private Node latest;

    /** Initialize an empty cache. You may assume 1 <= capacity <= 3000. */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.oldest = new Node(0,0);
        this.latest = new Node(0,0);
        this.oldest.next = this.latest;
        this.latest.prev = this.oldest;
    }

    /**
     * Return the stored value, or -1 if key is absent.
     * A successful lookup makes key the most recently used entry.
     * A missing lookup does not change recency.
     * Required average time: O(1).
     */
    public int get(int key) {
        if (cache.containsKey(key)){
            Node node = cache.get(key);
            remove(node);
            insert(node);
            return node.value;
        }
        return -1;
    }

    private void insert (Node node){
        Node prev = latest.prev;
        Node next = latest;
        prev.next = next.prev = node;
        node.next = next;
        node.prev = prev;
    }


    private void remove(Node node){
        Node prev = node.prev;
        Node next = node.next;
        prev.next=next;
        next.prev=prev;
    }

    /**
     * Insert or update key with value, making it the most recently used entry.
     * If inserting exceeds capacity, evict the least recently used key.
     * Updating an existing key must not increase the number of entries.
     * Required average time: O(1).
     */
    public void put(int key, int value) {
        if (cache.containsKey(key)){
            remove(cache.get(key));
        }
        Node newNode = new Node(key, value);
        cache.put(key, newNode);
        insert(newNode);

        if (cache.size()>this.capacity){
            Node lru = oldest.next;
            cache.remove(lru.key);
        }
    }
}


/*
Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.

Implement the LRUCache class:

LRUCache(int capacity) Initialize the LRU cache with positive size capacity.

int get(int key) Return the value of the key if the key exists, otherwise return -1.

void put(int key, int value) Update the value of the key if the key exists.
Otherwise, add the key-value pair to the cache.
If the number of keys exceeds the capacity from this operation, evict the least recently used key.
The functions get and put must each run in O(1) average time complexity.
 */