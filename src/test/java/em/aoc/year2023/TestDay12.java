package em.aoc.year2023;

import static org.junit.Assert.assertEquals;

import em.aoc.utils.TestDay;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class TestDay12 extends TestDay {

  public TestDay12() {
    logger = LoggerFactory.getLogger(TestDay12.class);
    day = new Day12();
  }

  @Override
  @Test
  public void testPart1() {
    logger.info("Testing part1");
    String result = day.part1();
    assertEquals("21", result);
  }

  @Override
  @Test
  public void testPart2() {
    logger.info("Testing part2");
    String result = day.part2();
    assertEquals("525152", result);
  }
}
