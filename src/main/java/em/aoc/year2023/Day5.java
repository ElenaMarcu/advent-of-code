package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.LoggerFactory;

public class Day5 extends Day {

  private List<Long> locations;

  public Day5() {
    logger = LoggerFactory.getLogger(Day5.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY5
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }

  @Override
  public String part1() {
    locations = new ArrayList<>();
    computeSeedsLocation(false);
    return findLowestLocation();
  }

  private String findLowestLocation() {
    long min = Long.MAX_VALUE;
    for (long location : locations) {
      if (min > location) {
        min = location;
      }
    }
    return String.valueOf(min);
  }

  private void computeSeedsLocation(boolean isPart2) {
    int i = 0;
    while (i < lines.size()) {
      String line = lines.get(i);
      if (line.startsWith("seeds:")) {
        line = line.split(":")[1].strip();
        addSeeds(line, isPart2);
      }
      i = switch (line) {
        case "seed-to-soil map:", "light-to-temperature map:", "soil-to-fertilizer map:", "fertilizer-to-water map:", "water-to-light map:", "temperature-to-humidity map:", "humidity-to-location map:" ->
            convertSeedNumber(i, isPart2);
        default -> i;
      };
      i++;
    }
  }

  private void addSeeds(String line, boolean isPart2) {
    String[] seeds = line.split("\\s+");
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
    logger.info(Arrays.toString(locations.toArray()));
  }

  private int convertSeedNumber(int i, boolean isPart2) {
    i++;
    List<String[]> mapSourceDestination = new ArrayList<>();
    while (i < lines.size() && lines.get(i).matches("\\d+\\s+\\d+\\s+\\d+")) {
      mapSourceDestination.add(lines.get(i).split("\\s+"));
      i++;
    }
    updatePosition(mapSourceDestination, isPart2);

    logger.info(Arrays.toString(locations.toArray()));
    return i;
  }

  private void updatePosition(List<String[]> mapSourceDestination, boolean isPart2) {
    int j = 0;
    while (j < locations.size()) {
      for (String[] numbers : mapSourceDestination) {
        long sourceStart = Long.parseLong(numbers[0]);
        long destinationStart = Long.parseLong(numbers[1]);
        long rangeLength = Long.parseLong(numbers[2]);
        if (isPart2) {
          if (destinationStart <= locations.get(j)
              && locations.get(j + 1) <= (destinationStart + rangeLength)) {
            locations.set(j, sourceStart + locations.get(j) - destinationStart);
            locations.set(j + 1, sourceStart + locations.get(j + 1) - destinationStart);

            break;
          }
        } else {
          if (destinationStart <= locations.get(j)
              && locations.get(j) <= (destinationStart + rangeLength)) {
            locations.set(j, sourceStart + locations.get(j) - destinationStart);
            break;
          }
        }
      }
      if (isPart2) {
        j++;
      }
      j++;
    }
  }

  @Override
  public String part2() {
    locations = new ArrayList<>();
    computeSeedsLocation(true);
    return findLowestLocation();
  }
}
