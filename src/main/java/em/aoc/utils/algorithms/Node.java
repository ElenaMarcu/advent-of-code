package em.aoc.utils.algorithms;

import java.util.HashMap;
import java.util.Map;

public class Node {

  private final Map<Node, Integer> adjacentNodes;
  private final String name;

  public Node(String name) {
    this.name = name;
    adjacentNodes = new HashMap<>();
  }

  public String getName() {
    return name;
  }

  public void addDestination(Node nodeB) {
    this.adjacentNodes.put(nodeB, 1);
  }

  public void addDestination(Node nodeB, int i) {
    this.adjacentNodes.put(nodeB, i);
  }

  public Map<Node, Integer> getAdjacentNodes() {
    return adjacentNodes;
  }
}
