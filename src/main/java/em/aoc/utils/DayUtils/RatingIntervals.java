package em.aoc.utils.DayUtils;

public class RatingIntervals {

  private int xStart;
  private int xEnd;
  private int mStart;
  private int mEnd;
  private int aStart;
  private int aEnd;
  private int sStart;
  private int sEnd;
  private String keyStep;

  public RatingIntervals() {
    this.xStart = 1;
    this.xEnd = 4000;
    this.mStart = 1;
    this.mEnd = 4000;
    this.aStart = 1;
    this.aEnd = 4000;
    this.sStart = 1;
    this.sEnd = 4000;
    keyStep = "in";
  }

  public RatingIntervals(RatingIntervals currentRI) {
    this.xStart = currentRI.xStart;
    this.xEnd = currentRI.xEnd;
    this.mStart = currentRI.mStart;
    this.mEnd = currentRI.mEnd;
    this.aStart = currentRI.aStart;
    this.aEnd = currentRI.aEnd;
    this.sStart = currentRI.sStart;
    this.sEnd = currentRI.sEnd;
    keyStep = currentRI.keyStep;
  }

  public String getKeyStep() {
    return this.keyStep;
  }
public long getPossibleCombinations(){
    return (long) (this.xEnd - this.xStart + 1) *(this.aEnd - this.aStart + 1)*(this.mEnd - this.mStart + 1)*(this.sEnd - this.sStart + 1);
}
  public void setKeyStep(String keyStep) {
    this.keyStep = keyStep;
  }

  public int getEndInterval(String s) {
    switch (s) {
      case "x" -> {
        return xEnd;
      }
      case "m" -> {
        return mEnd;
      }
      case "a" -> {
        return aEnd;
      }
      case "s" -> {
        return sEnd;
      }
      default -> {
        return 0;
      }
    }
  }

  public void setStartValue(String s, int conditionParam) {
    switch (s) {
      case "x" -> this.xStart = conditionParam;
      case "m" -> this.mStart = conditionParam;
      case "a" -> this.aStart = conditionParam;
      case "s" -> this.sStart = conditionParam;
    }
  }

  public void setEndValue(String s, int conditionParam) {
    switch (s) {
      case "x" -> this.xEnd = conditionParam;
      case "m" -> this.mEnd = conditionParam;
      case "a" -> this.aEnd = conditionParam;
      case "s" -> this.sEnd = conditionParam;
    }
  }

  public int getStartInterval(String s) {
    switch (s) {
      case "x" -> {
        return xStart;
      }
      case "m" -> {
        return mStart;
      }
      case "a" -> {
        return aStart;
      }
      case "s" -> {
        return sStart;
      }
      default -> {
        return 0;
      }
    }
  }
}
