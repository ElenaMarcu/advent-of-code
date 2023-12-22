package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.LoggerFactory;

public class Day4 extends Day {

  private static long sum = 0L;
  private static Map<Integer, Long> cardInstances;

  public Day4() {
    logger = LoggerFactory.getLogger(Day4.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY4
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }

  private static void countWinningNumbers(String line) {
    int noOfWinningNumbers = getNoWinningNumbers(line);
    if (noOfWinningNumbers > 0) {
      sum += Math.round(Math.pow(2, noOfWinningNumbers - 1.0));
    }
  }

  private static void countCardInstances(String line) {
    int cardNumber = Integer.parseInt(line.split(AppConstants.CHAR_SET_COLON)[0].split(AppConstants.CHAR_SET_SPACE)[1]);
    int noOfWinningNumbers = getNoWinningNumbers(line);
    cardInstances.putIfAbsent(cardNumber, 0L);
    cardInstances.computeIfPresent(cardNumber, (key, value) -> value + 1);
    if (noOfWinningNumbers > 0) {
      updateFurtherCardInstances(noOfWinningNumbers, cardNumber);
    }
  }

  private static void updateFurtherCardInstances(int size, Integer cardNumber) {
    int i = 1;
    while (i <= size) {
      cardInstances.putIfAbsent(cardNumber + i, 0L);
      cardInstances.computeIfPresent(cardNumber + i++,
          (key, value) -> value + cardInstances.get(cardNumber));
    }
  }

  private static int getNoWinningNumbers(String line) {
    line = line.split(AppConstants.CHAR_SET_COLON)[1];
    Set<String> winningNumbers = new HashSet<>(
        Arrays.asList(line.strip().split("\\|")[0].strip().split(AppConstants.CHAR_SET_SPACE)));
    Set<String> myNumbers = new HashSet<>(
        Arrays.asList(line.strip().split("\\|")[1].strip().split(AppConstants.CHAR_SET_SPACE)));
    winningNumbers.retainAll(myNumbers);
    return winningNumbers.size();
  }

  @Override
  public String part1() {
    lines.forEach(Day4::countWinningNumbers);
    return String.valueOf(sum);
  }

  @Override
  public String part2() {
    cardInstances = new HashMap<>();
    lines.forEach(Day4::countCardInstances);
    cardInstances.values().forEach(value -> sum += value);
    return String.valueOf(sum);
  }

}
