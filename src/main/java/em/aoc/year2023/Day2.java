package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;

public class Day2 extends Day {

  private static final Map<String, Integer> cubes = new HashMap<>();
  private static Long sum = 0L;

  public Day2() {
    logger = LoggerFactory.getLogger(Day2.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY2
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }

  private static void addPossibleLines(String line) {
    String gameNo = line.split(AppConstants.CHAR_SET_COLON)[0].trim().split(
        AppConstants.CHAR_SET_SPACE)[1];
    String[] reveals = line.split(AppConstants.CHAR_SET_COLON)[1].trim().split(AppConstants.CHAR_SET_COMMA);
    for (String reveal : reveals) {
      String[] revealedCubes = reveal.split(AppConstants.CHAR_SET_COMMA);
      for (String cube : revealedCubes) {
        String cubeNo = cube.trim().split(AppConstants.CHAR_SET_SPACE)[0];
        String cubeColor = cube.trim().split(AppConstants.CHAR_SET_SPACE)[1];
        if (cubes.get(cubeColor) < Integer.parseInt(cubeNo)) {
          return;
        }
      }
    }
    sum += Long.parseLong(gameNo);
  }

  private static void findSumOfPower(String line) {
    cubes.put(AppConstants.BLUE, 1);
    cubes.put(AppConstants.RED, 1);
    cubes.put(AppConstants.GREEN, 1);
    String[] reveals = line.split(AppConstants.CHAR_SET_COLON)[1].trim().split(";");
    for (String reveal : reveals) {
      String[] revealedCubes = reveal.split(AppConstants.CHAR_SET_COMMA);
      for (String cube : revealedCubes) {
        Integer cubeNo = Integer.parseInt(cube.trim().split(AppConstants.CHAR_SET_SPACE)[0]);
        String cubeColor = cube.trim().split(AppConstants.CHAR_SET_SPACE)[1];
        if (cubes.get(cubeColor) < cubeNo) {
          cubes.put(cubeColor, cubeNo);
        }
      }
    }
    sum += (long) cubes.get(AppConstants.RED) * cubes.get(AppConstants.GREEN) * cubes.get(
        AppConstants.BLUE);
  }

  @Override
  public String part1() {
    cubes.put(AppConstants.RED, 12);
    cubes.put(AppConstants.GREEN, 13);
    cubes.put(AppConstants.BLUE, 14);
    lines.forEach(Day2::addPossibleLines);
    return sum.toString();
  }

  @Override
  public String part2() {
    sum = 0L;
    lines.forEach(Day2::findSumOfPower);
    return sum.toString();
  }

}
