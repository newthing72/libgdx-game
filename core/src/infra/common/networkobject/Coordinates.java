package infra.common.networkobject;

public class Coordinates {
  float x;
  float y;

  public Coordinates(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return (int) this.x;
  }

  public int getY() {
    return (int) this.y;
  }

  public float getXReal() {
    return this.x;
  }

  public float getYReal() {
    return this.y;
  }

  public synchronized Coordinates getUp() {
    return new Coordinates(this.getX(), this.getY() + 1);
  }

  public synchronized Coordinates getDown() {
    return new Coordinates(this.getX(), this.getY() - 1);
  }

  public synchronized Coordinates getLeft() {
    return new Coordinates(this.getX() - 1, this.getY());
  }

  public synchronized Coordinates getRight() {
    return new Coordinates(this.getX() + 1, this.getY());
  }


  @Override
  public int hashCode() {
    return (this.x + "," + this.y).hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Coordinates other = (Coordinates) obj;
    return x == other.x && y == other.y;
  }

  public String tostring() {
    return this.getX() + "," + this.getY();
  }
}
