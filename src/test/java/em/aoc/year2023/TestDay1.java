package em.aoc.year2023;

import static org.junit.Assert.assertEquals;

import em.aoc.utils.Day;
import em.aoc.utils.TestDay;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDay1 implements TestDay {

  private static final Logger logger = LoggerFactory.getLogger(TestDay1.class);
  private final Day day = new Day1();

  public TestDay1() {
  }

  @Override
  @Test
  public void testPart1() {
    logger.info("Testing part1");
    String result = day.part1();
    assertEquals("142", result);
  }

  @Override
  @Test
  public void testPart2() {
    logger.info("Testing part2");
    String result = day.part2();
    assertEquals("281", result);
  }
}
