package em.aoc.year2015;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;

public class Day1 extends Day {

  public Day1() {
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2015 + AppConstants.DAY1
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }

  @Override
  public String part1() {
    long upLevels = lines.get(0).chars().filter(w -> '(' == w).count();
    long downLevels = lines.get(0).length() - upLevels;
    return String.valueOf(upLevels - downLevels);
  }

  @Override
  public String part2() {
    return null;
  }
}
