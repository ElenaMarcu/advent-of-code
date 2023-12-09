package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.LoggerFactory;

public class Day8 extends Day {

  public Day8() {
    logger = LoggerFactory.getLogger(Day8.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY8
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }

  @Override
  public String part1() {
    Map<String, String[]> nodes = readNodes();
    String instructions = lines.get(0);
    return String.valueOf(countSteps(instructions, nodes, "AAA", true));
  }

  private int countSteps(String instructions, Map<String, String[]> nodes, String currentNode,
      boolean isPart1) {
    int i = 0;
    int noOfSteps = 0;
    boolean condition = isPart1 ? "ZZZ".equals(currentNode) : currentNode.endsWith("Z");
    while (!condition) {
      char instruction = instructions.charAt(i);
      if (instruction == 'L') {
        currentNode = nodes.get(currentNode)[0];
      } else {
        currentNode = nodes.get(currentNode)[1];
      }
      noOfSteps++;
      condition = isPart1 ? "ZZZ".equals(currentNode) : currentNode.endsWith("Z");
      if (i == instructions.length() - 1) {
        i = -1;
      }
      i++;
    }
    return noOfSteps;
  }

  private Map<String, String[]> readNodes() {
    Pattern pattern = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");
    Map<String, String[]> map = new HashMap<>();
    for (String line : lines) {
      Matcher matcher = pattern.matcher(line);
      if (matcher.matches()) {
        String node = matcher.group(1);
        String left = matcher.group(2);
        String right = matcher.group(3);
        map.put(node, new String[]{left, right});
      }
    }
    return map;
  }

  @Override
  public String part2() {
    Map<String, String[]> nodes = readNodes();
    List<String> startingNodes = new ArrayList<>();
    nodes.forEach((k, v) -> {
      if (k.endsWith("A")) {
        startingNodes.add(k);
      }
    });
    String instructions = lines.get(0);
    List<Integer> steps = new ArrayList<>();
    startingNodes.forEach(k -> steps.add(countSteps(instructions, nodes, k, false)));
    return findLCM(steps);
  }

  private String findLCM(List<Integer> steps) {
    int valueToAdd = Collections.max(steps);
    long lcm = valueToAdd;
    boolean found = false;
    while (!found) {
      found = true;
      for (Integer step : steps) {
        if (lcm % step != 0) {
          lcm += valueToAdd;
          found = false;
          break;
        }
      }
    }
    return String.valueOf(lcm);
  }
}
