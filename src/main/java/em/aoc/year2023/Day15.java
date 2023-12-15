package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.slf4j.LoggerFactory;

public class Day15 extends Day {

  private final List<String> stringList;

  public Day15() {
    logger = LoggerFactory.getLogger(Day15.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY15
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
    stringList = Arrays.stream(lines.get(0).split(",")).toList();
  }

  @Override
  public String part1() {

    AtomicReference<Long> finalSum = new AtomicReference<>(0L);
    stringList.forEach(s -> finalSum.updateAndGet(v -> v + getHashValue(s)));
    return finalSum.toString();
  }

  private int getHashValue(String s) {
    final int[] sum = {0};
    s.chars().forEach(ch -> sum[0] = ((sum[0] + ch) * 17) % 256);
    return sum[0];
  }

  @Override
  public String part2() {
    Map<Integer, Map<String, Integer>> boxes = updateContentOfBoxes();
    AtomicLong sum = new AtomicLong(0L);
    boxes.forEach((k, v) -> sum.updateAndGet(s -> s + getFocusPower(k + 1, v)));
    return sum.toString();
  }

  private long getFocusPower(Integer k, Map<String, Integer> v) {
    long sum = 0;
    int slot = 0;
    for (Entry<String, Integer> entry : v.entrySet()) {
      slot++;
      sum += (long) k * slot * entry.getValue();
    }
    return sum;
  }

  private Map<Integer, Map<String, Integer>> updateContentOfBoxes() {
    Map<Integer, Map<String, Integer>> boxes = new HashMap<>();
    for (String s : stringList) {
      String label = s.split("[=\\-]")[0];
      int boxNo = getHashValue(label);
      boxes.putIfAbsent(boxNo, new LinkedHashMap<>());
      var map = boxes.get(boxNo);
      if (s.contains("-")) {
        map.remove(label);
        if (map.isEmpty()) {
          boxes.remove(boxNo);
        }
      } else {
        map.put(label, Integer.valueOf(s.split("[=\\-]")[1]));
      }
    }
    return boxes;
  }
}
