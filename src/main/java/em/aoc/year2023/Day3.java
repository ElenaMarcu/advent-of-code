package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import org.slf4j.LoggerFactory;

public class Day3 extends Day {

  private static Long sum = 0L;

  public Day3() {
    logger = LoggerFactory.getLogger(Day3.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY3
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }

  @Override
  public String part1() {
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
          if (isAdjacentWithASymbol(i, startIndex, endIndex)) {
            sum += Long.parseLong(sb.toString());
          }
          sb = new StringBuilder();
        } else {
          j++;
        }
      }
    }
    return sum.toString();
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
    return null;
  }
}
