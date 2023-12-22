package em.aoc.utils.DayUtils;

public class Rating {

  private int x;
  private int m;
  private int a;
  private int s;

  public Rating(int x, int m, int a, int s) {
    this.x = x;
    this.m = m;
    this.a = a;
    this.s = s;
  }

  public Rating(String line) {
    String[] values = line.split(",");
    for (String value : values) {
      if (value.startsWith("x")) {
        this.x = Integer.parseInt(value.split("=")[1]);
      } else if (value.startsWith("m")) {
        this.m = Integer.parseInt(value.split("=")[1]);
      } else if (value.startsWith("a")) {
        this.a = Integer.parseInt(value.split("=")[1]);
      } else if (value.startsWith("s")) {
        this.s = Integer.parseInt(value.split("=")[1]);
      }
    }
  }

  public long getPartSum() {
    return s + a + x + m;
  }

  public int getPartValue(String part) {
    switch (part) {
      case "x" -> {
        return x;
      }
      case "m" -> {
        return m;
      }
      case "a" -> {
        return a;
      }
      case "s" -> {
        return s;
      }
      default -> {
        return 0;
      }
    }
  }
}
