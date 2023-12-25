package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import em.aoc.utils.algorithms.DijkstraAlgorithm;
import em.aoc.utils.algorithms.Node;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;

public class Day10 extends Day {

  public Day10() {
    logger = LoggerFactory.getLogger(Day9.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY10
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }


  @Override
  public String part1() {
    Node startNode = createMaze();
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
            startNode = nodeMap.get(i + "," + j);
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
    nodeMap.putIfAbsent(i + "," + j, new Node(i + "," + j));
    Node node = nodeMap.get(i + "," + j);
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
      nodeMap.putIfAbsent(i + "," + (j + 1), new Node(i + "," + (j + 1)));
      node.addDestination(nodeMap.get(i + "," + (j + 1)));
    }
  }

  private void checkLeft(Map<String, Node> nodeMap, int i, int j, Node node) {
    if (j - 1 >= 0 && "L-SF".indexOf(lines.get(i).charAt(j - 1)) != -1) {
      nodeMap.putIfAbsent(i + "," + (j - 1), new Node(i + "," + (j - 1)));
      node.addDestination(nodeMap.get(i + "," + (j - 1)));
    }
  }

  private void checkDown(Map<String, Node> nodeMap, int i, int j, Node node) {
    if (i + 1 < lines.size() && "J|SL".indexOf(lines.get(i + 1).charAt(j)) != -1) {
      nodeMap.putIfAbsent((i + 1) + "," + j, new Node((i + 1) + "," + j));
      node.addDestination(nodeMap.get((i + 1) + "," + j));
    }
  }

  private void checkUp(Map<String, Node> nodeMap, int i, int j, Node node) {
    if (i - 1 >= 0 && "F|S7".indexOf(lines.get(i - 1).charAt(j)) != -1) {
      nodeMap.putIfAbsent((i - 1) + "," + j, new Node((i - 1) + "," + j));
      node.addDestination(nodeMap.get((i - 1) + "," + j));
    }
  }


  @Override
  public String part2() {
    return null;
  }
}
