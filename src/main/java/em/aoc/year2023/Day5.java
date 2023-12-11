package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.LoggerFactory;

public class Day5 extends Day {

  private final Map<Integer, List<String[]>> almanac;

  public Day5() {
    logger = LoggerFactory.getLogger(Day5.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY5
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
    almanac = readAlmanac();
  }

  @Override
  public String part1() {
    List<Long> locations = readInitialLocations(false);
    convertSeedLocation(locations, almanac);
    return String.valueOf(Collections.min(locations));
  }

  private void convertSeedLocation(List<Long> locations,
      Map<Integer, List<String[]>> almanac) {
    int j = 0;
    while (j < locations.size()) {
      for (Entry<Integer, List<String[]>> entry : almanac.entrySet()) {
        locations.set(j, findLocationNumber(entry.getValue(), locations.get(j)));
      }
      j++;
    }
  }

  private Long findLocationNumber(List<String[]> mapSourceDestination, Long currentLocation) {
    for (String[] numbers : mapSourceDestination) {
      long sourceStart = Long.parseLong(numbers[0]);
      long destinationStart = Long.parseLong(numbers[1]);
      long rangeLength = Long.parseLong(numbers[2]);
      if (destinationStart <= currentLocation
          && currentLocation <= (destinationStart + rangeLength)) {
        return sourceStart + currentLocation - destinationStart;
      }
    }
    return currentLocation;
  }

  private Map<Integer, List<String[]>> readAlmanac() {
    Map<Integer, List<String[]>> map = new HashMap<>();
    int mapIndex = 0;
    int i = 1;
    while (i < lines.size()) {
      String line = lines.get(i);
      if ("seed-to-soil map:".equals(line) || "light-to-temperature map:".equals(line)
          || "soil-to-fertilizer map:".equals(line) || "fertilizer-to-water map:".equals(line)
          || "water-to-light map:".equals(line) || "temperature-to-humidity map:".equals(line)
          || "humidity-to-location map:".equals(line)) {
        {
          List<String[]> mapSourceDestination = new ArrayList<>();
          i = moveToNewSection(i, mapSourceDestination);
          map.put(mapIndex++, mapSourceDestination);
        }

      }
      i++;
    }
    return map;
  }

  private List<Long> readInitialLocations(boolean isPart2) {
    List<Long> locations = new ArrayList<>();
    String[] seeds = lines.get(0).split(":")[1].strip().split("\\s+");
    int i = 0;
    while (i < seeds.length) {
      if (isPart2) {
        long startSeed = Long.parseLong(seeds[i++]);
        long endSeed = startSeed + Long.parseLong(seeds[i]) - 1;
        locations.add(startSeed);
        locations.add(endSeed);
      } else {
        locations.add(Long.valueOf(seeds[i]));
      }
      i++;
    }
    return locations;
  }

  private int moveToNewSection(int i, List<String[]> mapSourceDestination) {
    i++;
    while (i < lines.size() && lines.get(i).matches("\\d+\\s+\\d+\\s+\\d+")) {
      mapSourceDestination.add(lines.get(i).split("\\s+"));
      i++;
    }
    return i;
  }

  @Override
  public String part2() {
    List<Long> locations = readInitialLocations(true);
    List<Long> minis = new ArrayList<>();
    for (int i = 0; i < locations.size() - 1; i += 2) {
      Long startSeed = locations.get(i);
      Long endSeed = locations.get(i + 1);
      minis.add(binarySearch(startSeed, endSeed));
    }
    return String.valueOf(Collections.min(minis));
  }

  private Long getLocation(Long startSeed) {
    Long location = startSeed;
    for (Entry<Integer, List<String[]>> entry : almanac.entrySet()) {
      location = findLocationNumber(entry.getValue(), location);
    }
    return location;
  }

  private Long binarySearch(long startSeed, long endSeed) {
    if (startSeed == endSeed) {
      return getLocation(startSeed);
    }

    long middleSeed = (startSeed + endSeed) / 2;

    long startSeedLocation = getLocation(startSeed);
    long middleSeedLocation = getLocation(middleSeed);
    long endSeedLocation = getLocation(endSeed);

    if (startSeed == middleSeed) {
      return endSeedLocation;
    }
    return Math.min(
        startSeedLocation + (middleSeed - startSeed) != middleSeedLocation ? binarySearch(startSeed,
            middleSeed) : Long.MAX_VALUE,
        middleSeedLocation + (endSeed - middleSeed) != endSeedLocation ? binarySearch(middleSeed,
            endSeed) : Long.MAX_VALUE
    );
  }

}
