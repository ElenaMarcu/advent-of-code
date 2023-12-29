package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import em.aoc.utils.algorithms.DijkstraAlgorithm;
import em.aoc.utils.algorithms.Node;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.LoggerFactory;

public class Day10 extends Day {

  private final Node startNode;

  public Day10() {
    logger = LoggerFactory.getLogger(Day9.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY10
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
    startNode = createMaze();
  }


  @Override
  public String part1() {
    return String.valueOf(DijkstraAlgorithm.getGreatestDistance(startNode));
  }

  private Node createMaze() {
    Map<String, Node> nodeMap = new HashMap<>();
    Node startNode = null;
    for (int i = 0; i < lines.size(); i++) {
      for (int j = 0; j < lines.get(i).toCharArray().length; j++) {
        switch (lines.get(i).charAt(j)) {
          case '|' -> checkDirections(nodeMap, i, j, true, true, false, false);
          case '-' -> checkDirections(nodeMap, i, j, false, false, true, true);
          case 'L' -> checkDirections(nodeMap, i, j, true, false, false, true);
          case 'J' -> checkDirections(nodeMap, i, j, true, false, true, false);
          case '7' -> checkDirections(nodeMap, i, j, false, true, true, false);
          case 'F' -> checkDirections(nodeMap, i, j, false, true, false, true);
          case 'S' -> {
            checkDirections(nodeMap, i, j, true, true, true, true);
            startNode = nodeMap.get(i + AppConstants.CHAR_SET_COMMA + j);
          }
          case '.', default -> {
          }
        }
      }
    }
    return startNode;
  }

  private void checkDirections(Map<String, Node> nodeMap, int i, int j, boolean checkUp,
      boolean checkDown, boolean checkLeft, boolean checkRight) {
    nodeMap.putIfAbsent(i + AppConstants.CHAR_SET_COMMA + j,
        new Node(i + AppConstants.CHAR_SET_COMMA + j));
    Node node = nodeMap.get(i + AppConstants.CHAR_SET_COMMA + j);
    if (checkUp) {
      checkUp(nodeMap, i, j, node);
    }
    if (checkDown) {
      checkDown(nodeMap, i, j, node);
    }
    if (checkLeft) {
      checkLeft(nodeMap, i, j, node);
    }
    if (checkRight) {
      checkRight(nodeMap, i, j, node);
    }
  }

  private void checkRight(Map<String, Node> nodeMap, int i, int j, Node node) {
    if (j + 1 < lines.get(i).length() && "J-S7".indexOf(lines.get(i).charAt(j + 1)) != -1) {
      nodeMap.putIfAbsent(i + AppConstants.CHAR_SET_COMMA + (j + 1),
          new Node(i + AppConstants.CHAR_SET_COMMA + (j + 1)));
      node.addDestination(nodeMap.get(i + AppConstants.CHAR_SET_COMMA + (j + 1)));
    }
  }

  private void checkLeft(Map<String, Node> nodeMap, int i, int j, Node node) {
    if (j - 1 >= 0 && "L-SF".indexOf(lines.get(i).charAt(j - 1)) != -1) {
      nodeMap.putIfAbsent(i + AppConstants.CHAR_SET_COMMA + (j - 1),
          new Node(i + AppConstants.CHAR_SET_COMMA + (j - 1)));
      node.addDestination(nodeMap.get(i + AppConstants.CHAR_SET_COMMA + (j - 1)));
    }
  }

  private void checkDown(Map<String, Node> nodeMap, int i, int j, Node node) {
    if (i + 1 < lines.size() && "J|SL".indexOf(lines.get(i + 1).charAt(j)) != -1) {
      nodeMap.putIfAbsent((i + 1) + AppConstants.CHAR_SET_COMMA + j,
          new Node((i + 1) + AppConstants.CHAR_SET_COMMA + j));
      node.addDestination(nodeMap.get((i + 1) + AppConstants.CHAR_SET_COMMA + j));
    }
  }

  private void checkUp(Map<String, Node> nodeMap, int i, int j, Node node) {
    if (i - 1 >= 0 && "F|S7".indexOf(lines.get(i - 1).charAt(j)) != -1) {
      nodeMap.putIfAbsent((i - 1) + AppConstants.CHAR_SET_COMMA + j,
          new Node((i - 1) + AppConstants.CHAR_SET_COMMA + j));
      node.addDestination(nodeMap.get((i - 1) + AppConstants.CHAR_SET_COMMA + j));
    }
  }


  @Override
  public String part2() {
    List<String> nodesToCheck = new ArrayList<>();
    Polygon polygon = new Polygon();
    populateNodeToList(nodesToCheck);
    populatePolygon(polygon);
    //Second solution
    Set<Node> visited = new HashSet<>();
    double area = getArea(visited);
    //Pick's Theorem
    double noOfPoints = area - visited.size() / 2.0 + 1;
    return String.valueOf(countInsideNodes(polygon, nodesToCheck));
  }

  // Shoelace formula
  private double getArea(Set<Node> visited) {
    double area = 0;
    Node firstNode = startNode;
    visited.add(startNode);
    Node secondNode = getNextNode(visited, firstNode);
    while (!visited.contains(secondNode)) {
      visited.add(secondNode);
      area += getPointsMultiplication(firstNode, secondNode);
      firstNode = secondNode;
      secondNode = getNextNode(visited, firstNode);
    }
    area += getPointsMultiplication(firstNode, startNode);
    return area > 0 ? area / 2 : -1 * area / 2;
  }

  private double getPointsMultiplication(Node firstNode, Node secondNode) {
    int x1 = Integer.parseInt(firstNode.getName().split(AppConstants.CHAR_SET_COMMA)[0]);
    int y1 = Integer.parseInt(firstNode.getName().split(AppConstants.CHAR_SET_COMMA)[1]);
    int x2 = Integer.parseInt(secondNode.getName().split(AppConstants.CHAR_SET_COMMA)[0]);
    int y2 = Integer.parseInt(secondNode.getName().split(AppConstants.CHAR_SET_COMMA)[1]);
    return (y2 + y1) * (x2 - x1);
  }

  private Node getNextNode(Set<Node> visited, Node firstNode) {
    for (Entry<Node, Integer> entry : firstNode.getAdjacentNodes().entrySet()) {
      if (!visited.contains(entry.getKey())) {
        return entry.getKey();
      }
    }
    return firstNode;
  }

  private void populatePolygon(Polygon polygon) {
    Set<Node> visited = new HashSet<>();
    Node currentNode = startNode;
    while (!visited.contains(currentNode)) {
      visited.add(currentNode);
      int x = Integer.parseInt(currentNode.getName().split(AppConstants.CHAR_SET_COMMA)[0]);
      int y = Integer.parseInt(currentNode.getName().split(AppConstants.CHAR_SET_COMMA)[1]);
      polygon.addPoint(x, y);
      for (Entry<Node, Integer> entry : currentNode.getAdjacentNodes().entrySet()) {
        if (!visited.contains(entry.getKey())) {
          currentNode = entry.getKey();
        }
      }
    }
  }

  private int countInsideNodes(Polygon polygon, List<String> nodesToCheck) {
    int count = 0;
    for (String node : nodesToCheck) {
      int x = Integer.parseInt(node.split(AppConstants.CHAR_SET_COMMA)[0]);
      int y = Integer.parseInt(node.split(AppConstants.CHAR_SET_COMMA)[1]);
      if (polygon.contains(x, y)) {
        count++;
      }
    }
    return count;
  }

  private void populateNodeToList(List<String> nodesToCheck) {
    Map<Node, List<Node>> pathNodes = DijkstraAlgorithm.getShortestPathToNodes(startNode);
    List<String> polygonPoints = pathNodes.keySet().stream()
        .flatMap(c -> c.getName().describeConstable()
            .stream()).toList();
    for (int i = 0; i < lines.size(); i++) {
      for (int j = 0; j < lines.get(0).length(); j++) {
        if (!polygonPoints.contains(i + AppConstants.CHAR_SET_COMMA + j)) {
          nodesToCheck.add(i + AppConstants.CHAR_SET_COMMA + j);
        }
      }
    }
  }
}
