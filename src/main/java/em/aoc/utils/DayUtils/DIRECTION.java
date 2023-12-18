package em.aoc.utils.DayUtils;

public enum DIRECTION {
  UP("up"),
  DOWN("down"),
  LEFT("left"),
  RIGHT("right");

  private String value;

  DIRECTION(String value) {
    this.value = value;
  }

  String getValue() {
    return this.value;
  }
}
