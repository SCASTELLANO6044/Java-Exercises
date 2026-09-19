# BFS shortest-path exercise

Implement `shortestPath(Node start, Node goal)` in `BreadthFirstSearch.java`.
The method is deliberately unfinished; tests will fail until you implement it.
Your map, queue, result list, and optional `traceback` helper are available as
starting scaffolding. You may adapt them as needed.

Find a path with the fewest edges in an unweighted directed graph. Return a
`List<Node>` containing the original nodes along that path, from start to goal,
including both endpoints.

## Graph setup

`Node.java` provides an integer `value` and an ordered `neighbors` list.
`connectTo` adds a directed edge and returns the source node for chaining:

```java
Node a = new Node(1);
Node b = new Node(2);
Node c = new Node(3);
Node d = new Node(4);
a.connectTo(b).connectTo(c);
b.connectTo(d);
// shortestPath(a, d) should return [a, b, d].
// shortestPath(d, a) should return an empty list.
```

An edge from `a` to `b` does not add an edge from `b` to `a`. Add both explicitly
to model an undirected connection. Distinct nodes may have the same value.

## Requirements

- Return an empty list if either endpoint is null or the goal is unreachable.
  Never return null, including when both endpoints are null.
- For a non-null start that is the same object as goal, return `[start]`.
- Return only the path nodes in start-to-goal order, including both endpoints.
- Minimize the number of edges. For equally short paths, return the first one
  discovered by BFS when neighbors are inspected in list order.
- Identify nodes by object identity, not by their integer values.
- Handle cycles, self-loops, shared neighbors, and repeated edges.
  Neighbor lists contain no null entries.
- Preserve all graph nodes, values, edges, and neighbor ordering.
- Keep calls independent, including lists returned by earlier calls.
- Aim for O(V + E) time and O(V) extra space, where V and E count nodes and
  neighbor entries reachable from start.

The optional `traceback` helper can reconstruct a path using the predecessor
map you build. Tests check `shortestPath`; they do not require a particular
helper implementation.

## Run the tests

In IntelliJ, open `BreadthFirstSearchTest.java` and click the run icon beside
`main`. No JUnit installation or assertion flags are required.

Alternatively, with a JDK available on your PATH, run from the project root:

```powershell
javac -d out src/BFS/Node.java src/BFS/BreadthFirstSearch.java src/BFS/BreadthFirstSearchTest.java
java -cp out BFS.BreadthFirstSearchTest
```

The runner prints `PASS` or `FAIL` for each of 18 cases and a final count.
Success is `18 passed, 0 failed`; failures produce a nonzero exit code.
Tests check shortest paths, tie-breaking, node identity, unreachable goals,
edge cases, and graph preservation. Complexity is not automatically measured.

If your implementation loops forever on a cycle, stop the run in IntelliJ or
press Ctrl+C in the terminal.

