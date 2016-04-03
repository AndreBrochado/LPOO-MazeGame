package maze.logic;

import java.util.Arrays;

/**
 *
 * Represents the Game Objects such as empty spaces, walls, exit, sword.
 * It's a derived class of GameObject
 *
 * @author Andre Reis and Vasco Ribeiro
 *
 */
public class GameObject {

    final static int VISIBLE = 0, INVISIBLE = 1;

    protected Coordinate position;
    protected int state = 0;
    protected boolean impassable = false, equipable = false;
    protected char[] representations;

    /**
     *
     * Constructs and initializes values by defaults.
     *
     */

    //base constructor
    public GameObject() {
        this.position = new Coordinate();
        char[] initializer = {' ', ' '};
        this.representations = new char[initializer.length];
        System.arraycopy(initializer, 0, this.representations, 0, initializer.length);
    }

    /**
     *
     * Constructs and initializes at a specified coord and with a specified representation.
     *
     * @param x
     *          X coord
     * @param y
     *          Y coord
     * @param representations
     *          Array with char representation
     *
     */

    public GameObject(int x, int y, char[] representations) {
        this.position = new Coordinate(x, y);
        this.representations = new char[representations.length];
        System.arraycopy(representations, 0, this.representations, 0, representations.length);
    }

    /**
     *
     * Constructs and initializes at a specified coord representation and state.
     *
     * @param x
     *          X coord
     * @param y
     *          Y coord
     * @param representations
     *          Array with char representation
     *
     * @param impassable
     *          True if the object is impassable
     *
     * @param equipable
     *          True if the object is equipable
     *
     */
    public GameObject(int x, int y, char[] representations, boolean impassable, boolean equipable) {
        this(x, y, representations);
        this.impassable = impassable;
        this.equipable = equipable;
    }

    /**
     *
     * Returns the actual Representation
     *
     * @return Char representation.
     *
     */


    public char getRepresentation() {
        return representations[state];
    }

    /**
     *
     * Returns the Y coord.
     *
     * @return Y coord.
     *
     */

    public int getY() {
        return position.getY();
    }

    /**
     *
     * Returns the X coord.
     *
     * @return X coord.
     *
     */

    public int getX() {
        return position.getX();
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
        this.position.setX(x);
    }

    /**
     *
     * Set the Y coord to a specified integer.
     *
     * @param y
     *          New X coord
     *
     */

    public void setY(int y) {
        this.position.setY(y);
    }

    /**
     *
     * Return the object state
     *
     * @return actual state
     *
     */

    public int getState() {
        return state;
    }

    /**
     *
     * Set the state to a specified state
     *
     * @param state
     *          New state.
     *
     */

    public void setState(int state) {
        this.state = state;
    }

    /**
     *
     * Return the coordinate of the object
     *
     * @author Andre Reis
     * @author Vasco Ribeiro
     *
     */

    public Coordinate getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameObject that = (GameObject) o;

        return Arrays.equals(representations, that.representations);

    }
}
