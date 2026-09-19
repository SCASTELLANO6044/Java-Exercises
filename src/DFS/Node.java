package DFS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Distinct objects are distinct graph nodes, even when their values match. */
public class Node {
    public final int value;
    public final List<Node> neighbors = new ArrayList<>();

    public Node(int value) {
        this.value = value;
    }

    /** Add a directed edge, preserving insertion order. */
    public Node connectTo(Node neighbor) {
        neighbors.add(Objects.requireNonNull(neighbor, "neighbor"));
        return this;
    }
}
