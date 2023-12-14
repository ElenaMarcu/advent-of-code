package em.aoc.year2023;

import static org.junit.Assert.assertEquals;

import em.aoc.utils.TestDay;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class TestDay11 extends TestDay {

  public TestDay11() {
    logger = LoggerFactory.getLogger(TestDay11.class);
    day = new Day11();
  }

  @Override
  @Test
  public void testPart1() {
    logger.info("Testing part1");
    String result = day.part1();
    assertEquals("374", result);
  }

  @Override
  @Test
  public void testPart2() {
    logger.info("Testing part2");
    String result = day.part2();
    assertEquals("2", result);
  }
}