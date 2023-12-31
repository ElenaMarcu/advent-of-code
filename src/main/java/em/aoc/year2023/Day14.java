package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;

public class Day14 extends Day {

  public Day14() {
    logger = LoggerFactory.getLogger(Day14.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY14
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }

  @Override
  public String part1() {
    return String.valueOf(getTotalLoadPart1());
  }

  @Override
  public String part2() {
    char[][] platform = getPlatform();
    String resultPlatform = runSpinCycle(platform);
    return String.valueOf(countBeams(resultPlatform, platform.length, platform[0].length));
  }

  private int countBeams(String platform, int noOfRows, int rowLength) {
    int sum = 0;
    for (int i = 0; i < platform.length(); i++) {
      if (platform.charAt(i) == 'O') {
        sum += noOfRows - i / rowLength;
      }
    }
    return sum;
  }

  private String runSpinCycle(char[][] platform) {
    List<String> afterCycle = new ArrayList<>();
    String platformS = "";
    for (int cycle = 0; cycle < 1000000000; cycle++) {
      tiltNorth(platform);
      tiltWest(platform);
      tiltSouth(platform);
      tiltEast(platform);
      platformS = getPlatformString(platform);
      if (afterCycle.contains(platformS)) {
        int index = afterCycle.indexOf(platformS);
        return afterCycle.get(index - 1 + (1000000000 - index) % (afterCycle.size() - index));
      } else {
        afterCycle.add(platformS);
      }
    }
    return platformS;
  }

  private String getPlatformString(char[][] platform) {
    StringBuilder sb = new StringBuilder();
    for (char[] chars : platform) {
      sb.append(chars);
    }
    return sb.toString();
  }

  private void tiltEast(char[][] platform) {
    for (int i = 0; i < platform.length; i++) {
      char[] line = platform[i];
      int availablePosition = platform[0].length - 1;
      for (int j = line.length - 1; j >= 0; j--) {
        if (line[j] == '#') {
          availablePosition = j - 1;
        } else if (line[j] == 'O') {
          if (availablePosition != j) {
            platform[i][availablePosition] = 'O';
            platform[i][j] = '.';
          }
          availablePosition--;
        }
      }
    }
  }

  private void tiltSouth(char[][] platform) {
    Map<Integer, Integer> southestPosition = new HashMap<>();
    for (int i = platform.length - 1; i >= 0; i--) {
      char[] line = platform[i];
      for (int j = 0; j < line.length; j++) {
        southestPosition.putIfAbsent(j, platform.length - 1);
        if (line[j] == '#') {
          southestPosition.put(j, i - 1);
        } else if (line[j] == 'O') {
          if (southestPosition.get(j) != i) {
            platform[southestPosition.get(j)][j] = 'O';
            platform[i][j] = '.';
          }
          southestPosition.put(j, southestPosition.get(j) - 1);
        }
      }
    }
  }

  private void tiltWest(char[][] platform) {
    for (int i = 0; i < platform.length; i++) {
      char[] line = platform[i];
      int availablePosition = 0;
      for (int j = 0; j < line.length; j++) {
        if (line[j] == '#') {
          availablePosition = j + 1;
        } else if (line[j] == 'O') {
          if (availablePosition != j) {
            platform[i][availablePosition] = 'O';
            platform[i][j] = '.';
          }
          availablePosition++;
        }
      }
    }
  }

  private void tiltNorth(char[][] platform) {
    Map<Integer, Integer> northestPosition = new HashMap<>();
    int i = 0;
    for (char[] line : platform) {
      for (int j = 0; j < line.length; j++) {
        northestPosition.putIfAbsent(j, 0);
        if (line[j] == '#') {
          northestPosition.put(j, i + 1);
        } else if (line[j] == 'O') {
          if (northestPosition.get(j) != i) {
            platform[northestPosition.get(j)][j] = 'O';
            platform[i][j] = '.';
          }
          northestPosition.put(j, northestPosition.get(j) + 1);
        }
      }
      i++;
    }
  }

  private char[][] getPlatform() {
    char[][] platform = new char[lines.size()][lines.get(0).length()];
    for (int i = 0; i < lines.size(); i++) {
      platform[i] = lines.get(i).toCharArray();
    }
    return platform;
  }

  private long getTotalLoadPart1() {
    Map<Integer, Integer> northestPosition = new HashMap<>();
    long sum = 0L;
    int i = 0;
    for (String line : lines) {
      for (int j = 0; j < line.length(); j++) {
        northestPosition.putIfAbsent(j, 0);
        if (line.charAt(j) == '#') {
          northestPosition.put(j, i + 1);
        } else if (line.charAt(j) == 'O') {
          sum += lines.size() - northestPosition.get(j);
          northestPosition.put(j, northestPosition.get(j) + 1);
        }
      }
      i++;
    }
    return sum;
  }
}
