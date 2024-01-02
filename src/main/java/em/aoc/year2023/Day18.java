package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import em.aoc.utils.DayUtils.Coordinates;
import em.aoc.utils.Utilities;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.LoggerFactory;

public class Day18 extends Day {

  public Day18() {
    logger = LoggerFactory.getLogger(Day18.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY18
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }

  @Override
  public String part1() {
    List<Coordinates> coordinatesList = getCoordinatesList();
    return String.valueOf(Utilities.getTotalNumberOfPolygonPoints(coordinatesList));
  }

  private List<Coordinates> getCoordinatesList() {
    List<Coordinates> coordinatesList = new LinkedList<>();
    Coordinates currentPoint = new Coordinates(1, 1);
    coordinatesList.add(currentPoint);
    for (String line : lines) {
      Coordinates coordinates = Coordinates.getDirection(
          line.split(AppConstants.CHAR_SET_SPACE)[0]);
      int steps = Integer.parseInt(line.split(AppConstants.CHAR_SET_SPACE)[1]);
      currentPoint = new Coordinates(currentPoint.x() + coordinates.x() * steps,
          currentPoint.y() + coordinates.y() * steps);
      coordinatesList.add(currentPoint);
    }
    return coordinatesList;
  }


  @Override
  public String part2() {
    List<Coordinates> coordinatesList = getCoordinatesFromHexList();
    return String.valueOf(Utilities.getTotalNumberOfPolygonPoints(coordinatesList));
  }

  private List<Coordinates> getCoordinatesFromHexList() {
    List<Coordinates> coordinatesList = new LinkedList<>();
    Coordinates currentPoint = new Coordinates(0, 0);
    coordinatesList.add(currentPoint);
    for (String line : lines) {
      line = line.split(AppConstants.CHAR_SET_SPACE)[2].replaceAll("[#()]", "");
      Coordinates coordinates = Coordinates.getDirection(
          line.substring(line.length() - 1));
      int steps = Integer.parseInt(line.substring(0, line.length() - 1), 16);
      currentPoint = new Coordinates(currentPoint.x() + coordinates.x() * steps,
          currentPoint.y() + coordinates.y() * steps);
      coordinatesList.add(currentPoint);
    }
    return coordinatesList;
  }
}
