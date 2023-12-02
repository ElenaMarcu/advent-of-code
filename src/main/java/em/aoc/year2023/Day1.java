package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.LoggerFactory;

public class Day1 extends Day {

  private static Long sum;

  public Day1() {
    logger = LoggerFactory.getLogger(Day1.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY1
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }

  private static void sumCalibrations(String line) {
    sum += getFirstDigit(line) * 10L + getLastDigit(line);
  }

  private static int getLastDigit(String line) {
    for (int i = line.length() - 1; i >= 0; i--) {
      char c = line.charAt(i);
      if (Character.isDigit(c)) {
        return c - '0';
      }
    }
    return '\0';
  }

  private static int getFirstDigit(String line) {
    for (char c : line.toCharArray()) {
      if (Character.isDigit(c)) {
        return c - '0';
      }
    }
    return '\0';
  }

  private static void sumMixedCalibrations(String line) {
    StringBuilder sb = new StringBuilder(line);
    Pattern pattern = Pattern.compile(
        "zero|one|two|three|four|five|six|seven|eight|nine|\\d");
    Pattern patternReverse = Pattern.compile(
        "orez|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin|\\d");
    Matcher matcher = pattern.matcher(line);

    if (matcher.find()) {
      sum += getReplacement(matcher.group()) * 10L;
    }

    matcher = patternReverse.matcher(sb.reverse().toString());
    if (matcher.find()) {
      sum += getReplacement(matcher.group());
    }
  }

  private static long getReplacement(String digit) {
    return switch (digit) {
      case "zero", "orez" -> 0L;
      case "one", "eno" -> 1L;
      case "two", "owt" -> 2L;
      case "three", "eerht" -> 3L;
      case "four", "ruof" -> 4L;
      case "five", "evif" -> 5L;
      case "six", "xis" -> 6L;
      case "seven", "neves" -> 7L;
      case "eight", "thgie" -> 8L;
      case "nine", "enin" -> 9L;
      default -> Long.parseLong(digit);
    };
  }

  public String part1() {
    sum = 0L;
    lines.forEach(Day1::sumCalibrations);
    return sum.toString();
  }

  public String part2() {
    sum = 0L;
    lines.forEach(Day1::sumMixedCalibrations);
    return sum.toString();
  }
}
