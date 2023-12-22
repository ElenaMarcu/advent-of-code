package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import org.slf4j.LoggerFactory;

public class Day9 extends Day {

  private final List<List<Long>> history = new ArrayList<>();

  public Day9() {
    logger = LoggerFactory.getLogger(Day9.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY9
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
    lines.forEach(
        s -> history.add(Stream.of(s.split(AppConstants.CHAR_SET_SPACE)).mapToLong(Long::parseLong).boxed().toList()));

  }

  @Override
  public String part1() {
    AtomicLong sum = new AtomicLong(0);
    history.forEach(h -> sum.set(sum.get() + getPrediction(h)));
    return sum.toString();
  }

  private long getPrediction(List<Long> h) {
    List<Long> newSequence = new ArrayList<>();
    if (valuesAreAllZero(h, newSequence)) {
      return h.get(0) + newSequence.get(0);
    }
    return h.get(h.size() - 1) + getPrediction(newSequence);
  }


  private long getHistoryPrediction(List<Long> h) {

    List<Long> newSequence = new ArrayList<>();

    if (valuesAreAllZero(h, newSequence)) {
      return h.get(0) - newSequence.get(0);
    }

    return h.get(0) - getHistoryPrediction(newSequence);
  }

  private boolean valuesAreAllZero(List<Long> h, List<Long> newSequence) {
    boolean isZero = true;
    newSequence.add(h.get(1) - h.get(0));
    for (int i = 2; i < h.size(); i++) {
      long difference = h.get(i) - h.get(i - 1);
      newSequence.add(difference);
      if (difference != newSequence.get(0)) {
        isZero = false;
      }
    }
    return isZero;
  }

  @Override
  public String part2() {
    AtomicLong sum = new AtomicLong(0);
    history.forEach(h -> sum.set(sum.get() + getHistoryPrediction(h)));
    return sum.toString();
  }
}
