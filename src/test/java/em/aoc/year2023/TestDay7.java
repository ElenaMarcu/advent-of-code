package em.aoc.year2023;

import static org.junit.Assert.assertEquals;

import em.aoc.utils.TestDay;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class TestDay7 extends TestDay {

  public TestDay7() {
    logger = LoggerFactory.getLogger(TestDay7.class);
    day = new Day7();
  }

  @Override
  @Test
  public void testPart1() {
    logger.info("Testing part1");
    String result = day.part1();
    assertEquals("6440", result);
  }

  @Override
  @Test
  public void testPart2() {
    logger.info("Testing part2");
    String result = day.part2();
    assertEquals("5905", result);
  }
}
