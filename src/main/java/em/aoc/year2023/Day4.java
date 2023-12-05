package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.LoggerFactory;

public class Day4 extends Day {

  private static long sum = 0L;

  public Day4() {
    logger = LoggerFactory.getLogger(Day4.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY4
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }

  private static void countWinningNumbers(String line) {
    line = line.split(":")[1];
    Set<String> winningNumbers = new HashSet<>(
        Arrays.asList(line.strip().split("\\|")[0].strip().split("\\s+")));
    Set<String> myNumbers = new HashSet<>(
        Arrays.asList(line.strip().split("\\|")[1].strip().split("\\s+")));
    winningNumbers.retainAll(myNumbers);
    if (!winningNumbers.isEmpty()) {
      sum += Math.round(Math.pow(2, winningNumbers.size() - 1.0));
    }
  }

  @Override
  public String part1() {
    lines.forEach(Day4::countWinningNumbers);
    return String.valueOf(sum);
  }

  @Override
  public String part2() {
    return null;
  }

}
