package em.aoc.utils.DayUtils;

public class Beam {

  private int x;
  private int y;
  private DIRECTION direction;

  public Beam() {

  }

  public Beam(int x, int y, DIRECTION direction) {
    this.x = x;
    this.y = y;
    this.direction = direction;
  }

  public Beam(Beam c) {
    this.x = c.getX();
    this.y = c.getY();
    this.direction = c.getDirection();
  }

  public DIRECTION getDirection() {
    return direction;
  }

  public void setDirection(DIRECTION direction) {
    this.direction = direction;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  @Override
  public String toString() {
    return x + "," + y + "," + direction.getValue();
  }
}
