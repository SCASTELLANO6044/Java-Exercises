package LinkedList;

public class LinkedListReversal {

    /**
     * Reverse a finite, acyclic singly linked list and return its new head.
     *
     * Example: 1 -> 2 -> 3 -> null becomes 3 -> 2 -> 1 -> null.
     * An empty list is represented by null and must return null.
     * Reuse the existing nodes by changing their next references;
     * do not create replacement nodes or change their values.
     * Aim for O(n) time and O(1) extra space.
     *
     * @param head the first node, or null for an empty list
     * @return the first node of the reversed list, or null
     */
    public static Node reverse(Node head) {

        Node previous = null;
        Node current = head;

        while(current !=null){
            Node aux = current.next;
            current.next = previous;
            previous = current;
            current = aux;
        }

        return previous;

    }
}
