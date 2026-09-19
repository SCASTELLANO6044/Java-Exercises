package BFS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** A graph node. Distinct objects are distinct nodes, even with the same value. */
public class Node {
    public final int value;
    public final List<Node> neighbors = new ArrayList<>();

    public Node(int value) {
        this.value = value;
    }

    /** Add a directed edge from this node to neighbor, preserving insertion order. */
    public Node connectTo(Node neighbor) {
        neighbors.add(Objects.requireNonNull(neighbor, "neighbor"));
        return this;
    }
}
