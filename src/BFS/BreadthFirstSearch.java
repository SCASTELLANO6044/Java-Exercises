package BFS;

import java.util.*;

public class BreadthFirstSearch {

    /**
     * Return a path with the fewest edges from start to goal, including both.
     * Break ties by BFS discovery order, inspecting neighbors in list order.
     * Compare nodes by identity, not value. Cycles and repeated edges are allowed.
     * <p>
     * Return an empty list if either endpoint is null or goal is unreachable.
     * If start == goal and both are non-null, return a list containing start.
     * Do not modify the graph or create replacement nodes.
     * Neighbor lists contain no null entries.
     * Aim for O(V + E) time and O(V) extra space for the reachable graph.
     *
     * @param start the starting node
     * @param goal the target node
     * @return the original nodes along the path from start to goal, never null
     */
    public static List<Node> shortestPath(Node start, Node goal) {
        // TODO: Implement BFS to find the shortest path to goal.

        List<Node> result = new ArrayList<>();

        if (start == null || goal == null){
            return result;
        }

        if (start == goal){
            result.add(start);
            return result;
        }

        Queue<Node> openQueue = new LinkedList<>();
        Map<Node, Node> nodeNodeMap = new HashMap<>();

        nodeNodeMap.put(start, null);
        openQueue.add(start);

        while (!openQueue.isEmpty()){
            start = openQueue.remove();

            for (Node node: start.neighbors){
                if(nodeNodeMap.containsKey(node)){
                    continue;
                }

                nodeNodeMap.put(node, start);
                if (node == goal){
                    return traceback(nodeNodeMap, node);
                }
                openQueue.add(node);
            }
        }

        return result;

    }

    /** Optional helper: reconstruct a start-to-goal path from your predecessor map. */
    public static List<Node> traceback(Map<Node, Node> nodeNodeMap, Node finalNode){
        List<Node> nodeList = new ArrayList<>();

        while (nodeNodeMap.containsKey(finalNode)){
            Node prevNode = nodeNodeMap.get(finalNode);
            nodeList.add(finalNode);
            finalNode = prevNode;
        }

        nodeList =nodeList.reversed();

        return nodeList;
    }

}
