package LinkedList;

/** A node in a singly linked list. */
public class Node {
    public int value;
    public Node next;

    public Node(int value) {
        this(value, null);
    }

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }
}
