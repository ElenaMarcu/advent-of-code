package em.aoc.year2023;

import em.aoc.utils.AppConstants;
import em.aoc.utils.Day;
import em.aoc.utils.DayUtils.Beam;
import em.aoc.utils.DayUtils.DIRECTION;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.LoggerFactory;

public class Day16 extends Day {

  public Day16() {
    logger = LoggerFactory.getLogger(Day16.class);
    filePath = AppConstants.RESOURCES_PATH_MAIN + AppConstants.YEAR_2023 + AppConstants.DAY16
        + AppConstants.TXT_EXTENSION;
    lines = readLines();
  }

  @Override
  public String part1() {
    List<String> energizedTiles = getTiles();
    energizeTiles(energizedTiles, new Beam(0, 0, DIRECTION.RIGHT));
    return String.valueOf(countEnergizedTiles(energizedTiles));
  }

  private long countEnergizedTiles(List<String> energizedTiles) {
    AtomicLong totalNo = new AtomicLong(0);
    energizedTiles.forEach(l-> totalNo.getAndAdd(l.chars().filter(ch -> ch == '#').count()));
    return totalNo.longValue();
  }

  private void energizeTiles(List<String> energizedTiles, Beam beam) {
    List<String> visited = new ArrayList<>();
    Queue<Beam> queue = new ArrayDeque<>();
    queue.add(beam);
    while (!queue.isEmpty()) {
      Beam c = queue.remove();
      if (isOutOfBounds(c) || visited.contains(c.toString())) {
        continue;
      }
      visited.add(c.toString());
      energizedTiles.set(c.getX(),
          energizedTiles.get(c.getX()).substring(0, c.getY()) + "#" + energizedTiles.get(c.getX())
              .substring(c.getY() + 1));
      moveToNextBeam(queue, c);
    }
  }

  private void moveToNextBeam(Queue<Beam> queue, Beam c) {
    DIRECTION direction = c.getDirection();
    char contraption = lines.get(c.getX()).charAt(c.getY());
    if (contraption == '.' || (
        contraption == '-' && (direction.equals(DIRECTION.RIGHT) || direction.equals(
            DIRECTION.LEFT)) || (contraption == '|' && (direction.equals(DIRECTION.DOWN)
            || direction.equals(DIRECTION.UP))))) {
      queue.add(updateCoordinates(c, direction));
    } else if (contraption == '/') {
      switch (direction) {
        case UP -> queue.add(updateCoordinates(c, DIRECTION.RIGHT));
        case DOWN -> queue.add(updateCoordinates(c, DIRECTION.LEFT));
        case LEFT -> queue.add(updateCoordinates(c, DIRECTION.DOWN));
        case RIGHT -> queue.add(updateCoordinates(c, DIRECTION.UP));
      }
    } else if (contraption == '\\') {
      switch (direction) {
        case UP -> queue.add(updateCoordinates(c, DIRECTION.LEFT));
        case DOWN -> queue.add(updateCoordinates(c, DIRECTION.RIGHT));
        case LEFT -> queue.add(updateCoordinates(c, DIRECTION.UP));
        case RIGHT -> queue.add(updateCoordinates(c, DIRECTION.DOWN));
      }
    } else if (contraption == '-') {
      queue.add(updateCoordinates(c, DIRECTION.RIGHT));
      queue.add(updateCoordinates(c, DIRECTION.LEFT));
    } else if (contraption == '|') {
      queue.add(updateCoordinates(c, DIRECTION.DOWN));
      queue.add(updateCoordinates(c, DIRECTION.UP));
    }
  }

  private Beam updateCoordinates(Beam beam, DIRECTION direction) {
    switch (direction) {
      case UP -> beam.setX(beam.getX() - 1);
      case RIGHT -> beam.setY(beam.getY() + 1);
      case LEFT -> beam.setY(beam.getY() - 1);
      case DOWN -> beam.setX(beam.getX() + 1);
    }
    beam.setDirection(direction);
    return new Beam(beam);
  }

  private boolean isOutOfBounds(Beam c) {
    return c.getX() < 0 || c.getX() > (lines.size() - 1) || c.getY() < 0
        || c.getY() > (lines.get(0).length() - 1);
  }

  private List<String> getTiles() {
    List<String> tiles = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    sb.append(".".repeat(lines.get(0).length()));
    for (int i = 0; i < lines.size(); i++) {

      tiles.add(sb.toString());
    }
    return tiles;
  }

  @Override
  public String part2() {
    List<String> energizedTiles = getTiles();
    Long max = Long.MIN_VALUE;
    for(int i=0;i<energizedTiles.size();i++){
      energizedTiles = getTiles();
      energizeTiles(energizedTiles, new Beam(i, 0, DIRECTION.RIGHT));
      max = Math.max(max, countEnergizedTiles(energizedTiles));
      energizedTiles = getTiles();
      energizeTiles(energizedTiles, new Beam(i, energizedTiles.get(0).length()-1, DIRECTION.LEFT));
      max = Math.max(max, countEnergizedTiles(energizedTiles));
    }
    for(int j=0;j<energizedTiles.get(0).length();j++){
      energizedTiles = getTiles();
      energizeTiles(energizedTiles, new Beam(0, j, DIRECTION.DOWN));
      max = Math.max(max, countEnergizedTiles(energizedTiles));
      energizedTiles = getTiles();
      energizeTiles(energizedTiles, new Beam(energizedTiles.size()-1,j, DIRECTION.UP));
      max = Math.max(max, countEnergizedTiles(energizedTiles));
    }
    return String.valueOf(max);
  }

}
