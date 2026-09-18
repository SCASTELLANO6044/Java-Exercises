# Linked-list reversal exercise

`reverse(Node head)` in `LinkedListReversal.java` contains an iterative solution.
It saves the next node, points the current node back to the previous node, and
advances through the list. It runs in O(n) time with O(1) extra space.

`Node.java` provides an integer `value`, a `next` reference, and constructors.
For example, `new Node(1, new Node(2, new Node(3)))` builds `1 -> 2 -> 3 -> null`.

Your method should:

- Return the new head of the reversed list.
- Return `null` for an empty list.
- Reuse the exact same nodes and preserve each node's value.
- End the reversed list with `null`.
- Run in O(n) time with O(1) extra space as a target.

Inputs are finite lists without cycles. Only the exercise method needs editing.

## Run the tests

In IntelliJ, open `LinkedListReversalTest.java` and click the run icon beside
`main`. No JUnit installation or assertion flags are required.

Alternatively, with a JDK available on your PATH, run from the project root:

```powershell
javac -d out src/LinkedList/Node.java src/LinkedList/LinkedListReversal.java src/LinkedList/LinkedListReversalTest.java
java -cp out LinkedList.LinkedListReversalTest
```

The runner prints `PASS` or `FAIL` for each of 10 cases and a final count. Any
failure also causes a nonzero exit code. Success is `10 passed, 0 failed`.

Tests cover empty, single-node, two-node, odd/even-length, duplicate-value,
negative-value, and long lists, plus reversing twice. They check node identity,
unchanged values, and termination, so copying nodes or swapping values does
not pass. Complexity is a practice target, not automatically measured.

The checker detects cycles in a returned list. If your implementation itself
loops forever, stop the run in IntelliJ or press Ctrl+C in the terminal.
