package maze.logic;

/**
 * Created by up201403057 on 09-03-2016.
 */

//this class facilitate access to the position of the GameObjects
public class Coordinate {
    private int x, y;

    public Coordinate(){
        this(-1, -1);
    }

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
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

    public void incrementX(int deltax) {
        this.x += deltax;
    }

    public void incrementY(int deltay) {
        this.y += deltay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        return x == that.x && y == that.y;

    }
}
