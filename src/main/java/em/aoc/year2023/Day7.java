package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.DayUtils.CardsComparator;
import em.aoc.utils.Day;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.LoggerFactory;

public class Day7 extends Day {

  public Day7() {
    logger = LoggerFactory.getLogger(Day7.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY7
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }


  @Override
  public String part1() {
    Map<String, String> allCards = getAllCards();
    Set<String> orderedCards = orderCards(allCards, false);
    return getTotalWinnings(orderedCards, allCards);
  }

  private String getTotalWinnings(Set<String> orderedCards, Map<String, String> allCards) {
    AtomicInteger rank = new AtomicInteger(1);
    AtomicLong sum = new AtomicLong();
    orderedCards.forEach(hand -> {
      sum.addAndGet(Long.parseLong(allCards.get(hand)) * rank.get());
      rank.getAndIncrement();
    });
    return sum.toString();
  }

  private Set<String> orderCards(Map<String, String> allCards, boolean isPart2) {
    Set<String> orderedCards = new TreeSet<>(new CardsComparator(isPart2));
    allCards.forEach((k, v) -> orderedCards.add(k));
    return orderedCards;
  }

  private Map<String, String> getAllCards() {
    Map<String, String> allCards = new HashMap<>();
    lines.forEach(line -> allCards.put(line.split(AppConstants.CHAR_SET_SPACE)[0], line.split(AppConstants.CHAR_SET_SPACE)[1]));
    return allCards;
  }

  @Override
  public String part2() {
    Map<String, String> allCards = getAllCards();
    Set<String> orderedCards = orderCards(allCards, true);
    return getTotalWinnings(orderedCards, allCards);
  }
}
