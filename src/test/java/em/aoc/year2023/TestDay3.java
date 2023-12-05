package em.aoc.year2023;

import static org.junit.Assert.assertEquals;

import em.aoc.utils.TestDay;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class TestDay3 extends TestDay {

  public TestDay3() {
    logger = LoggerFactory.getLogger(TestDay3.class);
    day = new Day3();
  }
  @Override
  @Test
  public void testPart1() {
    logger.info("Testing part1");
    String result = day.part1();
    assertEquals("4361", result);
  }

  @Override
  @Test
  public void testPart2() {

  }
}
