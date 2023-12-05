package em.aoc.year2023;

import static org.junit.Assert.assertEquals;

import em.aoc.utils.TestDay;
import em.aoc.year2015.Day1;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class TestDay1 extends TestDay {

  public TestDay1() {
    logger = LoggerFactory.getLogger(TestDay1.class);
    day = new Day1();
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
