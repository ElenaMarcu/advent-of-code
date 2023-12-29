package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.LoggerFactory;

public class Day13 extends Day {

  private List<List<String>> patternList;

  public Day13() {
    logger = LoggerFactory.getLogger(Day13.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY13
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
    populatePatternList();
  }

  private void populatePatternList() {
    patternList = new ArrayList<>();
    List<String> pattern = new ArrayList<>();
    for (String line : lines) {
      if (line.isEmpty()) {
        patternList.add(new ArrayList<>(pattern));
        pattern = new ArrayList<>();
      } else {
        pattern.add(line);
      }
    }
    patternList.add(new ArrayList<>(pattern));
  }

  @Override
  public String part1() {
    AtomicLong sum = new AtomicLong(0);
    patternList.forEach(p -> sum.getAndAdd(getReflectionNumber(p, false)));
    return sum.toString();
  }


  public int getReflectionNumber(List<String> pattern, boolean isPart2) {
    int result = isPart2 ? getNewLineOfReflection(pattern) : getLineOfReflection(pattern);
    if (result != 0) {
      return result * 100;
    }
    List<String> newPattern = new ArrayList<>();
    for (int j = 0; j < pattern.get(0).length(); j++) {
      StringBuilder sb = new StringBuilder();
      for (String s : pattern) {
        sb.append(s.charAt(j));
      }
      newPattern.add(sb.toString());
    }
    return isPart2 ? getNewLineOfReflection(newPattern) : getLineOfReflection(newPattern);
  }

  private int getNewLineOfReflection(List<String> pattern) {
    for (int i = 0; i < pattern.size() - 1; i++) {
      if ((pattern.get(i).equals(pattern.get(i + 1)) && restOfPatternHasExactlyOneSmudge(i + 1,
          pattern)) || (
          hasExactlyOneSmudge(pattern.get(i), pattern.get(i + 1)) && isTrueMirror(i + 1,
              pattern))) {
        return i + 1;
      }
    }
    return 0;
  }

  private boolean hasExactlyOneSmudge(String s1, String s2) {
    boolean foundOneSmudge = false;
    if (s1.length() != s2.length()) {
      throw new IllegalStateException(
          "The length of the strings differ"); // Never should be this case
    }
    for (int i = 0; i < s1.length(); i++) {
      if (s1.charAt(i) != s2.charAt(i)) {
        if (foundOneSmudge) {
          return false;
        }
        foundOneSmudge = true;
      }
    }
    return foundOneSmudge;
  }

  private boolean restOfPatternHasExactlyOneSmudge(int i, List<String> pattern) {
    int leftIndex = i - 2;
    int rightIndex = i + 1;
    boolean foundOneSmudge = false;
    int limit = Math.min(pattern.size() - rightIndex, leftIndex + 1);
    for (int j = 0; j < limit; j++) {
      if (!pattern.get(leftIndex - j).equals(pattern.get(rightIndex + j))) {
        if (hasExactlyOneSmudge(pattern.get(leftIndex - j), pattern.get(rightIndex + j))) {
          if (foundOneSmudge) {
            return false;
          }
          foundOneSmudge = true;
        } else {
          return false;
        }
      }
    }
    return foundOneSmudge;
  }

  private int getLineOfReflection(List<String> pattern) {
    for (int i = 0; i < pattern.size() - 1; i++) {
      if (pattern.get(i).equals(pattern.get(i + 1)) && isTrueMirror(i + 1, pattern)) {
        return i + 1;
      }
    }
    return 0;
  }

  private boolean isTrueMirror(int i, List<String> pattern) {
    int leftIndex = i - 2;
    int rightIndex = i + 1;
    int limit = Math.min(pattern.size() - rightIndex, leftIndex + 1);
    for (int j = 0; j < limit; j++) {
      if (!pattern.get(leftIndex - j).equals(pattern.get(rightIndex + j))) {
        return false;
      }
    }
    return true;
  }


  @Override
  public String part2() {
    AtomicLong sum = new AtomicLong(0);
    patternList.forEach(p -> sum.getAndAdd(getReflectionNumber(p, true)));
    return sum.toString();
  }
}
