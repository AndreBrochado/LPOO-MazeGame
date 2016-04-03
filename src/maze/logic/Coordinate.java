package maze.logic;

/**
 *
 * A coordinate representing a location of point in space, specified in integer precision.
 *
 * @author Andre Reis
 * @author Vasco Ribeiro
 *
 */

public class Coordinate {
    private int x, y;

    /**
     *
     * Constructs and initializes a coord at (-1,-1) of the coordinate space.
     *
     */

    public Coordinate() {
        this(-1, -1);
    }

    /**
     *
     * Constructs and initializes a coord at specified (X,Y) of the coordinate space.
     *
     * @param x
     *          X coord
     *
     * @param y
     *          Y coord
     *
     */

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * Returns the X coord.
     *
     * @return X coord.
     *
     */

    public int getX() {
        return x;
    }

    /**
     *
     * Set the X coord to a specified integer.
     *
     * @param x
     *          New X coord
     *
     */

    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the Y coord.
     *
     * @return Y coord.
     *
     */

    public int getY() {
        return y;
    }

    /**
     * Set the Y coord to a specified integer.
     *
     * @param y
     *          New Y coord
     *
     */

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Increment an integer on X coord.
     *
     * @param deltax
     *          The number that we will increment.
     *
     */

    public void incrementX(int deltax) {
        this.x += deltax;
    }

    /**
     * Increment an integer on Y coord.
     *
     * @param deltay
     *          The number that we will increment.
     *
     */

    public void incrementY(int deltay) {
        this.y += deltay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;


        // Equality check here.
        return x == that.x && y == that.y;

    }
}
