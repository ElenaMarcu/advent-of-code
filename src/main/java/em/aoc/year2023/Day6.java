package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.LoggerFactory;

public class Day6 extends Day {

  public Day6() {
    logger = LoggerFactory.getLogger(Day6.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY6
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }

  @Override
  public String part1() {
    List<Long[]> mapTimeDistance = readMap();
    return findTotalNoOfWays(mapTimeDistance);
  }

  private String findTotalNoOfWays(List<Long[]> mapTimeDistance) {
    AtomicInteger noOfWays = new AtomicInteger(1);
    mapTimeDistance.forEach(v -> noOfWays.set(noOfWays.get() * countWays(v)));
    return String.valueOf(noOfWays.get());
  }

  private int countWays(Long[] v) {
    long time = v[0];
    long distance = v[1];
    return isRecord(distance, time, time / 2, -1) + isRecord(distance, time, time / 2 + 1, 1);
  }

  private int isRecord(long distance, long time, long pressTime, int parameter) {
    if (pressTime == 0) {
      return 0;
    }
    if (pressTime == time) {
      return 0;
    }
    if ((time - pressTime) * pressTime > distance) {
      return 1 + isRecord(distance, time, pressTime + parameter, parameter);
    }
    return 0;
  }


  private List<Long[]> readMap() {
    String[] time = lines.get(0).split(AppConstants.CHAR_SET_COLON)[1].trim().split(AppConstants.CHAR_SET_SPACE);
    String[] distance = lines.get(1).split(AppConstants.CHAR_SET_COLON)[1].trim().split(AppConstants.CHAR_SET_SPACE);
    List<Long[]> map = new ArrayList<>();
    for (int i = 0; i < time.length; i++) {
      map.add(new Long[]{Long.parseLong(time[i]), Long.parseLong(distance[i])});
    }
    return map;
  }

  @Override
  public String part2() {
    String timeString = lines.get(0).split(AppConstants.CHAR_SET_COLON)[1].trim().replaceAll(AppConstants.CHAR_SET_SPACE, "");
    String distanceString = lines.get(1).split(AppConstants.CHAR_SET_COLON)[1].trim().replaceAll(AppConstants.CHAR_SET_SPACE, "");
    long time = Long.parseLong(timeString);
    long distance = Long.parseLong(distanceString);
    double sqrt = Math.sqrt(time * time - 4.0 * distance);
    long x1 = (long) Math.ceil((-time - sqrt) / -2);
    long x2 = (long) Math.ceil((-time + sqrt) / -2);
    long result = x1 - x2;
    return String.valueOf(result);
  }
}
