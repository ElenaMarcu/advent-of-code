package em.aoc.utils.DayUtils;

public record Coordinates(int x, int y) {


  public static Coordinates getDirection(String s) {
    switch (s) {
      case "R", "0" -> {
        return new Coordinates(0, 1);
      }
      case "D", "1" -> {
        return new Coordinates(1, 0);
      }
      case "L", "2" -> {
        return new Coordinates(0, -1);
      }
      case "U", "3" -> {
        return new Coordinates(-1, 0);
      }
      default -> {
        return new Coordinates(0, 0);
      }
    }
  }
}
