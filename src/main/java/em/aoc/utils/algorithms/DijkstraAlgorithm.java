package em.aoc.utils.algorithms;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DijkstraAlgorithm {

  private DijkstraAlgorithm() {
    throw new IllegalStateException("Utility class");
  }

  private static void dijkstra(Node start, Map<Node, Integer> shortestDistance,
      Map<Node, List<Node>> shortestPath) {
    Set<Node> unvisited = new HashSet<>();
    unvisited.add(start);
    Set<Node> visited = new HashSet<>();
    shortestDistance.put(start, 0);
    while (!unvisited.isEmpty()) {
      Node currentNode = getLowestDistanceNode(unvisited, shortestDistance);
      unvisited.remove(currentNode);
      for (Entry<Node, Integer> entry : currentNode.getAdjacentNodes().entrySet()) {
        Node adjacentNode = entry.getKey();
        Integer distance = entry.getValue();
        if (!visited.contains(adjacentNode)) {
          calculateMinimumDistance(adjacentNode, distance, currentNode, shortestDistance,
              shortestPath);
          unvisited.add(adjacentNode);
        }
      }
      visited.add(currentNode);
    }
  }

  private static void calculateMinimumDistance(Node evaluationNode, Integer distance,
      Node sourceNode, Map<Node, Integer> shortestDistance, Map<Node, List<Node>> shortestPaths) {
    shortestDistance.putIfAbsent(evaluationNode, Integer.MAX_VALUE);
    if (shortestDistance.get(sourceNode) + distance < shortestDistance.get(
        evaluationNode)) {
      shortestDistance.put(evaluationNode, shortestDistance.get(sourceNode) + distance);
      shortestPaths.putIfAbsent(sourceNode, new LinkedList<>());
      LinkedList<Node> shortestPath = (LinkedList<Node>) shortestPaths.get(sourceNode);
      shortestPath.add(sourceNode);
      shortestPaths.put(evaluationNode, shortestPath);
    }
  }

  @NonNull
  private static Node getLowestDistanceNode(Set<Node> unvisited,
      Map<Node, Integer> shortestDistance) {
    Node lowestDistanceNode = unvisited.iterator().next();
    int lowestDistance = Integer.MAX_VALUE;
    for (Node node : unvisited) {
      shortestDistance.putIfAbsent(node, Integer.MAX_VALUE);
      int nodeDistance = shortestDistance.get(node);
      if (nodeDistance < lowestDistance) {
        lowestDistance = nodeDistance;
        lowestDistanceNode = node;
      }
    }
    return lowestDistanceNode;
  }

  public static int getGreatestDistance(Node nodeA) {
    int distance = Integer.MIN_VALUE;
    Map<Node, Integer> shortestDistance = new HashMap<>();
    dijkstra(nodeA, shortestDistance, new HashMap<>());
    for (Entry<Node, Integer> entry : shortestDistance.entrySet()) {
      Node node = entry.getKey();
      Integer pathDistance = entry.getValue();
      if (node != nodeA && distance < pathDistance) {
        distance = pathDistance;
      }
    }
    return distance;
  }
}
