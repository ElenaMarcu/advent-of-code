package em.aoc;

import em.aoc.utils.TestDay;
import em.aoc.utils.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMain {

  private static final Logger logger = LoggerFactory.getLogger(TestMain.class);

  public static void main(String[] args) {
    if (args.length >= 2) {
      TestDay day = (TestDay) Utilities.getClassInstance(args, "TestDay");
      if (args.length >= 3) {
        String partNo = args[2];
        if ("1".equals(partNo)) {
          day.testPart1();
          return;
        }
        if ("2".equals(partNo)) {
          day.testPart2();
          return;
        }
      }
      day.testPart1();
      day.testPart2();
    } else {
      logger.error("Insufficient command-line arguments.");
      logger.error("The arguments should be the following format: year day (part).");
    }
  }
}
