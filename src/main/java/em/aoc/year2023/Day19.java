package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import em.aoc.utils.DayUtils.Rating;
import em.aoc.utils.DayUtils.RatingIntervals;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.LoggerFactory;

public class Day19 extends Day {

  private final Map<String, List<String>> workflow;
  private final List<Rating> parts;

  public Day19() {
    logger = LoggerFactory.getLogger(Day19.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY19
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
    workflow = new LinkedHashMap<>();
    parts = new ArrayList<>();
    populateWorkflowAndParts(workflow, parts);
  }

  @Override
  public String part1() {
    List<Rating> acceptedParts = new ArrayList<>();
    parts.forEach(p -> {
      if (isAccepted(p, workflow)) {
        acceptedParts.add(p);
      }
    });
    AtomicLong sum = new AtomicLong(0);
    acceptedParts.forEach(ap -> sum.updateAndGet(s -> s + ap.getPartSum()));
    return sum.toString();
  }

  private boolean isAccepted(Rating p, Map<String, List<String>> workflow) {
    String currentKey = "in";
    while (true) {
      List<String> steps = workflow.get(currentKey);
      String result = getCurrentWorkflowResult(p, steps);
      if ("A".equals(result)) {
        return true;
      }
      if ("R".equals(result)) {
        return false;
      }
      currentKey = result;
    }
  }

  private String getCurrentWorkflowResult(Rating p, List<String> steps) {
    for (String step : steps) {
      if ("A".equals(step) || "R".equals(step) || step.matches(
          AppConstants.CHAR_SET_SMALL_LETTER_WORDS)) {
        return step;
      }
      int partValue = p.getPartValue(step.split(AppConstants.CHAR_SET_BIGGER_OR_SMALLER)[0]);
      int conditionParam = Integer.parseInt(
          step.split(AppConstants.CHAR_SET_BIGGER_OR_SMALLER)[1].split(":")[0]);
      String destination = step.split(AppConstants.CHAR_SET_BIGGER_OR_SMALLER)[1].split(":")[1];
      if ((step.contains("<") && partValue < conditionParam) || (step.contains(">")
          && partValue > conditionParam)) {
        return destination;
      }
    }
    return steps.get(steps.size() - 1);
  }

  private void populateWorkflowAndParts(Map<String, List<String>> workflow, List<Rating> parts) {
    for (String line : lines) {
      if (line.matches("^[a-z]+\\{.*$")) {
        String key = line.split("\\{")[0];
        List<String> steps = List.of(line.split("\\{")[1].replace("}", "").split(","));
        workflow.put(key, steps);
        continue;
      }
      if (line.startsWith("{")) {
        parts.add(new Rating(line.replace("{", "").replace("}", "")));
      }
    }
  }

  @Override
  public String part2() {
    List<RatingIntervals> acceptedRatingIntervals = new ArrayList<>();
    findAcceptedRatings(acceptedRatingIntervals);
    AtomicLong sum = new AtomicLong(0);
    acceptedRatingIntervals.forEach(s -> sum.getAndAdd(s.getPossibleCombinations()));
    return sum.toString();
  }

  private void findAcceptedRatings(List<RatingIntervals> acceptedRatingIntervals) {
    Queue<RatingIntervals> queue = new ArrayDeque<>();
    queue.add(new RatingIntervals());
    while (!queue.isEmpty()) {
      RatingIntervals currentRI = queue.remove();
      List<String> steps = workflow.get(currentRI.getKeyStep());
      for (String step : steps) {
        if ("A".equals(step)) {
          acceptedRatingIntervals.add(currentRI);
        } else if ("R".equals(step)) {
          break;
        } else if (step.matches(AppConstants.CHAR_SET_SMALL_LETTER_WORDS)) {
          RatingIntervals intervals = new RatingIntervals(currentRI);
          intervals.setKeyStep(step);
          queue.add(new RatingIntervals(intervals));
        } else {
          deconstructRule(step, currentRI, acceptedRatingIntervals, queue);
        }
      }
    }
  }

  private void deconstructRule(String step, RatingIntervals currentRI,
      List<RatingIntervals> acceptedRatingIntervals, Queue<RatingIntervals> queue) {
    int conditionParam = Integer.parseInt(
        step.split(AppConstants.CHAR_SET_BIGGER_OR_SMALLER)[1].split(":")[0]);
    String destination = step.split(AppConstants.CHAR_SET_BIGGER_OR_SMALLER)[1].split(":")[1];
    String category = step.split(AppConstants.CHAR_SET_BIGGER_OR_SMALLER)[0];
    int endValue = currentRI.getEndInterval(category);
    int startValue = currentRI.getStartInterval(category);
    RatingIntervals ri = new RatingIntervals(currentRI);
    if (endValue >= conditionParam && startValue < conditionParam) {
      if (step.contains("<")) {
        currentRI.setStartValue(category, conditionParam);
        ri.setEndValue(category, conditionParam - 1);
      } else if (step.contains(">")) {
        ri.setStartValue(category, conditionParam + 1);
        currentRI.setEndValue(category, conditionParam);
      }
    }
    if ("A".equals(destination)) {
      acceptedRatingIntervals.add(ri);
    } else if (destination.matches(AppConstants.CHAR_SET_SMALL_LETTER_WORDS)) {
      ri.setKeyStep(destination);
      queue.add(ri);
    }
  }
}
