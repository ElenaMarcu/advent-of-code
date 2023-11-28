package em.aoc.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utilities {

  static Logger logger = LoggerFactory.getLogger(Utilities.class);

  private Utilities() {

  }

  public static List<String> readLines(String filePath) {
    List<String> lines = null;
    try {
      lines = Files.readAllLines(Paths.get(filePath));
    } catch (IOException exception) {
      logger.error("An error occurred when reading the file: {}", filePath);
      logger.error(exception.toString());
    }
    return lines;
  }

  public static String getResults(String[] args, String className) {
    String year = args[0];
    String dayNo = args[1];
    Day day = Day.getClassInstance(year, dayNo, className);
    if (args.length >= 3) {
      String partNo = args[2];
      if ("1".equals(partNo)) {
        return day.part1();
      }
      if ("2".equals(partNo)) {
        return day.part2();
      }
    } else {
      return day.part1() + "/n" + day.part2();
    }
    return "";
  }
}
