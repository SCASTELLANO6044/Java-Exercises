package DFS;

import java.util.*;

public class DepthFirstSearch {

    /**
     * Return the first path from start to goal found by depth-first search.
     * Explore each node's neighbors in list order, fully exploring an unvisited
     * neighbor's branch before moving to the next neighbor. Visit each node
     * object at most once per search, even when backtracking.
     *
     * Return the original path nodes in start-to-goal order, including both
     * endpoints. This path is not necessarily the shortest path.
     * Return an empty list if either endpoint is null or goal is unreachable.
     * If start == goal and both are non-null, return a list containing start.
     * Compare nodes by identity, not value. Do not modify the graph.
     * Cycles and repeated edges are allowed; neighbor entries are never null.
     * Aim for O(V + E) time and O(V) extra space for the reachable graph.
     *
     * @param start the starting node
     * @param goal the target node
     * @return the first DFS path, or an empty list; never null
     */
    public static List<Node> findPath(Node start, Node goal) {
        List<Node> result = new ArrayList<>();

        if (start == null || goal == null) {
            return result;
        }

        if (start == goal) {
            result.add(start);
            return result;
        }

        Stack<Node> openStack = new Stack<>();
        Set<Node> closedList = new HashSet<>();
        Map<Node, Node> nodeNodeMap = new HashMap<>();


        nodeNodeMap.put(start, null);
        closedList.add(start);
        openStack.push(start);

        while (!openStack.isEmpty()) {
            start = openStack.peek();
            boolean foundUnvisitedNeighbor = false;

            for (Node node : start.neighbors) {
                if (closedList.contains(node)) {
                    continue;
                }

                nodeNodeMap.put(node, start);
                closedList.add(node);

                if (node == goal) {
                    result = getPath(nodeNodeMap, node);
                    return result;
                }

                openStack.push(node);
                foundUnvisitedNeighbor = true;
                // Explore this branch before considering the next neighbor.
                break;
            }

            if (!foundUnvisitedNeighbor) {
                // All neighbors were visited, so return to the parent.
                openStack.pop();
            }
        }

        return result;
    }

    public static List<Node> getPath(Map<Node, Node> nodeNodeMap, Node finalNode) {
        List<Node> result = new ArrayList<>();

        while (finalNode != null) {
            result.add(finalNode);
            finalNode = nodeNodeMap.get(finalNode);
        }

        return result.reversed();
    }

}
