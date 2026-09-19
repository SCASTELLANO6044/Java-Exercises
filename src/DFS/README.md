# DFS path-finding exercise

`findPath(Node start, Node goal)` in `DepthFirstSearch.java` contains an iterative
solution using a node stack, a visited set, and a parent map. It explores one
unvisited neighbor at a time and pops a node only after exhausting its neighbors.
The parent map supports path reconstruction through `getPath`.

This version keeps the original loop structure and rescans neighbor lists when
backtracking. It uses O(V) extra space but does not guarantee the O(V + E) time
target below; remembering the next neighbor position is a possible optimization.

DFS explores a branch as deeply as possible before backtracking to try another.
Return the first path found to the goal, including both endpoints. DFS does
not guarantee the shortest path.

## Graph setup

This package has its own `Node` class with an integer `value` and an ordered
`neighbors` list. `connectTo` adds a directed edge and returns the source node:

```java
Node a = new Node(1);
Node b = new Node(2);
Node c = new Node(3);
Node goal = new Node(4);
a.connectTo(b).connectTo(goal);
b.connectTo(c);
c.connectTo(goal);
// findPath(a, goal) should return [a, b, c, goal].
// DFS explores b's branch before trying a's direct edge to goal.
```

An edge from `a` to `b` does not add the reverse edge. Add both explicitly to
model an undirected connection. Distinct nodes may have the same value.

## Requirements

- Return an empty list if either endpoint is null or the goal is unreachable.
  Never return null, including when both endpoints are null.
- If the non-null start and goal are the same object, return `[start]`.
- Follow neighbors in list order. Fully explore each unvisited neighbor's
  branch before moving to the next neighbor, as recursive DFS would.
- Visit each node object at most once per search, even when backtracking.
- Return only the path nodes in start-to-goal order. Exclude dead-end branches.
- Use the original node objects and compare nodes by identity, not value.
- Handle cycles, self-loops, shared neighbors, and repeated edges.
  Neighbor lists contain no null entries.
- Preserve the graph, including all neighbor lists and their ordering.
- Keep calls independent, including lists returned by earlier calls.
- Aim for O(V + E) time and O(V) extra space for the reachable graph.

## Run the tests

In IntelliJ, open `DepthFirstSearchTest.java` and run `main`.
No JUnit installation or assertion flags are required.

Alternatively, with a JDK on your PATH, run from the project root:

```powershell
javac -d out src/DFS/Node.java src/DFS/DepthFirstSearch.java src/DFS/DepthFirstSearchTest.java
java -cp out DFS.DepthFirstSearchTest
```

The runner prints `PASS` or `FAIL` for each of 18 cases and a final count.
Success is `18 passed, 0 failed`; failures produce a nonzero exit code.
Tests cover depth-first path order, backtracking, cycles, duplicate values,
unreachable goals, repeated calls, and graph preservation. Complexity and
visit counts are practice requirements, not automatically measured.

The deeper-chain test uses 200 nodes to allow either recursion or an explicit
stack. Recursive implementations can exhaust the call stack on much larger
graphs. If your implementation loops forever on a cycle, stop the run in
IntelliJ or press Ctrl+C in the terminal.
