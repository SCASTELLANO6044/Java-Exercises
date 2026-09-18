package LinkedList;

/** Standalone tests: run main() in IntelliJ or use the commands in README.md. */
public class LinkedListReversalTest {
    private static int passed;
    private static int failed;

    public static void main(String[] args) {
        runTest("Empty list", () -> checkReversal());
        runTest("Single node", () -> checkReversal(42));
        runTest("Two nodes", () -> checkReversal(1, 2));
        runTest("Odd-length list", () -> checkReversal(1, 2, 3, 4, 5));
        runTest("Even-length list", () -> checkReversal(10, 20, 30, 40));
        runTest("Duplicate values", () -> checkReversal(7, 7, 2, 7));
        runTest("All values equal", () -> checkReversal(5, 5, 5, 5));
        runTest("Negative, zero, and extreme values",
                () -> checkReversal(Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE));
        runTest("Long list", () -> {
            int[] values = new int[10_000];
            for (int i = 0; i < values.length; i++) {
                values[i] = i;
            }
            checkReversal(values);
        });
        runTest("Reversing twice restores the original list", () -> {
            int[] values = {4, 8, 15, 16, 23, 42};
            Node[] nodes = createList(values);
            Node reversed = LinkedListReversal.reverse(nodes[0]);
            assertList(reversed, nodes, values, true);
            Node restored = LinkedListReversal.reverse(reversed);
            assertList(restored, nodes, values, false);
        });

        System.out.println("\nResults: " + passed + " passed, " + failed + " failed.");
        if (failed > 0) {
            throw new AssertionError("Some tests failed. Check your reverse implementation.");
        }
    }

    private static void checkReversal(int... values) {
        Node[] nodes = createList(values);
        Node head = nodes.length == 0 ? null : nodes[0];
        Node actual = LinkedListReversal.reverse(head);
        assertList(actual, nodes, values, true);
    }

    private static Node[] createList(int[] values) {
        Node[] nodes = new Node[values.length];
        for (int i = 0; i < values.length; i++) {
            nodes[i] = new Node(values[i]);
            if (i > 0) {
                nodes[i - 1].next = nodes[i];
            }
        }
        return nodes;
    }

    private static void assertList(Node actual, Node[] nodes, int[] values, boolean reversed) {
        // Visit only the expected number of nodes so a returned cycle cannot hang the tests.
        for (int position = 0; position < nodes.length; position++) {
            int index = reversed ? nodes.length - 1 - position : position;
            if (actual != nodes[index]) {
                throw new AssertionError("Position " + position
                        + ": expected original node at index " + index
                        + ". Check node order and reuse the existing nodes.");
            }
            if (actual.value != values[index]) {
                throw new AssertionError("Original node at index " + index
                        + " changed value: expected " + values[index] + ", got " + actual.value);
            }
            actual = actual.next;
        }
        if (actual != null) {
            throw new AssertionError("Expected null after the last node; found an extra node or cycle.");
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
