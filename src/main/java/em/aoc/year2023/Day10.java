package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import em.aoc.utils.DayUtils.Coordinates;
import em.aoc.utils.Utilities;
import em.aoc.utils.algorithms.DijkstraAlgorithm;
import em.aoc.utils.algorithms.Node;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.LoggerFactory;

public class Day10 extends Day {

  private final Node startNode;

  public Day10() {
    logger = LoggerFactory.getLogger(Day10.class);
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
    //Second solution
    List<Coordinates> coordinatesList = getPolygonExteriorPoints();
    return String.valueOf(Utilities.getNoOfInteriorPoints(coordinatesList));
  }

  private List<Coordinates> getPolygonExteriorPoints() {
    List<Coordinates> coordinatesList = new LinkedList<>();
    Node currentNode = startNode;
    Set<Node> visited = new HashSet<>();
    while (!visited.contains(currentNode)) {
      coordinatesList.add(
          new Coordinates(
              Integer.parseInt(currentNode.getName().split(AppConstants.CHAR_SET_COMMA)[0]),
              Integer.parseInt(currentNode.getName().split(AppConstants.CHAR_SET_COMMA)[1])));
      visited.add(currentNode);
      currentNode = getNextNode(visited, currentNode);
    }
    return coordinatesList;
  }

  private Node getNextNode(Set<Node> visited, Node firstNode) {
    for (Entry<Node, Integer> entry : firstNode.getAdjacentNodes().entrySet()) {
      if (!visited.contains(entry.getKey())) {
        return entry.getKey();
      }
    }
    return firstNode;
  }

}
