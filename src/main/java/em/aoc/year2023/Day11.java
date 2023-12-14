package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.LoggerFactory;

public class Day11 extends Day {

  private final Set<Integer> linesToExpand;
  private final Set<Integer> columnsToExpand;
  private List<List<Integer>> coordinates;

  public Day11() {
    logger = LoggerFactory.getLogger(Day11.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY11
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
    columnsToExpand = IntStream.rangeClosed(1, lines.get(0).length() - 1).boxed()
        .collect(Collectors.toSet());
    linesToExpand = IntStream.rangeClosed(1, lines.size() - 1).boxed()
        .collect(Collectors.toSet());
    expandGalaxy();
    getCoordinates();
  }

  @Override
  public String part1() {
    return String.valueOf(getSum(coordinates, false));
  }

  private void expandGalaxy() {
    int line = 0;
    while (line < lines.size()) {
      int column = -1;
      while ((column = lines.get(line).indexOf('#', column + 1)) != -1) {
        columnsToExpand.remove(column);
        linesToExpand.remove(line);
      }
      line++;
    }
  }

  private long getSum(List<List<Integer>> coordinates, boolean isPartTwo) {
    long sum = 0L;
    for (int i = 0; i < coordinates.size() - 1; i++) {
      for (int j = i + 1; j < coordinates.size(); j++) {
        sum += Math.abs(coordinates.get(i).get(0) - coordinates.get(j).get(0)) + Math.abs(
            coordinates.get(i).get(1) - coordinates.get(j).get(1)) + getLineOffset(isPartTwo,
            coordinates.get(i).get(0), coordinates.get(j).get(0)) + getColumnOffset(isPartTwo,
            coordinates.get(i).get(1), coordinates.get(j).get(1));
      }
    }
    return sum;
  }

  private long getColumnOffset(boolean isPartTwo, Integer c1, Integer c2) {
    int offset = 0;
    int start = c1 > c2 ? c2 : c1;
    int end = c1 > c2 ? c1 : c2;
    for (int i = start + 1; i < end; i++) {
      if (columnsToExpand.contains(i)) {
        offset++;
      }
    }
    if (isPartTwo) {
      return offset * (1000000L - 1);
    }
    return offset;
  }

  private long getLineOffset(boolean isPartTwo, Integer l1, Integer l2) {
    int offset = 0;
    for (int i = l1 + 1; i < l2; i++) {
      if (linesToExpand.contains(i)) {
        offset++;
      }
    }
    if (isPartTwo) {
      return offset * (1000000L - 1);
    }
    return offset;
  }

  private void getCoordinates() {
    coordinates = new ArrayList<>();
    for (int i = 0; i < lines.size(); i++) {
      int currentIndex = -1;
      while ((currentIndex = lines.get(i).indexOf('#', currentIndex + 1)) != -1) {
        coordinates.add(List.of(i, currentIndex));
      }
    }
  }

  @Override
  public String part2() {
    return String.valueOf(getSum(coordinates, true));
  }
}
