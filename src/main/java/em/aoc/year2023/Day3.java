package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.LoggerFactory;

public class Day3 extends Day {

  public Day3() {
    logger = LoggerFactory.getLogger(Day3.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY3
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }

  @Override
  public String part1() {
    long sum = 0L;
    for (int i = 0; i <= lines.size() - 1; i++) {
      sum += getLineSumNumbers(lines.get(i), i);
    }
    return String.valueOf(sum);
  }

  private Long getLineSumNumbers(String currentLine, int currentLineIndex) {
    int j = 0;
    int startIndex;
    int endIndex;
    long sum = 0L;
    StringBuilder sb = new StringBuilder();
    while (j <= currentLine.length() - 1) {
      if (Character.isDigit(currentLine.charAt(j))) {
        startIndex = j;
        endIndex = j;
        sb.append(currentLine.charAt(j));
        j++;
        while (j <= currentLine.length() - 1 && Character.isDigit(currentLine.charAt(j))) {
          endIndex = j;
          sb.append(currentLine.charAt(j));
          j++;
        }
        if (isAdjacentWithASymbol(currentLineIndex, startIndex, endIndex)) {
          sum += Long.parseLong(sb.toString());
        }
        sb = new StringBuilder();
      } else {
        j++;
      }
    }
    return sum;
  }

  private boolean isAdjacentWithASymbol(int i, int startIndex, int endIndex) {
    int startLine = i > 1 ? i - 1 : i;
    int endLine = i < lines.size() - 1 ? i + 1 : i;
    int startColumn = startIndex > 1 ? startIndex - 1 : startIndex;
    int endColumn = endIndex < lines.get(0).length() - 1 ? endIndex + 1 : endIndex;
    for (int a = startLine; a <= endLine; a++) {
      String line = lines.get(a);
      for (int b = startColumn; b <= endColumn; b++) {
        if (!(Character.isDigit(line.charAt(b)) || line.charAt(b) == '.')) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public String part2() {
    Map<String, List<Long>> adjacency = new HashMap<>();
    int startIndex;
    int endIndex;
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i <= lines.size() - 1; i++) {
      String currentLine = lines.get(i);
      int j = 0;
      while (j <= currentLine.length() - 1) {
        if (Character.isDigit(currentLine.charAt(j))) {
          startIndex = j;
          endIndex = j;
          sb.append(currentLine.charAt(j));
          j++;
          while (j <= currentLine.length() - 1 && Character.isDigit(currentLine.charAt(j))) {
            endIndex = j;
            sb.append(currentLine.charAt(j));
            j++;
          }
          isAdjacentWithGear(i, startIndex, endIndex, sb, adjacency);
          sb = new StringBuilder();
        } else {
          j++;
        }
      }
    }
    return computeSum(adjacency);
  }

  private String computeSum(Map<String, List<Long>> adjacency) {
    long sum = 0L;
    for (Entry<String, List<Long>> entry : adjacency.entrySet()) {
      List<Long> numbers = entry.getValue();
      if (numbers.size() == 2) {
        sum += numbers.get(0) * numbers.get(1);
      }
    }
    return String.valueOf(sum);
  }

  private void isAdjacentWithGear(int i, int startIndex, int endIndex, StringBuilder sb,
      Map<String, List<Long>> adjacency) {
    int startLine = i > 1 ? i - 1 : i;
    int endLine = i < lines.size() - 1 ? i + 1 : i;
    int startColumn = startIndex > 1 ? startIndex - 1 : startIndex;
    int endColumn = endIndex < lines.get(0).length() - 1 ? endIndex + 1 : endIndex;
    for (int a = startLine; a <= endLine; a++) {
      String line = lines.get(a);
      for (int b = startColumn; b <= endColumn; b++) {
        if (line.charAt(b) == '*') {
          if (adjacency.containsKey(a + AppConstants.CHAR_SET_COMMA + b)) {
            List<Long> numberList = adjacency.get(a + AppConstants.CHAR_SET_COMMA + b);
            numberList.add(Long.parseLong(sb.toString()));
            adjacency.put(a + AppConstants.CHAR_SET_COMMA + b, numberList);
          } else {
            adjacency.put(a + AppConstants.CHAR_SET_COMMA + b, new ArrayList<>(List.of(Long.parseLong(sb.toString()))));
          }
        }
      }
    }
  }
}
