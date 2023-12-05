package em.aoc.year2015;

import static org.junit.Assert.assertEquals;

import em.aoc.utils.TestDay;
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
    Day1 day = new Day1();
    long result = Integer.parseInt(day.part1());
    assertEquals(3, result);
  }

  @Override
  public void testPart2() {

  }
}
