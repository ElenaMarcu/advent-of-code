package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import org.slf4j.LoggerFactory;

public class Day12 extends Day {

  public Day12() {
    logger = LoggerFactory.getLogger(Day12.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY12
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }

  @Override
  public String part1() {
    AtomicLong sum = new AtomicLong(0);
    lines.forEach(s ->
        sum.addAndGet(
            getAllPossibleArrangements(s.split(AppConstants.CHAR_SET_SPACE)[0],
                s.split(AppConstants.CHAR_SET_SPACE)[1].split(AppConstants.CHAR_SET_COMMA))));
    return sum.toString();
  }

  private long countAllPossibleArrangements(String s, String[] split) {
    long totalPresentSprings = s.chars().filter(c -> c == '#').count();
    long totalSprings = Arrays.stream(split).mapToLong(Long::valueOf).sum();
    long totalSpringsToPermute = totalSprings - totalPresentSprings;
    String finalS = s;
    List<Integer> positions = IntStream.iterate(s.indexOf("?"), index->index>=0,index-> finalS.indexOf("?",index+1)).boxed().toList();
    StringBuilder sb = new StringBuilder(s);
    positions.forEach(p -> sb.setCharAt(p, '.'));
    s = sb.toString();
    return generateCombinations(positions, totalSpringsToPermute, 0,
        new int[(int) totalSpringsToPermute],
        0, s, split);
  }

  private long generateCombinations(List<Integer> positions, long nTotal, int currentIndex,
      int[] combination, int start, String s, String[] split) {
    if (currentIndex == nTotal) {
      StringBuilder sb = new StringBuilder(s);
      Arrays.stream(combination).forEach(c -> sb.setCharAt(c, '#'));
      if (isPossibleArrangement(sb.toString(), split)) {
        return 1;
      }
      return 0;
    }
    int sum = 0;
    for (int i = start; i < positions.size(); i++) {
      combination[currentIndex] = positions.get(i);
      sum += generateCombinations(positions, nTotal, currentIndex + 1, combination, i + 1, s,
          split);
    }
    return sum;
  }

  private long getAllPossibleArrangements(String s, String[] split) {
    long sum = 0;
    if (!s.contains("?")) {
      if (isPossibleArrangement(s, split)) {
        return 1;
      } else {
        return 0;
      }
    }
    for (char c : new char[]{'#', '.'}) {
      int index = s.indexOf('?');
      StringBuilder sb = new StringBuilder(s);
      sb.setCharAt(index, c);
      sum += getAllPossibleArrangements(sb.toString(), split);
    }

    return sum;
  }

  private boolean isPossibleArrangement(String s, String[] split) {
    String[] springs = s.replaceFirst("^\\.+", "").split("\\.+");
    if (springs.length != split.length) {
      return false;
    }
    for (int i = 0; i < springs.length; i++) {
      if (springs[i].length() != Integer.parseInt(split[i])) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String part2() {
    AtomicLong sum = new AtomicLong(0);
    lines.forEach(s ->
        sum.addAndGet(
            countAllPossibleArrangements(multiplyString(s.split(AppConstants.CHAR_SET_SPACE)[0], "?"),
                multiplyString(s.split(AppConstants.CHAR_SET_SPACE)[1], ",").split(
                    AppConstants.CHAR_SET_COMMA))));
    return sum.toString();
  }

  private String multiplyString(String s, String delimitation) {
    StringBuilder sb = new StringBuilder(s);
    for (int i = 0; i < 5; i++) {
      sb.append(delimitation);
      sb.append(s);
    }
    return sb.toString();
  }
}
