package em.aoc;

import em.aoc.utils.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

  private static final Logger logger = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    if (args.length >= 2) {
    String result = Utilities.getResults(args, "Day");
    logger.info(result);
    } else {
      logger.error("Insufficient command-line arguments.");
      logger.error("The arguments should be the following format: year day (part).");
    }
  }
}
