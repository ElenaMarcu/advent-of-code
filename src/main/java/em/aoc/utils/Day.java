package em.aoc.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;

public abstract class Day {

  protected static Logger logger;
  protected static String filePath;
  protected List<String> lines;

  protected static List<String> readLines() {
    List<String> lines = null;
    try {
      lines = Files.readAllLines(Paths.get(filePath));
    } catch (IOException exception) {
      logger.error("An error occurred when reading the file: {}", filePath);
      logger.error(exception.toString());
    }
    return lines;
  }

  abstract public String part1();

  abstract public String part2();


}
