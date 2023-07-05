package edu.cmu.cs214.hw3.map;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Position(Position other) {
        this.x = other.x;
        this.y = other.y;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    @Override
    public String toString() {
        return this.x+""+this.y+"";
    }

    public static String getDirection(Position pre, Position nxt) {
        int deltaX = nxt.getX() - pre.getX();
        int deltaY = nxt.getY() - pre.getY();
        if (Math.abs(deltaX) == 0 && Math.abs(deltaY) == 0) {
            return "mid";
        }
        if (Math.abs(deltaX) > 1 || Math.abs(deltaY) > 1) {
            return "invalid";
        }
        if (deltaX == -1) {
            if (deltaY == -1) {
                return "upleft";
            } else if (deltaY == 0) {
                return "up";
            } else if (deltaY == 1) {
                return "upright";
            }
        } else if (deltaX == 0) {
            if (deltaY == -1) {
                return "left";
            } else if (deltaY == 1) {
                return "right";
            }
        } else if (deltaX == 1) {
            if (deltaY == -1) {
                return "downleft";
            } else if (deltaY == 0) {
                return "down";
            } else if (deltaY == 1) {
                return "downright";
            }
        }
        return "invalid";
    }
}
