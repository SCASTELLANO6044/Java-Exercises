package DFS;

import java.util.List;

/** Standalone tests: run main() in IntelliJ or use the commands in README.md. */
public class DepthFirstSearchTest {
    private static int passed;
    private static int failed;

    public static void main(String[] args) {
        runTest("Null endpoints", () -> {
            Node a = new Node(1);
            assertPath(null, a);
            assertPath(a, null);
            assertPath(null, null);
        });
        runTest("Start equals goal", () -> {
            Node a = new Node(1);
            a.connectTo(new Node(2));
            assertPath(a, a, a);
        });
        runTest("Direct edge", () -> {
            Node[] n = nodes(1, 2);
            n[0].connectTo(n[1]);
            assertPath(n[0], n[1], n);
        });
        runTest("Linear chain in forward order", () -> {
            Node[] n = nodes(1, 2, 3, 4);
            connectChain(n);
            assertPath(n[0], n[3], n);
        });
        runTest("Explore the first branch even when its path is longer", () -> {
            Node[] n = nodes(1, 2, 3, 4, 5);
            n[0].connectTo(n[1]).connectTo(n[2]);
            n[1].connectTo(n[3]);
            n[3].connectTo(n[4]);
            n[2].connectTo(n[4]);
            assertPath(n[0], n[4], n[0], n[1], n[3], n[4]);
        });
        runTest("Follow neighbor insertion order, not numeric order", () -> {
            Node[] n = nodes(1, 30, 20, 4);
            n[0].connectTo(n[1]).connectTo(n[2]);
            n[1].connectTo(n[3]);
            n[2].connectTo(n[3]);
            assertPath(n[0], n[3], n[0], n[1], n[3]);
        });
        runTest("Backtrack out of dead ends and exclude them from the path", () -> {
            Node[] n = nodes(1, 2, 3, 4, 5, 6, 7);
            n[0].connectTo(n[1]).connectTo(n[2]);
            n[1].connectTo(n[3]);
            n[2].connectTo(n[4]).connectTo(n[5]);
            n[5].connectTo(n[6]);
            assertPath(n[0], n[6], n[0], n[2], n[5], n[6]);
        });
        runTest("An earlier branch can reach a later neighbor first", () -> {
            Node[] n = nodes(1, 2, 3);
            n[0].connectTo(n[1]).connectTo(n[2]);
            n[1].connectTo(n[2]);
            assertPath(n[0], n[2], n);
        });
        runTest("Cycle with reachable goal", () -> {
            Node[] n = nodes(1, 2, 3, 4);
            n[0].connectTo(n[1]);
            n[1].connectTo(n[2]);
            n[2].connectTo(n[0]).connectTo(n[3]);
            assertPath(n[0], n[3], n);
        });
        runTest("Cycle with unreachable goal", () -> {
            Node[] n = nodes(1, 2, 3);
            n[0].connectTo(n[1]);
            n[1].connectTo(n[0]);
            assertPath(n[0], n[2]);
        });
        runTest("Self-loops", () -> {
            Node[] n = nodes(1, 2);
            n[0].connectTo(n[0]).connectTo(n[1]);
            n[1].connectTo(n[1]);
            assertPath(n[0], n[1], n);
        });
        runTest("Repeated edges and a shared dead end", () -> {
            Node[] n = nodes(1, 2, 3, 4, 5);
            n[0].connectTo(n[1]).connectTo(n[1]).connectTo(n[2]);
            n[1].connectTo(n[3]);
            n[2].connectTo(n[3]).connectTo(n[4]);
            assertPath(n[0], n[4], n[0], n[2], n[4]);
        });
        runTest("Equal values represent distinct nodes and goals", () -> {
            Node[] n = nodes(7, 7, 7, 7, 7);
            n[0].connectTo(n[1]).connectTo(n[2]);
            n[2].connectTo(n[3]);
            assertPath(n[0], n[3], n[0], n[2], n[3]);
            assertPath(n[0], n[4]);
        });
        runTest("Disconnected goal", () -> {
            Node[] n = nodes(1, 2, 3, 4);
            n[0].connectTo(n[1]);
            n[2].connectTo(n[3]);
            assertPath(n[0], n[3]);
        });
        runTest("Follow directed edges only", () -> {
            Node[] n = nodes(1, 2, 3);
            connectChain(n);
            assertPath(n[1], n[2], n[1], n[2]);
            assertPath(n[2], n[0]);
        });
        runTest("Deeper chain", () -> {
            Node[] n = new Node[200];
            for (int i = 0; i < n.length; i++) {
                n[i] = new Node(i);
            }
            connectChain(n);
            assertPath(n[0], n[n.length - 1], n);
        });
        runTest("Repeated calls and returned paths are independent", () -> {
            Node[] n = nodes(1, 2, 3);
            connectChain(n);
            List<Node> first = DepthFirstSearch.findPath(n[0], n[2]);
            assertOrder(first, n);
            assertPath(n[1], n[2], n[1], n[2]);
            assertPath(n[2], n[0]);
            assertPath(n[0], null);
            assertPath(n[0], n[2], n);
            assertOrder(first, n);
        });
        runTest("Preserve graph edges and their order", () -> {
            Node[] n = nodes(1, 2, 3);
            n[0].connectTo(n[2]).connectTo(n[1]).connectTo(n[2]);
            n[1].connectTo(n[0]);
            n[2].connectTo(n[2]).connectTo(n[1]);
            assertPath(n[0], n[1], n[0], n[2], n[1]);
            assertOrder(n[0].neighbors, n[2], n[1], n[2]);
            assertOrder(n[1].neighbors, n[0]);
            assertOrder(n[2].neighbors, n[2], n[1]);
        });

        System.out.println("\nResults: " + passed + " passed, " + failed + " failed.");
        if (failed > 0) {
            throw new AssertionError("Some tests failed. Check your findPath implementation.");
        }
    }

    private static Node[] nodes(int... values) {
        Node[] result = new Node[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = new Node(values[i]);
        }
        return result;
    }

    private static void connectChain(Node[] nodes) {
        for (int i = 0; i < nodes.length - 1; i++) {
            nodes[i].connectTo(nodes[i + 1]);
        }
    }

    private static void assertPath(Node start, Node goal, Node... expected) {
        assertOrder(DepthFirstSearch.findPath(start, goal), expected);
    }

    private static void assertOrder(List<Node> actual, Node... expected) {
        if (actual == null) {
            throw new AssertionError("Expected a list, but got null.");
        }
        if (actual.size() != expected.length) {
            throw new AssertionError("Expected " + expected.length + " nodes, got " + actual.size());
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual.get(i) != expected[i]) {
                throw new AssertionError("Position " + i + ": expected the original node with value "
                        + expected[i].value + ". Check DFS path order and node identity.");
            }
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
