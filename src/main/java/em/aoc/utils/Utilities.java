package em.aoc.utils;

import java.lang.reflect.InvocationTargetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utilities {

  static Logger logger = LoggerFactory.getLogger(Utilities.class);

  private Utilities() {

  }

  public static String getResults(String[] args, Day day) {
    if (args.length >= 3) {
      String partNo = args[2];
      if ("1".equals(partNo)) {
        return day.part1();
      }
      if ("2".equals(partNo)) {
        return day.part2();
      }
    }
    return day.part1() + "/n" + day.part2();
  }

  public static Object getClassInstance(String[] args, String className) {
    String year = args[0];
    String day = args[1];
    try {
      String classPath = "em.aoc.year" + year + "." + className + day;
      return Class.forName(classPath).getDeclaredConstructor()
          .newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
             NoSuchMethodException |
             ClassNotFoundException exception) {
      logger.error("Error creating instance: {}", exception.toString());
    }
    return null;
  }
}
