package em.aoc.utils;

import em.aoc.utils.DayUtils.Coordinates;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
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
    return day.part1() + "\r\n" + day.part2();
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

  public static long getNoOfInteriorPoints(List<Coordinates> coordinatesList) {
    long area = 0L;
    Coordinates point1 = coordinatesList.get(0);
    Coordinates point2;
    for (int i = 1; i < coordinatesList.size(); i++) {
      point2 = coordinatesList.get(i);
      area += (long) point1.x() * point2.y() - (long) point1.y() * point2.x();
      point1 = point2;
    }
    if (point1 != coordinatesList.get(0)) {
      point2 = coordinatesList.get(0);
      area += (long) point1.x() * point2.y() - (long) point1.y() * point2.x();
    }
    area = area > 0 ? area / 2 : -1 * area / 2;
    return (long) (area - getNoOfExteriorPoints(coordinatesList) / 2.0 + 1);
  }


  private static long getNoOfExteriorPoints(List<Coordinates> coordinatesList) {
    long totalNo = 0L;
    Coordinates point1 = coordinatesList.get(0);
    Coordinates point2;
    for (int i = 1; i < coordinatesList.size(); i++) {
      point2 = coordinatesList.get(i);
      totalNo += Math.abs(point2.x() - point1.x()) + Math.abs(point2.y() - point1.y());
      point1 = point2;
    }
    if (point1 != coordinatesList.get(0)) {
      point2 = coordinatesList.get(0);
      totalNo += Math.abs(point2.x() - point1.x()) + Math.abs(point2.y() - point1.y());
    }
    return totalNo;
  }

  public static long getTotalNumberOfPolygonPoints(List<Coordinates> coordinatesList) {
    return getNoOfInteriorPoints(coordinatesList) + getNoOfExteriorPoints(coordinatesList);
  }
}
